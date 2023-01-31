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
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.swing.JPanel;

/**
 *
 * @author vj002349
 */
public class hiloSegLnChartP extends Thread{
    JPanel iframeM;
    conexionPostgres cnx;
    private Scene scene;
    private StackPane root;
    private JFXPanel fxPanelM;
    private int tmp1;
    Double tmp3;
    private PieChart pc;
    int tamReg;
    String fecha;
    String query;
    /**
     *
     * @param pM
     * @param q
     */
    public hiloSegLnChartP(JPanel pM, String q){
        iframeM=pM;
        query=q;
        Date fechaActual=new Date();
        SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
        fecha=forFecha.format(fechaActual);
    }
    /**
     *
     */
    public void initAndShowGUI() {
            fxPanelM = new JFXPanel();
            fxPanelM.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(iframeM);
            jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fxPanelM)
                .addContainerGap())
            );
            jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fxPanelM)
                .addContainerGap())
            );
            iframeM.setLayout(jPanelLayout);
            Platform.setImplicitExit(false);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    initFXM(fxPanelM);
                }
            });
    }
    /**
     *
     * @param fxPanel
     */
    public void initFXM(JFXPanel fxPanel) {
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
    public PieChart createChart() {
        pc = new PieChart();
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(5000), 
            new EventHandler<javafx.event.ActionEvent>() {
                @Override public void handle(javafx.event.ActionEvent actionEvent) {
                    tmp1=0;
                    if(pc.getData().isEmpty()){
                            conexion();
                            try {
                                    cnx.consulta(query);
                                    while(cnx.reg.next()){
                                         pc.getData().add(new PieChart.Data(cnx.reg.getString(2), cnx.reg.getDouble(1)));
                                         tmp1=tmp1+cnx.reg.getInt(1);
                                    }  
                            } catch (SQLException ex) {}
                            desconexion();
                    }else{
                            conexion();
                            try {   
                                    cnx.consulta(query);
                                    while(cnx.reg.next()){
                                         tamReg=cnx.reg.getRow();
                                         tmp1=tmp1+cnx.reg.getInt(1);
                                         if(tamReg>pc.getData().size()){
                                             pc.getData().add(new PieChart.Data(cnx.reg.getString(2), cnx.reg.getDouble(1)));
                                         }else{
                                             pc.getData().get(tamReg-1).setPieValue(cnx.reg.getDouble(1));
                                         }
                                    }
                            } catch (SQLException ex) {}
                            desconexion();
                    }
                    for (final PieChart.Data datos : pc.getData()) {
                         tmp3=Math.round((((Double) datos.getPieValue()*100)/(tmp1))*100.0)/100.0;
                         Tooltip.install(datos.getNode(), new Tooltip(
                         String.valueOf(datos.getPieValue()+" - "+tmp3+"%")));              
                    } 
            }
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
        // setup chart
        pc.setId("BasicPie");
        //pc.setTitle(estacion);
        pc.setPrefSize(209, 247);
        pc.setLabelsVisible(false);
        pc.setLegendSide(Side.LEFT);
        return pc;  
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
}
