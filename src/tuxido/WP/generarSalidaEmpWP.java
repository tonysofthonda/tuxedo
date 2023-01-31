/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author vj002349
 */
public class generarSalidaEmpWP {
        conexionWPPostgre cnx;
    
    public void conexion(){
        cnx=new conexionWPPostgre();
    }
    /**
     *
     */
    public void desconexion(){
        try {
            cnx.Conec.close();
        } catch (SQLException ex) {}
    }
    
    public void generar(String numID){
        conexion();
        try{
                String archivo="C:\\Tuxedo\\rep\\WP\\embarque.jasper";
                    if(archivo==null){
                        JOptionPane.showMessageDialog(null,"No se encuentra el archivo");
                    }else{
                        JasperReport masterReport=null;
                        try{
                            masterReport=(JasperReport)JRLoader.loadObject(archivo);
                        }catch(JRException e){
                            JOptionPane.showMessageDialog(null,"Error cargando el reporte maestro:"+e.getMessage());
                        }
                    //Este es el Parametro
                        Map parametro=new HashMap();
                        parametro.put("numID",Integer.parseInt(numID));
                    //Reporte Diseñado y compilado en iReport
                        JasperPrint jasperPrint=JasperFillManager.fillReport(masterReport,parametro,cnx.Conec);
                        //JasperPrintManager.printReport(jasperPrint, false);
                    //Se lanza el Viewer de Jasper
                        JasperViewer jviewer=new JasperViewer(jasperPrint,false);
                        jviewer.setTitle("Impresion");
                        jviewer.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        jviewer.setVisible(true);
                    }
            }catch(Exception j){
                JOptionPane.showMessageDialog(null,"Mensaje de Error: "+j.getMessage());
            }
        desconexion();
    }
    
}
