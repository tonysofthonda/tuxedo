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
public class ifrmWPInventario extends javax.swing.JInternalFrame {
    JDesktopPane panel1;
    conexionWPPostgre cnxO;
    private DefaultTableModel modelo;
    private DefaultTableModel modelo2;
    private Object[] filasx;
    private TableRowSorter ordenar;
    private int fila;
    String id;
    String user;
    /**
     * Creates new form ifrmWPInventario
     * @param jDesktopPane5
     * @param usr
     */
    public ifrmWPInventario(JDesktopPane jDesktopPane5, String usr) {
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

    public void entradas(){
        conexion();
            cnxO.consulta("select "
                            + " ID, INVOICE, TO_CHAR(LOAD_DATE,'dd-MM-yyyy'), TO_CHAR(ARRIVAL_DATE,'dd-MM-yyyy'), UNIDADES, USUARIO "
                            + "from WP_ENTRADAS order by id DESC");
                            //Reedefinimos un Modelo de Tabla
                            modelo = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            //Asignamos el nuevo Modelo
                            jTable1.setModel(modelo);
                            //Agregamos las columnas al nuevo modelo
                            modelo.addColumn("ID");
                            modelo.addColumn("Invoice");
                            modelo.addColumn("Fecha Carga");
                            modelo.addColumn("Fecha Llegada");
                            modelo.addColumn("Unidades");
                            modelo.addColumn("Usuario");
                            //Asiganomos un ancho de columnas por default
                            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                            jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
                            jTable1.getColumnModel().getColumn(2).setPreferredWidth(130);
                            jTable1.getColumnModel().getColumn(3).setPreferredWidth(130);
                            jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
                            jTable1.getColumnModel().getColumn(5).setPreferredWidth(90);


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
                                        ordenar = new TableRowSorter(modelo);
                                        jTable1.setRowSorter(ordenar);
                                }
                                cnxO.reg.close();
                            } catch (SQLException ex) {

                            }
        desconexion();
        
    }
    
    public void infoDetalle(){
        conexion();
            cnxO.consulta("select " +
                            "ID, " +
                            "INVOICE, " +
                            "MODELO, " +
                            "TIPO, " +
                            "OPCION, " +
                            "COLOR, " +
                            "UNIDADES, " +
                            "TO_CHAR(LOAD_DATE,'dd-MM-yyyy'), " +
                            "TO_CHAR(ARRIVAL_DATE,'dd-MM-yyyy'), " +
                            "USUARIO " +
                            "from WP_ENTRADAS_DETALLE WHERE INVOICE='"+jTable1.getValueAt(fila, 1)+"' order by id DESC");
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
                            modelo.addColumn("Invoice");
                            modelo.addColumn("Modelo");
                            modelo.addColumn("Tipo");
                            modelo.addColumn("Opcion");
                            modelo.addColumn("Color");
                            modelo.addColumn("Unidades");
                            modelo.addColumn("Fecha Carga");
                            modelo.addColumn("Fecha Llegada");
                            modelo.addColumn("Usuario");
                            //Asiganomos un ancho de columnas por default
                            jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                            jTable2.getColumnModel().getColumn(1).setPreferredWidth(120);
                            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(3).setPreferredWidth(90);
                            jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(5).setPreferredWidth(90);
                            jTable2.getColumnModel().getColumn(6).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(7).setPreferredWidth(90);
                            jTable2.getColumnModel().getColumn(8).setPreferredWidth(100);
                            jTable2.getColumnModel().getColumn(9).setPreferredWidth(120);


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
    
    public void infoInventario(){
        conexion();
            cnxO.consulta("select" + 
                            "  ID, " +
                            "  INVOICE, " +
                            "  MODELO, " +
                            "  TIPO, " +
                            "  OPCION, " +
                            "  COLOR, " +
                            "  FRAME, " +
                            "  ENGINE, " +
                            "  TO_CHAR(PRODUCTION_DATE,'dd-MM-yyyy'), " +
                            "  TO_CHAR(LOAD_DATE,'dd-MM-yyyy'), " +
                            "  TO_CHAR(ARRIVAL_DATE,'dd-MM-yyyy'), " +
                            "  STATUS, " +
                            "  ORDEN " +
                            "from wp_inventario where invoice='"+jTable2.getValueAt(fila, 1)+"' and modelo='"+jTable2.getValueAt(fila, 2)+"' and tipo='"+jTable2.getValueAt(fila, 3)+"' AND OPCION='"+jTable2.getValueAt(fila, 4)+"' ORDER BY ID ASC");
                            //Reedefinimos un Modelo de Tabla
                            modelo = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            //Asignamos el nuevo Modelo
                            jTable3.setModel(modelo);
                            //Agregamos las columnas al nuevo modelo
                            modelo.addColumn("ID");
                            modelo.addColumn("Invoice");
                            modelo.addColumn("Modelo");
                            modelo.addColumn("Tipo");
                            modelo.addColumn("Opcion");
                            modelo.addColumn("Color");
                            modelo.addColumn("Frame");
                            modelo.addColumn("Engine");
                            modelo.addColumn("Fecha Produccion");
                            modelo.addColumn("Fecha Carga");
                            modelo.addColumn("Fecha Llegada");
                            modelo.addColumn("Status");
                            modelo.addColumn("Orden Produccion");
                            //Asiganomos un ancho de columnas por default
                            jTable3.getColumnModel().getColumn(0).setPreferredWidth(60);
                            jTable3.getColumnModel().getColumn(1).setPreferredWidth(120);
                            jTable3.getColumnModel().getColumn(2).setPreferredWidth(80);
                            jTable3.getColumnModel().getColumn(3).setPreferredWidth(90);
                            jTable3.getColumnModel().getColumn(4).setPreferredWidth(80);
                            jTable3.getColumnModel().getColumn(5).setPreferredWidth(90);
                            jTable3.getColumnModel().getColumn(6).setPreferredWidth(120);
                            jTable3.getColumnModel().getColumn(7).setPreferredWidth(120);
                            jTable3.getColumnModel().getColumn(8).setPreferredWidth(120);
                            jTable3.getColumnModel().getColumn(9).setPreferredWidth(120);
                            jTable3.getColumnModel().getColumn(10).setPreferredWidth(120);
                            jTable3.getColumnModel().getColumn(11).setPreferredWidth(80);
                            jTable3.getColumnModel().getColumn(12).setPreferredWidth(100);


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
                                        jTable3.setModel(modelo);
                                        jTable3.setDefaultRenderer(Object.class, new formato_usuario());
                                        ordenar = new TableRowSorter(modelo);
                                        jTable3.setRowSorter(ordenar);
                                }
                                cnxO.reg.close();
                            } catch (SQLException ex) {

                            }
        desconexion();
        
    }
    
    public void barCharDetalle(){
        
        
        
        
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Tuxedo - WaterPump | Carga de Inventario");

        jSplitPane1.setDividerLocation(550);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Entradas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Invoice", "Fecha Carga", "Fecha Llegada", "Unidades", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(90);
        }

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Entrada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Invoice", "Modelo", "Tipo", "Opcion", "Color", "Unidades", "Fecha Carga", "Fecha Llegada", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(7).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(8).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(9).setPreferredWidth(80);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Inventario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Invoice", "Modelo", "Tipo", "Opcion", "Color", "Frame", "Engine", "Fecha Produccion", "Fecha Carga", "Fecha Llegada", "Status", "Orden Produccion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(90);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTable3.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(6).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(7).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(8).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(9).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(10).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(11).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(12).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 573, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ifrmWPAddInventario ifrmamm=new ifrmWPAddInventario(user);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmamm.getSize();
        panel1.add(ifrmamm);
        ifrmamm.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmamm.toFront();
        ifrmamm.show();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        entradas();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila = jTable1.getSelectedRow();
        infoDetalle();
        barCharDetalle();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        fila = jTable1.getSelectedRow();
        infoDetalle();
        barCharDetalle();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        // TODO add your handling code here:
        fila = jTable2.getSelectedRow();
        infoInventario();
    }//GEN-LAST:event_jTable2KeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        fila = jTable2.getSelectedRow();
        infoInventario();
    }//GEN-LAST:event_jTable2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
