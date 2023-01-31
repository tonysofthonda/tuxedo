/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 * @author vj002349
 **/
public class conexionPostgres {
     Connection Conec;
     Statement St;
     ResultSet reg;
     public String msg;
     
    public conexionPostgres() {
        try{
            Class.forName("org.postgresql.Driver");
            Conec = DriverManager.getConnection("jdbc:postgresql://thdmgvltestmdb1:5432/tuxprod","tuxhdm","t+lvo9I3oc=i");                                                                   
        }catch(ClassNotFoundException | SQLException ex){
            String Str = ex.toString();
            if(  Str.indexOf("ORA-125")!=-1
               ||Str.indexOf("ORA-126")!=-1
               ||Str.indexOf("ORA-12154")!=-1
               ||Str.indexOf("ORA-12170")!=-1     
               ||Str.indexOf("ORA-03113")!=-1
               ||Str.indexOf("ORA-01033")!=-1     
               ||Str.indexOf("ORA-00932")!=-1
               ||Str.indexOf("The Network Adapter could not establish the connection")!=-1
            ){
                msg=ex.getMessage();
            }else{
               JOptionPane.showMessageDialog(null,"Connection Error: " + ex.getMessage());
            }
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
        System.out.println("La consulta es: \n "+sql);
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
        System.out.println("La consulta es: \n "+sql);
        try{
            St=Conec.createStatement();
            reg=St.executeQuery(sql);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error en Consulta SELECT "+ex);
        }
        return(reg);
    }
    
}
