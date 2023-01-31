/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.io.File;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import tuxido.formato_usuario;


/**
 *
 * @author VJ002349
 */
public class ifrmWPTablaEmbarque extends javax.swing.JInternalFrame {
    conexionWPPostgre cnx;
    String emb;
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    File archivoExcel;
    JDesktopPane Pane3;
    /**
     * Creates new form ifrmTablaEmbarque
     */
    public ifrmWPTablaEmbarque(String embarque) {
        initComponents();
        emb=embarque;
        consulta();
    }
    
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
    
    public void consulta(){
        conexion();
        cnx.consulta("SELECT "
                    + " MODELO, "
                    + " TRIM(MODELO||'-'||TRIM(TIPO||REPLACE(OPCION,'null', ''))) AS DESCRIPCION, "
                    + " FRAME, "
                    + " ENGINE, "
                    + " EMPAQUE, "
                    + " CONTENEDOR, "
                    + " ENVIO, "
                    + " COLOR, "
                    + " INVOICE, "
                    + " to_char(PK,'dd-MM-yyyy') "
                    + "FROM "
                    + "  WP_PRODUCCION "
                    + " where "
                    + "  ENVIO='"+emb+"'");
        //Reedefinimos un Modelo de Tabla
                        modelo = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modelo);
                        jTable1.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable1.setModel(modelo);
                        //Agregamos las columnas al nuevo modelo
                        modelo.addColumn("Modelo");
                        modelo.addColumn("Descripcion");
                        modelo.addColumn("Frame");
                        modelo.addColumn("Motor");
                        modelo.addColumn("Empaque");
                        modelo.addColumn("Contenedor");
                        modelo.addColumn("Envio");
                        modelo.addColumn("Color");
                        modelo.addColumn("Factura");
                        modelo.addColumn("Produccion");   
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(140);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
                        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(8).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(9).setPreferredWidth(80);

                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                for (int i=0;i<modelo.getColumnCount();i++){
                                     filasx[i] = cnx.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modelo.addRow(filasx);
                                    jTable1.setModel(modelo);
                                    jTable1.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {
                        
                        }
        desconexion();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        archivo = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        excelExp = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Exportar a Excel");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Modelo", "Descripcion", "Frame", "Motor", "Color", "Fecha", "Empaque", "Tarima", "Envio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        excelExp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icono_excel.jpg"))); // NOI18N
        excelExp.setFocusable(false);
        excelExp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        excelExp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        excelExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelExpActionPerformed(evt);
            }
        });
        jToolBar1.add(excelExp);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void excelExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelExpActionPerformed
        // TODO add your handling code here:
        int returnVal = archivo.showSaveDialog(null);
        if(returnVal==JFileChooser.APPROVE_OPTION){
            archivoExcel=archivo.getSelectedFile();
            exportExcel exp=new exportExcel(jTable1, archivoExcel, "Produccion");
            exp.export();
        }
    }//GEN-LAST:event_excelExpActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser archivo;
    private javax.swing.JButton excelExp;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
