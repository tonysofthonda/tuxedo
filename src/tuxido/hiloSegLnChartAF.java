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
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.swing.JPanel;

/**
 *
 * @author vj002349
 */
public class hiloSegLnChartAF extends Thread{
    XYChart.Series series;
    private final JPanel iframe;
    private JFXPanel fxPanel;
    private Scene scene;
    private StackPane root;
    private conexionPostgres cnx;
    AreaChart pc = null;
    private SequentialTransition animation;
    private String estacion;
    
    /**
     *
     * @param p
     */
    public hiloSegLnChartAF(JPanel p){
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
        root  =  new  StackPane();
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
    public AreaChart createChart() {
        estacion="Ensamble - Assemble";
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hora");
        xAxis.setAutoRanging(true);
        yAxis.setLabel("Unidades");
        yAxis.setAutoRanging(true);
        pc = new AreaChart(xAxis,yAxis);
        pc.setTitle("Seguimiento");
        //setStyle("-fx-font: 18 arial;");
        
        //Series 
        series = new AreaChart.Series<>();
        series.setName("Unidades Ensambladas");
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(5), 
            new EventHandler<javafx.event.ActionEvent>() {
                @Override public void handle(javafx.event.ActionEvent actionEvent) {
                    Date fechaActual=new Date();
                    SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                    String fecha=forFecha.format(fechaActual);
                    if(series.getData().isEmpty()==false){
                       pc.getData().removeAll();
                    }
                            conexion();
                            try {
                                    //Ensamble
                                    cnx.consulta("select TO_CHAR(AF_TIM,'HH24:MI:SS'), row_number() over() as ROWNUM from" +
                                                 "(" +
                                                 "select AF_TIM " +
                                                 "from TX_ORDEN_PROD WHERE af='"+fecha+"' " +
                                                 "ORDER BY AF_TIM ASC)"); 
                                    while(cnx.reg.next()){
                                         series.getData().add(new AreaChart.Data(cnx.reg.getString(1), cnx.reg.getInt(2)));
                                    } 
                            } catch (SQLException ex) {}
                            desconexion();
                                   
            }
        }));
        animation = new SequentialTransition();
        animation.getChildren().addAll(tl);
        animation.play();
        
        pc.setTitle(estacion);
        pc.getData().add(series);
        return pc;
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
}
