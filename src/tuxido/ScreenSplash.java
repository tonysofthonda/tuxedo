/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.*;
import java.awt.SplashScreen;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
/**
 *
 * @author vj002349
 */
public final class ScreenSplash {

  final SplashScreen splash ;
  //texto que se muestra a medida que se va cargando el screensplah
  final String[] texto = {"Iniciando" ,"Configuracion", "Librerias",
                          "files XYZ","forms","iconos","Propiedades",
                          "XML files", "JRXML files", "anonymous",
                          "Conexion Oracle" ,"Servidor Oracle","Run...!",
                          ""};

    /**
     *
     */
    public ScreenSplash() {
  splash = SplashScreen.getSplashScreen();
  }

    /**
     *
     */
    public void animar(){
        if (splash != null){
            Graphics2D g = splash.createGraphics();
            for(int i=1; i<texto.length; i++){                       
                //se pinta texto del array
                //g.setColor( new Color(4,52,101));//color de fondo
                //g.fillRect(34, 228,280,12);//para tapar texto anterior*/
                g.setColor(Color.white);//color de texto 
                //g.drawString("Loading "+texto[i-1]+"...", 34, 120);                
                //g.setColor(Color.green);//color de barra de progeso
                g.fillRect(33, 99,(i*307/texto.length), 2);//la barra de progreso
                //g.setColor(Color.GREEN);//color de barra de progeso
                //g.setColor( new Color(4,52,101));
                //se actualiza todo
                splash.update();
                try {
                 Thread.sleep(321);
                } catch(InterruptedException e) { }
            }
    splash.close();
 }
        //una vez terminada la animación se muestra la aplicación principal
        try {
                  try{
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
           JOptionPane.showMessageDialog(null,"Imposible cambiar el estilo Visual: "+e);
        }
        logon ver =new logon();
        ver.setVisible(true);
        ver.setLocationRelativeTo(null);
         }
 catch (Exception e) {
            System.out.println(e.getMessage());
        }
   }

}
