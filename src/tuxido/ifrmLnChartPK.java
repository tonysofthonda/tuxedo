/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vj002349
 */
public class ifrmLnChartPK extends javax.swing.JInternalFrame {

    /**
     * Creates new form ifrmLnChartPK
     */
    public ifrmLnChartPK() {
        initComponents();
                Date fechaActual=new Date();
        SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
        String fecha=forFecha.format(fechaActual);
        String queryL="select VIN, TO_CHAR(PK_TIM,'HH24:MI:SS'), row_number() over() as ROWNUM, "+
                                                         "mod_desc, col_desc from" +
                                                         "(" +
                                                         "select VIN, PK_TIM,mod_desc, col_desc " +
                                                         "from TX_ORDEN_PROD WHERE PK='"+fecha+"' " +
                                                         "ORDER BY PK_TIM ASC) as d";
        String queryT="select row_number() over() as ROWNUM, vin, mod_desc, col_desc, anio, TO_CHAR(PK_TIM,'dd/mm/yyyy'), TO_CHAR(PK_TIM,'HH24:MI:SS'), pk_status, status, num_emp, tarima, contenedor, case when id_envio = 0 then 'No' else CAST (id_envio as VARCHAR) end id_envio  from" +
                                        "(" +
                                        "select vin, mod_desc, col_desc, anio,PK_TIM, pk_status, status, num_emp, tarima, contenedor, id_envio " +
                                        "from TX_ORDEN_PROD WHERE pk='"+fecha+"' " +
                                        "ORDER BY PK_TIM ASC " +
                                        ") as d ORDER BY ROWNUM DESC";
        String queryP="select count(*), mod_desc from TX_ORDEN_PROD WHERE pk='"+fecha+"' group by mod_desc order by count (*) desc";
        String e="Empaque-Packing";
        hiloSegLnChartLn hChtl=new hiloSegLnChartLn(panelChartLn, tabla, queryL, queryT, e);
        hChtl.start();
        hiloSegLnChartP hChtp=new hiloSegLnChartP(areaModelos,queryP);
        hChtp.start();
        queryP="select count(*), col_desc from TX_ORDEN_PROD WHERE pk='"+fecha+"' group by col_desc order by count (*) desc";
        hiloSegLnChartPC hChtpc=new hiloSegLnChartPC(areaColores,queryP);
        hChtpc.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel17 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panelChartLn = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jSplitPane2 = new javax.swing.JSplitPane();
        areaModelos = new javax.swing.JPanel();
        areaColores = new javax.swing.JPanel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Area de Empaque");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/PK.png"))); // NOI18N

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

        jSplitPane3.setLeftComponent(jScrollPane1);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        jSplitPane1.setBottomComponent(jPanel1);

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
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaColores;
    private javax.swing.JPanel areaModelos;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JPanel panelChartLn;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
