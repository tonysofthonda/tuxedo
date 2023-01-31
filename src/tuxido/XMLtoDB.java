/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.JDesktopPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
/**
 *
 * @author VJ002349
 */
public class XMLtoDB extends Thread{
    boolean band;
    conexionPostgres cnx;
    JTextArea areaTexto;
    String archivo;
    String logFile;
    String sqlFile;
    int opc=1;
    JDesktopPane panel1;
    ifrmXMLtoDB ifrmxml;
    String e;
    String fecha;
    String hora;
    BodyPart messageBodyPart;
    Multipart multipart;
    
    
    public XMLtoDB(JDesktopPane jDP1, String estacion, String f) {
       panel1=jDP1;
       e=estacion;
       archivo=f;
       band=true;
       fecha();
    }
    /**
     *
     */
    public void conexion(){
        cnx=new conexionPostgres();
    }
    
    public void logWindow(){
                //Internal Frame Carga 
                ifrmxml=new ifrmXMLtoDB();
                panel1.add(ifrmxml);
                ifrmxml.toBack();
                ifrmxml.show();
                opc=2;
    }
    
    public void closeWindow(){
                //Cerrar Internal Frame Carga
                ifrmxml.hide();
                //Detener Hilo
                band=false;
    }           
    
    public void fecha(){
            Date fechaActual=new Date();
            SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
            SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
            fecha=forFecha.format(fechaActual);
            hora=forHora.format(fechaActual);
    }
    
    public void lecturaArchivo(){
            ifrmxml.areaTexto.setText("");
            ifrmxml.areaTexto.append("TUXEDO\n"); 
            ifrmxml.areaTexto.append("Control de Linea de Produccion de Motocicletas\n");
            ifrmxml.areaTexto.append("Carga de Unidades Fuera de Linea\n");
            ifrmxml.areaTexto.append("Fecha y Hora de Inicio de Carga: "+hora+"\n");
            ifrmxml.areaTexto.append("\n");
            ifrmxml.areaTexto.append("------------------------------------------\n");
            ifrmxml.areaTexto.append("2-      Validando Archivo\n");
            File xmlFile = new File( archivo );
            if(xmlFile.exists()){
            ifrmxml.areaTexto.append("2.1-    Archivo Encontrado\n");
                opc=3;
            }else{
                ifrmxml.areaTexto.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
                ifrmxml.areaTexto.append(":: ERROR: No se encontro el Archivo:\n"+archivo);
                ifrmxml.areaTexto.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
                opc=4;
            }
    }
    
    public void importarDatosDB(){
        String stts = null;
        ifrmxml.areaTexto.append("------------------------------------------\n");
        ifrmxml.areaTexto.append("3-      Leyendo Archivo\n");
        ifrmxml.areaTexto.append("3.1-    Unidades Encontradas: ");
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(archivo);
        Element Unidades = null;
            try{
                Document document = (Document) builder.build( xmlFile );
                Element rootNode = document.getRootElement(); 
                Element carga = rootNode.getChild("CARGA");
                ifrmxml.areaTexto.append(String.valueOf(carga.getChildren().size())+"\n");
                ifrmxml.areaTexto.append("------------------------------------------\n");
                ifrmxml.areaTexto.append("3.2-    Creando Script de Carga\n");
                ifrmxml.areaTexto.append("Inicio de Generacion de Script: "+hora+"\n");
                ifrmxml.areaTexto.append("\n");
                try{
                    sqlFile=System.getProperty("user.dir")+"\\log\\log_"+e+"_"+fecha+".sql";
                    FileWriter fwr = new FileWriter(sqlFile);
                    BufferedWriter bw = new BufferedWriter(fwr);
                    PrintWriter wr = new PrintWriter(bw);
                    for ( int i = 0; i < carga.getChildren().size(); i++ ){
                        Unidades = (Element) carga.getChildren().get(i);    
                        wr.append("UPDATE TX_ORDEN_PROD SET "+e+"='"+Unidades.getChildTextTrim("FECHA")+"',"+e+"_TIM='"+Unidades.getChildTextTrim("HORA")+"', "+e+"_STATUS='Liberado' WHERE VIN='"+Unidades.getChildTextTrim("VIN")+"';\n");
                        ifrmxml.areaTexto.append("UPDATE TX_ORDEN_PROD SET "+e+"='"+Unidades.getChildTextTrim("FECHA")+"',"+e+"_TIM='"+Unidades.getChildTextTrim("HORA")+"', "+e+"_STATUS='Liberado' WHERE VIN='"+Unidades.getChildTextTrim("VIN")+"';\n");
                    }
                    wr.close();
                    bw.close();
                }catch(IOException e){
                    ifrmxml.areaTexto.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
                    ifrmxml.areaTexto.append(":: ERROR: En la escritura del SQL File:\n");
                    ifrmxml.areaTexto.append(":: "+e+"\n");
                    ifrmxml.areaTexto.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
                }
                ifrmxml.areaTexto.append("\n");
                ifrmxml.areaTexto.append("Fin de Generacion de Script: "+hora+"\n");
            ifrmxml.areaTexto.append("------------------------------------------\n");
                ifrmxml.areaTexto.append("3.3-    Cargando Unidades en BD\n");
                ifrmxml.areaTexto.append("Inicio de Carga en BD"+hora+"\n");
                ifrmxml.areaTexto.append("\n");
                conexion();
                if(cnx.Conec != null){
                    try {
                        for ( int i = 0; i < carga.getChildren().size(); i++ ){
                            Unidades = (Element) carga.getChildren().get(i);
                            cnx.consulta("select "+e+"_status from TX_ORDEN_PROD WHERE VIN='"+Unidades.getChildTextTrim("VIN")+"'");
                            while(cnx.reg.next()){
                                stts = cnx.reg.getString(1);
                            }
                            if("Pendiente".equals(stts)){
                                cnx.ejecutar("UPDATE TX_ORDEN_PROD SET "+e+"='"+Unidades.getChildTextTrim("FECHA")+"',"+e+"_TIM='"+Unidades.getChildTextTrim("HORA")+"', "+e+"_STATUS='Liberado' WHERE VIN='"+Unidades.getChildTextTrim("VIN")+"'");
                                ifrmxml.areaTexto.append("UPDATE TX_ORDEN_PROD SET "+e+"='"+Unidades.getChildTextTrim("FECHA")+"',"+e+"_TIM='"+Unidades.getChildTextTrim("HORA")+"', "+e+"_STATUS='Liberado' WHERE VIN='"+Unidades.getChildTextTrim("VIN")+"';\n");
                                ifrmxml.areaTexto.append("Unidad "+Unidades.getChildTextTrim("VIN")+" Cargada\n");
                            }else{
                                ifrmxml.areaTexto.append(" -ERROR: "+Unidades.getChildTextTrim("VIN")+" El codigo de barras ya fue Registrado en esta Estacion.\n");
                            }
                        }
                    } catch (SQLException ex) {
                        ifrmxml.areaTexto.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
                        ifrmxml.areaTexto.append(":: ERROR: En la carga de Informacion a BD:\n");
                        ifrmxml.areaTexto.append(":: "+ex.getMessage()+"\n");
                        ifrmxml.areaTexto.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
                    }
                }else{
                    ifrmxml.areaTexto.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
                    ifrmxml.areaTexto.append(":: ERROR: En la conexion BD:\n");
                    ifrmxml.areaTexto.append(":: -STATUS: "+cnx.Conec.toString()+"\n");
                    ifrmxml.areaTexto.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                }
                desconexion();
            }catch ( IOException io ) {
                ifrmxml.areaTexto.append( io.getMessage() + "\n");
            }catch ( JDOMException jdomex ) {
                ifrmxml.areaTexto.append( jdomex.getMessage() +"\n");
            }
        ifrmxml.areaTexto.append("\n");
        ifrmxml.areaTexto.append("Fin de Carga en BD"+hora+"\n");
        opc=4;
    }
    
    public void log(){
        ifrmxml.areaTexto.append("------------------------------------------\n");
        ifrmxml.areaTexto.append("4-    Creando Log\n");
        ifrmxml.areaTexto.append("Inicio Generacion de Log: "+hora+"\n");
        logFile=System.getProperty("user.dir")+"\\log\\log_"+e+"_"+fecha+".log";
        FileWriter fw = null;
        try {
            fw = new FileWriter(logFile,true);
            ifrmxml.areaTexto.write(fw);
            fw.close();
        } catch (IOException ex) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter wr = new PrintWriter(bw);
            wr.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
            wr.append(":: ERROR al escribir Log: \n");
            wr.append(":: "+ex.toString()+"\n");
            wr.append(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            wr.close();
        }
        ifrmxml.areaTexto.append("Fin Generacion de Log: "+hora+"\n");
        opc=5;
    }
    
    public void mail(){
      ifrmxml.areaTexto.append("5-    Envio Notificacion\n");
      // Recipient's email ID needs to be mentioned.
      String to = "jose_ramirez@hdm.honda.com";
      // Sender's email ID needs to be mentioned
      String from = "tuxedo@hdm.honda.com";
      // Assuming you are sending email from localhost
      String host = "HDMNOTES01";
      // Get system properties
      Properties properties = System.getProperties();
      // Setup mail server
      properties.setProperty("mail.smtp.host", host);
      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);
      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);
         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));
         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
         // Set Subject: header field
         message.setSubject("Notificacion de Carga Estacion: "+e);
         // Create the message part 
         messageBodyPart = new MimeBodyPart();
         // Fill the message
         messageBodyPart.setText(ifrmxml.areaTexto.getText());
         // Create a multipar message
         multipart = new MimeMultipart();
         // Set text message part
         multipart.addBodyPart(messageBodyPart);
         
            addAttachment(archivo);
            addAttachment(logFile);
            addAttachment(sqlFile);
         
         // Send the complete message parts
         message.setContent(multipart );
         // Send message
         Transport.send(message);
         ifrmxml.areaTexto.append("Envio de Notificación Satisfactorio");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
      opc=6;
    }
    
    public void addAttachment(String filename){
        try {
            MimeBodyPart mBP = new MimeBodyPart();
            FileDataSource source = new FileDataSource(filename);
            mBP.setDataHandler(new DataHandler(source));
            mBP.setFileName(filename);
            multipart.addBodyPart(mBP);
        } catch (MessagingException ex) {
            Logger.getLogger(XMLtoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *
     */
    public void desconexion(){
        try {
            cnx.Conec.close();
        } catch (SQLException ex) {}
    }
    
    public void run(){
        while(band){
            try {
                switch(opc){
                    case 1:
                        logWindow();
                        break;
                    case 2:
                        lecturaArchivo();
                        break;
                    case 3:
                        importarDatosDB();
                        break;
                    case 4:
                        log();
                        break;
                    case 5:
                        mail();
                        break;
                    case 6:
                        closeWindow();
                        break;
                }
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ifrmxml.areaTexto.append(ex.getMessage());
            }
        }
    }
}
