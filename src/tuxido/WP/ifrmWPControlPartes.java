/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import tuxido.formato_usuario;

/**
 *
 * @author VJ002349
 */
public class ifrmWPControlPartes extends javax.swing.JInternalFrame {
    JDesktopPane Pane5;
    conexionWPPostgre cnx;
    DefaultTableModel modelo;
    DefaultTableModel modelo2;
    DefaultTableModel modelo3;
    DefaultTableModel modelo4;
    TableRowSorter ordenar;
    String fde;
    String fa;
    Object[] filasx;
    int fila;
    /**
     * Creates new form ifrmWPControlPartes
     */
    public ifrmWPControlPartes(JDesktopPane jDesktopPane5) {
        initComponents();
        initTables();
        initBrowser();
        Pane5=jDesktopPane5;
    }
        
    public void conexion(){
        cnx=new conexionWPPostgre();
        
    }
    
    public void desconexion(){
        try {
            cnx.Conec.close();
        } catch (SQLException ex) {}
    }
    
    public void initTables(){
                            
                            //Reedefinimos un Modelo de Tabla
                            modelo3 = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            //Asignamos el nuevo Modelo
                            jTable3.setModel(modelo3);
                            jTable3.setDefaultRenderer(Object.class, new formato_usuario());
                            //Agregamos las columnas al nuevo modelo
                            modelo3.addColumn("");
                            modelo3.addColumn("ID");
                            modelo3.addColumn("Factura");
                            modelo3.addColumn("Modelo");
                            modelo3.addColumn("Tipo");
                            modelo3.addColumn("Opcion");
                            modelo3.addColumn("Color");
                            modelo3.addColumn("Frame");
                            modelo3.addColumn("Engine");
                            modelo3.addColumn("Fecha Produccion");
                            modelo3.addColumn("Fecha Carga");
                            modelo3.addColumn("Status");
                            modelo3.addColumn("Order");
                            //Asiganomos un ancho de columnas por default
                            jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
                            jTable3.getColumnModel().getColumn(1).setPreferredWidth(50);
                            jTable3.getColumnModel().getColumn(2).setPreferredWidth(90);
                            jTable3.getColumnModel().getColumn(3).setPreferredWidth(90);
                            jTable3.getColumnModel().getColumn(4).setPreferredWidth(50);
                            jTable3.getColumnModel().getColumn(5).setPreferredWidth(60);
                            jTable3.getColumnModel().getColumn(6).setPreferredWidth(90);
                            jTable3.getColumnModel().getColumn(7).setPreferredWidth(110);
                            jTable3.getColumnModel().getColumn(8).setPreferredWidth(110);
                            jTable3.getColumnModel().getColumn(9).setPreferredWidth(110);
                            jTable3.getColumnModel().getColumn(10).setPreferredWidth(90);
                            jTable3.getColumnModel().getColumn(11).setPreferredWidth(90);
                            jTable3.getColumnModel().getColumn(12).setPreferredWidth(60);
                            
                            ordenar = new TableRowSorter(modelo3);
                            jTable3.setRowSorter(ordenar);
    }
    
    public void initBrowser(){
        
    }
    
    public void buscar(){
            conexion();
            String qry= "SELECT " +
                        "  TO_CHAR(A.LOAD_DATE,'DD/MM/YY') as FECHA," +
                        "  A.ID AS ENTRADA," +
                        "  A.INVOICE," +
                        "  A.UNIDADES," +
                        "  COUNT(B.FRAME) AS PROCESADAS," +
                        "  (A.UNIDADES-COUNT(B.FRAME)) AS PENDIENTES," +
                        "  TO_NUMBER(TO_CHAR((COUNT(B.FRAME)/A.UNIDADES)*100,'fm9999999.90'),'999')," +
                        "  TO_NUMBER(TO_CHAR(((A.UNIDADES-COUNT(B.FRAME))/A.UNIDADES)*100,'fm9999999.90'),'999')," +
                        "  A.USUARIO " +
                        " FROM " +
                        "  WP_ENTRADAS A, " +
                        "  WP_INVENTARIO B " +
                        " WHERE " +
                        "  A.INVOICE=B.INVOICE " +
                        " and " +
                        "  B.ORDEN>0 " +
                        " GROUP BY A.ID, A.INVOICE, A.LOAD_DATE, A.UNIDADES, A.USUARIO " +
                        " ORDER BY 2 ASC";
            cnx.consulta(qry);
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
                                 modelo.addColumn("Fecha"); 
                                 modelo.addColumn("Entrada"); 
                                 modelo.addColumn("Factura");
                                 modelo.addColumn("Unidades"); 
                                 modelo.addColumn("Procesadas"); 
                                 modelo.addColumn("Pendientes");
                                 modelo.addColumn("% Avance");
                                 modelo.addColumn("% Pendientes"); 
                                 modelo.addColumn("Usuario");
                                 
                            jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
                            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(7).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(8).setPreferredWidth(120); 
                                 
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
                                    jTable1.setDefaultRenderer(Object.class, new formato_Inventario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,ex);
                        }
            
        desconexion();
    }
    
    public void nuevasEntradas(){
        conexion();
            String qry= "SELECT " +
                        "  TO_CHAR(A.LOAD_DATE,'DD/MM/YY') as FECHA," +
                        "  A.ID AS ENTRADA," +
                        "  A.INVOICE," +
                        "  A.UNIDADES," +
                        "  COUNT(B.FRAME) AS PENDIENTES," +
                        "  (A.UNIDADES-COUNT(B.FRAME)) AS PROCESADAS," +
                        "  TO_NUMBER(TO_CHAR((COUNT(B.FRAME)/A.UNIDADES)*100,'fm9999999.90'),'999')," +
                        "  TO_NUMBER(TO_CHAR(((A.UNIDADES-COUNT(B.FRAME))/A.UNIDADES)*100,'fm9999999.90'),'999')," +
                        "  A.USUARIO " +
                        " FROM " +
                        "  WP_ENTRADAS A, " +
                        "  WP_INVENTARIO B " +
                        " WHERE " +
                        "  A.INVOICE=B.INVOICE " +
                        " and " +
                        "  B.ORDEN=0 " +
                        " GROUP BY A.ID, A.INVOICE, A.LOAD_DATE, A.UNIDADES, A.USUARIO " +
                        " HAVING (A.UNIDADES-COUNT(B.FRAME))  = 0 " +
                        " ORDER BY 1 ASC";
            cnx.consulta(qry);
                        modelo4 = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };                       
                        
                        ordenar = new TableRowSorter(modelo4);
                        jTable4.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable4.setModel(modelo4);
                        //Agregamos las columnas al nuevo modelo
                                 modelo4.addColumn("Fecha"); 
                                 modelo4.addColumn("Entrada"); 
                                 modelo4.addColumn("Factura");
                                 modelo4.addColumn("Unidades"); 
                                 modelo4.addColumn("Pendientes"); 
                                 modelo4.addColumn("Procesadas");
                                 modelo4.addColumn("% Pendientes");
                                 modelo4.addColumn("% Avance"); 
                                 modelo4.addColumn("Usuario");
                                 
                            jTable4.getColumnModel().getColumn(0).setPreferredWidth(80);
                            jTable4.getColumnModel().getColumn(1).setPreferredWidth(80);
                            jTable4.getColumnModel().getColumn(2).setPreferredWidth(100);
                            jTable4.getColumnModel().getColumn(3).setPreferredWidth(80);
                            jTable4.getColumnModel().getColumn(4).setPreferredWidth(80);
                            jTable4.getColumnModel().getColumn(5).setPreferredWidth(80);
                            jTable4.getColumnModel().getColumn(6).setPreferredWidth(80);
                            jTable4.getColumnModel().getColumn(7).setPreferredWidth(90);
                            jTable4.getColumnModel().getColumn(8).setPreferredWidth(120); 
                                 
                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo4.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                for (int i=0;i<modelo4.getColumnCount();i++){
                                     filasx[i] = cnx.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modelo4.addRow(filasx);
                                    jTable4.setModel(modelo4);
                                    jTable4.setDefaultRenderer(Object.class, new formato_Inventario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,ex);
                        }
        desconexion();
    }
    
    public void infoInventario(){
        conexion();
            cnx.consulta(   " SELECT " +
                            "  invoice," +
                            "  modelo," +
                            "  tipo," +
                            "  opcion," +
                            "  color," +
                            "  sum (CASE WHEN ORDEN>0 THEN 1 ELSE 0 END) AS TERMINADAS, " +
                            "  sum (CASE WHEN ORDEN=0 THEN 1 ELSE 0 END) AS PENDIENTES, " +
                            "  CASE WHEN sum(CASE WHEN ORDEN=0 THEN 1 ELSE 0 END) = 0 THEN 'Cerrado'  ELSE 'Pendiente' end as BALANCE " +
                            " FROM " +
                            "  WP_INVENTARIO " +
                            " WHERE " +
                            "  INVOICE='"+jTable1.getValueAt(fila, 2)+"' " +
                            " GROUP BY " +
                            "  invoice, modelo,tipo,opcion,color " +
                            " ORDER BY MODELO ");
                            //Reedefinimos un Modelo de Tabla
                            modelo2 = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            
                            //Asignamos el nuevo Modelo
                            jTable2.setModel(modelo2);
                            //Agregamos las columnas al nuevo modelo
                            modelo2.addColumn("Factura");
                            modelo2.addColumn("Modelo");
                            modelo2.addColumn("Tipo");
                            modelo2.addColumn("Opcion");
                            modelo2.addColumn("Color");
                            modelo2.addColumn("Terminadas");
                            modelo2.addColumn("Pendiente");
                            modelo2.addColumn("Balance");
                            //Asiganomos un ancho de columnas por default
                            jTable2.getColumnModel().getColumn(0).setPreferredWidth(100);
                            jTable2.getColumnModel().getColumn(1).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
                            jTable2.getColumnModel().getColumn(4).setPreferredWidth(60);
                            jTable2.getColumnModel().getColumn(5).setPreferredWidth(90);
                            jTable2.getColumnModel().getColumn(6).setPreferredWidth(90);
                            jTable2.getColumnModel().getColumn(7).setPreferredWidth(90);

                            //asigno el tamaño del arreglo con la cantidad de columnas:
                            filasx = new Object[modelo2.getColumnCount()];
                            try {
                                //escribo las filas:
                                while (cnx.reg.next()){
                                    for (int i=0;i<modelo2.getColumnCount();i++){
                                         filasx[i] = cnx.reg.getObject(i+1);
                                    } 
                                    //escribo las filas del resultado SQL//
                                        modelo2.addRow(filasx);
                                        jTable2.setModel(modelo2);
                                        jTable2.setDefaultRenderer(Object.class, new formato_usuario());
                                        ordenar = new TableRowSorter(modelo2);
                                        jTable2.setRowSorter(ordenar);
                                }
                                cnx.reg.close();
                            } catch (SQLException ex) {

                            }
        desconexion();
    }
    
    public void infoInventarioDetalle(){
        conexion();
            cnx.consulta(   " SELECT row_number() over() as ROWNUM, ID,INVOICE,MODELO,TIPO,OPCION,COLOR,FRAME,ENGINE,PRODUCTION_DATE,LOAD_DATE,STATUS,ORDEN " +
                            " FROM ( SELECT ID," +
                            "  INVOICE," +
                            "  MODELO," +
                            "  TIPO," +
                            "  OPCION," +
                            "  COLOR," +
                            "  FRAME," +
                            "  ENGINE," +
                            "  TO_CHAR(PRODUCTION_DATE,'DD/MM/YY') AS PRODUCTION_DATE," +
                            "  TO_CHAR(LOAD_DATE,'DD/MM/YY') AS LOAD_DATE," +
                            "  STATUS," +
                            "  ORDEN " +
                            " FROM " +
                            "  WP_INVENTARIO " +
                            " WHERE " +
                            " INVOICE='"+jTable1.getValueAt(fila, 2)+"' " +
                            " AND ORDEN=0" +
                            " ORDER BY ID) as d");

                            //asigno el tamaño del arreglo con la cantidad de columnas:
                            filasx = new Object[modelo3.getColumnCount()];
                            try {
                                    //escribo las filas:                                    
                                    if (!cnx.reg.next()) {
                                            while(modelo3.getRowCount()>0){
                                                for(int i=0;i<modelo3.getRowCount();i++){
                                                    modelo3.removeRow(i);
                                                }
                                            }
                                    } else {
                                        while(modelo3.getRowCount()>0){
                                            for(int i=0;i<modelo3.getRowCount();i++){
                                                modelo3.removeRow(i);
                                            }
                                        }
                                        do {
                                            for (int i=0;i<modelo3.getColumnCount();i++){
                                             filasx[i] = cnx.reg.getObject(i+1);
                                            } 
                                        //escribo las filas del resultado SQL//
                                        modelo3.addRow(filasx);
                                        //jTable3.setModel(modelo3);
                                        } while (cnx.reg.next());
                                    }
//                                }
                                cnx.reg.close();
                            } catch (SQLException ex) {

                            }
        desconexion();
    }
    
//    public void inventario(){
//        conexion();
//            cnx.consulta("SELECT COUNT(ID) AS INVENTARIO FROM WP_INVENTARIO WHERE ORDEN=0");
//            try{
//                while(cnx.reg.next()){
//                    inv.setText(cnx.reg.getString(1));
//                }
//            }catch(SQLException ex){
//                
//            }
//            cnx.consulta("SELECT COUNT(ID) AS INVENTARIO FROM WP_INVENTARIO WHERE status='Terminada'");
//            try{
//                while(cnx.reg.next()){
//                    historico.setText(cnx.reg.getString(1));
//                }
//            }catch(SQLException ex){
//                
//            }
//        desconexion();
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tuxedo - WaterPump |  Control de Partes");

        jSplitPane1.setDividerLocation(850);

        jSplitPane2.setDividerLocation(180);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Modelos"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Modelo", "Tipo", "Opcion", "Color", "Terminadas", "Pendientes", "Status"
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
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
        );

        jSplitPane2.setTopComponent(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Unidades en Retraso"));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Factura", "Modelo", "Tipo", "Opcion", "Color", "Frame", "Engine", "Fecha Produccion", "Fecha Carga", "Status", "Orden"
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
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jPanel5);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );

        jSplitPane1.setRightComponent(jPanel3);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Entradas en Proceso"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Entrada", "Factura", "Unidades", "Procesadas", "Pendientes", "% Avance", "% Pendientes", "Usuario"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 837, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 837, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel6);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Entradas Nuevas"));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Entrada", "Factura", "Unidades", "Procesadas", "Pendientes", "% Avance", "% Pendientes", "Usuario"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable4.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable4.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTable4.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTable4.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(8).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_magnify.png"))); // NOI18N
        jButton1.setToolTipText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_open_mark.png"))); // NOI18N
        jButton3.setToolTipText("Reportes");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dashboard"));

        jScrollPane5.setViewportView(jEditorPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        buscar();
//        inventario();
        nuevasEntradas();
        //inihilo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
        infoInventario();
        infoInventarioDetalle();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
        infoInventario();
        infoInventarioDetalle();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ifrmWPCrtlPartesReportes ifrmtswp=new ifrmWPCrtlPartesReportes(Pane5);
        Dimension desktopSize = Pane5.getSize();
        Dimension jInternalFrameSize = ifrmtswp.getSize();
        Pane5.add(ifrmtswp);
        ifrmtswp.setLocation(20,100);
        ifrmtswp.show();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
