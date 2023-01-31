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
public class ifrmWPControlProduccion extends javax.swing.JInternalFrame {
    JDesktopPane panel1;
    conexionWPPostgre cnxO;
    private DefaultTableModel modelo;
    private DefaultTableModel modelo2;
    private DefaultTableModel modelo3;
    private Object[] filasx;
    private TableRowSorter ordenar;
    private int fila;
    String id;
    String user;
    /**
     * Creates new form ifrmWPControlProduccion
     * @param jDesktopPane5
     * @param usr
     */
    public ifrmWPControlProduccion(JDesktopPane jDesktopPane5, String usr) {
        initComponents();
        user=usr;
        panel1=jDesktopPane5;
    }

    public void conexion(){
        cnxO=new conexionWPPostgre();
        
    }
    
    /**
     *
     */
    public void desconexion(){
        try {
            cnxO.Conec.close();
        } catch (SQLException ex) {}
    }
    
    public void actualizar(){
        conexion();
            cnxO.consulta("select "
                            + " id, "
                            + " unidades, "
                            + " TO_CHAR(fecha_creacion,'DD/MM/YY'), "
                            + " TO_CHAR(fecha_modificacion,'DD/MM/YY'), "
                            + " usuario, "
                            + " status, "
                            + " avance, "
                            + " status_produccion "
                            + "   from WP_ORDEN order by id DESC");
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
                            modelo.addColumn("Unidades");
                            modelo.addColumn("Creacion");
                            modelo.addColumn("Modificacion");
                            modelo.addColumn("Usuario");
                            modelo.addColumn("Status");
                            modelo.addColumn("Avance");
                            modelo.addColumn("Status Produccion");
                            //Asiganomos un ancho de columnas por default
                            jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                            jTable2.getColumnModel().getColumn(1).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(3).setPreferredWidth(90);
                            jTable2.getColumnModel().getColumn(4).setPreferredWidth(90);
                            jTable2.getColumnModel().getColumn(5).setPreferredWidth(70);
                            jTable2.getColumnModel().getColumn(6).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(7).setPreferredWidth(120);


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
    
    public void infoDetalle(){
        conexion();
            cnxO.consulta("select "
                            + " * "
                            + "from WP_ORDEN_DETAIL WHERE ORDEN='"+jTable2.getValueAt(fila, 0)+"' order by id DESC");
                            //Reedefinimos un Modelo de Tabla
                            modelo2 = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            
                            //Asignamos el nuevo Modelo
                            jTable1.setModel(modelo2);
                            //Agregamos las columnas al nuevo modelo
                            modelo2.addColumn("ID");
                            modelo2.addColumn("Orden");
                            modelo2.addColumn("Modelo");
                            modelo2.addColumn("Descripcion");
                            modelo2.addColumn("Tipo");
                            modelo2.addColumn("Prefijo");
                            modelo2.addColumn("Motor");
                            modelo2.addColumn("Color");
                            modelo2.addColumn("Desc. Color");
                            modelo2.addColumn("Unidades");
                            modelo2.addColumn("Avance");
                            modelo2.addColumn("Status");
                            //Asiganomos un ancho de columnas por default
                            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                            jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
                            jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(3).setPreferredWidth(180);
                            jTable1.getColumnModel().getColumn(4).setPreferredWidth(60);
                            jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
                            jTable1.getColumnModel().getColumn(7).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(8).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(9).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(10).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(11).setPreferredWidth(90);


                            //asigno el tamaño del arreglo con la cantidad de columnas:
                            filasx = new Object[modelo2.getColumnCount()];
                            try {
                                //escribo las filas:
                                while (cnxO.reg.next()){
                                    for (int i=0;i<modelo2.getColumnCount();i++){
                                         filasx[i] = cnxO.reg.getObject(i+1);
                                    } 
                                    //escribo las filas del resultado SQL//
                                        modelo2.addRow(filasx);
                                        jTable1.setModel(modelo2);
                                        jTable1.setDefaultRenderer(Object.class, new formato_usuario());
                                        ordenar = new TableRowSorter(modelo2);
                                        jTable1.setRowSorter(ordenar);
                                }
                                cnxO.reg.close();
                            } catch (SQLException ex) {

                            }
        desconexion();
    }
    
    public void infoProduccion(){
        conexion();
            cnxO.consulta("select "
                            + " ID, FRAME, ENGINE, TO_CHAR(PK,'DD/MM/YY'), TO_CHAR(SHP,'DD/MM/YY') "
                            + "from WP_PRODUCCION WHERE ORDEN='"+jTable1.getValueAt(fila, 1) 
                            + "' AND MODELO='"+jTable1.getValueAt(fila, 2)+"' AND TRIM(TIPO||REPLACE(OPCION,'null', ''))='"+jTable1.getValueAt(fila, 4)+"' order by id ASC");
                            
                            System.out.println("select "
                            + " ID, FRAME, ENGINE, TO_CHAR(PK,'DD/MM/YY'), TO_CHAR(SHP,'DD/MM/YY') "
                            + "from WP_PRODUCCION WHERE ORDEN='"+jTable1.getValueAt(fila, 1) 
                            + "' AND MODELO='"+jTable1.getValueAt(fila, 2)+"' AND TRIM(TIPO||REPLACE(OPCION,'null', ''))='"+jTable1.getValueAt(fila, 4)+"' order by id ASC");
                            //Reedefinimos un Modelo de Tabla
                            modelo3 = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            
                            //Asignamos el nuevo Modelo
                            jTable3.setModel(modelo3);
                            //Agregamos las columnas al nuevo modelo
                            modelo3.addColumn("ID");
                            modelo3.addColumn("Frame");
                            modelo3.addColumn("Engine");
                            modelo3.addColumn("Empaque");
                            modelo3.addColumn("Embarque");
                            //Asiganomos un ancho de columnas por default
                            jTable3.getColumnModel().getColumn(0).setPreferredWidth(60);
                            jTable3.getColumnModel().getColumn(1).setPreferredWidth(110);
                            jTable3.getColumnModel().getColumn(2).setPreferredWidth(110);
                            jTable3.getColumnModel().getColumn(3).setPreferredWidth(90);
                            jTable3.getColumnModel().getColumn(4).setPreferredWidth(90);


                            //asigno el tamaño del arreglo con la cantidad de columnas:
                            filasx = new Object[modelo3.getColumnCount()];
                            try {
                                //escribo las filas:
                                while (cnxO.reg.next()){
                                    for (int i=0;i<modelo3.getColumnCount();i++){
                                         filasx[i] = cnxO.reg.getObject(i+1);
                                    } 
                                    //escribo las filas del resultado SQL//
                                        modelo3.addRow(filasx);
                                        jTable3.setModel(modelo3);
                                        jTable3.setDefaultRenderer(Object.class, new formato_usuario());
                                        ordenar = new TableRowSorter(modelo3);
                                        jTable3.setRowSorter(ordenar);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_start.png"))); // NOI18N
        jMenuItem1.setText("Imprimir Orden");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_start.png"))); // NOI18N
        jMenuItem2.setText("Imprimir Unidad");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem2);

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tuxedo - WaterPump | Control de Produccion");

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

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/key_stop.png"))); // NOI18N
        jButton5.setToolTipText("Cerrar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bullet_lightning.png"))); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Unidades", "Creacion", "Modificacion", "Usuario", "Status", "Avance", "Status Produccion"
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
        jTable2.setComponentPopupMenu(jPopupMenu1);
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
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(70);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(7).setPreferredWidth(120);
        }

        jSplitPane1.setTopComponent(jScrollPane2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Orden", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Orden", "Modelo", "Descripcion", "Tipo", "Prefijo", "Motor", "Color", "Desc. Color", "Unidades", "Avance", "Status"
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
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(180);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(11).setPreferredWidth(80);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setLeftComponent(jPanel3);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Produccion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Frame", "Engine", "Empaque", "Embarque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable3.setComponentPopupMenu(jPopupMenu2);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable3KeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ifrmWPOrdenProduccion ifrmamm=new ifrmWPOrdenProduccion(panel1,user);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmamm.getSize();
        panel1.add(ifrmamm);
        ifrmamm.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmamm.toFront();
        ifrmamm.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        fila = jTable2.getSelectedRow();
        infoDetalle();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        // TODO add your handling code here:
        fila=jTable2.getSelectedRow();
        infoDetalle();
    }//GEN-LAST:event_jTable2KeyReleased

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
        infoProduccion();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
        infoProduccion();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String status=(String) jTable2.getValueAt(fila, 5);
        id=jTable2.getValueAt(fila, 0).toString();
        if(status.equals("Activo")){
            status="Desactivado";
        }else{
            status="Activo";
        }
            conexion();
                cnxO.ejecutar("UPDATE WP_ORDEN SET STATUS='"+status+"' where ID='"+id+"'");
                cnxO.ejecutar("UPDATE WP_ORDEN_DETAIL SET STATUS='"+status+"' where ORDEN='"+id+"'");
            desconexion();
        actualizar();
        infoDetalle();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        generarTarjetaWP imp=new generarTarjetaWP();
        String FRAME=jTable3.getValueAt(fila, 1).toString();
        conexion();
        cnxO.consulta("select ORDEN, FRAME  FROM WP_PRODUCCION WHERE frame='"+FRAME+"' ORDER BY FRAME ASC");
        try {
            while(cnxO.reg.next()){
                imp.conReporte(cnxO.reg.getInt(1),cnxO.reg.getString(2));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {            
        }
        desconexion(); 
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ifrmWPEstampado ifrestamp=new ifrmWPEstampado(panel1,user);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrestamp.getSize();
        panel1.add(ifrestamp);
        ifrestamp.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrestamp.toFront();
        ifrestamp.show();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        generarTarjetaWP imp=new generarTarjetaWP();
        String orden=jTable2.getValueAt(fila, 0).toString();
        conexion();
        cnxO.consulta("select ORDEN, FRAME  FROM WP_PRODUCCION WHERE orden='"+orden+"' ORDER BY FRAME ASC");
        try {
            while(cnxO.reg.next()){
                System.out.println(cnxO.reg.getString(1)+","+cnxO.reg.getString(2));
                imp.conReporte(cnxO.reg.getInt(1),cnxO.reg.getString(2));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {            
        }
        desconexion();        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTable3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyReleased
        // TODO add your handling code here:
        fila=jTable3.getSelectedRow();
    }//GEN-LAST:event_jTable3KeyReleased

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        fila=jTable3.getSelectedRow();
    }//GEN-LAST:event_jTable3MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
