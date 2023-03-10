/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author vj002349
 */
public class ifrmWPModMotor extends javax.swing.JInternalFrame {
    conexionWPPostgre cnxO;
    String idn;
    String opc;
    /**
     * Creates new form ifrmWPModMotor
     */
    public ifrmWPModMotor(String id) {
        initComponents();
        idn=id;
        informacion();
        aplica.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                infomod((String) aplica.getSelectedItem());
            }
        });
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

    
    public void informacion(){
        conexion();
        cnxO.consulta("select * from WP_MOTOR WHERE id='"+idn+"'");
        try {
            while(cnxO.reg.next()){
                codigo.setText(cnxO.reg.getString(2));
                aplica.addItem(cnxO.reg.getObject(3));
                opc=cnxO.reg.getString(3);
                desc.setText(cnxO.reg.getString(4));
                numCar.setText(cnxO.reg.getString(6));
                Modelo.setText(cnxO.reg.getString(7));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmWPModMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
        modelos();
    }
        
    public void modelos(){
        conexion();
        cnxO.consulta("select distinct codigo_modelo from WP_MODELO where status='Activo' and codigo_modelo<>'"+opc+"'");
        try {
            while(cnxO.reg.next()){
                aplica.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmWPAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
    }
    /**
     *
     * @param opcn
     */
    public void infomod(String opcn){
        conexion();
        cnxO.consulta("select descripcion from WP_MODELO where status='Activo' and codigo_modelo='"+opcn+"'");
        try {
            while(cnxO.reg.next()){
                desc.setText(cnxO.reg.getString(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmWPAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
    }
    /**
     *
     */
    public void actualizar(){
        conexion();
        if(codigo.getText().length()>0&&numCar.getText().length()>0){
            cnxO.ejecutar("UPDATE WP_MOTOR SET motor='"+codigo.getText()+"' where id='"+idn+"'");
            cnxO.ejecutar("UPDATE WP_MOTOR SET aplica='"+aplica.getSelectedItem().toString()+"' where id='"+idn+"'");
            cnxO.ejecutar("UPDATE WP_MOTOR SET descripcion='"+desc.getText()+"' where id='"+idn+"'");
            cnxO.ejecutar("UPDATE WP_MOTOR SET caracteres='"+numCar.getText()+"' where id='"+idn+"'");
            cnxO.ejecutar("UPDATE WP_MOTOR SET modelo='"+Modelo.getText()+"' where id='"+idn+"'");
            JOptionPane.showMessageDialog(null,"El registro fue modificado con exito...");
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Informacion incompleta, valida la informacion");
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

        aplica = new javax.swing.JComboBox();
        desc = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        numCar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        Modelo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Tuxedo - WaterPump | Modificar Motor");

        desc.setEditable(false);
        desc.setBackground(new java.awt.Color(204, 204, 255));
        desc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk_black.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        numCar.setBackground(new java.awt.Color(204, 204, 255));
        numCar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setText("Num. Caracteres:");

        jLabel3.setText("Descripcion:");

        jLabel4.setText("Modelo Aplica:");

        jLabel1.setText("Codigo Motor:");

        codigo.setBackground(new java.awt.Color(204, 204, 255));
        codigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        Modelo.setBackground(new java.awt.Color(204, 204, 255));
        Modelo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setText("Modelo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(desc)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(numCar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(aplica, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 127, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Modelo)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel6)
                        .addComponent(Modelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(aplica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel2)
                    .addComponent(numCar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Modelo;
    private javax.swing.JComboBox aplica;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField desc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField numCar;
    // End of variables declaration//GEN-END:variables
}
