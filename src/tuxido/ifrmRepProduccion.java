/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.io.File;
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
public class ifrmRepProduccion extends javax.swing.JInternalFrame {
    private File archivoExcel;
    conexionPostgres cnx;
    private String fde;
    private String fa;
    private String sts;
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    String q1;
    /**
     * Creates new form ifrmRepProduccion
     * @param Pane3 
     */
    public ifrmRepProduccion(JDesktopPane Pane3) {
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
        switch(estacion.getSelectedIndex()){
            case 0:
                q1="";
            break;    
            case 1:
                q1="WE";
            break;
            case 2:
                q1="PA";
            break;    
            case 3:
                q1="AF";
            break;    
            case 4:
                q1="VQ";
            break;    
            case 5:
                q1="PK";
            break;    
            case 6:
                q1="SHP";
            break;    
        }
        if(estacion.getSelectedIndex()>0){
            Date fch=de.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            fde=sdf.format(fch);
            fch=a.getDate();
            fa=sdf.format(fch);
            String q2="select  "+
                    "ID,"+
                    "ID_ORDEN," +
                    "VIN," +
                    "ID_MODELO," +
                    "MOD_DESC," +
                    "ANIO," +
                    "MOTOR||NUMERO," +
                    "ID_COLOR," +
                    "COL_DESC," +
                    "DESTINO," +
                    "TO_CHAR(PRODUCCION,'dd-MM-yyyy')," +
                    "TO_CHAR(WE,'dd-MM-yyyy')," +
                    "TO_CHAR(WE_TIM,'HH24:MI:SS')," +
                    "WE_STATUS," +
                    "TO_CHAR(PA,'dd-MM-yyyy')," +
                    "TO_CHAR(PA_TIM,'HH24:MI:SS')," +
                    "PA_STATUS," +
                    "TO_CHAR(AF,'dd-MM-yyyy')," +
                    "TO_CHAR(AF_TIM,'HH24:MI:SS')," +
                    "AF_STATUS," +
                    "TO_CHAR(VQ,'dd-MM-yyyy')," +
                    "TO_CHAR(VQ_TIM,'HH24:MI:SS')," +
                    "VQ_STATUS," +
                    "TO_CHAR(PK,'dd-MM-yyyy')," +
                    "TO_CHAR(PK_TIM,'HH24:MI:SS')," +
                    "PK_STATUS," +
                    "TO_CHAR(SHP,'dd-MM-yyyy')," +
                    "TO_CHAR(SHP_TIM,'HH24:MI:SS')," +
                    "SHP_STATUS," +
                    "NUM_EMP," +
                    "TARIMA," +
                    "CONTENEDOR," +
                    "STATUS," +
                    "ENVIO," +
                    "ID_ENVIO"
                    +" from TX_ORDEN_PROD WHERE "+q1+" BETWEEN '"+fde+"' AND '"+fa+"' AND "+q1+"_STATUS='Liberado' ORDER BY ID ASC"; 
            cnx.consulta(q2);
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
                                 modelo.addColumn("ID_ORDEN"); 
                                 modelo.addColumn("VIN");
                                 modelo.addColumn("ID_MODELO"); 
                                 modelo.addColumn("MOD_DESC"); 
                                 modelo.addColumn("ANIO");
                                 modelo.addColumn("MOTOR");
                                 modelo.addColumn("ID_COLOR"); 
                                 modelo.addColumn("COL_DESC");
                                 modelo.addColumn("DESTINO");
                                 modelo.addColumn("PRODUCCION");
                                 modelo.addColumn("WE");
                                 modelo.addColumn("WE_TIM");
                                 modelo.addColumn("WE_STATUS");
                                 modelo.addColumn("PA");
                                 modelo.addColumn("PA_TIM");
                                 modelo.addColumn("PA_STATUS"); 
                                 modelo.addColumn("AF");
                                 modelo.addColumn("AF_TIM");
                                 modelo.addColumn("AF_STATUS"); 
                                 modelo.addColumn("VQ");
                                 modelo.addColumn("VQ_TIM");
                                 modelo.addColumn("VQ_STATUS"); 
                                 modelo.addColumn("PK");
                                 modelo.addColumn("PK_TIM");
                                 modelo.addColumn("PK_STATUS"); 
                                 modelo.addColumn("SHP");
                                 modelo.addColumn("SHP_TIM");
                                 modelo.addColumn("SHP_STATUS"); 
                                 modelo.addColumn("NUM_EMP");
                                 modelo.addColumn("TARIMA"); 
                                 modelo.addColumn("CONTENEDOR"); 
                                 modelo.addColumn("STATUS"); 
                                 modelo.addColumn("ENVIO");
                                 modelo.addColumn("ID_ENVIO");

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
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,ex);
                        }
        }else{
             JOptionPane.showMessageDialog(null,"Selecciona una Opcion...");
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

        archivo = new javax.swing.JFileChooser();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        de = new com.toedter.calendar.JDateChooser();
        a = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        estacion = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jMenuItem2.setText("Contar");
        jMenuItem2.setToolTipText("");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tuxedo | Produccion");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel1.setText("De:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel2.setText("A:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bullet_magnify.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icono_excel.jpg"))); // NOI18N
        jButton3.setText("Excel");
        jButton3.setToolTipText("Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel3.setText("Estacion:");

        estacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije una opcion...", "Soldadura", "Pintura", "Ensamble", "Calidad", "Empaque", "Embarque" }));

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
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(estacion, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(de, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jLabel3)
                    .addComponent(estacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "ID_ORDEN", "VIN", "ID_MODELO", "MODELO", "ANIO", "MOTOR", "NUMERO", "ID_COLOR", "COLOR", "DESTINO", "PRODUCCION", "WE", "WE_TIM", "WE_STATUS", "PA", "PA_TIM", "PA_STATUS", "AF", "AF_TIM", "AF_STATUS", "VQ", "VQ_TIM", "VQ_STATUS", "PK", "PK_TIM", "PK_STATUS", "SHP", "SHP_TIM", "SHP_STATUS", "NUM_EMP", "TARIMA", "CONTENEDOR", "STATUS", "ENVIO", "ID_ENVIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
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
            exportExcel exp=new exportExcel(jTable1, archivoExcel, "Produccion");
            exp.export();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        String lineas = "";
        int total=0;
        try {
        conexion();
            cnx.consulta("Select Mod_Desc, Count(*) from TX_ORDEN_PROD WHERE "+q1+" BETWEEN '"+fde+"' AND '"+fa+"' Group By Mod_Desc");
            while (cnx.reg.next()){
                lineas=lineas+"<p><strong>"+cnx.reg.getString(1)+"</strong>\t\t"+cnx.reg.getString(2);
                total=total+cnx.reg.getInt(2);
            }
            JOptionPane.showMessageDialog(null,"<html>Numero Total de Unidades: <strong>"+total+"</strong>"
                                             + "<p>"
                                             + "<p>Detalle de Unidades:"
                                             + lineas+"</hrml>");

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"ERROR: "+e);
        }
        desconexion();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser a;
    private javax.swing.JFileChooser archivo;
    private com.toedter.calendar.JDateChooser de;
    private javax.swing.JComboBox estacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
