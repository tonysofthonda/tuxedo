/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.io.File;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import tuxido.formato_usuario;

/**
 *
 * @author VJ002349
 */
public class ifrmWPAddInventario extends javax.swing.JInternalFrame {
    conexionWPPostgre cnxO;
    private DefaultTableModel modelo;
    private Object[] filasx;
    private TableRowSorter ordenar;
    private int fila;
    String user;
    /**
     * Creates new form ifrmWPAddInventario
     */
    public ifrmWPAddInventario(String usr) {
        initComponents();
        user=usr;
    }
    
    public void conexion(){
        cnxO=new conexionWPPostgre();
        
    }
    
    public void desconexion(){
        try {
            cnxO.Conec.close();
        } catch (SQLException ex) {}
    }

    public void mostrar(){
        conexion();
            cnxO.consulta("select "
                        + "     modelo,"
                        + "     tipo,"
                        + "     opcion,"
                        + "     color,"
                        + "     count(*) as Unidades"
                        + " from "
                        + " WP_CARGA_TEMP"
                        + " group by modelo, tipo, opcion, color order by 1");
                            //Reedefinimos un Modelo de Tabla
                            modelo = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            //Asignamos el nuevo Modelo
                            jTable2.setModel(modelo);
                            //Agregamos las columnas al nuevo modelo
                            modelo.addColumn("Modelo");
                            modelo.addColumn("Tipo");
                            modelo.addColumn("Opcion");
                            modelo.addColumn("Color");
                            modelo.addColumn("Unidades");
                            //Asiganomos un ancho de columnas por default
                            jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                            jTable2.getColumnModel().getColumn(1).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(3).setPreferredWidth(90);
                            jTable2.getColumnModel().getColumn(4).setPreferredWidth(90);

                            //asigno el tamaño del arreglo con la cantidad de columnas:
                            filasx = new Object[modelo.getColumnCount()];
                            try {
                                //escribo las filas:
                                while (cnxO.reg.next()){
                                    for (int i=0;i<modelo.getColumnCount();i++){
                                         filasx[i] = cnxO.reg.getObject(i+1);
                                    } 
                                    //escribo las filas del resultado SQL//
                                        modelo.addRow(filasx);
                                        jTable2.setModel(modelo);
                                        jTable2.setDefaultRenderer(Object.class, new formato_usuario());
                                        ordenar = new TableRowSorter(modelo);
                                        jTable2.setRowSorter(ordenar);
                                }
                                cnxO.reg.close();
                            } catch (SQLException ex) {

                            }
        desconexion();
    }
    
    public void info(){
        conexion();
        //informacion Unidades x Factura (Invoice)
            try {
            cnxO.consulta("SELECT INVOICE, COUNT(*) AS UNIDADES FROM WP_CARGA_TEMP GROUP BY INVOICE");
                    while (cnxO.reg.next()){
                        guardarOrden(cnxO.reg.getString(1),cnxO.reg.getInt(2));
                    }

            cnxO.consulta("select invoice, modelo, tipo, opcion, color, count(*) as Unidades "
                        + "from WP_CARGA_TEMP group by invoice, modelo, tipo, opcion, color order by 1");
            
                    while (cnxO.reg.next()){
                        guardarDetalle(cnxO.reg.getString(1),cnxO.reg.getString(2),cnxO.reg.getString(3),
                                       cnxO.reg.getString(4),cnxO.reg.getString(5),cnxO.reg.getString(6));
                    }
            cnxO.reg.close();       
            } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error: "+ex);
            }
             
        desconexion();
    }
    
    public void guardarOrden(String i, int u){
        //conexion();
            //Entrada
                cnxO.ejecutar("INSERT INTO WP_ENTRADAS (INVOICE, LOAD_DATE, ARRIVAL_DATE, UNIDADES, USUARIO)"
                            + " VALUES ('"+i+"',to_date(to_char(current_date, 'MM/dd/yyyy'),'MM/dd/yyyy'),to_date(to_char(current_date, 'MM/dd/yyyy'),'MM/dd/yyyy'),'"+u+"','"+user+"')");
        //desconexion();
    }
    
    public void guardarDetalle(String i,String m,String t,String o,String c,String u){
        //conexion();
            //DETALLE
                cnxO.ejecutar("INSERT INTO WP_ENTRADAS_DETALLE "
                        + "(INVOICE, MODELO, TIPO, OPCION, COLOR, UNIDADES, LOAD_DATE, ARRIVAL_DATE, USUARIO) VALUES "
                            + "('"+i+"','"+m+"','"+t+"','"+o+"','"+c+"','"+u+"',to_date(to_char(current_date, 'MM/dd/yyyy'),'MM/dd/yyyy'),to_date(to_char(current_date, 'MM/dd/yyyy'),'MM/dd/yyyy'),'"+user+"')");
        //desconexion();
    }
    
    public void cargaInventario(){
        conexion();
            //Carga de Inventario
                cnxO.ejecutar("INSERT INTO WP_INVENTARIO (INVOICE, MODELO, TIPO, OPCION, COLOR, FRAME, ENGINE, PRODUCTION_DATE, LOAD_DATE, ARRIVAL_DATE, STATUS, ORDEN)" +
                                " select " +
                                "  INVOICE," +
                                "  MODELO," +
                                "  TIPO," +
                                "  OPCION," +
                                "  COLOR," +
                                "  FRAME," +
                                "  ENGINE," +
                                "  PRODUCTION_DATE," +
                                "  current_date," +
                                "  current_date," +
                                "  'Pendiente' as STATUS," +
                                "  '0' as ORDEN " +
                                " from WP_CARGA_TEMP ORDER BY 1 ASC");
        desconexion();
    }
    
    /** GROUP
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buscarArchivo = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        ruta = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Tuxedo - WaterPump | Nueva Entrada de Inventario");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Archivo de Carga", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        ruta.setEditable(false);
        ruta.setBackground(new java.awt.Color(204, 204, 255));
        ruta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cargar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Entrada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Modelo", "Tipo", "Opcion", "Color", "Unidades"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int returnVal = buscarArchivo.showOpenDialog(null);
        if(returnVal==JFileChooser.APPROVE_OPTION){
            File archivoExcel=buscarArchivo.getSelectedFile();
            ruta.setText(archivoExcel.getPath());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
            //jTabbedPane1.setSelectedIndex(0);
            ExceltoDB imp=new ExceltoDB(ruta.getText());
            imp.Import();
            mostrar();
            //jTabbedPane1.setSelectedIndex(1);            
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
            info();
            cargaInventario();
            dispose();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser buscarArchivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField ruta;
    // End of variables declaration//GEN-END:variables
}
