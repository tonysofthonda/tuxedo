/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author vj002349
 */
public class generarTarjetaWP {
    conexionWPPostgre cnxO;

    public void conexion(){
            cnxO=new conexionWPPostgre();
        }
        /**
     *
     */
    public void desconexion(){
            try {
                cnxO.Conec.close();
            } catch (SQLException ex) {}
        }
        /**
     *
     * @param Orden
     * @param frm
     */
    public void conReporte(int Orden, String frm){
            conexion();        
            try{
                String archivo="C:\\Tuxedo\\rep\\WP\\tarjeta.jasper";
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
                        parametro.put("ORDEN",Orden);
                        parametro.put("FRAME",frm);
                    //Reporte Diseñado y compilado en iReport
                        JasperPrint jasperPrint=JasperFillManager.fillReport(masterReport,parametro,cnxO.Conec);
                        JasperPrintManager.printReport(jasperPrint, false);
                    }
            }catch(HeadlessException | JRException j){                
                if (j.getMessage().equals("Invalid page index range : 0 - -1 of 0")) {                    
                    JOptionPane.showMessageDialog(null,"No se encontró información");
                }else{
                    JOptionPane.showMessageDialog(null,"Mensaje de Error: "+j.getMessage());
                }                
            }
            desconexion();
    }
}
