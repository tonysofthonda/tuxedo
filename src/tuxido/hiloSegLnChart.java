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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.swing.JPanel;

/**
 *
 * @author VJ002349
 */
public class hiloSegLnChart extends Thread{
    private Scene scene;
    private StackPane root;
    private conexionPostgres cnx;
    private JPanel iframe;
    private JFXPanel fxPanel;
    private SequentialTransition animation;
    private AreaChart<String, Number> lc;
    int tamReg = 0;
    int Yval = 0;
    String Xval = null;
    
    public hiloSegLnChart(JPanel p){
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
            //modeloTabla();
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
    
    public AreaChart createChart() {
        
        Date fechaActual=new Date();
        SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
        final String fecha=forFecha.format(fechaActual);
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hora de Registro");
        xAxis.setAutoRanging(true);
        yAxis.setLabel("Numero de Unidades");
        yAxis.setAutoRanging(true);
        lc = new AreaChart<String,Number>(xAxis,yAxis);
        //SERIES
        final XYChart.Series sAF = new XYChart.Series();
        sAF.setName("Ensamble");
        final XYChart.Series sVQ = new XYChart.Series();
        sVQ.setName("Calidad");
        //setStyle("-fx-font: 18 arial;"); 
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(5), 
            new EventHandler<javafx.event.ActionEvent>() {                
                    @Override 
                    public void handle(javafx.event.ActionEvent actionEvent) {
                    conexion();
                    try {
                        //Serie Ensamble
                        if(sAF.getData().isEmpty()){
                                            cnx.consulta("select VIN, TO_CHAR(AF_TIM,'HH24:MI:SS'), row_number() over() as ROWNUM, mod_desc, col_desc from( select VIN, AF_TIM, mod_desc, col_desc from TX_ORDEN_PROD WHERE af='"+fecha+"' ORDER BY AF_TIM ASC)"); 
                                            int nodo=0;
                                            while(cnx.reg.next()){
                                                 sAF.getData().add(new XYChart.Data(cnx.reg.getString(2), cnx.reg.getInt(3)));
                                                 XYChart.Data item = ( XYChart.Data)sAF.getData().get(nodo);
                                                 Tooltip.install(item.getNode(), new Tooltip(String.valueOf("Unidad No.: "+cnx.reg.getInt(3)+"\nHora: "+cnx.reg.getString(2)+"\nVIN: "+cnx.reg.getString(1)+"\nModelo: "+cnx.reg.getString(4)+"\nColor: "+cnx.reg.getString(5))));
                                                 nodo=nodo+1;
                                            }
                                           
                        }else{
                            cnx.consulta("select VIN, TO_CHAR(AF_TIM,'HH24:MI:SS'), row_number() over() as ROWNUM, mod_desc, col_desc from( select VIN, AF_TIM,mod_desc, col_desc from TX_ORDEN_PROD WHERE af='"+fecha+"' ORDER BY AF_TIM ASC)"); 
                            while(cnx.reg.next()){
                                  tamReg=cnx.reg.getRow();
                                  Xval=cnx.reg.getString(2); 
                                  Yval=cnx.reg.getInt(3);
                                if(tamReg>sAF.getData().size()){
                                    sAF.getData().add(new XYChart.Data(Xval, Yval));
                                    XYChart.Data item = ( XYChart.Data)sAF.getData().get(sAF.getData().size()-1);
                                    Tooltip.install(item.getNode(), new Tooltip(String.valueOf("Unidad No.: "+cnx.reg.getInt(3)+"\nHora: "+cnx.reg.getString(2)+"\nVIN: "+cnx.reg.getString(1)+"\nModelo: "+cnx.reg.getString(4)+"\nColor: "+cnx.reg.getString(5))));                                
                                }
                            }
                        }
                        //serie Calidad
                        if(sVQ.getData().isEmpty()){
                                            cnx.consulta("select VIN, TO_CHAR(VQ_TIM,'HH24:MI:SS'), row_number() over() as ROWNUM, mod_desc, col_desc from( select VIN, VQ_TIM, mod_desc, col_desc from TX_ORDEN_PROD WHERE VQ='"+fecha+"' ORDER BY VQ_TIM ASC)"); 
                                            int nodo=0;
                                            while(cnx.reg.next()){
                                                 sVQ.getData().add(new XYChart.Data(cnx.reg.getString(2), cnx.reg.getInt(3)));
                                                 XYChart.Data item = ( XYChart.Data)sVQ.getData().get(nodo);
                                                 Tooltip.install(item.getNode(), new Tooltip(String.valueOf("Unidad No.: "+cnx.reg.getInt(3)+"\nHora: "+cnx.reg.getString(2)+"\nVIN: "+cnx.reg.getString(1)+"\nModelo: "+cnx.reg.getString(4)+"\nColor: "+cnx.reg.getString(5))));
                                                 nodo=nodo+1;
                                            }
                                           
                        }else{
                            cnx.consulta("select VIN, TO_CHAR(VQ_TIM,'HH24:MI:SS'), row_number() over() as ROWNUM, mod_desc, col_desc from( select VIN, VQ_TIM, mod_desc, col_desc from TX_ORDEN_PROD WHERE VQ='"+fecha+"' ORDER BY VQ_TIM ASC)"); 
                            while(cnx.reg.next()){
                                  tamReg=cnx.reg.getRow();
                                  Xval=cnx.reg.getString(2); 
                                  Yval=cnx.reg.getInt(3);
                                if(tamReg>sVQ.getData().size()){
                                    sVQ.getData().add(new XYChart.Data(Xval, Yval));
                                    XYChart.Data item = ( XYChart.Data)sVQ.getData().get(sVQ.getData().size()-1);
                                    Tooltip.install(item.getNode(), new Tooltip(String.valueOf("Unidad No.: "+cnx.reg.getInt(3)+"\nHora: "+cnx.reg.getString(2)+"\nVIN: "+cnx.reg.getString(1)+"\nModelo: "+cnx.reg.getString(4)+"\nColor: "+cnx.reg.getString(5))));                                
                                }
                            }
                        }
                    } catch (SQLException ex) {}
                    desconexion();
                    lc.setTitle("Flujo en Linea de Produccion");
                    lc.getData().addAll(sAF, sVQ);
                    }
                    
        }));
        animation = new SequentialTransition();
        animation.getChildren().addAll(tl);
        animation.play();
        return lc;
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
    
}
