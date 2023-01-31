/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
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
public class hiloSegLnChartLn extends Thread{
    ScrollPane scroll;
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
    String queryl;
    Vector columnNames = new Vector();
    Vector data = new Vector();
    Vector row;
    ResultSetMetaData rsmd;
    int column; 
    /**
     *
     * @param p
     * @param tabla
     * @param ql
     * @param qt
     * @param e
     */
    public hiloSegLnChartLn(JPanel p,JTable tabla,String ql, String qt, String e){
        iframe=p;
        queryt=qt;
        queryl=ql;
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
        scroll = new ScrollPane();
        root  =  new  StackPane();
        scroll.setContent(root);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scene  =  new  Scene(scroll);
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
      //Agregamos las columnas al vector
      columnNames.addElement("Unidad");
      columnNames.addElement("VIN");
      columnNames.addElement("Modelo");
      columnNames.addElement("Color");
      columnNames.addElement("Año");
      columnNames.addElement("Fecha Registro"); 
      columnNames.addElement("Hora Registro");
      columnNames.addElement("Status Terminal");
      columnNames.addElement("Status Linea");
      columnNames.addElement("Empaque");
      columnNames.addElement("Tarima");
      columnNames.addElement("Contenedor");
      columnNames.addElement("Envio");
      //Asignamos el nuevo Modelo
      cargaTabla();
      modelo = new DefaultTableModel(data,columnNames){
      @Override
      public boolean isCellEditable(int rowIndex, int vColIndex) {
             return false;
        }
      };
      ordenar = new TableRowSorter(modelo);
      t.setModel(modelo);
      t.setRowSorter(ordenar);
      //Asiganomos un ancho de columnas por default
      t.getColumnModel().getColumn(0).setPreferredWidth(60);
      t.getColumnModel().getColumn(1).setPreferredWidth(140);
      t.getColumnModel().getColumn(5).setPreferredWidth(95);
      t.getColumnModel().getColumn(6).setPreferredWidth(90);
      t.getColumnModel().getColumn(7).setPreferredWidth(130);
      t.getColumnModel().getColumn(8).setPreferredWidth(90);
      t.getColumnModel().getColumn(11).setPreferredWidth(90);
    }
    /**
     *
     */
    public void cargaTabla(){
      //Limpiamos vactores
        data.clear();
        conexion();
        cnx.consulta(queryt);
        try {
            //escribo las filas:
            while (
                cnx.reg.next()
            ){
                column = columnNames.size();
                row = new Vector(column);
                for (int i=0;i<column;i++){
                    row.addElement(cnx.reg.getObject(i+1));
                }
                data.addElement(row);
            }
            t.setDefaultRenderer(Object.class, new formato_usuario());
        } catch (SQLException ex) {  }
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
        yAxis.setLabel("Numero de Unidades");
        yAxis.setAutoRanging(true);
        lc = new AreaChart<String,Number>(xAxis,yAxis);
        lc.setTitle("Seguimiento");
        series = new XYChart.Series();
        series.setName("Unidades Registradas");
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(5), 
            new EventHandler<javafx.event.ActionEvent>() {
                
                    @Override 
                    public void handle(javafx.event.ActionEvent actionEvent) {
                    conexion();
                        try {
                            if(series.getData().isEmpty()){
                                                cnx.consulta(queryl);
                                                int nodo=0;
                                                while(cnx.reg.next()){
                                                     series.getData().add(new XYChart.Data(cnx.reg.getString(2), cnx.reg.getInt(3)));
                                                     XYChart.Data item = ( XYChart.Data)series.getData().get(nodo);
                                                     Tooltip.install(item.getNode(), new Tooltip(String.valueOf("Unidad No.: "+cnx.reg.getInt(3)+"\nHora: "+cnx.reg.getString(2)+"\nVIN: "+cnx.reg.getString(1)+"\nModelo: "+cnx.reg.getString(4)+"\nColor: "+cnx.reg.getString(5))));
                                                     nodo=nodo+1;
                                                }
                            }else{
                                cnx.consulta(queryl); 
                                while(cnx.reg.next()){
                                      tamReg=cnx.reg.getRow();
                                      Xval=cnx.reg.getString(2); 
                                      Yval=cnx.reg.getInt(3);
                                    if(tamReg>series.getData().size()){
                                        series.getData().add(new XYChart.Data(Xval, Yval));
                                        XYChart.Data item = ( XYChart.Data)series.getData().get(series.getData().size()-1);
                                        Tooltip.install(item.getNode(), new Tooltip(String.valueOf("Unidad No.: "+cnx.reg.getInt(3)+"\nHora: "+cnx.reg.getString(2)+"\nVIN: "+cnx.reg.getString(1)+"\nModelo: "+cnx.reg.getString(4)+"\nColor: "+cnx.reg.getString(5))));                                
                                    }
                                }
                            }    
                        } catch (SQLException ex){
                            System.out.println(ex.getMessage());
                        }
                    desconexion();
                    cargaTabla();
                    modelo.fireTableDataChanged();
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
