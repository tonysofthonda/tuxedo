/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class ifrmRepCorte extends javax.swing.JInternalFrame {
    private File archivoExcel;
    conexionPostgres cnx;
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    private String fde;
    private String fa;
    private String trm;
    private String sts;
    private int fila;
    private String q1;

    /**
     * Creates new form ifrmRepCorte
     * @param Pane3 
     */
    public ifrmRepCorte(JDesktopPane Pane3) {
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
        if(terminal.getSelectedIndex()>0){
            trm=" ESTACION='"+terminal.getSelectedItem().toString()+"' AND ";
        }else{
            trm="";
        }
        if(status.getSelectedIndex()>0){
            sts=" STATUS='"+status.getSelectedItem().toString()+"' AND ";
        }else{
            sts="";
        }
        cnx.consulta("SELECT " 
                        +"ID," 
                        +"ESTACION, " 
                        +"TO_CHAR(FECHA_INI,'DD/MM/YYYY'), "
                        +"TO_CHAR(FECHA_INI,'HH24:MI:SS'), "
                        +"TO_CHAR(FECHA_FIN,'DD/MM/YYYY'), "
                        +"TO_CHAR(FECHA_FIN,'HH24:MI:SS'), "
                        +"PRODUCCION, "
                        +"STATUS "
                      +"FROM "
                      +"TX_PROD_TURNO "
                      +"WHERE "
                      +trm
                      +sts
                      +"FECHA_INI "
                      +"BETWEEN "
                      +"to_timestamp('"+fde+"','MM-DD-YYYY HH24:MI:SS') AND "
                      +"to_timestamp('"+fa+" 23:59:59','MM-DD-YYYY HH24:MI:SS') "
                      +" ORDER BY ID ASC");
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
                        modelo.addColumn("Terminal");
                        modelo.addColumn("Fecha Inicio");
                        modelo.addColumn("Hora Inicio");
                        modelo.addColumn("Fecha Fin");
                        modelo.addColumn("Hora Fin");
                        modelo.addColumn("Produccion");
                        modelo.addColumn("Status");  
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(90);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(5).setPreferredWidth(90);
                        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);

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
                        } catch (SQLException ex) {}
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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        de = new com.toedter.calendar.JDateChooser();
        a = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        terminal = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jMenuItem1.setText("Recalcular");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tuxedo | Reporte de Cortes");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel1.setText("De:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel2.setText("A:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel3.setText("Terminal:");

        terminal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todo...", "Soldadura", "Pintura", "Ensamble", "Calidad", "Empaque" }));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bullet_magnify.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_color.png"))); // NOI18N
        jButton2.setToolTipText("Impresion");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icono_excel.jpg"))); // NOI18N
        jButton3.setToolTipText("Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel4.setText("Status:");

        status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos...", "Abierto", "Cerrado" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(de, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(a, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(terminal, 0, 120, Short.MAX_VALUE)
                    .addComponent(status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(280, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(de, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(terminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jLabel4)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Terminal", "Fecha Inicio", "Hora Inicio", "Fecha Fin", "Hora Fin", "Produccion", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setComponentPopupMenu(jPopupMenu1);
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
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int returnVal = archivo.showSaveDialog(null);
        if(returnVal==JFileChooser.APPROVE_OPTION){
            archivoExcel=archivo.getSelectedFile();
            exportExcel exp=new exportExcel(jTable1, archivoExcel, "Corte");
            exp.export();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        String term=(String) jTable1.getValueAt(fila, 1);
        switch(term){
            case "Soldadura":
                q1="WE";
            break;
            case "Pintura":
                q1="PA";
            break;    
            case "Ensamble":
                q1="AF";
            break;    
            case "Calidad":
                q1="VQ";
            break;    
            case "Empaque":
                q1="PK";
            break;    
            case "Embarque":
                q1="SHP";
            break;    
        }
        conexion();
            cnx.consulta("Select Count(*) from TX_ORDEN_PROD WHERE "
                       + q1
                       + " BETWEEN '"+jTable1.getValueAt(fila, 2)+"' "
                       + " AND '"+jTable1.getValueAt(fila, 4)+"' and "+q1+"_STATUS='Liberado'");
            try {
              while (cnx.reg.next()){
                cnx.ejecutar("Update TX_PROD_TURNO set PRODUCCION='"+cnx.reg.getString(1)+"'"
                           + "WHERE ID='"+jTable1.getValueAt(fila, 0)+"'");
              }
              buscar();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error"+ex);
            }
        desconexion();
        buscar();    
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser a;
    private javax.swing.JFileChooser archivo;
    private com.toedter.calendar.JDateChooser de;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox status;
    private javax.swing.JComboBox terminal;
    // End of variables declaration//GEN-END:variables
}
