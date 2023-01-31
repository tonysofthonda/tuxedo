/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javax.swing.JPanel;
import javafx.scene.chart.PieChart;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 *
 * @author vj002349
 */
public class weChart extends Thread{
    JPanel iframe;
    conexionPostgres cnx;
    Scene scene;
    Group root;
    JFXPanel fxPanel;
    int tmp1;
    int tmp2;
    String estacion;
    Double tmp3;
    ObservableList<PieChart.Data> data;
    ExecutorService executor;
    PieChart pc;
    
    /**
     *
     * @param p
     */
    public weChart(JPanel p){
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

        pc = new PieChart();
        estacion="Soldadura - Welding";
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(5000), 
            new EventHandler<javafx.event.ActionEvent>() {
                @Override public void handle(javafx.event.ActionEvent actionEvent) {
                    if(pc.getData().isEmpty()){
                            conexion();
                            try {
                                    //Soldadura
                                    cnx.consulta("select COUNT(*) from tx_orden_prod where we_status='Pendiente' AND STATUS='Pendiente'");
                                    while(cnx.reg.next()){
                                         pc.getData().add(new PieChart.Data("Pendientes", cnx.reg.getDouble(1)));
                                         tmp1=cnx.reg.getInt(1);
                                    }
                                    cnx.consulta("select COUNT(*) from tx_orden_prod where we_status='Liberado' AND STATUS='Pendiente'");
                                    while(cnx.reg.next()){
                                         pc.getData().add(new PieChart.Data("Liberados", cnx.reg.getDouble(1)));
                                         tmp2=cnx.reg.getInt(1);
                                    }  
                            } catch (SQLException ex) {}
                            desconexion();
                    }else{
                            conexion();
                            try {
                                //Soldadura
                                //pc.getData().remove(0);
                                //pc.getData().remove(0);
                                    cnx.consulta("select COUNT(*) from tx_orden_prod where we_status='Pendiente' AND STATUS='Pendiente'");
                                    while(cnx.reg.next()){
                                         //pc.getData().add(new PieChart.Data("Liberados", cnx.reg.getDouble(1)));
                                         pc.getData().get(0).setPieValue(cnx.reg.getDouble(1));
                                         tmp1=cnx.reg.getInt(1);
                                    }
                                    cnx.consulta("select COUNT(*) from tx_orden_prod where we_status='Liberado' AND STATUS='Pendiente'");
                                    while(cnx.reg.next()){
                                        //pc.getData().add(new PieChart.Data("Pendientes", cnx.reg.getDouble(1)));
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
