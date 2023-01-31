/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.awt.Dimension;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import tuxido.formato_usuario;
import tuxido.ifrmIncidentes;


/**
 *
 * @author VJ002349
 */
public class ifrmWPProduccion extends javax.swing.JInternalFrame {
    PrintService  printService = PrintServiceLookup.lookupDefaultPrintService();
    conexionWPPostgre cnx;
    String temp="";
    JDesktopPane panel1;
    private String sttsvq;
    private String fecha;
    private String sttspk;
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    private int fila;
    private String hora;
    private String orden;
    private String unidades;
    private String avance;
    private String unidadesOrd;
    private String avanceOrd;
    private String invoice;
    String estacion="PK WP";
    int band;
     
    /*-------------------------*/
    String MODELO="";
    String DESCRIPCION="";
    String TIPO="";
    String OPCION="";
    String TIPOOPCION="";
    String CODIGO="";
    String NOMBRE="";
    String MOTOR="";
    String STATUS="";
    String FRAME="";
    String VALIDA="";
    String INVOICE="";
    String ENTRADA="";
    String TMOTOR="";
    /**
     * Creates new form ifrmWPProduccion
     */
    public ifrmWPProduccion(JDesktopPane jDesktopPane1) {
        initComponents();
        panel1=jDesktopPane1;
        codigo.requestFocus();
        
    }
    
    public void conexion(){
        cnx=new conexionWPPostgre();
    }

    public void cerrarCon(){
        try {
            cnx.Conec.close();
        } catch (SQLException ex) {}
    }
    
    public void lectura(){
        
                            MODELO="";
                            DESCRIPCION="";
                            TIPO="";
                            OPCION="";
                            TIPOOPCION="";
                            CODIGO="";
                            NOMBRE="";
                            MOTOR="";
                            STATUS="";
                            FRAME="";
                            VALIDA="";
                            INVOICE="";
                            ENTRADA="";
                            TMOTOR="";
        try{
                conexion();
                cnx.consulta("select " +
                                "  A.MODELO," +
                                "  A.DESCRIPCION," +
                                "  A.TIPO," +
                                "  REPLACE(A.OPCION,'null', '')," +
                                "  TRIM(A.TIPO||REPLACE(A.OPCION,'null', ''))," +
                                "  B.CODIGO," +
                                "  B.NOMBRE," +
                                "  A.ENGINE," +
                                "  C.TMODELO,"+
                                "  A.STATUS,"+
                                "  A.FRAME,"+
                                "  'X' AS VALIDA "+
                                "from " +
                                "  WP_PRODUCCION A," +
                                "  WP_COLOR B, " +
                                "  WP_ORDEN_DETAIL C " +
                                "WHERE" +
                                "  A.frame='"+codigo.getText()+"' " +
                                "AND" +
                                "  A.MODELO = B.APLICA " +
                                "AND" +
                                "  A.COLOR = B.NOMBRE " +
                                "AND" + 
                                "    a.modelo = c.CODIGO_MODELO "+
                                "AND" +
                                "    a.orden=c.orden "
                                );
                while(cnx.reg.next()){
                    MODELO=cnx.reg.getString(1);
                    DESCRIPCION=cnx.reg.getString(2);
                    TIPO=cnx.reg.getString(3);
                    OPCION=cnx.reg.getString(4);
                    TIPOOPCION=cnx.reg.getString(5);
                    CODIGO=cnx.reg.getString(6);
                    NOMBRE=cnx.reg.getString(7);
                    MOTOR=cnx.reg.getString(8);
                    TMOTOR=cnx.reg.getString(9);
                    STATUS=cnx.reg.getString(10);
                    FRAME=cnx.reg.getString(11);
                    VALIDA=cnx.reg.getString(12);
                    //INVOICE=cnx.reg.getString(12);
                    //ENTRADA=cnx.reg.getString(13);
                }    
                cerrarCon();
                
                    if("X".equals(VALIDA)){
                        validaplan(MODELO,TIPOOPCION.trim());
                        if(band==1){
                            
                            if("Pendiente".equals(STATUS)){
                                cmodelo.setText("");
                                desc.setText("");
                                tipo.setText("");
                                opcion.setText("");
                                color.setText("");
                                pref.setText("");
                                motor.setText("");
                                frame.setText("");
                                empaque.setText("");

                                cmodelo.setText(MODELO);
                                desc.setText(DESCRIPCION);
                                tipo.setText(TIPO);
                                opcion.setText(OPCION);
                                color.setText(NOMBRE);
                                pref.setText(MOTOR);
                                motor.setText("");
                                frame.setText(FRAME);
                                empaque.setText("");
                                invoice=INVOICE;
                                //btnG.setEnabled(true);
                                //btnRP.setEnabled(false);
                                //btnI.setEnabled(true);
                                motor.requestFocus();
                                motor.setEditable(true);
                                btnValidar.setEnabled(true);
                            }else{
                                JOptionPane.showMessageDialog(null,"El codigo de barras ya fue Registrado en esta Estacion.");
                                btnG.setEnabled(false);
                                btnI.setEnabled(false);
                                btnRP.setEnabled(false);
                                btnValidar.setEnabled(false);
                                cmodelo.setText("");
                                desc.setText("");
                                tipo.setText("");
                                opcion.setText("");
                                color.setText("");
                                pref.setText("");
                                motor.setText("");
                                frame.setText("");
                                empaque.setText("");
                                codigo.requestFocus();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"No existe plan de produccion para esta Unidad.");
                            btnG.setEnabled(false);
                            btnI.setEnabled(false);
                            btnRP.setEnabled(false);
                            btnValidar.setEnabled(false);
                        
                            cmodelo.setText("");
                            desc.setText("");
                            tipo.setText("");
                            opcion.setText("");
                            color.setText("");
                            pref.setText("");
                            motor.setText("");
                            frame.setText("");
                            empaque.setText("");
                            codigo.requestFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Unidad no disponible.");
                        btnG.setEnabled(false);
                        btnI.setEnabled(false);
                        btnRP.setEnabled(false);
                        btnValidar.setEnabled(false);
                        
                            cmodelo.setText("");
                            desc.setText("");
                            tipo.setText("");
                            opcion.setText("");
                            color.setText("");
                            pref.setText("");
                            motor.setText("");
                            frame.setText("");
                            empaque.setText("");
                            codigo.requestFocus();
                    }
                
                if(band==1){
                    conexion();
                    empaque.setText("");
                    temp="";
                    cnx.consulta("select ACT_SERIE_EMP from WP_MODELO WHERE CODIGO_MODELO='"+cmodelo.getText()+"' and STATUS='Activo'");
                    while(cnx.reg.next()){
                        if(cnx.reg.getString(1).length()<6){
                            for(int z=0;z<(6-cnx.reg.getString(1).length());z++){
                                temp=temp+"0";
                            }
                        }
                        temp=temp+String.valueOf(cnx.reg.getString(1));
                        empaque.setText(temp);
                    }
                    cerrarCon();
                }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        
    }
    
    public void planesActivos(){
        conexion();
        cnx.consulta("SELECT "
                        + "     ID, "
                        + "     ORDEN,"
                        + "     CODIGO_MODELO,"
                        + "     DESCRIPCION,"
                        + "     TIPO,"
                        + "     DESC_COLOR,"
                        + "     UNIDADES,"
                        + "     AVANCE,"
                        + "     STATUS"
                        + " FROM"
                        + "  WP_ORDEN_DETAIL"
                        + " WHERE STATUS='Activo'"
                        + "  ORDER BY 8 DESC");
        
                        modelo = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modelo);
                        jTable1.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable1.setModel(modelo);
                        //Agregamos las columnas al nuevo modelo
                        
                        modelo.addColumn("ID");
                        modelo.addColumn("Orden");
                        modelo.addColumn("Modelo");
                        modelo.addColumn("Descripcion");
                        modelo.addColumn("Tipo");
                        modelo.addColumn("Desc. Color");
                        modelo.addColumn("Plan");
                        modelo.addColumn("Avance");
                        modelo.addColumn("Status");
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(180);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(60);
                        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
                        jTable1.getColumnModel().getColumn(6).setPreferredWidth(60);
                        jTable1.getColumnModel().getColumn(7).setPreferredWidth(60);
                        jTable1.getColumnModel().getColumn(8).setPreferredWidth(80);

                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                for (int i=0;i<modelo.getColumnCount();i++){
                                     filasx[i] = cnx.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modelo.addRow(filasx);
                                    jTable1.setModel(modelo);
                                    jTable1.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,ex);
                        }
        cerrarCon();
    }
    
    public void empacados(){
        conexion();
        
            cnx.consulta("SELECT " +
                            "   ID," +
                            "   FRAME," +
                            "   ENGINE," +
                            "   MODELO," +
                            "   DESCRIPCION," +
                            "   TIPO," +
                            "   REPLACE(OPCION,'null', ' ')," +
                            "   COLOR," +
                            "   EMPAQUE," +
                            "   PK_STATUS," +
                            "   TO_CHAR(PK,'MM/DD/YYYY')," +
                            "   TO_CHAR(PK_TIME,'HH24:MI:SS')," +
                            "   ORDEN," +
                            "   STATUS " +
                            " FROM "
                          + "   WP_PRODUCCION" +
                            "  WHERE"
                          + " PK_STATUS='Liberado' AND "
                          + "   STATUS='Pendiente' ORDER BY 1 DESC");
        
                        modelo = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modelo);
                        jTable3.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable3.setModel(modelo);
                        //Agregamos las columnas al nuevo modelo                       
                        modelo.addColumn("ID");
                        modelo.addColumn("Frame");
                        modelo.addColumn("Engine");
                        modelo.addColumn("Modelo");
                        modelo.addColumn("Descripcion");
                        modelo.addColumn("Tipo");
                        modelo.addColumn("Opcion"); 
                        modelo.addColumn("Color"); 
                        modelo.addColumn("Empaque"); 
                        modelo.addColumn("Status Teminal"); 
                        modelo.addColumn("Fecha"); 
                        modelo.addColumn("Hora"); 
                        modelo.addColumn("Orden"); 
                        modelo.addColumn("Status");
                        
                        //Asiganomos un ancho de columnas por default
                        jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable3.getColumnModel().getColumn(1).setPreferredWidth(120);
                        jTable3.getColumnModel().getColumn(2).setPreferredWidth(140);
                        jTable3.getColumnModel().getColumn(3).setPreferredWidth(90);
                        jTable3.getColumnModel().getColumn(4).setPreferredWidth(180);
                        jTable3.getColumnModel().getColumn(5).setPreferredWidth(40);
                        jTable3.getColumnModel().getColumn(6).setPreferredWidth(60);
                        jTable3.getColumnModel().getColumn(7).setPreferredWidth(80);
                        jTable3.getColumnModel().getColumn(8).setPreferredWidth(90);
                        jTable3.getColumnModel().getColumn(9).setPreferredWidth(120);
                        jTable3.getColumnModel().getColumn(10).setPreferredWidth(90);
                        jTable3.getColumnModel().getColumn(11).setPreferredWidth(90);
                        jTable3.getColumnModel().getColumn(12).setPreferredWidth(80);
                        jTable3.getColumnModel().getColumn(13).setPreferredWidth(80);

                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                for (int i=0;i<modelo.getColumnCount();i++){
                                     filasx[i] = cnx.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modelo.addRow(filasx);
                                    jTable3.setModel(modelo);
                                    jTable3.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {}
                        
                        
            cnx.consulta("select "
                        + " MODELO, "
                        + " TIPO, "
                        + " REPLACE(OPCION,'null', ''), "
                        + " COUNT(*) AS UNIDADES "
                        + " from "
                        + "  WP_PRODUCCION "
                        + " WHERE PK=current_date"
                        + "  GROUP BY "
                        + "   MODELO, "
                        + "   TIPO, "
                        + " OPCION order by 1 asc");//jTable2
                        modelo = new DefaultTableModel(){
                                        @Override
                                        public boolean isCellEditable(int rowIndex, int vColIndex) {
                                            return false;
                                        }
                                    };
                                    ordenar = new TableRowSorter(modelo);
                                    jTable2.setRowSorter(ordenar);
                                    //Asignamos el nuevo Modelo
                                    jTable2.setModel(modelo);
                                    //Agregamos las columnas al nuevo modelo                       
                                    modelo.addColumn("Modelo");
                                    modelo.addColumn("Tipo");
                                    modelo.addColumn("Opcion");
                                    modelo.addColumn("Unidades");
                                    //Asiganomos un ancho de columnas por default
                                    jTable2.getColumnModel().getColumn(0).setPreferredWidth(80);
                                    jTable2.getColumnModel().getColumn(1).setPreferredWidth(50);
                                    jTable2.getColumnModel().getColumn(2).setPreferredWidth(50);
                                    jTable2.getColumnModel().getColumn(3).setPreferredWidth(70);
                                    //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                for (int i=0;i<modelo.getColumnCount();i++){
                                     filasx[i] = cnx.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modelo.addRow(filasx);
                                    jTable2.setModel(modelo);
                                    jTable2.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {}
        cerrarCon();
    }
    
    public void imprimir(){
        try {
            Date fechaActual=new Date();
            SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
            fecha=forFecha.format(fechaActual);
            
            String comando ="^XA"
                            +"^FO50,10^A@N,15,15,R:ARIAL.TTF^FDINFO. EMPAQUE:^FS"
                            +"^FO50,30^A@N,15,15,B:ARIAL.TTF^FDMODELO:^FS"
                            +"^FO220,30^A@N,15,15,B:ARIAL.TTF^FD"+cmodelo.getText()+"^FS"
                            +"^FO370,30^A@N,15,15,B:ARIAL.TTF^FDTIPO:^FS"
                            +"^FO520,30^A@N,15,15,B:ARIAL.TTF^FD"+tipo.getText()+opcion.getText()+"^FS"
                            +"^FO50,50^A@N,15,15,E:ARIAL.TTF^FDMOTOR:^FS"
                            +"^FO220,50^A@N,15,15,B:ARIAL.TTF^FD"+motor.getText()+"^FS"
                            +"^FO50,70^A@N,15,15,B:ARIAL.TTF^FDCOLOR:^FS"
                            +"^FO220,70^A@N,15,15,B:ARIAL.TTF^FD"+color.getText()+"^FS"
                            +"^FO50,90^A@N,15,15,B:ARIAL.TTF^FDDESTINO:Mexico^FS"
                            +"^FO220,90^A@N,15,15,B:ARIAL.TTF^FD^FS"
                            +"^FO370,70^A@N,15,15,B:ARIAL.TTF^FDFECHA:^FS"
                            +"^FO520,70^A@N,15,15,B:ARIAL.TTF^FD"+fecha+"^FS"
                            +"^FO370,90^A@N,15,15,B:ARIAL.TTF^FDEMPAQUE:^FS"
                            +"^FO520,90^A@N,15,15,B:ARIAL.TTF^FD"+empaque.getText()+"^FS"
                            +"^FO50,110^B3N,N,50,Y^FD"+frame.getText()+"^FS"
                            +"^XZ";
            
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(comando.getBytes(), flavor, null);
            DocPrintJob docPrintJob =  printService.createPrintJob();
            
                docPrintJob.print(doc, null);
            
        } catch (PrintException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        
    }
    
    public void reimprimir(){
         try {
//            Date fechaActual=new Date();
//            SimpleDateFormat forFecha=new SimpleDateFormat("dd-MM-yyyy");
//            fecha=forFecha.format(fechaActual);
            String tipop;
            String opc;
            String emp=jTable3.getValueAt(fila, 8).toString();
            String mod=jTable3.getValueAt(fila, 3).toString();
            String mtr=jTable3.getValueAt(fila, 2).toString();
            String clr=jTable3.getValueAt(fila, 7).toString();
            String tip=jTable3.getValueAt(fila, 5).toString();
            //String opc=jTable3.getValueAt(fila, 6).toString();
            if (jTable3.getValueAt(fila, 6).toString().length()<1){
                tipop=tip;
            }else{
                opc=jTable3.getValueAt(fila, 6).toString();
                tipop=tip+opc;
            }
            String fch=jTable3.getValueAt(fila, 10).toString();
            String frm=jTable3.getValueAt(fila, 1).toString();
            
            String comando ="^XA"
                            +"^FO50,10^A@N,15,15,R:ARIAL.TTF^FDINFO. EMPAQUE:^FS"
                            +"^FO50,30^A@N,15,15,B:ARIAL.TTF^FDMODELO:^FS"
                            +"^FO210,30^A@N,15,15,B:ARIAL.TTF^FD"+mod+"^FS"
                            +"^FO370,30^A@N,15,15,B:ARIAL.TTF^FDTIPO:^FS"
                            +"^FO520,30^A@N,15,15,B:ARIAL.TTF^FD"+tipop+"^FS"
                            +"^FO50,50^A@N,15,15,E:ARIAL.TTF^FDMOTOR:^FS"
                            +"^FO210,50^A@N,15,15,B:ARIAL.TTF^FD"+mtr+"^FS"
                            +"^FO50,70^A@N,15,15,B:ARIAL.TTF^FDCOLOR:^FS"
                            +"^FO210,70^A@N,15,15,B:ARIAL.TTF^FD"+clr+"^FS"
                            +"^FO50,90^A@N,15,15,B:ARIAL.TTF^FDDESTINO:^FS"
                            +"^FO210,90^A@N,15,15,B:ARIAL.TTF^FDMexico^FS"
                            +"^FO370,70^A@N,15,15,B:ARIAL.TTF^FDFECHA:^FS"
                            +"^FO520,70^A@N,15,15,B:ARIAL.TTF^FD"+fch+"^FS"
                            +"^FO370,90^A@N,15,15,B:ARIAL.TTF^FDEMPAQUE:^FS"
                            +"^FO520,90^A@N,15,15,B:ARIAL.TTF^FD"+emp+"^FS"
                            +"^FO50,110^B3N,N,50,Y^FD"+frm+"^FS"
                            +"^XZ";
            
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(comando.getBytes(), flavor, null);
            DocPrintJob docPrintJob =  printService.createPrintJob();
            docPrintJob.print(doc, null);
            
        } catch (PrintException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    public void guardar(){
        conexion();
        btnG.setEnabled(false);
        btnI.setEnabled(true);
        
        Date fechaActual=new Date();
        SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
        fecha=forFecha.format(fechaActual);
        hora=forHora.format(fechaActual);
        
        cnx.consulta("SELECT * FROM ("
                        + " SELECT "
                        + " A.ID, A.ORDEN, A.UNIDADES AS UNIDADES_DETALLES, B.UNIDADES AS UNIDADES_ORDEN, A.AVANCE AS AVANCE_DETALLES, B.AVANCE AS AVANCE_ORDEN "
                        + " FROM "
                        + " WP_ORDEN_DETAIL A,"
                        + " WP_ORDEN B"
                        + " WHERE "
                        + " A.CODIGO_MODELO='"+cmodelo.getText()+"' "
                        + " AND" +
                        "       A.MOTOR='"+pref.getText()+"' " +
                        "   AND" +
                        "       A.STATUS='Activo' "
                        + " AND" +
                        "       A.ORDEN=B.ID "
                        + " AND" +
                        "     A.TIPO='"+TIPOOPCION.trim()+"' "
                        + " ORDER BY ID ASC " +
                        " ) as s LIMIT 1");
                        try {
                            while(cnx.reg.next()){
                                orden=cnx.reg.getString(2);
                                unidades=cnx.reg.getString(3);
                                unidadesOrd=cnx.reg.getString(4);
                                avance=cnx.reg.getString(5);
                                avanceOrd=cnx.reg.getString(6);
                            }
                        } catch (SQLException ex) {
                        }       
        
//        cnx.ejecutar("INSERT INTO WP_PRODUCCION (FRAME,ENGINE,MODELO,DESCRIPCION,TIPO,OPCION,COLOR,EMPAQUE,ORDEN,ENTRADA,INVOICE,STATUS) " +
//                     "values('"+frame.getText()+"','"+motor.getText()+"','"+cmodelo.getText()+"','"+desc.getText()+"','"+tipo.getText()+"',"
//                   + "'"+opcion.getText()+"','"+color.getText()+"','"+empaque.getText()+"','"+orden+"','"+ENTRADA+"','"+invoice+"','Pendiente')");
        
        cnx.ejecutar("update WP_PRODUCCION SET ENGINE='"+motor.getText().toUpperCase()+"' where frame='"+frame.getText().toUpperCase()+"'");
        cnx.ejecutar("update WP_PRODUCCION SET pk='"+fecha+"' where frame='"+frame.getText().toUpperCase()+"'");
        cnx.ejecutar("update WP_PRODUCCION SET pk_time='"+hora+"' where frame='"+frame.getText().toUpperCase()+"'");
        cnx.ejecutar("update WP_PRODUCCION SET pk_status='Liberado' where frame='"+frame.getText().toUpperCase()+"'");
        
        cnx.ejecutar("update WP_ORDEN SET AVANCE='"+(Integer.parseInt(avanceOrd)+1)+"' where ID='"+orden+"'");
        
        //System.out.println("update WP_ORDEN_DETAIL SET AVANCE='"+(Integer.parseInt(avance)+1)+"' "
        //           + "where ORDEN='"+orden+"' AND CODIGO_MODELO='"+cmodelo.getText()+"' AND MOTOR='"+pref.getText()+"' AND TIPO='"+TIPOOPCION.trim()+"' and STATUS='Activo'");
        
        cnx.ejecutar("update WP_ORDEN_DETAIL SET AVANCE='"+(Integer.parseInt(avance)+1)+"' "
                   + "where ORDEN='"+orden+"' AND CODIGO_MODELO='"+cmodelo.getText()+"' AND MOTOR='"+pref.getText()+"' AND TIPO='"+TIPOOPCION.trim()+"' and STATUS='Activo'");
        if((Integer.parseInt(unidades))==(Integer.parseInt(avance)+1)){
            cnx.ejecutar("update WP_ORDEN_DETAIL SET STATUS='Cerrado' where orden='"+orden+"' and CODIGO_MODELO='"+cmodelo.getText()+"' and MOTOR='"+pref.getText()+"' AND TIPO='"+TIPOOPCION.trim()+"' and STATUS='Activo'");
        }
        if((Integer.parseInt(unidadesOrd))==(Integer.parseInt(avanceOrd)+1)){
            cnx.ejecutar("update WP_ORDEN SET STATUS='Cerrado', STATUS_PRODUCCION='Cerrado' where id='"+orden+"'");
        }
        cnx.ejecutar("update WP_MODELO SET ACT_SERIE_EMP='"+(Integer.parseInt(empaque.getText())+1)+"' where codigo_modelo='"+cmodelo.getText()+"'");
        cnx.ejecutar("update WP_PRODUCCION SET EMPAQUE='"+empaque.getText()+"' where FRAME='"+frame.getText().toUpperCase()+"'");
        cnx.ejecutar("update WP_INVENTARIO SET STATUS='Terminada' where engine='"+motor.getText().toUpperCase()+"'");
        cnx.ejecutar("update WP_INVENTARIO SET ORDEN='"+orden+"' where engine='"+motor.getText().toUpperCase()+"'");
        cnx.ejecutar("update WP_INVENTARIO SET FRAME='"+frame.getText()+"' where engine='"+motor.getText().toUpperCase()+"'");
        
        for(int x=0;x<2;x++){
            imprimir();
        }
        
        cmodelo.setText("");
        desc.setText("");
        tipo.setText("");
        opcion.setText("");
        color.setText("");
        pref.setText("");
        motor.setText("");
        frame.setText("");
        empaque.setText("");
        cerrarCon();
    }
    
    public void validaMotor(){
        conexion();
        try{
            cnx.consulta("SELECT 'EXISTE' AS VALIDA, FRAME, ENGINE, STATUS, TIPO||OPCION AS TMOTOR FROM WP_INVENTARIO WHERE ENGINE='"+motor.getText()+"'");
                while(cnx.reg.next()){
                    if(cnx.reg.getString(1).equals("EXISTE")){
                    if(cnx.reg.getString(4).equals("Pendiente")){
                        if(cnx.reg.getString(5).equals(TMOTOR)){
                            motor.setEditable(false);
                            btnG.setEnabled(true);
                            btnI.setEnabled(false);
                            btnRP.setEnabled(false);
                            btnValidar.setEnabled(false);
                        }else{
                            motor.setText("");
                            motor.requestFocus();
                            JOptionPane.showMessageDialog(null,"Este tipo de motor no corresponde al modelo de la Motobomba.");
                        }
                    }else{
                        motor.setText("");
                        motor.requestFocus();
                        JOptionPane.showMessageDialog(null,"El numero de motor ya fue asigando a otra unidad .");
                    }
                }else{
                        motor.setText("");
                        motor.requestFocus();
                        JOptionPane.showMessageDialog(null,"El Motor no existe, favor de validar con el Administrador.");
                    }
                }
                
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        cerrarCon();
    }
    
    public void validaplan(String mod, String tip){
        band=0;
        conexion();
        try{
            //System.out.println("SELECT 'X' FROM WP_ORDEN_DETAIL WHERE CODIGO_MODELO='"+mod+"' AND TIPO='"+tip+"' AND STATUS='Activo'");
            cnx.consulta("SELECT 'X' FROM WP_ORDEN_DETAIL WHERE CODIGO_MODELO='"+mod+"' AND TIPO='"+tip+"' AND STATUS='Activo'");
            while (cnx.reg.next()){
                if(cnx.reg.getString(1).equals("X")){
                    band=1;
                }else{
                    band=0;
                }
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        cerrarCon();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tipo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pref = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        motor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        color = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmodelo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        opcion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        frame = new javax.swing.JTextField();
        btnValidar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        empaque = new javax.swing.JTextField();
        btnG = new javax.swing.JButton();
        btnRP = new javax.swing.JButton();
        btnI = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jToolBar3 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        codigo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        jMenuItem1.setText("Re-Impresion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Tuxedo - WaterPump | Empaque");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Descripcion de Unidad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setText("Codigo Modelo:");

        tipo.setEditable(false);
        tipo.setBackground(new java.awt.Color(204, 204, 255));
        tipo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tipo.setFocusable(false);

        jLabel5.setText("Motor:");

        pref.setEditable(false);
        pref.setBackground(new java.awt.Color(204, 204, 255));
        pref.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pref.setFocusable(false);

        jLabel6.setText("Num. Motor:");

        motor.setBackground(new java.awt.Color(204, 204, 255));
        motor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setText("Color:");

        color.setEditable(false);
        color.setBackground(new java.awt.Color(204, 204, 255));
        color.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        color.setFocusable(false);

        jLabel3.setText("Tipo:");

        cmodelo.setEditable(false);
        cmodelo.setBackground(new java.awt.Color(204, 204, 255));
        cmodelo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        cmodelo.setFocusable(false);

        jLabel9.setText("Opcion:");

        opcion.setEditable(false);
        opcion.setBackground(new java.awt.Color(204, 204, 255));
        opcion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        opcion.setFocusable(false);

        jLabel2.setText("Descripcion:");

        desc.setEditable(false);
        desc.setBackground(new java.awt.Color(204, 204, 255));
        desc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        desc.setFocusable(false);

        frame.setEditable(false);
        frame.setBackground(new java.awt.Color(204, 204, 255));
        frame.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        frame.setFocusable(false);

        btnValidar.setText(" Validar");
        btnValidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(desc)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(frame))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pref, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(opcion, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(color)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(motor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnValidar)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(opcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(motor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnValidar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recepcion y Etiquetado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel7.setText("Num. Empaque:");

        empaque.setEditable(false);
        empaque.setBackground(new java.awt.Color(204, 204, 255));
        empaque.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk_black.png"))); // NOI18N
        btnG.setToolTipText("Guardar e Imprimir");
        btnG.setEnabled(false);
        btnG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGActionPerformed(evt);
            }
        });

        btnRP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_go.png"))); // NOI18N
        btnRP.setToolTipText("Re-Imprimir");
        btnRP.setEnabled(false);
        btnRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRPActionPerformed(evt);
            }
        });

        btnI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editar2.gif"))); // NOI18N
        btnI.setToolTipText("Incidencia");
        btnI.setEnabled(false);
        btnI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(empaque)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnG, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnI, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(empaque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnRP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnG, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnI, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Frame", "Engine", "Modelo", "Descripcion", "Tipo", "Opcion", "Color", "Empaque", "Status Teminal", "Fecha", "Hora", "Orden", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable3.setComponentPopupMenu(jPopupMenu1);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable3KeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(140);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(180);
            jTable3.getColumnModel().getColumn(5).setPreferredWidth(40);
            jTable3.getColumnModel().getColumn(6).setPreferredWidth(40);
            jTable3.getColumnModel().getColumn(7).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(8).setPreferredWidth(90);
            jTable3.getColumnModel().getColumn(9).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(10).setPreferredWidth(90);
            jTable3.getColumnModel().getColumn(11).setPreferredWidth(90);
            jTable3.getColumnModel().getColumn(12).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(13).setPreferredWidth(80);
        }

        jToolBar3.setFloatable(false);
        jToolBar3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton5.setToolTipText("Actualizar");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editar2.gif"))); // NOI18N
        jButton6.setToolTipText("Incidencia");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton6);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Orden", "Modelo", "Descripcion", "Tipo", "Desc. Color", "Plan", "Avance", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(4).setHeaderValue("Tipo");
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(5).setHeaderValue("Desc. Color");
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(6).setHeaderValue("Plan");
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(7).setHeaderValue("Avance");
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(8).setHeaderValue("Status");
        }

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Modelo", "Tipo", "Opcion", "Unidades"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1147, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Info.", jPanel2);

        codigo.setBackground(new java.awt.Color(204, 204, 255));
        codigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        jLabel8.setText("Num. Frame:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGActionPerformed
        // TODO add your handling code here:
            guardar();
            planesActivos();
            empacados();
        codigo.requestFocus();
    }//GEN-LAST:event_btnGActionPerformed

    private void btnRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRPActionPerformed
        // TODO add your handling code here:
        imprimir();
    }//GEN-LAST:event_btnRPActionPerformed

    private void btnIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIActionPerformed
        // TODO add your handling code here:
        ifrmIncidentes ifrmInc=new ifrmIncidentes(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmInc.getSize();
        panel1.add(ifrmInc);
        ifrmInc.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmInc.toFront();
        ifrmInc.show();
    }//GEN-LAST:event_btnIActionPerformed

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        // TODO add your handling code here:
        if(codigo.getText().length()==12){
            empaque.setText("");
            lectura();
            codigo.setText("");
            planesActivos();
            empacados();
        }
    }//GEN-LAST:event_codigoKeyReleased

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        fila=jTable3.getSelectedRow();
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyReleased
        // TODO add your handling code here:
        fila=jTable3.getSelectedRow();
    }//GEN-LAST:event_jTable3KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        planesActivos();
        empacados();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnValidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarActionPerformed
        // TODO add your handling code here:
        validaMotor();
    }//GEN-LAST:event_btnValidarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        ifrmIncidentes ifrmInc=new ifrmIncidentes(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmInc.getSize();
        panel1.add(ifrmInc);
        ifrmInc.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmInc.toFront();
        ifrmInc.show();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        reimprimir();
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnG;
    private javax.swing.JButton btnI;
    private javax.swing.JButton btnRP;
    private javax.swing.JButton btnValidar;
    private javax.swing.JTextField cmodelo;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField color;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField empaque;
    private javax.swing.JTextField frame;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JTextField motor;
    private javax.swing.JTextField opcion;
    private javax.swing.JTextField pref;
    private javax.swing.JTextField tipo;
    // End of variables declaration//GEN-END:variables
}
