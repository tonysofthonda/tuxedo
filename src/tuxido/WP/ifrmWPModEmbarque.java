/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import tuxido.formato_usuario;
/**
 *
 * @author VJ002349
 */
public class ifrmWPModEmbarque extends javax.swing.JInternalFrame {
    String id_envio;
    conexionWPPostgre cnxO;
    double uni=0;
    double tar=0;
    int tmp=0;
    private String fecha;
    private String hora;
    private DefaultTableModel modeloT;
    private TableRowSorter ordenar;
    private Object[] filasx;
    private int fila;
    private int fila2;
    /**
     * Creates new form ifrmWPModEmbarque
     */
    public ifrmWPModEmbarque(String id) {
        initComponents();
        codigo.requestFocus(true);
        id_envio=id;
        info();
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
    
    public void info(){
      conexion();
        cnxO.consulta("select destino, transportista, contenedor, unidades, tarimas, id from WP_INFO_EMBARQUE WHERE ID='"+id_envio+"'");
        try {
            while(cnxO.reg.next()){
                destino.addItem(cnxO.reg.getObject(1));
                trnsp.addItem(cnxO.reg.getObject(2));
                numCon.setText(cnxO.reg.getString(3));
                unidades.setText(cnxO.reg.getString(4));
                uni=cnxO.reg.getDouble(4);
                tarimas.setText(cnxO.reg.getString(5));
                tar=cnxO.reg.getDouble(5);
                id.setText(cnxO.reg.getString(6));
            }
        } catch (SQLException ex) {}
        listas();
        pendientes();
        liberadas();
      desconexion();
    }
    
    /**
     *
     */
    public void listas(){
        try {
           
                cnxO.consulta("select linea from WP_LN_TRANS where status='Activo' and LINEA<>'"+trnsp.getSelectedItem().toString()+"'");
                while(cnxO.reg.next()){
                    trnsp.addItem(cnxO.reg.getObject(1));
                }
                cnxO.consulta("select DESTINO from WP_DESTINO where status='Activo' and DESTINO<>'"+destino.getSelectedItem().toString()+"'");
                while(cnxO.reg.next()){
                    destino.addItem(cnxO.reg.getObject(1));
                }
            
        } catch (SQLException ex) {}
    }

    /**
     *
     */
    public void cerrarEmb(){
        conexion();
        if(numCon.getText().length()>0){
             cnxO.ejecutar("UPDATE WP_INFO_EMBARQUE SET CONTENEDOR='"+numCon.getText()+"' WHERE ID='"+id.getText()+"'");
             cnxO.ejecutar("UPDATE WP_INFO_EMBARQUE SET TRANSPORTISTA='"+trnsp.getSelectedItem().toString()+"' WHERE ID='"+id.getText()+"'");
             cnxO.ejecutar("UPDATE WP_INFO_EMBARQUE SET DESTINO='"+destino.getSelectedItem().toString()+"' WHERE ID='"+id.getText()+"'");
             cnxO.ejecutar("UPDATE WP_INFO_EMBARQUE SET UNIDADES='"+unidades.getText()+"' WHERE ID='"+id.getText()+"'");
             cnxO.ejecutar("UPDATE WP_INFO_EMBARQUE SET TARIMAS='"+tarimas.getText()+"' WHERE ID='"+id.getText()+"'");
             cnxO.ejecutar("UPDATE WP_INFO_EMBARQUE SET STATUS='Listo' WHERE ID='"+id.getText()+"'");
             cnxO.ejecutar("UPDATE WP_PRODUCCION SET CONTENEDOR='"+numCon.getText()+"' WHERE ENVIO='"+id.getText()+"'");
        }else{
             JOptionPane.showMessageDialog(null,"Numero de Contenedor Invalido");
        }
        desconexion();
    }
    /**
     *
     */
    public void lectura(){
        cnxO.consulta("select SHP_STATUS from WP_PRODUCCION WHERE FRAME='"+codigo.getText().toUpperCase()+"'");
        try {
            while(cnxO.reg.next()){
                if(cnxO.reg.getString(1).equals("Pendiente")){
                            uni=uni+1;
                            int tmp2=(int)uni;
                            unidades.setText(String.valueOf(tmp2));
                            double val=uni%8;
                            if(val==1){
                                tar++;
                            }
                            tmp = (int)tar;
                            tarimas.setText(String.valueOf(tmp));
                            if(val==0){
                                cnxO.ejecutar("update WP_PRODUCCION SET TARIMA='"+tarimas.getText()+"' where FRAME='"+codigo.getText().toUpperCase()+"'");
                            }
                            Date fechaActual=new Date();
                            SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                            SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
                            fecha=forFecha.format(fechaActual);
                            hora=forHora.format(fechaActual);
                            cnxO.ejecutar("update WP_INFO_EMBARQUE SET UNIDADES='"+unidades.getText()+"' where ID='"+id.getText()+"'");
                            cnxO.ejecutar("update WP_INFO_EMBARQUE SET TARIMAS='"+tarimas.getText()+"' where ID='"+id.getText()+"'");
                            cnxO.ejecutar("update WP_PRODUCCION SET TARIMA='"+tarimas.getText()+"' where FRAME='"+codigo.getText().toUpperCase()+"'");                    
                            cnxO.ejecutar("update WP_PRODUCCION SET CONTENEDOR='"+numCon.getText()+"' where FRAME='"+codigo.getText().toUpperCase()+"'");
                            cnxO.ejecutar("update WP_PRODUCCION SET ENVIO='"+id.getText()+"' where FRAME='"+codigo.getText().toUpperCase()+"'");
                            cnxO.ejecutar("update WP_PRODUCCION SET SHP='"+fecha+"' where FRAME='"+codigo.getText().toUpperCase()+"'");
                            cnxO.ejecutar("update WP_PRODUCCION SET SHP_TIME='"+hora+"' where FRAME='"+codigo.getText().toUpperCase()+"'");
                            cnxO.ejecutar("update WP_PRODUCCION SET SHP_STATUS='Liberado' where FRAME='"+codigo.getText().toUpperCase()+"'");
                            cnxO.ejecutar("update WP_PRODUCCION SET STATUS='Terminada' where FRAME='"+codigo.getText().toUpperCase()+"'");
                            codigo.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(null,"Esta unidad ya fue Registrada.");
                    codigo.requestFocus();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    public void pendientes(){
        cnxO.consulta("select /*+ FIRST_ROWS(10) */ ID, FRAME, EMPAQUE, MODELO, DESCRIPCION "
                + "from WP_PRODUCCION WHERE STATUS='Pendiente' and PK_STATUS='Liberado' and SHP_STATUS='Pendiente' order by ID DESC");
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
                        modeloT.addColumn("FRAME");
                        modeloT.addColumn("EMPAQUE");
                        modeloT.addColumn("MODELO");
                        modeloT.addColumn("DESCRIPCION");
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(160);

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
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,ex);
                        }   
    }
    /**
     *
     */
    public void liberadas(){
        cnxO.consulta("select /*+ FIRST_ROWS(10) */ ID, FRAME, EMPAQUE, MODELO, DESCRIPCION, CONTENEDOR "
                + "from WP_PRODUCCION WHERE STATUS='Terminada' and SHP_STATUS='Liberado' and ENVIO='"+id.getText()+"' order by ID DESC");
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
                        modeloT.addColumn("FRAME");
                        modeloT.addColumn("EMPAQUE");
                        modeloT.addColumn("MODELO");
                        modeloT.addColumn("DESCRIPCION"); 
                        modeloT.addColumn("CONTENEDOR");
                        //Asiganomos un ancho de columnas por default
                        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable2.getColumnModel().getColumn(1).setPreferredWidth(100);
                        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(4).setPreferredWidth(160);
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
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,ex);
                        }   
    }
    
    public void eliminarTodo(){
        conexion();
            unidades.setText("0");
            tarimas.setText("0");
            cnxO.ejecutar("update WP_PRODUCCION SET tarima='0' where ENVIO='"+id.getText()+"'");
            cnxO.ejecutar("update WP_PRODUCCION SET contenedor='0' where ENVIO='"+id.getText()+"'");
            cnxO.ejecutar("update WP_PRODUCCION SET shp='' where ENVIO='"+id.getText()+"'");
            cnxO.ejecutar("update WP_PRODUCCION SET shp_time='' where ENVIO='"+id.getText()+"'");
            cnxO.ejecutar("update WP_PRODUCCION SET shp_status='Pendiente' where ENVIO='"+id.getText()+"'");
            cnxO.ejecutar("update WP_PRODUCCION SET envio='0' where ENVIO='"+id.getText()+"'");
            codigo.requestFocus();
        pendientes();
        liberadas();
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        destino = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        numCon = new javax.swing.JTextField();
        trnsp = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        codigo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        unidades = new javax.swing.JTextField();
        tarimas = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        id = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Tuxedo - WaterPump | Modificacion Bitacora de Embarque");

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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable2KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Embarque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel10.setText("Destino:");

        destino.setFocusable(false);

        jLabel11.setText("Transportista:");

        numCon.setBackground(new java.awt.Color(204, 204, 255));
        numCon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        trnsp.setFocusable(false);

        jLabel9.setText("Contenedor:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trnsp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numCon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(trnsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(numCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rewind_green.png"))); // NOI18N
        jButton1.setToolTipText("Eliminar Todo...");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        codigo.setBackground(new java.awt.Color(204, 204, 255));
        codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Codigo:");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Pendientes de Embarque");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Embarque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel3.setText("Tarimas:");

        jLabel4.setText("Unidades:");

        unidades.setEditable(false);
        unidades.setBackground(new java.awt.Color(204, 204, 255));
        unidades.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        unidades.setFocusable(false);

        tarimas.setEditable(false);
        tarimas.setBackground(new java.awt.Color(204, 204, 255));
        tarimas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tarimas.setFocusable(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        jButton2.setText("Cerrar");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        id.setEditable(false);
        id.setBackground(new java.awt.Color(204, 204, 255));
        id.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        id.setFocusable(false);

        jLabel5.setText("Envio:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unidades, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tarimas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(tarimas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("En Embarque");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        fila2=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        // TODO add your handling code here:
        fila2=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable2KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        eliminarTodo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        // TODO add your handling code here:
        if(codigo.getText().length()==12){
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cerrarEmb();
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1KeyPressed

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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField numCon;
    private javax.swing.JTextField tarimas;
    private javax.swing.JComboBox trnsp;
    private javax.swing.JTextField unidades;
    // End of variables declaration//GEN-END:variables
}
