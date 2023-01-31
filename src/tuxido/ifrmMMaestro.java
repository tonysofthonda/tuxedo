/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class ifrmMMaestro extends javax.swing.JInternalFrame {
    JDesktopPane panel1;
    conexionPostgres cnxO;
    private DefaultTableModel modelo;
    private Object[] filasx;
    private TableRowSorter ordenar;
    private int fila;
    /**
     * Creates new form ifrmMMaestro
     * @param jDesktopPane1 
     */
    public ifrmMMaestro(JDesktopPane jDesktopPane1) {
        initComponents();
        panel1=jDesktopPane1;
    }
    
    /**
     *
     */
    public void conexion(){
        cnxO=new conexionPostgres();
        
    }
    
    /**
     *
     */
    public void desconexion(){
        try {
            cnxO.Conec.close();
        } catch (SQLException ex) {}
    }
    
    /**
     *
     */
    public void actualizar(){
        conexion();
        cnxO.consulta("select id, modelo, descripcion, color, ttl_prod, TO_CHAR(fecha_creacion,'dd/mm/yyyy'), TO_CHAR(fecha_prod,'dd/mm/yyyy'), status  from TX_MMaestro order by id DESC");
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
                        modelo.addColumn("ID");
                        modelo.addColumn("Modelo");
                        modelo.addColumn("Descripcion");
                        modelo.addColumn("Color");
                        modelo.addColumn("Total");
                        modelo.addColumn("Creacion");
                        modelo.addColumn("Produccion");
                        modelo.addColumn("Status");
                        //Asiganomos un ancho de columnas por default
                        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable2.getColumnModel().getColumn(1).setPreferredWidth(60);
                        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(3).setPreferredWidth(60);
                        jTable2.getColumnModel().getColumn(4).setPreferredWidth(60);
                        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(6).setPreferredWidth(90);
                        jTable2.getColumnModel().getColumn(7).setPreferredWidth(70);

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
    
    /**
     *
     */
    public void infoReg(){
        conexion();
        String id=jTable2.getValueAt(fila, 0).toString();
        cnxO.consulta("select modelo, descripcion, anio, destino, prefijo, motor from TX_MMAESTRO WHERE id='"+id+"'");
        try {
            while(cnxO.reg.next()){
                cmodelo.setText(cnxO.reg.getString(1));
                desc.setText(cnxO.reg.getString(2));
                anio.setText(cnxO.reg.getString(3));
                destino.setText(cnxO.reg.getString(4));
                pref.setText(cnxO.reg.getString(5));
                motor.setText(cnxO.reg.getString(6));
                
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmMMaestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
        consultaReg(id);
    }
    /**
     *
     * @param id
     */
    public void consultaReg(String id){
         conexion();
         cnxO.consulta("select id, vin, status from TX_ORDEN_PROD where id_orden='"+id+"'order by id DESC");
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
                        modelo.addColumn("ID");
                        modelo.addColumn("VIN");
                        modelo.addColumn("Status");
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(160);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);

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
                                    jTable1.setModel(modelo);
                                    jTable1.setDefaultRenderer(Object.class, new formato_usuario());                                
                            }
                            cnxO.reg.close();
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmodelo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        anio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pref = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        motor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        destino = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tuxedo | Modelo Maestro");

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton1.setToolTipText("Refrescar");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton2.setToolTipText("Agregar");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bullet_edit.png"))); // NOI18N
        jButton4.setToolTipText("Modificar");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Modelo", "Descripcion", "Color", "Total", "Creacion", "Produccion", "Status"
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
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(60);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(7).setPreferredWidth(70);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Descripcion Mod. Maestro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setText("Codigo Modelo:");

        cmodelo.setEditable(false);
        cmodelo.setBackground(new java.awt.Color(204, 204, 255));
        cmodelo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setText("Descripcion:");

        desc.setEditable(false);
        desc.setBackground(new java.awt.Color(204, 204, 255));
        desc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("Año:");

        anio.setEditable(false);
        anio.setBackground(new java.awt.Color(204, 204, 255));
        anio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel5.setText("Prefijo:");

        pref.setEditable(false);
        pref.setBackground(new java.awt.Color(204, 204, 255));
        pref.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setText("Motor:");

        motor.setEditable(false);
        motor.setBackground(new java.awt.Color(204, 204, 255));
        motor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setText("Destino:");

        destino.setEditable(false);
        destino.setBackground(new java.awt.Color(204, 204, 255));
        destino.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(desc, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pref))
                    .addComponent(motor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(pref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(motor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(160);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        }

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton6.setToolTipText("Refrescar Vista");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addGap(0, 149, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ifrmAddMMaestro ifrmamm=new ifrmAddMMaestro();
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmamm.getSize();
        panel1.add(ifrmamm);
        ifrmamm.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmamm.toFront();
        ifrmamm.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        ifrmModMMaestro ifrmmmm=new ifrmModMMaestro();
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmmmm.getSize();
        panel1.add(ifrmmmm);
        ifrmmmm.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmmmm.toFront();
        ifrmmmm.show();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        // TODO add your handling code here:
        fila=jTable2.getSelectedRow();
        infoReg();
    }//GEN-LAST:event_jTable2KeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        fila=jTable2.getSelectedRow();
        infoReg();
    }//GEN-LAST:event_jTable2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anio;
    private javax.swing.JTextField cmodelo;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField destino;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField motor;
    private javax.swing.JTextField pref;
    // End of variables declaration//GEN-END:variables
}
