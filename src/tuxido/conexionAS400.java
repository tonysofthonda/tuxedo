/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author vj002349
 */
public class conexionAS400 {
     Connection Conec;
     Statement St;
     ResultSet reg;
//Metodo de Conexion para la Instancia AS400    
    /**
     *
     */
    public conexionAS400() {
           try{
                Class.forName("com.ibm.as400.access.AS400JDBCDriver");
                Conec = DriverManager.getConnection("jdbc:as400://207.130.116.47","SCM01","HONDA123");
            }catch(ClassNotFoundException | SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null,"problemas con la Conexion: " + ex.getMessage());
            }
    }
//Metodo de ejecucion de inserts,updates y deletes a la base de datos
    /**
     *
     * @param sql
     * @return
     */
    public String ejecutar(String sql){
        String error="";
        try{
            St=Conec.createStatement();
            St.executeUpdate(sql);
        }catch(Exception ex){
            error = ex.getMessage();
            JOptionPane.showMessageDialog(null,"Error en Ejecutar "+ex);
        }
        return(error);
    }
//Metodo para las consultas a la base de datos
    /**
     *
     * @param sql
     * @return
     */
    public ResultSet consulta(String sql){
        try{
            St=Conec.createStatement();
            reg=St.executeQuery(sql);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error en Consulta SELECT "+ex);
        }
        return(reg);
    }

}
