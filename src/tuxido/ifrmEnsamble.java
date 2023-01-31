/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class ifrmEnsamble extends javax.swing.JInternalFrame {
    conexionPostgres cnx;
    private String fecha;
    private String hora;
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    String sttsaf=null;
    String sttspa=null;
    private int num;
    JDesktopPane panel1;
    private String cadena;
    String estacion="Ensamble";
    private String idmodelo;
    /**
     * Creates new form ifrmEnsamble
     * @param jDesktopPane1 
     */
    public ifrmEnsamble(JDesktopPane jDesktopPane1) {
        panel1=jDesktopPane1;
        initComponents();
        codigo.requestFocus();
        corte();
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
    public void cerrarCon(){
        try {
            cnx.Conec.close();
        } catch (SQLException ex) {}
    }
    /**
     *
     */
    public void lectura(){

        try {
            cnx.consulta("select af_status, pa_status, motor, id_modelo from TX_ORDEN_PROD WHERE VIN='"+codigo.getText().toUpperCase()+"'");
            while(cnx.reg.next()){
                sttsaf=cnx.reg.getString(1);
                sttspa=cnx.reg.getString(2);
                cdMtr.setText(cnx.reg.getString(3));
                idmodelo=cnx.reg.getString(4);
            }
            cnx.consulta("select caracteres from TX_MOTOR where motor='"+cdMtr.getText()+"' and status='Activo' AND APLICA='"+idmodelo+"'");
            while(cnx.reg.next()){
                num=cnx.reg.getInt(1);
            }
        } catch (SQLException ex) {}
        validacion();
    }
    /**
     *
     */
    public void numMotor(){
        try {
            cnx.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
            while(cnx.reg.next()){
                corte.setText(cnx.reg.getString(1));
                reg.setText(cnx.reg.getString(2));
            } 
            Date fechaActual=new Date();
            SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
            SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
            fecha=forFecha.format(fechaActual);
            hora=forHora.format(fechaActual);
            cnx.ejecutar("update TX_ORDEN_PROD SET numero='"+cadena.toUpperCase()+"' where vin='"+codigo.getText().toUpperCase()+"'");
            cnx.ejecutar("update TX_ORDEN_PROD SET af='"+fecha+"' where vin='"+codigo.getText().toUpperCase()+"'");
            cnx.ejecutar("update TX_ORDEN_PROD SET af_tim='"+hora+"' where vin='"+codigo.getText().toUpperCase()+"'");
            cnx.ejecutar("update TX_ORDEN_PROD SET af_status='Pendiente-c/Motor' where vin='"+codigo.getText().toUpperCase()+"'");
            cnx.ejecutar("update TX_ORDEN_PROD SET status='Pendiente' where vin='"+codigo.getText().toUpperCase()+"'");
            cnx.ejecutar("update TX_PROD_TURNO SET produccion='"+(Integer.parseInt(reg.getText())+1)+"' where id='"+corte.getText()+"'");
            
            cnx.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
            while(cnx.reg.next()){
                corte.setText(cnx.reg.getString(1));
                reg.setText(cnx.reg.getString(2));
            }   
        } catch (SQLException ex) {}
    }
    /**
     *
     */
    public void validacion(){
             if("Liberado".equals(sttspa)){ 
                if("Pendiente".equals(sttsaf)){
                    numMtr.setEditable(true);
                    numMtr.requestFocus();
                    codigo.setEditable(false);
                }else{
                    codigo.setText("");
                    codigo.requestFocus();
                    cdMtr.setText("");
                    JOptionPane.showMessageDialog(null,"El código de barras ya fue Recepcionado en esta Estacion.");

                }
            }else{
                codigo.setText("");
                codigo.requestFocus();
                cdMtr.setText("");
                JOptionPane.showMessageDialog(null,"El código de barras aun no se encuentra registrado en la Estación Anterior.");

            }
    } 
    /**
     *
     */
    public void pendientes(){
                cnx.consulta("select id, VIN, MOD_DESC, ANIO,MOTOR, NUMERO, STATUS, to_char(AF,'dd-MM-yyyy'), to_char(AF_tim,'HH24:MI:SS'), AF_STATUS "
                + "from TX_ORDEN_PROD WHERE STATUS='Pendiente' and PA_STATUS='Liberado' and AF_STATUS='Pendiente' order by ID DESC");
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
                        modelo.addColumn("VIN");
                        modelo.addColumn("Descripcion");
                        modelo.addColumn("Año Modelo");
                        modelo.addColumn("Motor");
                        modelo.addColumn("Num. Motor");
                        modelo.addColumn("Status");
                        modelo.addColumn("Fecha");
                        modelo.addColumn("Hora");
                        modelo.addColumn("Status Terminal");   
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(130);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(90);
                        jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
                        jTable1.getColumnModel().getColumn(9).setPreferredWidth(100);

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
                        } catch (SQLException ex) {}
    }
    /**
     *
     */
    public void liberadas(){
                 cnx.consulta("select id, VIN, MOD_DESC, ANIO,MOTOR, NUMERO, STATUS, to_char(AF,'dd-MM-yyyy'), to_char(AF_tim,'HH24:MI:SS'), AF_STATUS "
                + "from TX_ORDEN_PROD WHERE status='Pendiente' and af_status='Pendiente-c/Motor' order by ID DESC");
        //Reedefinimos un Modelo de Tabla
                        modelo = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modelo);
                        jTable2.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable2.setModel(modelo);
                        //Agregamos las columnas al nuevo modelo
                        modelo.addColumn("ID");
                        modelo.addColumn("VIN");
                        modelo.addColumn("Descripcion");
                        modelo.addColumn("Año Modelo");
                        modelo.addColumn("Motor");
                        modelo.addColumn("Num. Motor");
                        modelo.addColumn("Status");
                        modelo.addColumn("Fecha");
                        modelo.addColumn("Hora");
                        modelo.addColumn("Status Terminal");   
                        //Asiganomos un ancho de columnas por default
                        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable2.getColumnModel().getColumn(1).setPreferredWidth(130);
                        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(4).setPreferredWidth(100);
                        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(6).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(8).setPreferredWidth(70);
                        jTable2.getColumnModel().getColumn(9).setPreferredWidth(130);

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
                                    jTable2.setModel(modelo);
                                    jTable2.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnx.reg.close();
                        } catch (SQLException ex) {
                        
                        }
        
    }
    /**
     *
     */
    public void corte(){
        conexion();
        try {
            cnx.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
            while(cnx.reg.next()){
                corte.setText(cnx.reg.getString(1));
                reg.setText(cnx.reg.getString(2));
            }
            cnx.consulta("select count(*) from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and status='Abierto'");
            while(cnx.reg.next()){
                if(cnx.reg.getInt(1)==0){
                    ifrmTurno ifrmTrn=new ifrmTurno(estacion);
                    Dimension desktopSize = panel1.getSize();
                    Dimension jInternalFrameSize = ifrmTrn.getSize();
                    panel1.add(ifrmTrn);
                    ifrmTrn.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
                    ifrmTrn.toFront();
                    ifrmTrn.show();
                }
            }
            cnx.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
            while(cnx.reg.next()){
                corte.setText(cnx.reg.getString(1));
                reg.setText(cnx.reg.getString(2));
            }
        } catch (SQLException ex) {}
        cerrarCon();
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
        codigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cdMtr = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        numMtr = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        reg = new javax.swing.JTextField();
        corte = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setTitle("Tuxedo | Ensamble");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dato de Captura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel1.setText("VIN:");

        codigo.setBackground(new java.awt.Color(204, 204, 255));
        codigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel2.setText("Motor:");

        cdMtr.setEditable(false);
        cdMtr.setBackground(new java.awt.Color(204, 204, 255));
        cdMtr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel3.setText("Num. Motor:");

        numMtr.setEditable(false);
        numMtr.setBackground(new java.awt.Color(204, 204, 255));
        numMtr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton2.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk_edit.png"))); // NOI18N
        jButton2.setText("Asignar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cdMtr, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numMtr, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cdMtr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(numMtr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton1.setToolTipText("Actualizar");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editar2.gif"))); // NOI18N
        jButton3.setToolTipText("Incidencia");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user_alert.png"))); // NOI18N
        jButton4.setToolTipText("Registro de Turno");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Descripcion", "Año Modelo", "Motor", "Num. Motor", "Status", "Fecha", "Hora", "Status Terminal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(100);

        jTabbedPane1.addTab("Pendintes", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Descripcion", "Año Modelo", "Motor", "Num. Motor", "Status", "Fecha", "Hora", "Status Terminal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(120);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(9).setPreferredWidth(100);

        jTabbedPane1.addTab("Liberadas", jScrollPane2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Motor", "Fecha", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(100);

        jTabbedPane1.addTab("Offline", jScrollPane3);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Corte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel4.setText("Registros:");

        reg.setEditable(false);
        reg.setBackground(new java.awt.Color(204, 204, 255));
        reg.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        corte.setEditable(false);
        corte.setBackground(new java.awt.Color(204, 204, 255));
        corte.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel5.setText("Corte:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(corte, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reg, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(corte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(reg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        // TODO add your handling code here:
        if(codigo.getText().length()==17){
            conexion();
                lectura();
                pendientes();
                liberadas();
            cerrarCon();
        }
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
              System.out.println("ENTER");
          }

    }//GEN-LAST:event_codigoKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        conexion();
        pendientes();
        liberadas();
        cerrarCon();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ifrmIncidentes ifrmInc=new ifrmIncidentes(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmInc.getSize();
        panel1.add(ifrmInc);
        ifrmInc.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmInc.toFront();
        ifrmInc.show();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
                 conexion();
                 int temp=numMtr.getText().length()-num;
                    cadena=numMtr.getText();
                    cadena=cadena.substring(temp);
                 try {
                    if(codigo.getText().equals(numMtr.getText())){
                        JOptionPane.showMessageDialog(null,"Numero de Motor Invalido");
                    }else{
                        cnx.consulta("select COUNT(*) FROM TX_ORDEN_PROD WHERE MOTOR='"+cdMtr.getText()+"' AND NUMERO='"+cadena.toUpperCase()+"'");
                        while(cnx.reg.next()){
                            if(cnx.reg.getInt(1)<1){
                                numMotor();
                                codigo.setText("");
                                codigo.setEditable(true);
                                codigo.requestFocus();
                                numMtr.setText("");
                                cdMtr.setText("");
                                pendientes();
                                liberadas();
                            }else{
                                JOptionPane.showMessageDialog(null,"Este Numero de Motor ya fue Asignado a otra Unidad");
                                numMtr.setText("");
                                numMtr.requestFocus();
                            }
                        }
                    }
                 } catch (SQLException ex) {}
                 cerrarCon();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        ifrmTurno ifrmTrn=new ifrmTurno(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmTrn.getSize();
        panel1.add(ifrmTrn);
        ifrmTrn.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmTrn.toFront();
        ifrmTrn.show();
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cdMtr;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField corte;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField numMtr;
    private javax.swing.JTextField reg;
    // End of variables declaration//GEN-END:variables
}
