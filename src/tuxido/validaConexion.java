/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JLabel;
/**
 *
 * @author vj002349
 */
public class validaConexion extends Thread{
    conexionPostgres cnx;
    boolean band=true;
    JLabel l;
    JLabel l2;
    Connection Conec;
    String status;
   
    
    /**
     *
     * @param lbl
     */
    public validaConexion(JLabel lbl, JLabel lblError, String bs){
        this.l=lbl;
        this.l2=lblError;
        this.status=bs;
    }
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
    public void deterHilo(){
        band=false;
    }
    /**
     *
     */
    public void valida(){
        conexion();
        if(cnx.Conec != null){
            l.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ajax-loader2.gif")));
            desconexion();
            l.setToolTipText("Online");
            status="Online";
            l2.setText("");
        }else{
            status="Offline";
            l.setToolTipText("Offline");
            l.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ajax-loader.gif")));
            l2.setText(cnx.msg);
        }

    }
    @Override
    public void run(){
        while(band){
            valida();
            try{
                Thread.sleep(3000);
            }catch(InterruptedException ex){}
        }
    }
}
