/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class hiloSegLnChartSHP extends Thread{
    XYChart.Series series;
    private final JPanel iframe;
    private JFXPanel fxPanel;
    private Scene scene;
    private StackPane root;
    private conexionPostgres cnx;
    AreaChart<String,Number> lc = null;
    private SequentialTransition animation;
    private String estacion;
    int tamReg = 0;
    int Yval = 0;
    String Xval = null;
    String[] vin;
    private DefaultTableModel modelo;
    private Object[] filasx;
    private Object[][] filas;
    private TableRowSorter ordenar;
    JTable t;
    String fecha;
    String queryt;
    //String queryl;
    
    /**
     *
     * @param p
     * @param tabla
     * @param ql
     * @param qt
     * @param e
     */
    public hiloSegLnChartSHP(JPanel p,JTable tabla, String qt, String e){
        iframe=p;
        queryt=qt;
        //queryl=ql;
        estacion=e;
        this.t=tabla;
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
            modeloTabla();
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
     */
    public void modeloTabla(){
      //Asignamos el nuevo Modelo  
      modelo = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int rowIndex, int vColIndex) {
             return false;
        }
      };
      ordenar = new TableRowSorter(modelo);
      t.setModel(modelo);
      t.setRowSorter(ordenar);
      //Agregamos las columnas al nuevo modelo
      modelo.addColumn("Envio");
      modelo.addColumn("Bitacora");
      modelo.addColumn("Transportista");
      modelo.addColumn("Contenedor");
      modelo.addColumn("Destino");
      modelo.addColumn("Fecha Registro"); 
      modelo.addColumn("Hora Registro");
      modelo.addColumn("Unidades");
      modelo.addColumn("Tarimas");
      modelo.addColumn("Status");
      //Asiganomos un ancho de columnas por default

      //asigno el tamaño del arreglo con la cantidad de columnas:
      filasx = new Object[modelo.getColumnCount()];

    }
    /**
     *
     */
    public void cargaTabla(){
        conexion();
                            int a =modelo.getRowCount()-1;
                            for(int i=a;i>=0;i--){  
                                modelo.removeRow(i);
                            }

                            cnx.consulta(queryt);
                            try {
                                //escribo las filas:
                                while (cnx.reg.next()){
                                    for (int i=0;i<modelo.getColumnCount();i++){
                                         filasx[i] = cnx.reg.getObject(i+1);
                                    }
                                    modelo.addRow(filasx);
                                }
                              t.setDefaultRenderer(Object.class, new formato_usuario());
                            } catch (SQLException ex) {}
        desconexion();        
    }
    /**
     *
     * @return
     */
    public AreaChart createChart() {
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hora de Registro");
        xAxis.setAutoRanging(true);
        yAxis.setLabel("Numero de Envios");
        yAxis.setAutoRanging(true);
        lc = new AreaChart<String,Number>(xAxis,yAxis);
        //setStyle("-fx-font: 18 arial;");
        //Series 
        series = new XYChart.Series();
        series.setName("Envios Registrados");
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(5), 
            new EventHandler<javafx.event.ActionEvent>() {
                
                    @Override 
                    public void handle(javafx.event.ActionEvent actionEvent) {

                    conexion();
                    try {
                        if(series.getData().isEmpty()){
                                            cnx.consulta(queryt); 
                                            while(cnx.reg.next()){
                                                 series.getData().add(new XYChart.Data(cnx.reg.getString(7), cnx.reg.getInt(1)));
                                                 XYChart.Data item = ( XYChart.Data)series.getData().get(series.getData().size()-1);
                                                 Tooltip.install(item.getNode(), new Tooltip(String.valueOf
                                                        ("Envio.: "+cnx.reg.getInt(1)+
                                                         "\nBitacora: "+cnx.reg.getString(2)+
                                                         "\nTransportista: "+cnx.reg.getString(3)+
                                                         "\nContenedor: "+cnx.reg.getString(4)+
                                                         "\nUnidades: "+cnx.reg.getString(8)+
                                                         "\nTarimas: "+cnx.reg.getString(9))
                                                        ));
                                            }
                        }else{
                            cnx.consulta(queryt); 
                            while(cnx.reg.next()){
                                  tamReg=cnx.reg.getRow();
                                  Xval=cnx.reg.getString(7); 
                                  Yval=cnx.reg.getInt(1);
                                if(tamReg>series.getData().size()){
                                    series.getData().add(new XYChart.Data(Xval, Yval));
                                    XYChart.Data item = ( XYChart.Data)series.getData().get(series.getData().size()-1);
                                    Tooltip.install(item.getNode(), new Tooltip(String.valueOf
                                            ("Envio.: "+cnx.reg.getInt(1)+
                                             "\nBitacora: "+cnx.reg.getString(2)+
                                             "\nTransportista: "+cnx.reg.getString(3)+
                                             "\nContenedor: "+cnx.reg.getString(4)+
                                             "\nUnidades: "+cnx.reg.getString(8)+
                                             "\nTarimas: "+cnx.reg.getString(9))
                                            ));                                
                                }
                            }
                        }    
                    } catch (SQLException ex) {}
                    desconexion();
                    cargaTabla();
                    }
        }));
        animation = new SequentialTransition();
        animation.getChildren().addAll(tl);
        animation.play();
        lc.setTitle(estacion);
        lc.getData().add(series);
        return lc;
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
}
