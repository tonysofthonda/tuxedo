/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author vj002349
 */
public class ifrmWPModColor extends javax.swing.JInternalFrame {
    conexionWPPostgre cnxO;
    String color;
    String opc;
    String ap;
    /**
     * Creates new form ifrmWPModColor
     * @param clr
     * @param apl
     */
    public ifrmWPModColor(String clr, String apl) {
        initComponents();
        color=clr;
        ap=apl;
        informacion();
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
        cnxO.consulta("select * from WP_COLOR where codigo='"+color+"' and aplica='"+ap+"'");
        try {
            while(cnxO.reg.next()){
                codigo.setText(cnxO.reg.getString(1));
                desc.setText(cnxO.reg.getString(2));
                opc=cnxO.reg.getString(3);
                aplica.addItem(cnxO.reg.getString(3));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmWPModColor.class.getName()).log(Level.SEVERE, null, ex);
        }
        cnxO.consulta("select distinct codigo_modelo from WP_MODELO where status='Activo' and codigo_modelo<>'"+opc+"'");
        try{
            while(cnxO.reg.next()){
                aplica.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmWPModColor.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
    }
    
    public void guardar(){
        conexion();
        if(codigo.getText().length()>0&&desc.getText().length()>0){
            cnxO.ejecutar("update WP_COLOR set codigo='"+codigo.getText()+"' where codigo='"+color+"' and aplica='"+ap+"'");
            cnxO.ejecutar("update WP_COLOR SET nombre='"+desc.getText()+"' where codigo='"+color+"' and aplica='"+ap+"'");
            cnxO.ejecutar("update WP_COLOR SET aplica='"+aplica.getSelectedItem().toString()+"' where codigo='"+color+"' and aplica='"+ap+"'");
            JOptionPane.showMessageDialog(null,"El Registro fue modificado con exito");
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Campos vacios, valida la informcion");
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        aplica = new javax.swing.JComboBox();
        desc = new javax.swing.JTextField();
        codigo = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Tuxedo - WaterPump | Modificar Color ");
        setToolTipText("");

        jLabel2.setText("Codigo:");

        jLabel3.setText("Aplica a:");

        desc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        codigo.setBackground(new java.awt.Color(204, 204, 255));
        codigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk_black.png"))); // NOI18N
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Descripcion:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(aplica, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(aplica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox aplica;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField desc;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
