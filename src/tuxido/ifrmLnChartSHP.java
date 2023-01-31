/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class ifrmLnChartSHP extends javax.swing.JInternalFrame {
    int fila;
    conexionPostgres cnx;
    DefaultTableModel modelo;
    TableRowSorter ordenar;
    Object[] filasx;
    /**
     * Creates new form ifrmLnChartSHP
     */
    public ifrmLnChartSHP() {
        initComponents();
        Date fechaActual=new Date();
        SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
        String fecha=forFecha.format(fechaActual);
        String queryT="select row_number() over() as ROWNUM, id, transportista, contenedor, destino, TO_CHAR(fecha,'dd-MM-yyyy'), TO_CHAR(HORA,'HH24:MI:SS'), unidades, tarimas, status " +
                                        "from TX_INFO_EMBARQUE WHERE FECHA='"+fecha+"' " +
                                        "ORDER BY HORA ASC ";
        String queryP="select count(*), mod_desc from TX_ORDEN_PROD WHERE shp='"+fecha+"' group by mod_desc order by count (*) desc";
        String e="Embarque-Shipping";
        hiloSegLnChartSHP hChtl=new hiloSegLnChartSHP(panelChartLn, tablaLn, queryT, e);
        hChtl.start();
        hiloSegLnChartP hChtp=new hiloSegLnChartP(areaModelos,queryP);
        hChtp.start();
        queryP="select count(*), col_desc from TX_ORDEN_PROD WHERE shp='"+fecha+"' group by col_desc order by count (*) desc";
        hiloSegLnChartPC hChtpc=new hiloSegLnChartPC(areaColores,queryP);
        hChtpc.start();
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
    public void infoReg(){
      conexion();
      cnx.consulta("select row_number() over() as ROWNUM, vin, mod_desc, col_desc, anio, TO_CHAR(SHP_TIM,'dd/mm/yyyy'), TO_CHAR(SHP_TIM,'HH24:MI:SS'), shp_status, status, num_emp, tarima, contenedor, case when id_envio = 0 then 'No' else CAST (id_envio as VARCHAR) end id_envio from" +
                                        "(" +
                                        "select vin, mod_desc, col_desc, anio,SHP_TIM, shp_status, status, num_emp, tarima, contenedor, id_envio " +
                                        "from TX_ORDEN_PROD WHERE id_envio='"+tablaLn.getValueAt(fila, 0).toString()+"' " +
                                        "ORDER BY SHP_TIM ASC " +
                                        ") as d ");
      
      modelo = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int rowIndex, int vColIndex) {
             return false;
        }
      };
      ordenar = new TableRowSorter(modelo);
      tabla.setModel(modelo);
      tabla.setRowSorter(ordenar);
      //Agregamos las columnas al nuevo modelo
      modelo.addColumn("Unidad");
      modelo.addColumn("VIN");
      modelo.addColumn("Modelo");
      modelo.addColumn("Color");
      modelo.addColumn("Año");
      modelo.addColumn("Fecha Registro"); 
      modelo.addColumn("Hora Registro");
      modelo.addColumn("Status Terminal");
      modelo.addColumn("Status Linea");
      modelo.addColumn("Empaque");
      modelo.addColumn("Tarima");
      modelo.addColumn("Contenedor");
      modelo.addColumn("Envio");
      //Asiganomos un ancho de columnas por default
      tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
      tabla.getColumnModel().getColumn(1).setPreferredWidth(140);
      tabla.getColumnModel().getColumn(5).setPreferredWidth(95);
      tabla.getColumnModel().getColumn(6).setPreferredWidth(90);
      tabla.getColumnModel().getColumn(7).setPreferredWidth(130);
      tabla.getColumnModel().getColumn(8).setPreferredWidth(90);
      tabla.getColumnModel().getColumn(11).setPreferredWidth(90);
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
                                tabla.setModel(modelo);
                            }
      } catch (SQLException ex) {
          System.out.println(ex);
      }
      tabla.setDefaultRenderer(Object.class, new formato_usuario());
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

        jSplitPane1 = new javax.swing.JSplitPane();
        panelChartLn = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        areaModelos = new javax.swing.JPanel();
        areaColores = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaLn = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Area de Embarque");

        jSplitPane1.setDividerLocation(260);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelChartLn.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelChartLnLayout = new javax.swing.GroupLayout(panelChartLn);
        panelChartLn.setLayout(panelChartLnLayout);
        panelChartLnLayout.setHorizontalGroup(
            panelChartLnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );
        panelChartLnLayout.setVerticalGroup(
            panelChartLnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );

        jSplitPane1.setTopComponent(panelChartLn);

        jSplitPane3.setDividerLocation(1300);

        jSplitPane2.setDividerLocation(219);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        areaModelos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modelos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        javax.swing.GroupLayout areaModelosLayout = new javax.swing.GroupLayout(areaModelos);
        areaModelos.setLayout(areaModelosLayout);
        areaModelosLayout.setHorizontalGroup(
            areaModelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        areaModelosLayout.setVerticalGroup(
            areaModelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane2.setTopComponent(areaModelos);

        areaColores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Colores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        javax.swing.GroupLayout areaColoresLayout = new javax.swing.GroupLayout(areaColores);
        areaColores.setLayout(areaColoresLayout);
        areaColoresLayout.setHorizontalGroup(
            areaColoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        areaColoresLayout.setVerticalGroup(
            areaColoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(areaColores);

        jSplitPane3.setRightComponent(jSplitPane2);

        jSplitPane4.setDividerLocation(150);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Unidad", "VIN", "Modelo", "Color", "Año", "Fecha Registro", "Hora Registro", "Status", "Empaque", "Tarima", "Envio", "Contenedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabla);

        jSplitPane4.setRightComponent(jScrollPane1);

        tablaLn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Transportista", "Contenedor", "Destino", "Fecha", "Hora Carga", "Unidades", "Tarimas", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaLn.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaLn.getTableHeader().setReorderingAllowed(false);
        tablaLn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaLnMouseClicked(evt);
            }
        });
        tablaLn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaLnKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablaLn);

        jSplitPane4.setLeftComponent(jScrollPane2);

        jSplitPane3.setLeftComponent(jSplitPane4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, Short.MAX_VALUE)
        );

        jSplitPane1.setBottomComponent(jPanel1);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/SHP.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaLnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaLnKeyReleased
        // TODO add your handling code here:
        fila=tablaLn.getSelectedRow();
        infoReg();
    }//GEN-LAST:event_tablaLnKeyReleased

    private void tablaLnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaLnMouseClicked
        // TODO add your handling code here:
        fila=tablaLn.getSelectedRow();
        infoReg();
    }//GEN-LAST:event_tablaLnMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaColores;
    private javax.swing.JPanel areaModelos;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JPanel panelChartLn;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tablaLn;
    // End of variables declaration//GEN-END:variables
}
