/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import tuxido.formato_usuario;

/**
 *
 * @author VJ002349
 */
public class ifrmTablaSegWP extends javax.swing.JInternalFrame {
    private String query;
    private String fecha;
    int fila;
    private final JDesktopPane Panel;
    conexionWPPostgre cnx;
    Object[] filasx;
    DefaultTableModel modelo;
    TableRowSorter ordenar;
    
    /**
     * Creates new form ifrmTablaSegWP
     */
    public ifrmTablaSegWP(JDesktopPane jDesktopPane) {
        initComponents();
        Panel=jDesktopPane;
        initTables();
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
    
        public void initTables(){
                            
                            //Reedefinimos un Modelo de Tabla
                            modelo = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            //Asignamos el nuevo Modelo
                            jTable1.setModel(modelo);
                            jTable1.setDefaultRenderer(Object.class, new formato_usuario());
                            //Agregamos las columnas al nuevo modelo
                            modelo.addColumn("ID");
                            modelo.addColumn("Frame");
                            modelo.addColumn("Engine");
                            modelo.addColumn("Modelo");
                            modelo.addColumn("Tipo");
                            modelo.addColumn("Opcion");
                            modelo.addColumn("Color");
                            modelo.addColumn("Empaque");
                            modelo.addColumn("Liberado");
                            modelo.addColumn("No Empaque");
                            modelo.addColumn("Embarque");
                            modelo.addColumn("Liberado");
                            //Asiganomos un ancho de columnas por default
                            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
                            jTable1.getColumnModel().getColumn(1).setPreferredWidth(110);
                            jTable1.getColumnModel().getColumn(2).setPreferredWidth(110);
                            jTable1.getColumnModel().getColumn(3).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
                            jTable1.getColumnModel().getColumn(5).setPreferredWidth(60);
                            jTable1.getColumnModel().getColumn(6).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(7).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(8).setPreferredWidth(150);
                            jTable1.getColumnModel().getColumn(9).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(10).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(11).setPreferredWidth(90);

                            ordenar = new TableRowSorter(modelo);
                            jTable1.setRowSorter(ordenar);
    }
    
    public void buscar(){
        conexion();
            cnx.consulta("SELECT" +
                            "  ID," +
                            "  FRAME," +
                            "  ENGINE," +
                            "  MODELO," +
                            "  TIPO," +
                            "  replace(opcion,'null','') OPCION," +
                            "  COLOR," +
                            "  PK_STATUS," +
                            "  to_char(PK_TIME,'dd/mm/yyyy hh24:mi:ss')," +
                            "  EMPAQUE," +
                            "  SHP_STATUS,"+
                            "  to_char(SHP_TIME,'dd/mm/yyyy hh24:mi:ss')" +
                            " FROM WP_PRODUCCION WHERE "
                          + "SHP_STATUS='Pendiente' order by id");
                            //asigno el tamaño del arreglo con la cantidad de columnas:
                            filasx = new Object[modelo.getColumnCount()];
                            try {
                                    //escribo las filas:                                    
                                    if (!cnx.reg.next()) {
                                            while(modelo.getRowCount()>0){
                                                for(int i=0;i<modelo.getRowCount();i++){
                                                    modelo.removeRow(i);
                                                }
                                            }
                                    } else {
                                        while(modelo.getRowCount()>0){
                                            for(int i=0;i<modelo.getRowCount();i++){
                                                modelo.removeRow(i);
                                            }
                                        }
                                        do {
                                            for (int i=0;i<modelo.getColumnCount();i++){
                                             filasx[i] = cnx.reg.getObject(i+1);
                                            } 
                                        //escribo las filas del resultado SQL//
                                        modelo.addRow(filasx);
                                        //jTable3.setModel(modelo3);
                                        } while (cnx.reg.next());
                                    }
//                                }
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

        jToolBar1 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tuxeso - WaterPump | Tabla de Seguimiento");

        jToolBar1.setFloatable(false);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton5.setToolTipText("Refrescar Informacion");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_magnify.png"))); // NOI18N
        jButton2.setToolTipText("Ver Info.");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Frame", "Engine", "Modelo", "Tipo", "Opcion", "Color", "Empaque", "Liberado", "No Empaque", "Embarque", "Liberado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(11).setPreferredWidth(160);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ifrmWPInfoUnidad ifrmiu=new ifrmWPInfoUnidad(Panel, jTable1.getValueAt(fila, 1).toString(), "Frame");
        Dimension desktopSize = Panel.getSize();
        Dimension jInternalFrameSize = ifrmiu.getSize();
        Panel.add(ifrmiu);
        ifrmiu.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmiu.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
