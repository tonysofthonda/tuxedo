/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import tuxido.formato_usuario;
import tuxido.principal;

/**
 *
 * @author VJ002349
 */
public class ifrmWPOrdenProduccion extends javax.swing.JInternalFrame {
    principal prn;
    conexionWPPostgre cnxO;
    String fchini;
    String hoy;
    int band=0;
    String val=null;
    private DefaultTableModel modeloT;
    private Object[] filasx;
    private TableRowSorter ordenar;
    String TtlUnidades;
    String user;
    int id;
    int fila;
    JDesktopPane panel5;
    /**
     * Creates new form ifrmWPOrdenProduccion
     */
    public ifrmWPOrdenProduccion(JDesktopPane Panel,String usr) {
        initComponents();
        user=usr;
        System.out.println(user);
        informacion();
        panel5=Panel;
    }
    
    public void conexion(){
        cnxO=new conexionWPPostgre();
        
    }
    
    public void desconexion(){
        try {
            cnxO.Conec.close();
        } catch (SQLException ex) {}
    }
    
    public void informacion(){
        conexion();
        cnxO.consulta("select codigo_modelo from WP_MODELO WHERE status='Activo'");
        try {
            while(cnxO.reg.next()){
                modelo.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            
        }
        
        modelo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelo.getSelectedIndex()>0){
                    info((String) modelo.getSelectedItem());
                }else{
                    pref.setText("");
                    desc.setText("");
                    motor.removeAllItems();
                    motor.addItem("Elije");
                    color.removeAllItems();
                    tipo.removeAllItems();
                    color.addItem("Elije");
                    descclr.setText("");
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
        motor.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(motor.getSelectedIndex()>0){
                    desTMotor((String) motor.getSelectedItem(),(String) tipo.getSelectedItem());
                }else{
                    descclr.setText("");
                }
            }
        });
        desconexion();
    
    }
    
    public void info(String opc){
        conexion();
        cnxO.consulta("select prefijo, descripcion, ACT_SERIE_PROD from WP_MODELO where status='Activo' and codigo_modelo='"+opc+"'");
        try {
            while(cnxO.reg.next()){
                pref.setText(cnxO.reg.getString(1));
                desc.setText(cnxO.reg.getString(2));
                inicio.setText(cnxO.reg.getString(3));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
        }
        motor.removeAllItems();
        motor.addItem("Elije");
        cnxO.consulta("select distinct motor from WP_MOTOR where status='Activo' and aplica='"+opc+"'");
        try{
            while(cnxO.reg.next()){
                motor.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
        }
        color.removeAllItems();
        color.addItem("Elije");
        cnxO.consulta("select distinct codigo from WP_COLOR where status='Activo' and aplica='"+opc+"'");
        try{
            while(cnxO.reg.next()){
                color.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
        }
        tipo.removeAllItems();
        tipo.addItem("Elije");
        cnxO.consulta("select distinct (TIPO||OPCION) AS TIPO from WP_TIPOS WHERE Status='Activo' order by 1");
        try{
            while(cnxO.reg.next()){
                tipo.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
        }
        desconexion();

    }
    
    public void desclr (String clr){
        conexion();
        cnxO.consulta("select nombre from WP_COLOR where status='Activo' and codigo='"+clr+"'");
        try{
            while(cnxO.reg.next()){
                descclr.setText(cnxO.reg.getString(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
        }
        desconexion();
        
    }
    
    public void desTMotor (String mtr, String tp){
        conexion();
        cnxO.consulta("select TMOTOR from WP_TIPOS where status='Activo' and MOTOR='"+mtr+"' and TIPO||OPCION='"+tp+"'");
        try{
            while(cnxO.reg.next()){
                pref1.setText(cnxO.reg.getString(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
        }
        desconexion();
    }
    
    public void crear(){
        conexion();
            cnxO.ejecutar("INSERT INTO WP_ORDEN "
                    + "(UNIDADES,FECHA_CREACION,FECHA_MODIFICACION,STATUS,AVANCE,STATUS_PRODUCCION,USUARIO) VALUES"
                    + "('0',to_date(to_char(current_date, 'MM/dd/yyyy'),'MM/dd/yyyy'),to_date(to_char(current_date, 'MM/dd/yyyy'),'MM/dd/yyyy'),'Abierto','0','Abierto','"+user+"')");
        desconexion();
        crear.setEnabled(false);
        habilitar();
    }
    
    public void generarOrden(){
        generaOrdenProduccion v=new generaOrdenProduccion(
                                    Integer.parseInt(inicio.getText()), 
                                    Integer.parseInt(fin.getText()),
                                    pref.getText(),
                                    (String)motor.getSelectedItem(), 
                                    (String)modelo.getSelectedItem(), 
                                    desc.getText(), 
                                    pref1.getText(),
                                    descclr.getText(),
                                    Integer.parseInt(orden.getText())
                                );
        v.start();
    }
    
    public void habilitar(){
            modelo.setEnabled(true);
            agregar.setEnabled(true);
            color.setEnabled(true);
            unidades.setEnabled(true);
            unidades.setEditable(true);
            motor.setEnabled(true);
            tipo.setEnabled(true);
    }
    
    public void mostrar(){
        conexion();
            cnxO.consulta("select * FROM ( "
                        + " SELECT ID, TO_CHAR(FECHA_CREACION,'DD/MM/YY'), TO_CHAR(FECHA_MODIFICACION,'DD/MM/YY'), STATUS, AVANCE, STATUS_PRODUCCION, USUARIO from WP_ORDEN order by id desc"
                        + ") as s LIMIT 1");
            try{
                while(cnxO.reg.next()){
                    orden.setText(cnxO.reg.getString(1));
                    creacion.setText(cnxO.reg.getString(2));
                    modificacion.setText(cnxO.reg.getString(3));
                    usuario.setText(cnxO.reg.getString(7));
                    status.setText(cnxO.reg.getString(4));
                    avance.setText(cnxO.reg.getString(5));
                    sttsProd.setText(cnxO.reg.getString(6));
                }
                cnxO.reg.close();
            } catch (SQLException ex) {
            }
        desconexion();
    }
    
    public void agregar(){
        conexion();
            cnxO.ejecutar("INSERT INTO WP_ORDEN_DETAIL "
                    + "(ORDEN,CODIGO_MODELO,DESCRIPCION,TIPO,PREFIJO,MOTOR,COLOR,DESC_COLOR,UNIDADES,AVANCE,STATUS,TMODELO, INICIO, FIN) VALUES"
                    + "('"+orden.getText()+"','"+(String)modelo.getSelectedItem()+"','"+desc.getText()+"','"+(String)tipo.getSelectedItem()+"','"+pref.getText()+"',"
                    + "'"+(String)motor.getSelectedItem()+"','"+(String)color.getSelectedItem()+"','"+descclr.getText()+"',"
                    + "'"+unidades.getText()+"','0','Activo','"+pref1.getText()+"','"+inicio.getText()+"','"+fin.getText()+"')");
            
            
         desconexion();
         contabilizar(orden.getText());
    }
    
    public void contabilizar(String ord){
        conexion();
            cnxO.consulta("select sum(unidades) from WP_ORDEN_DETAIL where ORDEN="+ord+"");
            try{
                while(cnxO.reg.next()){
                    TtlUnidades=(cnxO.reg.getString(1));
                }
            } catch (SQLException ex) {
            }
            cnxO.ejecutar("UPDATE WP_ORDEN SET UNIDADES='"+TtlUnidades+"' WHERE ID='"+ord+"'");
        desconexion();
    }

    public void ordenDetalle(){
        conexion();
            cnxO.consulta("select "
                            + " ID," +
                                "ORDEN," +
                                "CODIGO_MODELO," +
                                "DESCRIPCION," +
                                "TIPO," +
                                "PREFIJO," +
                                "MOTOR," +
                                "TMODELO," +
                                "COLOR," +
                                "DESC_COLOR," +
                                "UNIDADES," +
                                "INICIO," +
                                "FIN, " +
                                "AVANCE," +
                                "STATUS " 
                            + "from WP_ORDEN_DETAIL where ORDEN='"+orden.getText()+"' order by id DESC");
                            //Reedefinimos un Modelo de Tabla
                            modeloT = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                    return false;
                                }
                            };
                            
                            //Asignamos el nuevo Modelo
                            jTable1.setModel(modeloT);
                            //Agregamos las columnas al nuevo modelo
                            modeloT.addColumn("ID");
                            modeloT.addColumn("Orden");
                            modeloT.addColumn("Modelo");
                            modeloT.addColumn("Descripcion");
                            modeloT.addColumn("Tipo");
                            modeloT.addColumn("Prefijo");
                            modeloT.addColumn("Motor");
                            modeloT.addColumn("Tipo Motor");
                            modeloT.addColumn("Color");
                            modeloT.addColumn("Desc. Color");
                            modeloT.addColumn("Unidades");
                            modeloT.addColumn("Ini. Prod.");
                            modeloT.addColumn("Fin Prod.");
                            modeloT.addColumn("Avance");
                            modeloT.addColumn("Status");
                            //Asiganomos un ancho de columnas por default
                            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                            jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
                            jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
                            jTable1.getColumnModel().getColumn(4).setPreferredWidth(60);
                            jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
                            jTable1.getColumnModel().getColumn(7).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(8).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(9).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(10).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(11).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(12).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(13).setPreferredWidth(90);
                            jTable1.getColumnModel().getColumn(14).setPreferredWidth(90);

                            //asigno el tamaño del arreglo con la cantidad de columnas:
                            filasx = new Object[modeloT.getColumnCount()];
                            try {
                                //escribo las filas:
                                while (cnxO.reg.next()){
                                    for (int i=0;i<modeloT.getColumnCount();i++){
                                         filasx[i] = cnxO.reg.getObject(i+1);
                                    } 
                                    //escribo las filas del resultado SQL//
                                        modeloT.addRow(filasx);
                                        jTable1.setModel(modeloT);
                                        jTable1.setDefaultRenderer(Object.class, new formato_usuario());
                                        ordenar = new TableRowSorter(modeloT);
                                        jTable1.setRowSorter(ordenar);
                                }
                                cnxO.reg.close();
                            } catch (SQLException ex) {

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

        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        orden = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        creacion = new javax.swing.JTextField();
        modificacion = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        status = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        avance = new javax.swing.JTextField();
        sttsProd = new javax.swing.JTextField();
        crear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        motor = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        color = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pref = new javax.swing.JTextField();
        modelo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        descclr = new javax.swing.JTextField();
        agregar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        unidades = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tipo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        pref1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        inicio = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        fin = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();

        setClosable(true);
        setMaximizable(true);
        setTitle("Tuxedo - WaterPump | Nueva Orden Produccion");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Orden de Produccion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel8.setText("Orden:");

        orden.setEditable(false);
        orden.setBackground(new java.awt.Color(204, 204, 255));
        orden.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        orden.setFocusable(false);

        jLabel9.setText("Creacion:");

        jLabel10.setText("Modificacion:");

        creacion.setEditable(false);
        creacion.setBackground(new java.awt.Color(204, 204, 255));
        creacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        creacion.setFocusable(false);

        modificacion.setEditable(false);
        modificacion.setBackground(new java.awt.Color(204, 204, 255));
        modificacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        modificacion.setFocusable(false);

        jLabel11.setText("Usuario:");

        jLabel12.setText("Status:");

        usuario.setEditable(false);
        usuario.setBackground(new java.awt.Color(204, 204, 255));
        usuario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        usuario.setFocusable(false);

        status.setEditable(false);
        status.setBackground(new java.awt.Color(204, 204, 255));
        status.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        status.setFocusable(false);

        jLabel13.setText("Avance Produccion:");

        jLabel14.setText("Status Produccion:");

        avance.setEditable(false);
        avance.setBackground(new java.awt.Color(204, 204, 255));
        avance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        avance.setFocusable(false);

        sttsProd.setEditable(false);
        sttsProd.setBackground(new java.awt.Color(204, 204, 255));
        sttsProd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        sttsProd.setFocusable(false);

        crear.setText("Crear");
        crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(creacion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(orden, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(modificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(status))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(crear, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(sttsProd)
                    .addComponent(avance))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(orden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(crear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(creacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(avance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(modificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(sttsProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Produccion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        motor.setEnabled(false);

        jLabel4.setText("Motor:");

        color.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije" }));
        color.setEnabled(false);

        jLabel6.setText("Color:");

        jLabel2.setText("Prefijo:");

        pref.setEditable(false);
        pref.setBackground(new java.awt.Color(204, 204, 255));
        pref.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        modelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije" }));
        modelo.setEnabled(false);

        jLabel1.setText("Modelo:");

        desc.setEditable(false);
        desc.setBackground(new java.awt.Color(204, 204, 255));
        desc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        descclr.setEditable(false);
        descclr.setBackground(new java.awt.Color(204, 204, 255));
        descclr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        agregar.setText("Agregar");
        agregar.setEnabled(false);
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });

        jLabel3.setText("Unidades:");

        unidades.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        unidades.setEnabled(false);
        unidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unidadesActionPerformed(evt);
            }
        });
        unidades.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                unidadesKeyReleased(evt);
            }
        });

        jLabel5.setText("Tipo Modelo:");

        tipo.setEnabled(false);

        jLabel7.setText("Tipo Motor:");

        pref1.setEditable(false);
        pref1.setBackground(new java.awt.Color(204, 204, 255));
        pref1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(modelo, 0, 120, Short.MAX_VALUE)
                    .addComponent(color, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(descclr, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                    .addComponent(desc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(motor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unidades)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pref1)
                            .addComponent(pref))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(modelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(pref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descclr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(motor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(pref1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(agregar)
                    .addComponent(unidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Orden", "Modelo", "Descripcion", "Tipo", "Prefijo", "Motor", "Tipo Motor", "Color", "Desc. Color", "Unidades", "Ini. Prod.", "Fin. Prod.", "Avance", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
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
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(13).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(14).setPreferredWidth(90);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Intervalo de producción", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel15.setText("Inicio:");

        inicio.setEditable(false);
        inicio.setBackground(new java.awt.Color(204, 204, 255));
        inicio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel16.setText("Fin:");

        fin.setEditable(false);
        fin.setBackground(new java.awt.Color(204, 204, 255));
        fin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel17.setText("Produccion Total:");

        total.setEditable(false);
        total.setBackground(new java.awt.Color(204, 204, 255));
        total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(inicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(fin, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 416, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearActionPerformed
        // TODO add your handling code here:
        crear();
        mostrar();
    }//GEN-LAST:event_crearActionPerformed

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        // TODO add your handling code here:
        if((unidades.getText().length()>0)||(Integer.getInteger(unidades.getText())>0)){
            agregar();
            ordenDetalle();
            generarOrden();
        }else{
            JOptionPane.showMessageDialog(null,"Datos Incorrectos, captura una cantidad valida de unidades");
        }
    }//GEN-LAST:event_agregarActionPerformed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

    private void unidadesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_unidadesKeyReleased
        // TODO add your handling code here:
        if(unidades.getText().length()>0){
            int seriefin=(Integer.parseInt(inicio.getText())+Integer.parseInt(unidades.getText()))-1;
            fin.setText(String.valueOf(seriefin));
            int resultado=(Integer.parseInt(fin.getText())-Integer.parseInt(inicio.getText()))+1;
            total.setText(String.valueOf(resultado));
        }
    }//GEN-LAST:event_unidadesKeyReleased

    private void unidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unidadesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregar;
    private javax.swing.JTextField avance;
    private javax.swing.JComboBox color;
    private javax.swing.JTextField creacion;
    private javax.swing.JButton crear;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField descclr;
    private javax.swing.JTextField fin;
    private javax.swing.JTextField inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox modelo;
    private javax.swing.JTextField modificacion;
    private javax.swing.JComboBox motor;
    private javax.swing.JTextField orden;
    private javax.swing.JTextField pref;
    private javax.swing.JTextField pref1;
    private javax.swing.JTextField status;
    private javax.swing.JTextField sttsProd;
    private javax.swing.JComboBox tipo;
    private javax.swing.JTextField total;
    private javax.swing.JTextField unidades;
    private javax.swing.JTextField usuario;
    // End of variables declaration//GEN-END:variables
}
