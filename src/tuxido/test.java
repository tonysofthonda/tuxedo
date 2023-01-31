/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

/**
 *
 * @author vj002349
 */
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author VJ002349
 */
public class test extends Thread {
    conexionPostgres cnx;
    JFXPanel fxPanel;
    JInternalFrame iframe;
    JDesktopPane panel;
    Scene  scene;
    Label label;
    Group  root;
    int tmp1=0;
    int tmp2=0;
    int tmp4=0;
    Double tmp3;
    String e="";
    String estacion="";
    
    /**
     *
     * @param panel3
     * @param est
     */
    public test(JDesktopPane panel3, String est){
        panel = panel3;
        e=est;
    }
    /**
     *
     */
    public void initAndShowGUI() {
        // This method is invoked on the EDT thread
        iframe= new JInternalFrame (); 
        fxPanel = new JFXPanel();
        iframe.add(fxPanel);
        iframe.setClosable(true);
        iframe.setResizable(true);
        iframe.setSize(500 , 450);
        Dimension desktopSize = panel.getSize();
        Dimension jInternalFrameSize = iframe.getSize();
        panel.add(iframe);
        iframe.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        iframe.show();
        iframe.toFront();
        
            Platform.setImplicitExit(false);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    initFX(fxPanel);
                }
            });
    }
    /**
     *
     * @param fxPanel
     */
    public void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        scene = createScene();
        fxPanel.setScene(scene);
    }
    /**
     *
     * @return
     */
    public Scene createScene() {
        root  =  new  Group();
        scene  =  new  Scene(root);
        root.getChildren().add(createChart());
        return (scene);
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
    /**
     *
     * @return
     */
    public PieChart createChart() {
        conexion();
        try{
        switch(e){
            
            case "we":
                estacion="Soldadura - Welding";
                cnx.consulta("select COUNT(*) from tx_orden_prod where "+e+"_status='Pendiente' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    tmp1=cnx.reg.getInt(1);
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where "+e+"_status='Liberado' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    tmp2=cnx.reg.getInt(1);
                }
            break;
            case "pa":
                estacion="Pintura - Painting";
                //Pintura
                cnx.consulta("select COUNT(*) from tx_orden_prod where pa_status='Pendiente' AND we_status='Liberado' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                     tmp1=cnx.reg.getInt(1);
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where pa_status='Liberado' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                     tmp2=cnx.reg.getInt(1);
                }
            break;
            case "af":
                estacion="Ensamble - Assemble";
                //Ensamble
                cnx.consulta("select COUNT(*) from tx_orden_prod where af_status='Pendiente' AND pa_status='Liberado' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                     tmp1=cnx.reg.getInt(1);
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where af_status='Liberado' AND  ID_ENVIO=0");
                while(cnx.reg.next()){
                     tmp2=cnx.reg.getInt(1);
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where af_status='Pendiente-c/Motor' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    tmp4=cnx.reg.getInt(1);
                }   
            break;
            case "vq":
                estacion="Calidad - Quality";
                //Calidad
                cnx.consulta("select COUNT(*) from tx_orden_prod where vq_status='Pendiente' AND af_status='Pendiente-c/Motor' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                     tmp1=cnx.reg.getInt(1);
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where vq_status='Liberado' AND  ID_ENVIO=0");
                while(cnx.reg.next()){
                     tmp2=cnx.reg.getInt(1);
                }
            break;    
            case "pk":
                estacion="Empaque - Packing";
                //Empaque
                cnx.consulta("select COUNT(*) from tx_orden_prod where pk_status='Pendiente' AND vq_status='Liberado' AND ID_ENVIO=0");
                while(cnx.reg.next()){
                      tmp1=cnx.reg.getInt(1);
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where pk_status='Liberado' AND  ID_ENVIO=0");
                while(cnx.reg.next()){
                      tmp2=cnx.reg.getInt(1);
                }
            break;
            case "shp":
                estacion="Embarque - Shipping";
                //Embarque
                cnx.consulta("select COUNT(*) from tx_orden_prod where shp_status='Pendiente' AND pk_status='Liberado' AND ID_ENVIO=0"); 
                while(cnx.reg.next()){
                       tmp1=cnx.reg.getInt(1);
                }
                Date fechaActual=new Date();
                SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                String fecha=forFecha.format(fechaActual);
                cnx.consulta("select COUNT(*) from tx_orden_prod where shp_status='Liberado' AND ID_ENVIO>0 AND SHP='"+fecha+"'");
                while(cnx.reg.next()){
                       tmp2=cnx.reg.getInt(1);
                }
            break;

        }
        } catch (SQLException ex) {}
        desconexion();
        PieChart pc = null;
        label = new Label("");
        label.setTextFill(Color.DARKRED);
        label.setStyle("-fx-font: 24 arial;");
        if(e.equals("af")){
            pc = new PieChart(FXCollections.observableArrayList(
                new PieChart.Data("Pendiente", tmp1),
                new PieChart.Data("Liberado", tmp2),
                new PieChart.Data("En Linea", tmp4)
            ));
        }else{
            pc = new PieChart(FXCollections.observableArrayList(
                new PieChart.Data("Pendiente", tmp1),
                new PieChart.Data("Liberado", tmp2)

            ));

        }
        
        for (final PieChart.Data data : pc.getData()) {
          data.getNode().addEventHandler(
		javafx.scene.input.MouseEvent.MOUSE_PRESSED,
		new EventHandler<javafx.scene.input.MouseEvent>() {
		  @Override
		  public void handle(MouseEvent e) {
                      if(e.equals("af")){
                        tmp3=Math.round((((Double) data.getPieValue()*100)/(tmp1+tmp2+tmp4))*100.0)/100.0;  
                      }else{
                        tmp3=Math.round((((Double) data.getPieValue()*100)/(tmp1+tmp2))*100.0)/100.0;
                      }
                        label.toFront();
                        label.setTranslateX(e.getSceneX());
                        label.setTranslateY(e.getSceneY());
                        label.setText(String.valueOf(data.getPieValue())+" - "+tmp3+"%");

		  }
		}
	  );
        }
        root.getChildren().add(label);
        
        // setup chart
        pc.setId("BasicPie");
        pc.setTitle(estacion);
        return pc;
    }    
    @Override
    public void run() {
       initAndShowGUI();
    }
}