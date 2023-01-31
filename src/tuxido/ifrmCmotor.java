/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author vj002349
 */
public class ifrmCmotor extends javax.swing.JInternalFrame {
    conexionPostgres cnx;
    private int num;
    private String cadena;
    private String fecha;
    private String hora;
    private String modelo;
    /**
     * Creates new form ifrmCmotor
     *
     * @param cd 
     */
    public ifrmCmotor(String cd) {
        initComponents();
        VIN.setText(cd);
        info();
    }

    /**
     *
     */
    public void conexion(){
        cnx=new conexionPostgres ();
        
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
    public void info(){
        try {
            conexion();
                cnx.consulta("select MOTOR, NUMERO, STATUS, ID_MODELO from TX_ORDEN_PROD WHERE VIN='"+VIN.getText()+"'");
                while(cnx.reg.next()){
                    STATUS.setText(cnx.reg.getString(3));
                    MOTOR.setText(cnx.reg.getString(1));
                    NUMERO.setText(cnx.reg.getString(2));
                    modelo=cnx.reg.getString(4);
                }
                cnx.consulta("select caracteres from TX_MOTOR where motor='"+MOTOR.getText()+"' and aplica='"+modelo+"' and status='Activo'");
                while(cnx.reg.next()){
                    num=cnx.reg.getInt(1);
                }
                System.out.println(num);
            desconexion();
        } catch (SQLException ex) {}
    }

    /**
     *
     */
    public void guardar(){
        //conexion();   
                    Date fechaActual=new Date();
                    SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                    SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
                    fecha=forFecha.format(fechaActual);
                    hora=forHora.format(fechaActual);
                    cnx.ejecutar("update TX_ORDEN_PROD SET numero='"+cadena+"' where vin='"+VIN.getText()+"'");
                    cnx.ejecutar("update TX_ORDEN_PROD SET af='"+fecha+"' where vin='"+VIN.getText()+"'");                    
                    cnx.ejecutar("update TX_ORDEN_PROD SET af_tim=current_timestamp where vin='"+VIN.getText()+"'");
                    cnx.ejecutar("INSERT INTO TX_INCIDENTES (vin,terminal,tipo,descr,fecha,hora) "
                        +"VALUES ('"+VIN.getText()+"','Ensamble','Otro',"
                        + "'Cambio de Motor Anterior "+NUMERO.getText()+" - Nuevo "+cadena+"','"+fecha+"',current_timestamp)");
        //desconexion();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        NMOTOR = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        VIN = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        STATUS = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        MOTOR = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        NUMERO = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Tuxedo | Cambio de Motor");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/info.png"))); // NOI18N
        jLabel5.setText("<html>El cambio de Motor genera un registro de Incidencia con el numero de unidad, numero anterior y posterior de motor, fecha y hora.</html>");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel6.setText("Nuevo Motor:");

        NMOTOR.setBackground(new java.awt.Color(204, 204, 255));
        NMOTOR.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk_black.png"))); // NOI18N
        jButton1.setText("Guardar");
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NMOTOR))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(NMOTOR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Info. Unidad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setText("VIN:");

        VIN.setEditable(false);
        VIN.setBackground(new java.awt.Color(204, 204, 255));
        VIN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setText("Status:");

        STATUS.setEditable(false);
        STATUS.setBackground(new java.awt.Color(204, 204, 255));
        STATUS.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("Motor:");

        MOTOR.setEditable(false);
        MOTOR.setBackground(new java.awt.Color(204, 204, 255));
        MOTOR.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setText("Numero:");

        NUMERO.setEditable(false);
        NUMERO.setBackground(new java.awt.Color(204, 204, 255));
        NUMERO.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(STATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(MOTOR, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NUMERO))
                    .addComponent(VIN, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(VIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(STATUS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(MOTOR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(NUMERO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
                 int temp=NMOTOR.getText().length()-num;
                 System.out.println(temp);
                 cadena=NMOTOR.getText();
                 cadena=cadena.substring(temp);
                 System.out.println(cadena);
                 try {
                    conexion();
                    cnx.consulta("select COUNT(*) FROM TX_ORDEN_PROD WHERE MOTOR='"+MOTOR.getText()+"' AND NUMERO='"+cadena+"'");
                    while(cnx.reg.next()){
                        if(cnx.reg.getInt(1)<1){
                            guardar();
                            dispose();
                        }else{
                            JOptionPane.showMessageDialog(null,"Este Numero de Motor ya fue Asignado a otra Unidad");
                            NMOTOR.setText("");
                            NMOTOR.requestFocus();
                        }
                    }
                    desconexion();
                 } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(null,ex);
                 }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField MOTOR;
    private javax.swing.JTextField NMOTOR;
    private javax.swing.JTextField NUMERO;
    private javax.swing.JTextField STATUS;
    private javax.swing.JTextField VIN;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
