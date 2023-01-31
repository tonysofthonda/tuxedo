/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class ifrmAS400 extends javax.swing.JInternalFrame {
    conexionAS400 cnx;
    private final JDesktopPane panel3;
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    /**
     * Creates new form ifrmAS400
     * @param Pane3 
     * @param e
     * @param c  
     */
    public ifrmAS400(JDesktopPane Pane3, String e, String c) {
        initComponents();
        panel3=Pane3;
        envio.setText(e);
        contenedor.setText(c);
        ASNMD();
        MPB9();
        MTA9();
        wms();
    }

    /**
     *
     */
    public void conexion(){
        cnx=new conexionAS400();
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
    public void ASNMD(){
        conexion();
        // MDFACT LIKE '%HDM-15%' AND MDNCON LIKE '%57528%'
            cnx.consulta("select MDORDN, MDFACT, MDPROD, MDNSER, MDMOT, MDPACK, MDNCON "
                       + "from E82PARF.ASNMD WHERE MDFACT LIKE '%HDM-"+envio.getText()+"%' "
                       + "AND MDNCON LIKE '%"+contenedor.getText()+"%'");
                       
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
                        modelo.addColumn("Ord. Compra");
                        modelo.addColumn("Factura");
                        modelo.addColumn("Producto");
                        modelo.addColumn("VIN");
                        modelo.addColumn("Motor");
                        modelo.addColumn("Empaque");
                        modelo.addColumn("Contenedor");
                        //Asiganomos un ancho de columnas por default
                        jTable2.getColumnModel().getColumn(0).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(1).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(2).setPreferredWidth(150);
                        jTable2.getColumnModel().getColumn(3).setPreferredWidth(140);
                        jTable2.getColumnModel().getColumn(4).setPreferredWidth(140);
                        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(6).setPreferredWidth(90);

                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                oCom.setText(cnx.reg.getString(1));
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
    public void MPB9(){
        conexion();
            cnx.consulta("select NLINE, NPRD, NQTY, NREF, NNOC "
                       + "from E82PARF.MPB9 WHERE NNOC = '"+oCom.getText()+"'");
                       
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
                        modelo.addColumn("Ln.");
                        modelo.addColumn("Producto");
                        modelo.addColumn("Cantidad");
                        modelo.addColumn("Folio Niguri");
                        modelo.addColumn("Ord. Compra");
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);

                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                niguri.setText(cnx.reg.getString(4));
                                for (int i=0;i<modelo.getColumnCount();i++){
                                     filasx[i] = cnx.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modelo.addRow(filasx);
                                    jTable1.setModel(modelo);
                                    jTable1.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {}
                        
        desconexion();       
    }
    /**
     *
     */
    public void MTA9(){
        conexion();
            cnx.consulta("select TAPON, TAITEM, TAVIN, TAENG "
                       + "from E82PARF.MTA9 WHERE TAPON = '"+oCom.getText()+"'");
                       
                       //Reedefinimos un Modelo de Tabla
                        modelo = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modelo);
                        jTable4.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable4.setModel(modelo);
                        //Agregamos las columnas al nuevo modelo
                        modelo.addColumn("Ord. Compra");
                        modelo.addColumn("Producto");
                        modelo.addColumn("VIN");
                        modelo.addColumn("Motor");
                        //Asiganomos un ancho de columnas por default
                        jTable4.getColumnModel().getColumn(0).setPreferredWidth(80);
                        jTable4.getColumnModel().getColumn(1).setPreferredWidth(150);
                        jTable4.getColumnModel().getColumn(2).setPreferredWidth(140);
                        jTable4.getColumnModel().getColumn(3).setPreferredWidth(140);

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
                                    jTable4.setModel(modelo);
                                    jTable4.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {}
                        
        desconexion(); 
    }
    /**
     *
     */
    public void wms(){
        conexionSQLServer cnxSQL=new conexionSQLServer();
        cnxSQL.consulta("select" 
                     +  " RECEIPTLINENUMBER," 
                     +  " SKU," 
                     +  " TOID," 
                     +  " SUSR2," 
                     +  " SUSR4," 
                     +  " CONTAINERKEY," 
                     +  " CASE" 
                     +  "         WHEN LABELPRINT = '1'" 
                     +  "         THEN 'Listo'"
                     +  "         ELSE 'Pendiente'"
                     +  " END"
                     +  " from receiptdetail"
                     +  " WHERE EXTERNRECEIPTKEY LIKE '%"+oCom.getText()+"%' AND CONTAINERKEY = '"+contenedor.getText()+"'  ORDER BY RECEIPTLINENUMBER ASC;");
                       
                       //Reedefinimos un Modelo de Tabla
                        modelo = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modelo);
                        jTable3.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable3.setModel(modelo);
                        //Agregamos las columnas al nuevo modelo
                        modelo.addColumn("Ln.");
                        modelo.addColumn("Producto");
                        modelo.addColumn("VIN");
                        modelo.addColumn("Motor");
                        modelo.addColumn("Envio");
                        modelo.addColumn("Contenedor");
                        modelo.addColumn("Status");
                        //Asiganomos un ancho de columnas por default
                        jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
                        jTable3.getColumnModel().getColumn(2).setPreferredWidth(140);
                        jTable3.getColumnModel().getColumn(3).setPreferredWidth(140);
                        jTable3.getColumnModel().getColumn(4).setPreferredWidth(80);
                        jTable3.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable3.getColumnModel().getColumn(6).setPreferredWidth(90);

                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnxSQL.reg.next()){
                                for (int i=0;i<modelo.getColumnCount();i++){
                                     filasx[i] = cnxSQL.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modelo.addRow(filasx);
                                    jTable3.setModel(modelo);
                                    jTable3.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                        } catch (SQLException ex) {}
        try {
            cnxSQL.Conec.close();
        } catch (SQLException ex) {}
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        niguri = new javax.swing.JTextField();
        oCom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        contenedor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        envio = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tuxedo | AS400 - BPCS");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Niguri / Ord. Compra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setText("Folio Niguri:");

        jLabel2.setText("Folio Ord. Compra:");

        niguri.setEditable(false);
        niguri.setBackground(new java.awt.Color(204, 204, 255));
        niguri.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        oCom.setEditable(false);
        oCom.setBackground(new java.awt.Color(204, 204, 255));
        oCom.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("Contendor:");

        contenedor.setEditable(false);
        contenedor.setBackground(new java.awt.Color(204, 204, 255));
        contenedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setText("Num. Envio:");

        envio.setEditable(false);
        envio.setBackground(new java.awt.Color(204, 204, 255));
        envio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(niguri, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(envio)
                    .addComponent(oCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(niguri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(oCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(envio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ln.", "Producto", "Cantidad", "Folio Niguri", "Ord. Compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Info. Niguri (MPB9)", jPanel2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ord. Compra", "Factura", "Producto", "VIN", "Motor", "Empaque", "Contenedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(140);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(140);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(90);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ASN (ASNMD)", jPanel3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ord. Compra", "Producto", "VIN", "Motor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable4.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable4);
        jTable4.getColumnModel().getColumn(0).setPreferredWidth(80);
        jTable4.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable4.getColumnModel().getColumn(2).setPreferredWidth(140);
        jTable4.getColumnModel().getColumn(3).setPreferredWidth(140);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Detalle (MTA9)", jPanel4);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ln.", "Producto", "VIN", "Motor", "Envio", "Contenedor", "Status Almacen"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(140);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(140);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(80);
        jTable3.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable3.getColumnModel().getColumn(6).setPreferredWidth(90);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Almacen (WMS)", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField contenedor;
    private javax.swing.JTextField envio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField niguri;
    private javax.swing.JTextField oCom;
    // End of variables declaration//GEN-END:variables
}
