/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.Dimension;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author vj002349
 */
public class ifrmRepEmbarque extends javax.swing.JInternalFrame {
    int fila;
    conexionPostgres cnx;
    String fchini;
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    private String ordEmb;
    JDesktopPane panel;
    private String fde;
    private String fa;
    /**
     * Creates new form ifrmRepEmbarque
     * @param jDesktopPane 
     */
    public ifrmRepEmbarque(JDesktopPane jDesktopPane) {
         panel = jDesktopPane;
        initComponents();
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
    public void buscar(){
        conexion();
            Date fch=de.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            fde=sdf.format(fch);
            fch=a.getDate();
            fa=sdf.format(fch);
        cnx.consulta("select "
                + "ID, Transportista, to_char(fecha,'dd-MM-yyyy'), to_char(hora,'HH24:MI:SS'), contenedor, unidades, "
                + "tarimas, Status from TX_INFO_EMBARQUE WHERE FECHA BETWEEN '"+fde+"' AND '"+fa+"' ORDER BY ID ASC");
                //Reedefinimos un Modelo de Tabla
        
                        modelo = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modelo);
                        jTable2.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable2.setModel(modelo);
                        //Agregamos las columnas al nuevo modelo
                        modelo.addColumn("ID");
                        modelo.addColumn("Transportista");
                        modelo.addColumn("Fecha");
                        modelo.addColumn("Hora");
                        modelo.addColumn("Contenedor");
                        modelo.addColumn("Unidades");
                        modelo.addColumn("Tarimas");
                        modelo.addColumn("Status");
                        //Asiganomos un ancho de columnas por default
                        jTable2.getColumnModel().getColumn(0).setPreferredWidth(60);
                        jTable2.getColumnModel().getColumn(1).setPreferredWidth(100);
                        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(4).setPreferredWidth(90);
                        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(6).setPreferredWidth(70);
                        jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);

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
                                    jTable2.setModel(modelo);
                                    jTable2.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {}
        
        desconexion();
    }
    /**
     *
     */
    public void impresion(){
        ordEmb=jTable2.getValueAt(fila, 0).toString();
        generarSalidaEmb imp=new generarSalidaEmb();
        imp.generar(ordEmb);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        de = new com.toedter.calendar.JDateChooser();
        a = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setTitle("Tuxedo |  Bitácora de Embarque");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Transportista", "Fecha", "Hora", "Contenedor", "Unidades", "Tarimas", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable2KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(90);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(70);
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel2.setText("De:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel3.setText("A:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_magnify.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.setToolTipText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(de, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(a, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(de, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton5.setToolTipText("Nueva Bitacora");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bullet_edit.png"))); // NOI18N
        jButton3.setToolTipText("Editar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_key.png"))); // NOI18N
        jButton2.setToolTipText("Informe de Bitacora");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        fila=jTable2.getSelectedRow();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        // TODO add your handling code here:
        fila=jTable2.getSelectedRow();
    }//GEN-LAST:event_jTable2KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(jTable2.getValueAt(fila, 7).toString().equals("Pendiente")){
            ifrmModEmbarque ifrmmemb=new ifrmModEmbarque(jTable2.getValueAt(fila, 0).toString());
            Dimension desktopSize = panel.getSize();
            Dimension jInternalFrameSize = ifrmmemb.getSize();
            panel.add(ifrmmemb);
            ifrmmemb.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
            ifrmmemb.show();
            ifrmmemb.toFront();
        }else{
            JOptionPane.showMessageDialog(null,"<html>La Bitacora solo puede ser edita si el Status es <strong>Pendiente</strong><p>Solicita ayuda con el Supervisor del Sistema</hrml>");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        ifrmEmbarque ifrmemb=new ifrmEmbarque();
        Dimension desktopSize = panel.getSize();
        Dimension jInternalFrameSize = ifrmemb.getSize();
        panel.add(ifrmemb);
        ifrmemb.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmemb.show();
        ifrmemb.toFront();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        impresion();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser a;
    private com.toedter.calendar.JDateChooser de;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
