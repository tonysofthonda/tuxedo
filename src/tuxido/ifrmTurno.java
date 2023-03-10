/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author vj002349
 */
public class ifrmTurno extends javax.swing.JInternalFrame {
    conexionPostgres cnx;
    private String hora;
    /**
     * Creates new form ifrmTurno
     * @param term 
     */
    public ifrmTurno(String term) {
        initComponents();
        estacion.setText(term);
        info();
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
    public void info(){
        conexion();
            cnx.consulta("select PRODUCCION,TO_CHAR(FECHA_INI,'DD/MM/YYYY HH:MI:SS AM'),TO_CHAR(FECHA_FIN,'DD/MM/YYYY HH:MI:SS AM'),STATUS from TX_PROD_TURNO WHERE ESTACION='"+estacion.getText()+"' and STATUS='Abierto'");
        try {
            while(cnx.reg.next()){
                regTurno.setText(cnx.reg.getString(1));
                hrIni.setText(cnx.reg.getString(2));
                hrFin.setText(cnx.reg.getString(3));
                Status.setText(cnx.reg.getString(4));
            }
        } catch (SQLException ex) {}
        
        if(Status.getText().equals("Abierto")){
            iniciar.setEnabled(false);
        }else{
            cerrar.setEnabled(false);
            
        }
        desconexion();
    }
    /**
     *
     */
    public void iniciarProd(){
        conexion();
        Date fechaActual=new Date();
        SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
        hora=forHora.format(fechaActual);
        cnx.ejecutar("INSERT INTO TX_PROD_TURNO (ESTACION,FECHA_INI,PRODUCCION,STATUS)VALUES('"+estacion.getText()+"','"+hora+"','0','Abierto')");
        desconexion();
        dispose();
    }
    /**
     *
     */
    public void cerrarProd(){
        conexion();
        Date fechaActual=new Date();
        SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
        hora=forHora.format(fechaActual);
        cnx.ejecutar("UPDATE TX_PROD_TURNO SET FECHA_FIN='"+hora+"' where ESTACION='"+estacion.getText()+"' and status='Abierto'");
        cnx.ejecutar("UPDATE TX_PROD_TURNO SET STATUS='Cerrado' where ESTACION='"+estacion.getText()+"' and status='Abierto'");
        desconexion();
        dispose();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        estacion = new javax.swing.JTextField();
        regTurno = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cerrar = new javax.swing.JButton();
        hrIni = new javax.swing.JTextField();
        hrFin = new javax.swing.JTextField();
        Status = new javax.swing.JLabel();
        iniciar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Tuxedo | Turno de Linea");

        jLabel1.setText("Estacion:");

        estacion.setEditable(false);
        estacion.setBackground(new java.awt.Color(204, 204, 255));
        estacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        regTurno.setEditable(false);
        regTurno.setBackground(new java.awt.Color(204, 204, 255));
        regTurno.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setText("Registros en Turno:");

        jLabel3.setText("Fecha y Hora Inicio:");

        jLabel4.setText("Fecha y Hora Fin:");

        cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stop_red.png"))); // NOI18N
        cerrar.setText("Cerrar");
        cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });

        hrIni.setEditable(false);
        hrIni.setBackground(new java.awt.Color(204, 204, 255));
        hrIni.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        hrFin.setEditable(false);
        hrFin.setBackground(new java.awt.Color(204, 204, 255));
        hrFin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        Status.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        Status.setForeground(new java.awt.Color(102, 153, 0));
        Status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        iniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/play_green.png"))); // NOI18N
        iniciar.setText("Iniciar");
        iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(iniciar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cerrar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hrFin, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(hrIni, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(regTurno)
                            .addComponent(estacion))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(estacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(regTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hrIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(hrFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cerrar)
                    .addComponent(iniciar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarActionPerformed
        // TODO add your handling code here:
        iniciarProd();
    }//GEN-LAST:event_iniciarActionPerformed

    private void cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarActionPerformed
        // TODO add your handling code here:
        cerrarProd();
    }//GEN-LAST:event_cerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Status;
    private javax.swing.JButton cerrar;
    private javax.swing.JTextField estacion;
    private javax.swing.JTextField hrFin;
    private javax.swing.JTextField hrIni;
    private javax.swing.JButton iniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField regTurno;
    // End of variables declaration//GEN-END:variables
}
