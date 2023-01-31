/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class hiloTablaSeg extends Thread{
    conexionPostgres cnx;
    String q;
    boolean band=true;
    JTable t;
    private TableRowSorter ordenar;
    private DefaultTableModel modelo;
    private Object[] filasx;
    Vector columnNames = new Vector();
    Vector data = new Vector();
    Vector row;
    int column; 
    /**
     *
     * @param tabla
     * @param query
     */
    public hiloTablaSeg(JTable tabla, String query){
        this.t=tabla;
        q=query;
    }
    /**
     *
     */
    public void deterHilo(){
        band=false;
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
                        columnNames.addElement("ID"); //0
                        columnNames.addElement("VIN"); //1
                        columnNames.addElement("Descripcion"); //2
                        columnNames.addElement("Color"); //3
                        columnNames.addElement("Año Modelo"); //4
                        columnNames.addElement("Soldadura"); //5
                        columnNames.addElement("Liberado"); //6
                        columnNames.addElement("Pintura"); //7
                        columnNames.addElement("Liberado"); //8
                        columnNames.addElement("Ensamble"); //9
                        columnNames.addElement("Liberado"); //10
                        columnNames.addElement("Calidad"); //11
                        columnNames.addElement("Liberado"); //12
                        columnNames.addElement("Empaque"); //13
                        columnNames.addElement("Liberado"); //14
                        columnNames.addElement("Embarque"); //15
                        columnNames.addElement("Liberado"); //16
                        //Asignamos el nuevo Modelo
                        //Reedefinimos un Modelo de Tabla
                        modelo = new DefaultTableModel(data,columnNames){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };                
                        ordenar = new TableRowSorter(modelo);
                        t.setModel(modelo);
                        t.setRowSorter(ordenar);
                        t.setDefaultRenderer(Object.class, new formato_usuario());
                        //Asiganomos un ancho de columnas por default
                        t.getColumnModel().getColumn(0).setPreferredWidth(50);
                        t.getColumnModel().getColumn(1).setPreferredWidth(140);
                        t.getColumnModel().getColumn(2).setPreferredWidth(90);
                        t.getColumnModel().getColumn(3).setPreferredWidth(60);
                        t.getColumnModel().getColumn(4).setPreferredWidth(80);
                        t.getColumnModel().getColumn(5).setPreferredWidth(80);
                        t.getColumnModel().getColumn(6).setPreferredWidth(150);
                        t.getColumnModel().getColumn(7).setPreferredWidth(80);
                        t.getColumnModel().getColumn(8).setPreferredWidth(150);
                        t.getColumnModel().getColumn(9).setPreferredWidth(130);
                        t.getColumnModel().getColumn(10).setPreferredWidth(150);
                        t.getColumnModel().getColumn(11).setPreferredWidth(80);
                        t.getColumnModel().getColumn(12).setPreferredWidth(150);
                        t.getColumnModel().getColumn(13).setPreferredWidth(80);
                        t.getColumnModel().getColumn(14).setPreferredWidth(150);
                        t.getColumnModel().getColumn(15).setPreferredWidth(80);
                        t.getColumnModel().getColumn(16).setPreferredWidth(150);
    }
    
    /**
     * 
     */
    public void consulta(){
              data.clear();
              conexion();          
                        cnx.consulta(q);
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){    
                                column = columnNames.size();
                                row = new Vector(column);
                                for (int i=0;i<column;i++){
                                     row.addElement(cnx.reg.getObject(i+1));
                                } 
                                data.addElement(row);
                            }
                            t.setDefaultRenderer(Object.class, new formato_usuario());
                        }catch (SQLException ex) {
                            System.out.println(ex);
                        }
              desconexion();
    }
    /**
     * 
     */
    @Override
    public void run(){
        modeloTabla();
        while(band){
            consulta();
            modelo.fireTableDataChanged();
            try{
                Thread.sleep(5000);
            }catch(InterruptedException ex){}
        }
    }
}
