/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
/**
 *
 * @author vj002349
 */
public class logon extends javax.swing.JFrame {
    conexionPostgres cnx;
    Element rootNode;
    List<Element> listCofiguraciones;
    Element Usuario;
    List<Element> listUsuario;
    Element Usuarios;
    List<Element> listUsuarios;
    Element Campos;
    String tipo;

    /**
     * Creates new form logon
     */
    public logon() {
        initComponents();
        USER.requestFocus();
    }
    /**
     *
     */
    public void conexion(){
        cnx=new conexionPostgres();
        if(cnx.Conec != null){
        //
        }
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
    public void validacion() throws PropertyVetoException{
        String password;
        conexion();
        if(cnx.Conec != null){
            int band=0;
            String temppsw = null;
            tipo = null;
            password= new String(PASS.getPassword()); 
            System.out.println(password);
            cnx.consulta("select COUNT(*) FROM TX_USUARIOS WHERE USUARIO='"+USER.getText()+"' and status='Activo'");
            try {
                while(cnx.reg.next()){
                    band=cnx.reg.getInt(1);
                }
            } catch (SQLException ex) {}
            if(band>0){
                cnx.consulta("select password,tipo FROM TX_USUARIOS WHERE USUARIO='"+USER.getText()+"' and status='Activo'");
                try {
                   while(cnx.reg.next()){
                       temppsw=cnx.reg.getString(1);
                       tipo=cnx.reg.getString(2);
                   }
                } catch (SQLException ex) {}
                System.out.println(temppsw);
                if(temppsw.equals(password)){
                             try{
                                javax.swing.UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
                             }catch ( Exception e ) { 
                                 JOptionPane.showMessageDialog(null,"Imposible cambiar el estilo Visual: "+e);
                             }
                            principal ver = new principal(tipo, USER.getText());
                            ver.setVisible(true);
                            ver.setLocationRelativeTo(null);
                            ver.setExtendedState(JFrame.MAXIMIZED_BOTH);
                            dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Contraseña Incorrecta");
                    PASS.setText("");
                    PASS.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null,"El usuario no existe o se encuentra Inhabilitado");
                USER.setText("");
                PASS.setText("");
                USER.requestFocus();
            }
        desconexion();
        }else{
        String patchConfig=System.getProperty("user.dir")+"\\xml\\config.xml";
        File xmlFile = new File( patchConfig );
        if(xmlFile.exists()){
            SAXBuilder builder = new SAXBuilder();
            
            int i;
            int j;
            int k;
            boolean band=false;
            try{
                        Document document = (Document) builder.build( xmlFile );
                        rootNode = document.getRootElement();
                        listCofiguraciones = rootNode.getChildren();
                        for ( i = 0; i < listCofiguraciones.size(); i++ ){
                            Usuario = (Element) listCofiguraciones.get(i);
                            listUsuario = Usuario.getChildren();
                            for ( j = 0; j < listUsuario.size(); j++ ){
                                Usuarios = (Element) listUsuario.get(j);
                                listUsuarios = Usuarios.getChildren();
                                for ( k = 0; k < listUsuarios.size(); k++ ){
                                    Campos = (Element) listUsuarios.get(k);
                                    if(USER.getText().equals(Campos.getChildTextTrim("NOMBRE"))){
                                        password= new String(PASS.getPassword());
                                        if(Campos.getChildTextTrim("STATUS").equals("Activo")){
                                            if(password.equals(Campos.getChildTextTrim("PASSWORD"))){
                                                tipo=Campos.getChildTextTrim("TIPO");
                                                i=listCofiguraciones.size();
                                                j=listUsuario.size();
                                                k=listUsuarios.size();
                                                band=true;
                                            }else{
                                                JOptionPane.showMessageDialog(null,"Contraseña Incorrecta");
                                                PASS.setText("");
                                                PASS.requestFocus();
                                                i=listCofiguraciones.size();
                                                j=listUsuario.size();
                                                k=listUsuarios.size();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(band){
                                 try{
                                    javax.swing.UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
                                 }catch ( Exception e ) { 
                                     JOptionPane.showMessageDialog(null,"Imposible cambiar el estilo Visual: "+e);
                                 }
                                 principal ver =new principal(tipo, USER.getText());
                                 ver.setVisible(true);
                                 ver.setLocationRelativeTo(null);
                                 ver.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                 dispose();
                        }else{
                            JOptionPane.showMessageDialog(null,"El usuario no existe o se encuentra Inhabilitado");
                            USER.setText("");
                            PASS.setText("");
                            USER.requestFocus();
                        }

            }catch ( IOException io ) {
                    System.out.println( io.getMessage() );
            }catch ( JDOMException jdomex ) {
                    System.out.println( jdomex.getMessage() );
            }
            }else{
                JOptionPane.showMessageDialog(null,
                        "<html>" +
                            "<font face='Arial, Helvetica, sans-serif'>"+
                            "<p>"+
                            "<font color='#CC0000'><strong>Advertencia</strong>: Modo Offline<br /></font>"+
                            "<br />"+
                            "<strong>Error</strong>:<br />"+
                            "-No te encuentras habilitado para utilizar  esta herramienta en modo Offline.<br />"+
                            "-No se encuentra el archivo de configuración.<br />"+
                            "<br />"+
                            "<strong>Contacta al administrador del Sistema.</strong></p>"+
                            "</font>"+
                        "</html>");
            }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        USER = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        PASS = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tuxedo Project");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Conexion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setText("Usuario:");

        USER.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setText("Pass:");

        jButton1.setText("Conexion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        PASS.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(USER)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PASS)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(USER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(PASS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            validacion();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(logon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField PASS;
    private javax.swing.JTextField USER;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
