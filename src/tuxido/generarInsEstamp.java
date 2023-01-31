/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.JFrame;

/**
 *
 * @author vj002349
 */
public class generarInsEstamp {
    conexionPostgres cnx;
    /**
     *
     */
    public void conexion(){
        cnx=new conexionPostgres();
    }
    /**
     *
     */
    public void desconexion(){
        try {
            cnx.Conec.close();
        } catch (SQLException ex) {}
    }
    /**
     *
     * @param ordProd
     */
    public void generar(String ordProd){
        conexion();
        try{
                String archivo="C:\\Tuxedo\\rep\\instrEstamp.jasper";
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
                        parametro.put("ordProd",Integer.parseInt(ordProd));
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

