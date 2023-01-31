/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class ifrmAddMMaestro extends javax.swing.JInternalFrame {
    generarOrdenProd gen=new generarOrdenProd();
    generarTarjeta genT=new generarTarjeta();
    conexionPostgres cnxO;
    String fchini;
    String hoy;
    int band=0;
    String val=null;
    private DefaultTableModel modeloT;
    private Object[] filasx;
    private TableRowSorter ordenar;
    /**
     * Creates new form ifrmAddMMaestro
     */
    public ifrmAddMMaestro() {
        initComponents();
        informacion();
    }
    /**
     *
     */
    public void conexion(){
        cnxO=new conexionPostgres();
        
    }
    
    /**
     *
     */
    public void desconexion(){
        try {
            cnxO.Conec.close();
        } catch (SQLException ex) {}
    }
    /**
     *
     */
    public void informacion(){
        conexion();
        cnxO.consulta("select codigo_modelo from TX_MODELO WHERE status='Activo'");
        try {
            while(cnxO.reg.next()){
                modelo.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddColor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        modelo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelo.getSelectedIndex()>0){
                info((String) modelo.getSelectedItem());
                }else{
                    pref.setText("");
                    desc.setText("");
                    anio.removeAllItems();
                    motor.removeAllItems();
                    color.removeAllItems();
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
        anio.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(anio.getSelectedIndex()>0){
                    carProd(anio.getSelectedItem().toString());
                }else{
                    inicio.setText("");
                }
            }
        });
        cnxO.consulta("select destino from TX_DESTINO WHERE status='Activo'");
        try{
            while(cnxO.reg.next()){
                destino.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
    }
    /**
     *
     * @param opc
     */
    public void info(String opc){
        conexion();
        cnxO.consulta("select prefijo, descripcion from TX_MODELO where status='Activo' and codigo_modelo='"+opc+"'");
        try {
            while(cnxO.reg.next()){
                pref.setText(cnxO.reg.getString(1));
                desc.setText(cnxO.reg.getString(2));
                //inicio.setText(cnxO.reg.getString(3));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        anio.removeAllItems();
        anio.addItem("Elije");
        cnxO.consulta("select distinct anio from TX_MODELO where status='Activo' and codigo_modelo='"+opc+"'");
        try{
            while(cnxO.reg.next()){
                anio.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        motor.removeAllItems();
        cnxO.consulta("select distinct motor from TX_MOTOR where status='Activo' and aplica='"+opc+"'");
        try{
            while(cnxO.reg.next()){
                motor.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        color.removeAllItems();
        color.addItem("Elije");
        cnxO.consulta("select distinct codigo from TX_COLOR where status='Activo' and aplica='"+opc+"'");
        try{
            while(cnxO.reg.next()){
                color.addItem(cnxO.reg.getObject(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
    }
    /**
     *
     * @param clr
     */
    public void desclr(String clr){
        conexion();
        cnxO.consulta("select nombre from TX_COLOR where status='Activo' and codigo='"+clr+"'");
        try{
            while(cnxO.reg.next()){
                descclr.setText(cnxO.reg.getString(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
    }
    /**
     *
     * @param anio
     */
    public void carProd(String anio){
        conexion();
        cnxO.consulta("select ACT_SERIE_PROD from TX_MODELO where codigo_modelo='"+(String)modelo.getSelectedItem()+"' AND ANIO='"+anio+"' AND STATUS='Activo'");
        try {
            while(cnxO.reg.next()){
                inicio.setText(cnxO.reg.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
    }
    /**
     *
     */
    public void guardarPlantilla(){
        conexion();
        if(color.getSelectedIndex()>0 && modelo.getSelectedIndex()>0 && destino.getSelectedIndex()>0){
            Date fch=fecha.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            fchini=sdf.format(fch);
            cnxO.ejecutar("INSERT INTO TX_MMAESTRO "
                    + "(modelo, descripcion, prefijo, anio, motor, color, destino,"
                    + "fecha_prod,fecha_creacion,status,ttl_prod,desc_color) VALUES"
                    + "('"+modelo.getSelectedItem().toString()+"','"+desc.getText()+"',"
                    + "'"+pref.getText()+"','"+anio.getSelectedItem().toString()+"',"
                    + "'"+motor.getSelectedItem().toString()+"','"+color.getSelectedItem().toString()+"',"
                    + "'"+destino.getSelectedItem().toString()+"','"+fchini+"',to_date(to_char(current_date, 'MM/dd/yyyy'),'MM/dd/yyyy'),'Pendiente',"
                    + "'"+total.getText()+"','"+descclr.getText()+"')");
            
            band=1;
            modelo.setEditable(false);
            anio.setEditable(false);
            color.setEditable(false);
            destino.setEditable(false);
            motor.setEditable(false);
            fecha.setEnabled(false);
            jMenuItem1.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null,"Existen Campos Incorrectos, valida la informacion");
        }
        desconexion();
    }
    /**
     *
     */
    public void refrescar(){
        conexion();
        cnxO.consulta("select id, vin, motor, numero, status from TX_ORDEN_PROD where id_orden='"+val+"' ORDER BY ID ASC");
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
                        modeloT.addColumn("VIN");
                        modeloT.addColumn("Motor");
                        modeloT.addColumn("Num. Motor");
                        modeloT.addColumn("Status");
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);

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
     *
     */
    public void imprimir (){
        conexion();
        cnxO.consulta("select VIN  FROM TX_ORDEN_PROD WHERE id_orden='"+val+"' ORDER BY VIN ASC");
        try {
            while(cnxO.reg.next()){
                genT.conReporte(cnxO.reg.getString(1));
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmAddMMaestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconexion();
    }
    /**
     *
     */
    public void impresionEstamp(){
        generarInsEstamp imp=new generarInsEstamp();
        imp.generar(val);
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
        destino = new javax.swing.JComboBox();
        motor = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        anio = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        color = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pref = new javax.swing.JTextField();
        modelo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        desc = new javax.swing.JTextField();
        descclr = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        inicio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        fin = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        generar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setIconifiable(true);
        setTitle("Tuxedo | Nueva Orden Produccion");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Produccion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        destino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije" }));

        jLabel4.setText("Motor:");

        jLabel3.setText("Año:");

        jLabel5.setText("Destino:");

        color.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije" }));

        jLabel6.setText("Color:");

        jLabel2.setText("Prefijo:");

        pref.setEditable(false);
        pref.setBackground(new java.awt.Color(204, 204, 255));
        pref.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        modelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije" }));

        jLabel1.setText("Modelo:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel7.setText("La producción de este registro estará disponible para la fecha:");

        desc.setEditable(false);
        desc.setBackground(new java.awt.Color(204, 204, 255));
        desc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        descclr.setEditable(false);
        descclr.setBackground(new java.awt.Color(204, 204, 255));
        descclr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(modelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pref, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(anio, 0, 90, Short.MAX_VALUE)
                            .addComponent(descclr)
                            .addComponent(desc))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(motor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(destino, 0, 110, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(modelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(motor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descclr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Intervalo de producción", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel8.setText("Inicio:");

        inicio.setEditable(false);
        inicio.setBackground(new java.awt.Color(204, 204, 255));
        inicio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        inicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inicioKeyReleased(evt);
            }
        });

        jLabel9.setText("Fin:");

        fin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finActionPerformed(evt);
            }
        });
        fin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                finKeyReleased(evt);
            }
        });

        jLabel10.setText("Produccion Total:");

        total.setEditable(false);
        total.setBackground(new java.awt.Color(204, 204, 255));
        total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(inicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(fin, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Motor", "Num. Motor", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        generar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cdr_play.png"))); // NOI18N
        generar.setToolTipText("Guardar y Generar");
        generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_go.png"))); // NOI18N
        jButton1.setToolTipText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bullet_cross.png"))); // NOI18N
        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_key.png"))); // NOI18N
        jButton4.setToolTipText("Instruccion Estampado");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk_black.png"))); // NOI18N
        jMenuItem1.setText("Guardar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cdr_play.png"))); // NOI18N
        jMenuItem2.setText("Generar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bullet_cross.png"))); // NOI18N
        jMenuItem4.setText("Salir");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(generar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton3)
                                .addComponent(jButton4))
                            .addComponent(generar, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 237, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
            if(color.getSelectedIndex()>0 && modelo.getSelectedIndex()>0 && destino.getSelectedIndex()>0){
                if(total.getText().length()>0&& Integer.parseInt(total.getText())>0){
                    if(band==0){
                        guardarPlantilla();
                        band=1;
                    }
                        conexion();
                        try {
                            cnxO.consulta("select max(id) from TX_MMAESTRO");
                            while(cnxO.reg.next()){
                                val=cnxO.reg.getString(1);
                            }
                        desconexion();
                            gen.ordenProd(Integer.parseInt(total.getText()), Integer.parseInt(inicio.getText()), pref.getText(), anio.getSelectedItem().toString(),val);
                        conexion();    
                            cnxO.ejecutar("update TX_MODELO SET ACT_SERIE_PROD="
                                    + "'"+(Integer.valueOf(fin.getText())+1)+"' "
                                    + "where codigo_modelo='"+modelo.getSelectedItem().toString()+"' "
                                    + "and anio='"+anio.getSelectedItem().toString()+"'");
                            cnxO.ejecutar("update TX_MMAESTRO SET status='Listo' WHERE id='"+val+"'");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,"SERIE PRODUCTION WITH ERROR: "+ex);
                        }           
                        
                        desconexion();
                    inicio.setEditable(false);
                    fin.setEditable(false);
                    generar.setEnabled(false);
                    jMenuItem2.setEnabled(false);
                    refrescar();
                }else{
                    JOptionPane.showMessageDialog(null,"<html>Valida la informacion de Unidades a Producir,<p> el total no podra ser <strong>menor o iguala 0</strong></html>");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Existen Campos Incorrectos, valida la informacion");
            }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        guardarPlantilla();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void finKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_finKeyReleased
        // TODO add your handling code here:
        if(inicio.getText().length()>0 && fin.getText().length()>0){
            int resultado=(Integer.parseInt(fin.getText())-Integer.parseInt(inicio.getText()))+1;
            total.setText(String.valueOf(resultado));
        }
    }//GEN-LAST:event_finKeyReleased

    private void inicioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inicioKeyReleased
        // TODO add your handling code here:
        if(inicio.getText().length()>0 && fin.getText().length()>0){
            int resultado=(Integer.parseInt(fin.getText())-Integer.parseInt(inicio.getText()))+1;
            total.setText(String.valueOf(resultado));
        }
    }//GEN-LAST:event_inicioKeyReleased

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        // TODO add your handling code here:

        if(color.getSelectedIndex()>0 && modelo.getSelectedIndex()>0 && destino.getSelectedIndex()>0){
            if(total.getText().length()>0&& Integer.parseInt(total.getText())>0){
                if(band==0){
                    guardarPlantilla();
                    band=1;
                }
                    conexion();
                    try {
                        cnxO.consulta("select max(id) from TX_MMAESTRO");
                        while(cnxO.reg.next()){
                            val=cnxO.reg.getString(1);
                    }
                    desconexion();
                        gen.ordenProd(Integer.parseInt(total.getText()), Integer.parseInt(inicio.getText()), pref.getText(), anio.getSelectedItem().toString(),val);
                    conexion();    
                        cnxO.ejecutar("update TX_MODELO SET ACT_SERIE_PROD="
                                + "'"+(Integer.valueOf(fin.getText())+1)+"' "
                                + "where codigo_modelo='"+modelo.getSelectedItem().toString()+"' "
                                + "and anio='"+anio.getSelectedItem().toString()+"'");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,ex);
                    }
                    desconexion();
                inicio.setEditable(false);
                fin.setEditable(false);
                generar.setEnabled(false);
                jMenuItem2.setEnabled(false);
                refrescar();
            }else{
                JOptionPane.showMessageDialog(null,"<html>Valida la informacion de Unidades a Producir,<p> el total no podra ser <strong>menor o iguala 0</strong></html>");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Existen Campos Incorrectos, valida la informacion");
        }

    }//GEN-LAST:event_generarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        refrescar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        imprimir();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        impresionEstamp();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void finActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_finActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox anio;
    private javax.swing.JComboBox color;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField descclr;
    private javax.swing.JComboBox destino;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JTextField fin;
    private javax.swing.JButton generar;
    private javax.swing.JTextField inicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox modelo;
    private javax.swing.JComboBox motor;
    private javax.swing.JTextField pref;
    private javax.swing.JTextField total;
    // End of variables declaration//GEN-END:variables
}
