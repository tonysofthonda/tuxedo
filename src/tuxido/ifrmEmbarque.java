/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class ifrmEmbarque extends javax.swing.JInternalFrame {
    conexionPostgres cnxO;
    private String fecha;
    private String hora;
    double uni=0;
    double tar=0;
    private Object[] filasx;
    private DefaultTableModel modeloT;
    private TableRowSorter ordenar;

    /**
     * Creates new form ifrmEmbarque
     */
    public ifrmEmbarque() {
        initComponents();
        info();
    }
    /**
     *
     */
    public void info(){
        conexion();
            cnxO.consulta("select destino from TX_DESTINO where Status='Activo'");
            try {
                while(cnxO.reg.next()){
                    destino.addItem(cnxO.reg.getObject(1));
                }
            } catch (SQLException ex) {}
            cnxO.consulta("select Linea from TX_LN_TRANS where Status='Activo'");
            try {
                while(cnxO.reg.next()){
                    trnsp.addItem(cnxO.reg.getObject(1));
                }
            } catch (SQLException ex) {}
        desconexion();
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
    public void guardar(){
        if(destino.getSelectedIndex()>0 &&
           trnsp.getSelectedIndex()>0 &&
           numCon.getText().length()>0)
        {
            conexion();
                    Date fechaActual=new Date();
                    SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                    SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
                    fecha=forFecha.format(fechaActual);
                    hora=forHora.format(fechaActual);
            cnxO.ejecutar("INSERT INTO TX_INFO_EMBARQUE "
                    + "(TRANSPORTISTA, CONTENEDOR, DESTINO, FECHA, "
                    + "HORA, MODELO, DESCRIPCION, UNIDADES, TARIMAS, "
                    + "NUM_ENVIO, STATUS) VALUES ('"+trnsp.getSelectedItem().toString()+"','"+numCon.getText()+"',"
                    + "'"+destino.getSelectedItem().toString()+"','"+fecha+"','"+hora+"',"
                    + "'Mixto','Mixto','0','0','0', "
                    + "'Pendiente')");
            cnxO.consulta("select max(id) from TX_INFO_EMBARQUE");
            try {
                while(cnxO.reg.next()){
                    id.setText(cnxO.reg.getString(1));
                }
            } catch (SQLException ex) {}

                    destino.setEnabled(false);
                    trnsp.setEnabled(false);
                    numCon.setEditable(false);
                    jButton1.setEnabled(false);
                    jButton2.setEnabled(true);
                    codigo.setEditable(true);
                    codigo.requestFocus();
                    unidades.setText("0");
                    tarimas.setText("0");
                    jMenuItem1.setEnabled(false);
                    jMenuItem2.setEnabled(true);
        }else{
            JOptionPane.showMessageDialog(null,"Datos Incompletos, Valida la Informacion");
        }
        pendientes();
        liberadas();
        desconexion();
    }
    /**
     *
     */
    public void lectura(){
        cnxO.consulta("select SHP_STATUS, STATUS from TX_ORDEN_PROD WHERE VIN='"+codigo.getText().toUpperCase()+"' AND STATUS='Terminada'");
        try {
            while(cnxO.reg.next()){
                if(cnxO.reg.getString(2).equals("Terminada")){
                    if(cnxO.reg.getString(1).equals("Pendiente")){
                                uni=uni+1;
                                int tmp2=(int)uni;
                                unidades.setText(String.valueOf(tmp2));
                                double val=uni%2;
                                if(val==1){
                                    tar=((uni/2)+0.5);
                                }else{
                                    tar=(uni/2);
                                }
                                int tmp=(int)tar;
                                tarimas.setText(String.valueOf(tmp));

                                Date fechaActual=new Date();
                                SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                                SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
                                fecha=forFecha.format(fechaActual);
                                hora=forHora.format(fechaActual);
                                cnxO.ejecutar("update TX_INFO_EMBARQUE SET UNIDADES='"+unidades.getText()+"' where ID='"+id.getText()+"'");
                                cnxO.ejecutar("update TX_INFO_EMBARQUE SET TARIMAS='"+tarimas.getText()+"' where ID='"+id.getText()+"'");
                                cnxO.ejecutar("update TX_ORDEN_PROD SET tarima='"+tarimas.getText()+"' where vin='"+codigo.getText().toUpperCase()+"'");
                                cnxO.ejecutar("update TX_ORDEN_PROD SET contenedor='"+numCon.getText()+"' where vin='"+codigo.getText().toUpperCase()+"'");
                                cnxO.ejecutar("update TX_ORDEN_PROD SET id_envio='"+id.getText()+"' where vin='"+codigo.getText().toUpperCase()+"'");
                                cnxO.ejecutar("update TX_ORDEN_PROD SET shp='"+fecha+"' where vin='"+codigo.getText().toUpperCase()+"'");
                                cnxO.ejecutar("update TX_ORDEN_PROD SET shp_tim='"+hora+"' where vin='"+codigo.getText().toUpperCase()+"'");
                                cnxO.ejecutar("update TX_ORDEN_PROD SET shp_status='Liberado' where vin='"+codigo.getText().toUpperCase()+"'");
                                codigo.requestFocus();
                    }else{
                        JOptionPane.showMessageDialog(null,"Esta unidad ya fue Registrada. ");                                             
                        codigo.requestFocus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,                        
                        "<html>" +
                            "<font face='Arial, Helvetica, sans-serif'>"+
                            "<p>"+
                            "<font color='#CC0000'><strong>Advertencia</strong>: UNIDAD INCORRECTA "+codigo.getText().toUpperCase()+"<br /></font>"+
                            "<br />"+
                            "<strong>Error</strong>:<br />"+
                            "-Esta unidad cuenta con estatus No Terminada en la linea de produccion.<br />"+
                            "-No es posible procesar esta Unidad.<br />"+
                            "-Favor de validar la informacion.<br />"+
                            "<br />"+
                            "<strong>Contacta al administrador del Sistema.</strong></p>"+
                            "</font>"+
                        "</html>");
                    codigo.requestFocus();
                }
            }
        } catch (SQLException ex) {}
    }
    /**
     *
     */
    public void cerrarEmb(){
        conexion();
            int x=0;
            String[][] modelo=new String[10][2];
            cnxO.ejecutar("update TX_INFO_EMBARQUE SET UNIDADES='"+unidades.getText()+"' where ID='"+id.getText()+"'");
            cnxO.ejecutar("update TX_INFO_EMBARQUE SET TARIMAS='"+tarimas.getText()+"' where ID='"+id.getText()+"'");
            cnxO.ejecutar("update TX_INFO_EMBARQUE SET STATUS='Listo' where ID='"+id.getText()+"'");
            cnxO.consulta("SELECT DISTINCT ID_MODELO, ANIO FROM TX_ORDEN_PROD WHERE ID_ENVIO='"+id.getText()+"'");
            try {
                while(cnxO.reg.next()){
                    conexionPostgres cnx=new conexionPostgres();
                    cnx.consulta("select ACT_SERIE_ENVIOS from TX_MODELO where CODIGO_MODELO='"+cnxO.reg.getString(1)+"' AND ANIO='"+cnxO.reg.getString(2)+"' AND STATUS='Activo'");
                    while(cnx.reg.next()){
                        x=cnx.reg.getInt(1);
                    }
                    cnx.ejecutar("update TX_ORDEN_PROD SET ENVIO='"+x+"' WHERE ID_ENVIO='"+id.getText()+"' and ID_MODELO='"+cnxO.reg.getString(1)+"' AND ANIO='"+cnxO.reg.getString(2)+"'");
                    x=x+1;
                    cnx.ejecutar("update TX_MODELO SET ACT_SERIE_ENVIOS='"+x+"' "
                               + "where status='Activo' and codigo_modelo='"+cnxO.reg.getString(1)+"' "
                               + "and anio='"+cnxO.reg.getString(2)+"'");
                    cnx.Conec.close();
                }
            } catch (SQLException ex) {}
        desconexion();
    }
    /**
     *
     */
    public void pendientes(){
        cnxO.consulta("select /*+ FIRST_ROWS(10) */ id, VIN, NUM_EMP, DESTINO, MOD_DESC "
                + "from TX_ORDEN_PROD WHERE STATUS='Terminada' and PK_STATUS='Liberado' and SHP_STATUS='Pendiente' order by ID DESC");
        //Reedefinimos un Modelo de Tabla
                        modeloT = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modeloT);
                        jTable1.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable1.setModel(modeloT);
                        //Agregamos las columnas al nuevo modelo
                        modeloT.addColumn("ID");
                        modeloT.addColumn("VIN");
                        modeloT.addColumn("EMPAQUE");
                        modeloT.addColumn("DESTINO");
                        modeloT.addColumn("MODELO"); 
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);

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

                            }
                            cnxO.reg.close();
                        } catch (SQLException ex) {}    
    }
    /**
     *
     */
    public void liberadas(){
        cnxO.consulta("select /*+ FIRST_ROWS(10) */ id, VIN, NUM_EMP, DESTINO, MOD_DESC, CONTENEDOR "
                + "from TX_ORDEN_PROD WHERE STATUS='Terminada' and SHP_STATUS='Liberado' and id_envio='"+id.getText()+"'  order by ID DESC");
        //Reedefinimos un Modelo de Tabla
                        modeloT = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modeloT);
                        jTable2.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        jTable2.setModel(modeloT);
                        //Agregamos las columnas al nuevo modelo
                        modeloT.addColumn("ID");
                        modeloT.addColumn("VIN");
                        modeloT.addColumn("EMPAQUE");
                        modeloT.addColumn("DESTINO");
                        modeloT.addColumn("MODELO"); 
                        modeloT.addColumn("CONTENEDOR");
                        //Asiganomos un ancho de columnas por default
                        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
                        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(5).setPreferredWidth(100);

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
                                    jTable2.setModel(modeloT);
                                    jTable2.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                            cnxO.reg.close();
                        } catch (SQLException ex) {}    
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
        jLabel10 = new javax.swing.JLabel();
        destino = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        numCon = new javax.swing.JTextField();
        trnsp = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        unidades = new javax.swing.JTextField();
        tarimas = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        id = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setClosable(true);
        setMaximizable(true);
        setTitle("Tuxedo | Embarque");
        setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Embarque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel10.setText("Destino:");

        destino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije" }));

        jLabel11.setText("Transportista:");

        numCon.setBackground(new java.awt.Color(204, 204, 255));
        numCon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        trnsp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Elije" }));

        jLabel9.setText("Contenedor:");

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(numCon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                    .addComponent(trnsp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(trnsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(numCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "EMPAQUE", "DESTINO", "MODELO", "CONTENEDOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(100);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "EMPAQUE", "DESTINO", "MODELO"
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
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Pendientes de Embarque");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("En Embarque");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Embarque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel3.setText("Tarimas:");

        jLabel4.setText("Unidades:");

        unidades.setEditable(false);
        unidades.setBackground(new java.awt.Color(204, 204, 255));
        unidades.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        tarimas.setEditable(false);
        tarimas.setBackground(new java.awt.Color(204, 204, 255));
        tarimas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        jButton2.setText("Cerrar");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        id.setEditable(false);
        id.setBackground(new java.awt.Color(204, 204, 255));
        id.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(unidades, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tarimas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tarimas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Codigo:");

        codigo.setEditable(false);
        codigo.setBackground(new java.awt.Color(204, 204, 255));
        codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk_black.png"))); // NOI18N
        jMenuItem1.setText("Guardar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        jMenuItem2.setText("Cerrar Bitacora");
        jMenuItem2.setEnabled(false);
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codigo))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        // TODO add your handling code here:
        if(codigo.getText().length()==17){
            conexion();
            lectura();
            codigo.setText("");
            pendientes();
            liberadas();
            desconexion();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_SPACE || evt.getKeyCode() == KeyEvent.VK_TAB) {
              System.out.println("ENTER");
              codigo.requestFocus();
        }
    }//GEN-LAST:event_codigoKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cerrarEmb();
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codigo;
    private javax.swing.JComboBox destino;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField numCon;
    private javax.swing.JTextField tarimas;
    private javax.swing.JComboBox trnsp;
    private javax.swing.JTextField unidades;
    // End of variables declaration//GEN-END:variables
}
