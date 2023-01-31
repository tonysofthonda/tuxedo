/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author vj002349
 */
public class planAreaChart extends Thread{
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
    /**
     *
     * @param p
     */
    public planAreaChart(JPanel p){
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
                .addComponent(fxPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanelLayout.setVerticalGroup(
                jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fxPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    public AreaChart createChart() {
        AreaChart pc = null;
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Value");
        pc = new AreaChart<>(xAxis,yAxis);
        pc.setTitle("Seguimiento");
        pc.setPrefSize(524, 273);
        
        //Series 1
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Ensamble");
        
        series1.getData().add(new XYChart.Data("07:00 AM", 0));
        series1.getData().add(new XYChart.Data("08:00 AM", 20));
        series1.getData().add(new XYChart.Data("09:00 AM", 40));
        series1.getData().add(new XYChart.Data("10:00 AM", 60));
        series1.getData().add(new XYChart.Data("11:00 AM", 80));
        series1.getData().add(new XYChart.Data("12:00 AM", 100));
        series1.getData().add(new XYChart.Data("01:00 PM", 120));
        series1.getData().add(new XYChart.Data("02:00 PM", 140));
        series1.getData().add(new XYChart.Data("03:00 PM", 160));
        series1.getData().add(new XYChart.Data("04:00 PM", 160));
        series1.getData().add(new XYChart.Data("05:00 PM", 160));
        series1.getData().add(new XYChart.Data("06:00 PM", 160));
        
        //Series 2
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Calidad");
        
        series2.getData().add(new XYChart.Data("07:00 AM", 0));
        series2.getData().add(new XYChart.Data("08:00 AM", 28));
        series2.getData().add(new XYChart.Data("09:00 AM", 30));
        series2.getData().add(new XYChart.Data("10:00 AM", 50));
        series2.getData().add(new XYChart.Data("11:00 AM", 70));
        series2.getData().add(new XYChart.Data("12:00 AM", 90));
        series2.getData().add(new XYChart.Data("01:00 PM", 110));
        series2.getData().add(new XYChart.Data("02:00 PM", 135));
        series2.getData().add(new XYChart.Data("03:00 PM", 160));
        series2.getData().add(new XYChart.Data("04:00 PM", 160));
        series2.getData().add(new XYChart.Data("05:00 PM", 160));
        series2.getData().add(new XYChart.Data("06:00 PM", 160));
        
        //Series 3
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Empaque");
        
        series3.getData().add(new XYChart.Data("07:00 AM", 0));
        series3.getData().add(new XYChart.Data("08:00 AM", 10));
        series3.getData().add(new XYChart.Data("09:00 AM", 20));
        series3.getData().add(new XYChart.Data("10:00 AM", 40));
        series3.getData().add(new XYChart.Data("11:00 AM", 60));
        series3.getData().add(new XYChart.Data("12:00 AM", 80));
        series3.getData().add(new XYChart.Data("01:00 PM", 100));
        series3.getData().add(new XYChart.Data("02:00 PM", 120));
        series3.getData().add(new XYChart.Data("03:00 PM", 140));
        series3.getData().add(new XYChart.Data("04:00 PM", 160));
        series3.getData().add(new XYChart.Data("05:00 PM", 160));
        series3.getData().add(new XYChart.Data("06:00 PM", 160));
        
        pc.getData().addAll(series1, series2, series3);
        //pc.autosize();
        return pc;  
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
}
