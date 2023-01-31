/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;
/**
 *
 * @author VJ002349
 */
public class writeXML {
    String patch;
    /**
     *Ruta archivo
     */
    public String archivo;
    String fecha;
    String hora;
    List list;
    
    public writeXML(){
        patch=System.getProperty("user.dir")+"\\xml\\";
    }
    
    public void fecha(){
            Date fechaActual=new Date();
            SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
            SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
            fecha=forFecha.format(fechaActual);
            hora=forHora.format(fechaActual);
    }
    
    public void write(String e, String v){
        fecha();
        archivo=patch+"info"+e+fecha+".xml";
        File a=new File(archivo);
        if(a.exists()){
            try{
                SAXBuilder builder = new SAXBuilder();
                Document document = (Document) builder.build( a );
                Element rootNode = document.getRootElement();
                Element carga = rootNode.getChild("CARGA");
                Element unidad=new Element("UNIDAD");
                fecha();
                unidad.addContent(new Element("VIN").setText(v));
                unidad.addContent(new Element("FECHA").setText(fecha));
                unidad.addContent(new Element("HORA").setText(hora));
                carga.addContent(unidad);
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(document, new FileWriter(archivo));
            }catch ( IOException io ) {
                System.out.println( io.getMessage() );
            }catch ( JDOMException jdomex ) {
                System.out.println( jdomex.getMessage() );
            }
        }else{
            try{
                //RAIZ
                Element raiz = new Element("INFO");
                Document doc = new Document();
                doc.setRootElement(raiz);
                //FECHA
                Element fechaOffline = new Element("FECHA_OFFLINE");
                fechaOffline.setText(fecha);
                doc.getRootElement().addContent(fechaOffline);
                //HORA
                Element horaOffline= new Element("HORA_OFFLINE");
                horaOffline.setText(hora);
                doc.getRootElement().addContent(horaOffline);
                //TERMINAL
                Element terminal= new Element("TERMINAL");
                terminal.setText(e);
                doc.getRootElement().addContent(terminal);
                //CARGA
                Element carga=new Element("CARGA");
                Element unidad=new Element("UNIDAD");
                fecha();
                unidad.addContent(new Element("VIN").setText(v));
                unidad.addContent(new Element("FECHA").setText(fecha));
                unidad.addContent(new Element("HORA").setText(hora));
                carga.setAttribute(new Attribute("ID", "CARGA"));
                carga.addContent(unidad);
                doc.getRootElement().addContent(carga);
                //CREACION ARCHIVO XML
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(doc, new FileWriter(archivo));
            } catch (IOException io) {
                System.out.println(io.getMessage());
            }       
        }
    }
}
