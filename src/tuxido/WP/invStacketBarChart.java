/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javax.swing.JPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.CategoryAxisBuilder;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.NumberAxisBuilder;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Tooltip;


/**
 *
 * @author vj002349
 */
public class invStacketBarChart extends Thread{
    JPanel iframe;
    conexionWPPostgre cnx;
    Scene scene;
    Group root;
    JFXPanel fxPanel;
    StackedBarChart sbc;
    ObservableList<StackedBarChart.Series> barChartData;
    CategoryAxis xAxis;
    NumberAxis yAxis;
    
    public invStacketBarChart(JPanel p){
        iframe=p;   
    }
    
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
        cnx=new conexionWPPostgre();
        
    }
    /**
     *
     */
    public void desconexion(){
        try {
            cnx.Conec.close();
        } catch (SQLException ex) {}
    }
    
    public StackedBarChart createChart() {
        yAxis = NumberAxisBuilder.create()
                                       .label("% Avance")
                                       .lowerBound(0.0d)
                                       .upperBound(100.0d)
                                       .tickUnit(5.0d).build();
        String[] years={"TEST","TEST2","TEST3"};
        xAxis = CategoryAxisBuilder.create().categories(FXCollections.<String>observableArrayList(years)).build();
        barChartData = FXCollections.observableArrayList(
            new StackedBarChart.Series("Procesado", FXCollections.observableArrayList(
               new StackedBarChart.Data(years[0], 75),
               new StackedBarChart.Data(years[1], 30),
               new StackedBarChart.Data(years[2], 85.9)
            )),
            new StackedBarChart.Series("Pendiente", FXCollections.observableArrayList(
               new StackedBarChart.Data(years[0], 25),
               new StackedBarChart.Data(years[1], 70),
               new StackedBarChart.Data(years[2], 14.1)
            ))
        );
        sbc = new StackedBarChart(xAxis, yAxis, barChartData);
        return sbc; 
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
}
