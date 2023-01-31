/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.io.File;
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
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author vj002349
 */
public class generarTarjeta {
    
    conexionPostgres cnxO;

    public void conexion() {
        cnxO = new conexionPostgres();
    }
    
    public void desconexion() {
        try {
            cnxO.Conec.close();
        } catch (SQLException ex) {
            
        }
    }

    /**
     *
     * @param vin
     */
    public void conReporte(String vin) {
        conexion();
        try {
            String archivo = "C:\\Tuxedo\\rep\\tarjeta.jasper";          
            if (archivo == null) {
                JOptionPane.showMessageDialog(null, "No se encuentra el archivo");
            } else {
                JasperReport masterReport = null;
                try {
                    masterReport = (JasperReport) JRLoader.loadObject(archivo);
                } catch (JRException e) {
                    JOptionPane.showMessageDialog(null, "Error cargando el reporte maestro:" + e.getMessage());
                }
                //Este es el Parametro
                Map parametro = new HashMap();
                parametro.put("VIN", vin);
                //Reporte Diseñado y compilado en iReport
                JasperPrint jasperPrint = JasperFillManager.fillReport(masterReport, parametro, cnxO.Conec);
                JasperPrintManager.printReport(jasperPrint, false);
            }
        } catch (Exception j) {
            JOptionPane.showMessageDialog(null, "Mensaje de Error: " + j.getMessage());
        }
        desconexion();
    }
}
