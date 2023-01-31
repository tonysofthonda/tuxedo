/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDesktopPane;
/**
 *
 * @author vj002349
 */
public class ifrmResetStatus extends javax.swing.JInternalFrame {
    conexionPostgres cnx;
    JDesktopPane Pane3;
    private String fecha;
    private String hora;
    private String corte;
    private int reg;
    /**
     * Creates new form ifrmResetStatus
     * @param codigo 
     */
    public ifrmResetStatus(String codigo) {
        initComponents();
        VIN.setText(codigo);
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
            cnx.consulta("select ID_MODELO, MOD_DESC, ANIO, DESTINO, MOTOR, NUMERO "
                    + "FROM TX_ORDEN_PROD WHERE VIN='"+VIN.getText()+"'");
        try {
            while(cnx.reg.next()){
                cmodelo.setText(cnx.reg.getString(1));
                desc.setText(cnx.reg.getString(2));
                anio.setText(cnx.reg.getString(3));
                destino.setText(cnx.reg.getString(4));
                pref.setText(cnx.reg.getString(5));
                motor.setText(cnx.reg.getString(6));
            }
        } catch (SQLException ex) {}
        desconexion();
    }
    /**
     *
     */
    public void reset(){
        conexion();
        
                    Date fechaActual=new Date();
                    SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                    SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
                    fecha=forFecha.format(fechaActual);
                    hora=forHora.format(fechaActual);
                    
        switch(terminal.getSelectedIndex()){
            case 0:
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ID_ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET CONTENEDOR='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET TARIMA='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET NUM_EMP='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PA_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PA_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PA=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET WE_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET WE_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET WE=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET NUMERO='0' WHERE VIN='"+VIN.getText()+"'");

                    cnx.ejecutar("INSERT INTO TX_INCIDENTES (vin,terminal,tipo,descr,fecha,hora) "
                        +"VALUES ('"+VIN.getText()+"','Ensamble','Otro',"
                        + "'Retroceso de Unidad Total','"+fecha+"','"+hora+"')");
                break;
            case 1:
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ID_ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET CONTENEDOR='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET TARIMA='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET NUM_EMP='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PA_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PA_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PA=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET NUMERO='0' WHERE VIN='"+VIN.getText()+"'");
                    
                     cnx.ejecutar("INSERT INTO TX_INCIDENTES (vin,terminal,tipo,descr,fecha,hora) "
                        +"VALUES ('"+VIN.getText()+"','Ensamble','Otro',"
                        + "'Retroceso de Unidad a la terminal Pintura','"+fecha+"','"+hora+"')");
                break;
            case 2:
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ID_ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET CONTENEDOR='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET TARIMA='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET NUM_EMP='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET AF=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET NUMERO='0' WHERE VIN='"+VIN.getText()+"'");
                    
                    cnx.ejecutar("INSERT INTO TX_INCIDENTES (vin,terminal,tipo,descr,fecha,hora) "
                        +"VALUES ('"+VIN.getText()+"','Ensamble','Otro',"
                        + "'Retroceso de Unidad a la terminal Ensamble','"+fecha+"','"+hora+"')");
                break;
            case 3:
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ID_ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET CONTENEDOR='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET TARIMA='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET NUM_EMP='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET VQ=null WHERE VIN='"+VIN.getText()+"'");
                    
                    cnx.ejecutar("INSERT INTO TX_INCIDENTES (vin,terminal,tipo,descr,fecha,hora) "
                        +"VALUES ('"+VIN.getText()+"','Ensamble','Otro',"
                        + "'Retroceso de Unidad a la terminal Calidad','"+fecha+"','"+hora+"')");
                break;
            case 4:
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ID_ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET ENVIO='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET CONTENEDOR='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET TARIMA='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET NUM_EMP='0' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET SHP=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_STATUS='Pendiente' WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK_TIM=null WHERE VIN='"+VIN.getText()+"'");
                    cnx.ejecutar("UPDATE TX_ORDEN_PROD SET PK=null WHERE VIN='"+VIN.getText()+"'");
                    
                    cnx.ejecutar("INSERT INTO TX_INCIDENTES (vin,terminal,tipo,descr,fecha,hora) "
                        +"VALUES ('"+VIN.getText()+"','Ensamble','Otro',"
                        + "'Retroceso de Unidad a la terminal Empaque','"+fecha+"','"+hora+"')");
                break;               
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
        cmodelo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        anio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pref = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        motor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        destino = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        VIN = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        terminal = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Tuxedo | Reiniciar Unidad a un Punto Determinado");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Descripcion de Unidad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setText("Codigo Modelo:");

        cmodelo.setEditable(false);
        cmodelo.setBackground(new java.awt.Color(204, 204, 255));
        cmodelo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setText("Descripcion:");

        desc.setEditable(false);
        desc.setBackground(new java.awt.Color(204, 204, 255));
        desc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("Año:");

        anio.setEditable(false);
        anio.setBackground(new java.awt.Color(204, 204, 255));
        anio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel5.setText("Prefijo:");

        pref.setEditable(false);
        pref.setBackground(new java.awt.Color(204, 204, 255));
        pref.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setText("Motor:");

        motor.setEditable(false);
        motor.setBackground(new java.awt.Color(204, 204, 255));
        motor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setText("Destino:");

        destino.setEditable(false);
        destino.setBackground(new java.awt.Color(204, 204, 255));
        destino.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel7.setText("VIN:");

        VIN.setEditable(false);
        VIN.setBackground(new java.awt.Color(204, 204, 255));
        VIN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(desc, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pref))
                    .addComponent(motor)
                    .addComponent(VIN))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(pref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(motor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(VIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        terminal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Total", "Pintura", "Ensamble", "Calidad", "Empaque" }));

        jLabel8.setText("Reset:");

        jLabel9.setText("<html>Al reiniciar la unidad a una terminal determinada, la información anterior podría perderse. (Hora, Fecha y Numero de motor).</html>");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/info.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(terminal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(terminal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField VIN;
    private javax.swing.JTextField anio;
    private javax.swing.JTextField cmodelo;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField destino;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField motor;
    private javax.swing.JTextField pref;
    private javax.swing.JComboBox terminal;
    // End of variables declaration//GEN-END:variables
}
