/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author VJ002349
 */
public class ifrmAddProduccion extends javax.swing.JInternalFrame {
    conexionPostgres cnx;
    private String fchini;
    /**
     * Creates new form ifrmAddProduccion
     */
    public ifrmAddProduccion() {
        initComponents();
        informacion();
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
    public void informacion(){
        conexion();
        cnx.consulta("select codigo_modelo from TX_MODELO WHERE status='Activo'");
        try {
            while(cnx.reg.next()){
                modelo.addItem(cnx.reg.getObject(1));
            }
            cnx.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddColor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        modelo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelo.getSelectedIndex()>0){
                    info((String) modelo.getSelectedItem());
                }else{
                    desc.setText("");
                }
            }
        });
        color.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(color.getSelectedIndex()>0){
                    desclr((String) color.getSelectedItem());
                }else{
                    descclr.setText("");
                }
            }
        });
        /*cnx.consulta("select destino from TX_DESTINO WHERE status='Activo'");
        try{
            while(cnx.reg.next()){
                destino.addItem(cnx.reg.getObject(1));
            }
            cnx.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        desconexion();
    }
    
    /**
     *
     * @param opc
     */
    public void info(String opc){
        conexion();
            cnx.consulta("select descripcion from TX_MODELO where status='Activo' and codigo_modelo='"+opc+"'");
            try {
                while(cnx.reg.next()){
                    desc.setText(cnx.reg.getString(1));
                }
            } catch (SQLException ex) {}
            anio.removeAllItems();
            cnx.consulta("select distinct anio from TX_MODELO where status='Activo' and codigo_modelo='"+opc+"'");
            try{
                while(cnx.reg.next()){
                    anio.addItem(cnx.reg.getObject(1));
                }
                cnx.reg.close();
            } catch (SQLException ex) {}
            color.removeAllItems();
            color.addItem("Elije");
            cnx.consulta("select distinct codigo from TX_COLOR where status='Activo' and aplica='"+opc+"'");
            try{
                while(cnx.reg.next()){
                    color.addItem(cnx.reg.getObject(1));
                }
                cnx.reg.close();
            } catch (SQLException ex) {}
        desconexion();
    }
    
    /**
     *
     * @param clr
     */
    public void desclr(String clr){
        conexion();
            cnx.consulta("select nombre from TX_COLOR where status='Activo' and codigo='"+clr+"'");
            try{
                while(cnx.reg.next()){
                    descclr.setText(cnx.reg.getString(1));
                }
            } catch (SQLException ex) {}
        desconexion();
    }
    /**
     *
     */
    public void guardar(){
        if(color.getSelectedIndex()>0 && modelo.getSelectedIndex()>0 && plan.getText().length()>0){
            conexion();
                Date fch=fecha.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                fchini=sdf.format(fch);
                cnx.ejecutar("INSERT INTO TX_PLAN_PRODUCCION "
                    + "(fecha, modelo, descripcion, color, desc_col,"
                    + "anio,plan, hora_ini) VALUES"
                    + "('"+fchini+"','"+modelo.getSelectedItem().toString()+"',"
                    + "'"+desc.getText()+"','"+color.getSelectedItem().toString()+"',"
                    + "'"+descclr.getText()+"','"+anio.getSelectedItem().toString()+"',"
                    + "'"+plan.getText()+"','"+fchini+" "+hora.getText()+"')");
            desconexion();
            JOptionPane.showMessageDialog(null,"Registro Completo ...");
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Existen Campos Incorrectos, valida la informacion");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        modelo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        anio = new javax.swing.JComboBox();
        color = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        plan = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        descclr = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        hora = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Tuxedo | Agregar Plan de Produccion");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/info.png"))); // NOI18N

        jLabel1.setText("Fecha:");

        jLabel2.setText("Modelo:");

        modelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije ..." }));

        jLabel3.setText("Descripción:");

        desc.setEditable(false);
        desc.setBackground(new java.awt.Color(204, 204, 255));
        desc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setText("Color:");

        jLabel5.setText("Año:");

        jLabel6.setText("Plan:");

        plan.setBackground(new java.awt.Color(204, 204, 255));
        plan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/guardar.gif"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        descclr.setEditable(false);
        descclr.setBackground(new java.awt.Color(204, 204, 255));
        descclr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel7.setText("Hora Inicio:");

        hora.setBackground(new java.awt.Color(204, 204, 255));
        hora.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            hora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("<html><strong>La hora de Inicio de produccion</strong> debe ser capturada en formato de <strong>24 Hrs.</strong> <p>Ejemplo 13:00:00, 15:30:59, etc.</html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(plan)
                            .addComponent(color, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(desc)
                            .addComponent(modelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(descclr)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(modelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(descclr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(plan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1)
                    .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox anio;
    private javax.swing.JComboBox color;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField descclr;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JFormattedTextField hora;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox modelo;
    private javax.swing.JTextField plan;
    // End of variables declaration//GEN-END:variables
}
