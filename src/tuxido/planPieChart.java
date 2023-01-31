/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.swing.JPanel;

/**
 *
 * @author vj002349
 */
public class planPieChart extends Thread{
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
    public planPieChart(JPanel p){
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
    public PieChart createChart() {
        PieChart pc = null;
        label = new Label("");
        label.setTextFill(Color.DARKRED);
        label.setStyle("-fx-font: 18 arial;");
        pc = new PieChart(FXCollections.observableArrayList(
             new PieChart.Data(60+" CGL-150", 60),
             new PieChart.Data(60+" GL-125", 50),
             new PieChart.Data(50+" DIO", 50)
            ));
        for (final PieChart.Data data : pc.getData()) {
          data.getNode().addEventHandler(
		javafx.scene.input.MouseEvent.MOUSE_PRESSED,
		new EventHandler<javafx.scene.input.MouseEvent>() {
		  @Override
		  public void handle(MouseEvent e) {
                      tmp3=Math.round((((Double) data.getPieValue()*100)/(60+50+50))*100.0)/100.0;
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
        pc.setLegendSide(Side.LEFT);
        pc.setTitle("Produccion en Linea");
        pc.setPrefSize(524, 273);
        pc.setLabelsVisible(false);
        return pc;  
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
}
