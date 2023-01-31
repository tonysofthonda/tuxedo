/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javax.swing.JPanel;

/**
 *
 * @author vj002349
 */
public class shpChart extends Thread{
    JPanel iframe;
    conexionPostgres cnx;
    private Scene scene;
    private Group root;
    private Label label;
    private JFXPanel fxPanel;
    private int tmp1;
    private int tmp2;
    private String estacion;
    Double tmp3;
    private PieChart pc;
    /**
     *
     * @param p
     */
    public shpChart(JPanel p){
        iframe=p;   
    }
    /**
     *
     */
    public void initAndShowGUI() {
            fxPanel = new JFXPanel();
            fxPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(iframe);
            jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fxPanel)
                .addContainerGap())
            );
            jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fxPanel)
                .addContainerGap())
            );
            iframe.setLayout(jPanelLayout);
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
        estacion="Embarque - Shipping";
        pc = new PieChart();
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(5000), 
            new EventHandler<javafx.event.ActionEvent>() {
                @Override public void handle(javafx.event.ActionEvent actionEvent) {
                    Date fechaActual=new Date();
                    SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                    String fecha=forFecha.format(fechaActual);
                    if(pc.getData().isEmpty()){
                            conexion();
                            try {
                                    //Embarque
                                    cnx.consulta("select COUNT(*) from tx_orden_prod where shp_status='Pendiente' AND pk_status='Liberado' AND ID_ENVIO=0"); 
                                    while(cnx.reg.next()){
                                         pc.getData().add(new PieChart.Data("Pendientes", cnx.reg.getDouble(1)));
                                         tmp1=cnx.reg.getInt(1);
                                    }
                                    cnx.consulta("select COUNT(*) from tx_orden_prod where shp_status='Liberado' AND ID_ENVIO>0 AND SHP='"+fecha+"'");
                                    while(cnx.reg.next()){
                                         pc.getData().add(new PieChart.Data("Liberados", cnx.reg.getDouble(1)));
                                         tmp2=cnx.reg.getInt(1);
                                    }  
                            } catch (SQLException ex) {}
                            desconexion();
                    }else{
                            conexion();
                            try {
                                    cnx.consulta("select COUNT(*) from tx_orden_prod where shp_status='Pendiente' AND pk_status='Liberado' AND ID_ENVIO=0"); 
                                    while(cnx.reg.next()){
                                         pc.getData().get(0).setPieValue(cnx.reg.getDouble(1));
                                         tmp1=cnx.reg.getInt(1);
                                    }
                                    cnx.consulta("select COUNT(*) from tx_orden_prod where shp_status='Liberado' AND ID_ENVIO>0 AND SHP='"+fecha+"'");
                                    while(cnx.reg.next()){
                                        pc.getData().get(1).setPieValue(cnx.reg.getDouble(1));
                                        tmp2=cnx.reg.getInt(1);
                                    }  
                            } catch (SQLException ex) {}
                            desconexion();
                    }
                        for (final PieChart.Data datos : pc.getData()) {
                            tmp3=Math.round((((Double) datos.getPieValue()*100)/(tmp1+tmp2))*100.0)/100.0;
                            Tooltip.install(datos.getNode(), new Tooltip(
                            String.valueOf(datos.getPieValue()+" - "+tmp3+"%")));              
                        } 
            }
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
        // setup chart
        pc.setId("BasicPie");
        pc.setTitle(estacion);
        pc.setPrefSize(209, 247);
        pc.setLabelsVisible(false);
        return pc;  
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
}
