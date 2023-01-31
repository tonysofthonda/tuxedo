/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author vj002349
 */
public class ifrmPintura extends javax.swing.JInternalFrame {
    conexionPostgres cnxO;
    private String fecha;
    private String hora;
    private int fila;
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    JDesktopPane panel1;
    String estacion="Pintura";
    private boolean bandera;
    private writeXML wr;
    private String archivo;
    private Element rootNode;
    private Element Unidades;
    /**
     * Creates new form ifrmPintura
     * @param jDesktopPane1 
     */
    public ifrmPintura(JDesktopPane jDesktopPane1) {
        initComponents();
        codigo.requestFocus();
        panel1=jDesktopPane1;
        corte();
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
    public void corte(){
        conexion();
        if(cnxO.Conec != null){
        try {
            cnxO.consulta("select count(*) from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and status='Abierto'");
            while(cnxO.reg.next()){
                if(cnxO.reg.getInt(1)==0){
                    ifrmTurno ifrmTrn=new ifrmTurno(estacion);
                    Dimension desktopSize = panel1.getSize();
                    Dimension jInternalFrameSize = ifrmTrn.getSize();
                    panel1.add(ifrmTrn);
                    ifrmTrn.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
                    ifrmTrn.toFront();
                    ifrmTrn.show();
                }
            }
            cnxO.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
            while(cnxO.reg.next()){
                corte.setText(cnxO.reg.getString(1));
                reg.setText(cnxO.reg.getString(2));
            }
        } catch (SQLException ex) {}
        cerrarCon();
        }else{
            bandera=true;
            jTabbedPane1.setEnabledAt(1, false);
            jTabbedPane1.setEnabledAt(2, true);
            jTabbedPane1.setSelectedIndex(2);
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
        }
    }
   
    /**
     *
     */
    public void recepcion(){
        int band=0;
        String sttswe=null;
        String sttspa=null;
        if(cnxO.Conec != null){
            if(bandera){
                bandera=false;
                XMLtoDB ini=new XMLtoDB(panel1,"PA", archivo);
                ini.start();
                jTabbedPane1.setEnabledAt(0, true);
                jTabbedPane1.setEnabledAt(1, true);
                jTabbedPane1.setEnabledAt(2, false);
                jTabbedPane1.setSelectedIndex(1);
                jButton1.setEnabled(true);
                jButton2.setEnabled(true);
                jButton3.setEnabled(true);
            }
            try {
                cnxO.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
                while(cnxO.reg.next()){
                    corte.setText(cnxO.reg.getString(1));
                    reg.setText(cnxO.reg.getString(2));
                }
                cnxO.consulta("select we_status, pa_status from TX_ORDEN_PROD WHERE VIN='"+codigo.getText().toUpperCase()+"'");
                while(cnxO.reg.next()){
                    sttswe=cnxO.reg.getString(1);
                    sttspa=cnxO.reg.getString(2);
                }
                if("Liberado".equals(sttswe)){ 
                    if("Pendiente".equals(sttspa)){
                        Date fechaActual=new Date();
                        SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
                        SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
                        fecha=forFecha.format(fechaActual);
                        hora=forHora.format(fechaActual);
                        cnxO.ejecutar("update TX_ORDEN_PROD SET pa='"+fecha+"' where vin='"+codigo.getText().toUpperCase()+"'");
                        cnxO.ejecutar("update TX_ORDEN_PROD SET pa_tim='"+hora+"' where vin='"+codigo.getText().toUpperCase()+"'");
                        cnxO.ejecutar("update TX_ORDEN_PROD SET pa_status='Liberado' where vin='"+codigo.getText().toUpperCase()+"'");
                        //cnxO.ejecutar("update TX_ORDEN_PROD SET status='Pendiente' where vin='"+cdRcp.getText().toUpperCase()+"'");
                        cnxO.ejecutar("update TX_PROD_TURNO SET produccion='"+(Integer.parseInt(reg.getText())+1)+"' where id='"+corte.getText()+"'");
                        band=1;
                        codigo.setText("");
                        codigo.requestFocus();
                    }else{
                        codigo.setText("");
                        codigo.requestFocus();
                        JOptionPane.showMessageDialog(null,"El código de barras ya fue Recepcionado en esta Estacion.");
                    }

                }else{
                    codigo.setText("");
                    codigo.requestFocus();
                    JOptionPane.showMessageDialog(null,"El código de barras aun no se encuentra registrado en la Estación Anterior.");
                }
                if(band==1){
                    cnxO.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
                    while(cnxO.reg.next()){
                        corte.setText(cnxO.reg.getString(1));
                        reg.setText(cnxO.reg.getString(2));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ifrmSoldadura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            if(bandera){   
            }else{
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, true);
                jTabbedPane1.setSelectedIndex(2);
                jButton1.setEnabled(false);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
            }
            bandera=true;
            wr=new writeXML();
            wr.write("PA", codigo.getText().toUpperCase());
            archivo=wr.archivo;
        }
    }
    /*public void salida(){
        String sttswe=null;
        String sttspa=null;
        try {
            cnxO.consulta("select we_status, pa_status from TX_ORDEN_PROD WHERE VIN='"+cdLbr.getText().toUpperCase()+"'");
            while(cnxO.reg.next()){
                sttswe=cnxO.reg.getString(1);
                sttspa=cnxO.reg.getString(2);
            }
            if("Liberado".equals(sttswe)){
                if("Recibido".equals(sttspa)){
                    if("Liberado".equals(sttspa)){
                            JOptionPane.showMessageDialog(null,"El código de barras ya fue Liberado en esta Estacion.");
                    }else{
                            Date fechaActual=new Date();
                            SimpleDateFormat forFecha=new SimpleDateFormat("dd-MM-yyyy");
                            SimpleDateFormat forHora=new SimpleDateFormat("dd-MM-yyyy H:mm:ss");
                            fecha=forFecha.format(fechaActual);
                            hora=forHora.format(fechaActual);
                            cnxO.ejecutar("update TX_ORDEN_PROD SET pa='"+fecha+"' where vin='"+cdLbr.getText().toUpperCase()+"'");
                            cnxO.ejecutar("update TX_ORDEN_PROD SET pa_tim='"+hora+"' where vin='"+cdLbr.getText().toUpperCase()+"'");
                            cnxO.ejecutar("update TX_ORDEN_PROD SET pa_status='Liberado' where vin='"+cdLbr.getText().toUpperCase()+"'");
                            //cnxO.ejecutar("update TX_ORDEN_PROD SET status='Pendiente' where vin='"+cdRcp.getText().toUpperCase()+"'");
                    }   
                }else{
                    JOptionPane.showMessageDialog(null,"El codigo de barras no fue registrado como Recibido.");
                }
            }else{
                JOptionPane.showMessageDialog(null,"El código de barras aun no se encuentra registrado en la Estación Anterior.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ifrmSoldadura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    /**
     *
     */
    public void pendientes(){
        if(cnxO.Conec != null){
            cnxO.consulta("select id, VIN, MOD_DESC, ANIO, STATUS, to_char(PA,'dd-MM-yyyy'), to_char(PA_tim,'HH24:MI:SS'), PA_STATUS "
                    + "from TX_ORDEN_PROD WHERE STATUS='Pendiente' and WE_STATUS='Liberado' and PA_STATUS='Pendiente' order by ID DESC");
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
                            modelo.addColumn("Status");
                            modelo.addColumn("Fecha");
                            modelo.addColumn("Hora");
                            modelo.addColumn("Status Terminal");   
                            //Asiganomos un ancho de columnas por default
                            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                            jTable1.getColumnModel().getColumn(1).setPreferredWidth(140);
                            jTable1.getColumnModel().getColumn(2).setPreferredWidth(120);
                            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
                            jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);

                            //asigno el tamaño del arreglo con la cantidad de columnas:
                            filasx = new Object[modelo.getColumnCount()];
                            try {
                                //escribo las filas:
                                while (cnxO.reg.next()){
                                    for (int i=0;i<modelo.getColumnCount();i++){
                                         filasx[i] = cnxO.reg.getObject(i+1);
                                    } 
                                    //escribo las filas del resultado SQL//
                                        modelo.addRow(filasx);
                                        jTable1.setModel(modelo);
                                        jTable1.setDefaultRenderer(Object.class, new formato_usuario());

                                }
                                cnxO.reg.close();
                            } catch (SQLException ex) {

                            }
                }else{
                    jTabbedPane1.setEnabledAt(0, false);
                }
    }
    /**
     *
     */
    public void liberadas(){
        if(cnxO.Conec != null){
        cnxO.consulta("select id, VIN, MOD_DESC, ANIO, STATUS, to_char(PA,'dd-MM-yyyy'), to_char(PA_TIM,'HH24:MI:SS'), PA_STATUS "
                + "from TX_ORDEN_PROD WHERE pa_status='Liberado' and status='Pendiente' order by ID DESC");
        //Reedefinimos un Modelo de Tabla
                        modelo = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        //Asignamos el nuevo Modelo
                        jTable2.setModel(modelo);
                        //Agregamos las columnas al nuevo modelo
                        modelo.addColumn("ID");
                        modelo.addColumn("VIN");
                        modelo.addColumn("Descripcion");
                        modelo.addColumn("Año Modelo");
                        modelo.addColumn("Status");
                        modelo.addColumn("Fecha");
                        modelo.addColumn("Hora");
                        modelo.addColumn("Status Terminal");   
                        //Asiganomos un ancho de columnas por default
                        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable2.getColumnModel().getColumn(1).setPreferredWidth(140);
                        jTable2.getColumnModel().getColumn(2).setPreferredWidth(120);
                        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(6).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(7).setPreferredWidth(100);

                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modelo.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnxO.reg.next()){
                                for (int i=0;i<modelo.getColumnCount();i++){
                                     filasx[i] = cnxO.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modelo.addRow(filasx);
                                    jTable2.setModel(modelo);
                                    jTable2.setDefaultRenderer(Object.class, new formato_usuario());
                                    ordenar = new TableRowSorter(modelo);
                                    jTable2.setRowSorter(ordenar);
                            }
                            cnxO.reg.close();
                        } catch (SQLException ex) {
                        
                        }
                }else{
                    jTabbedPane1.setEnabledAt(0, false);
                }
    }
    /**
     *
     */
    public void cerrarCon(){
        try {
            cnxO.Conec.close();
        } catch (SQLException ex) {
            Logger.getLogger(ifrmSoldadura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void readXML(){
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File( archivo );
                                        modelo = new DefaultTableModel(){
                                            @Override
                                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                                return false;
                                            }
                                        };
                                        //Asignamos el nuevo Modelo
                                        jTable3.setModel(modelo);
                                        //Agregamos las columnas al nuevo modelo
                                        modelo.addColumn("ID");
                                        modelo.addColumn("VIN");
                                        modelo.addColumn("Fecha");
                                        modelo.addColumn("Hora");
                                        //Asiganomos un ancho de columnas por default
                                        jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
                                        jTable3.getColumnModel().getColumn(1).setPreferredWidth(140);
                                        jTable3.getColumnModel().getColumn(2).setPreferredWidth(80);
                                        jTable3.getColumnModel().getColumn(3).setPreferredWidth(80);
                                        filasx = new Object[modelo.getColumnCount()];
                                        try {
                                            Document document = (Document) builder.build( xmlFile );
                                            //INFO
                                            rootNode = document.getRootElement();
                                            //CARGA
                                            Element carga = rootNode.getChild("CARGA");
                                            for ( int i = 0; i < carga.getChildren().size(); i++ ){
                                                //UNIDAD
                                                Unidades = (Element) carga.getChildren().get(i);
                                                filasx[0] = i;
                                                filasx[1] = Unidades.getChildTextTrim("VIN");
                                                filasx[2] = Unidades.getChildTextTrim("FECHA");
                                                filasx[3] = Unidades.getChildTextTrim("HORA");
                                                //escribo las filas del resultado SQL//
                                                    modelo.addRow(filasx);
                                                    jTable3.setModel(modelo);
                                                    jTable3.setDefaultRenderer(Object.class, new formato_usuario());
                                                    ordenar = new TableRowSorter(modelo);
                                                    jTable3.setRowSorter(ordenar);
                                            }
                                            
                                        }catch ( IOException io ) {
                                                System.out.println( io.getMessage() );
                                        }catch ( JDOMException jdomex ) {
                                                System.out.println( jdomex.getMessage() );
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        reg = new javax.swing.JTextField();
        corte = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setTitle("Tuxedo | Pintura");
        setToolTipText("");

        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setRequestFocusEnabled(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Descripcion", "Año Modelo", "Status", "Fecha", "Hora", "Status Terminal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setFocusable(false);
        jTable1.setRequestFocusEnabled(false);
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

        jTabbedPane1.addTab("Pendientes", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Descripcion", "Año Modelo", "Status", "Fecha", "Hora", "Status Terminal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.setFocusable(false);
        jTable2.setRequestFocusEnabled(false);
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

        jTabbedPane1.addTab("Liberadas", jScrollPane2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Fecha", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable3.setFocusable(false);
        jTable3.setRequestFocusEnabled(false);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(120);

        jTabbedPane1.addTab("Offline", jScrollPane3);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codigo, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton1.setToolTipText("Actualizar");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setRequestFocusEnabled(false);
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
        jButton3.setRequestFocusEnabled(false);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user_alert.png"))); // NOI18N
        jButton2.setToolTipText("Registro de Turno");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setRequestFocusEnabled(false);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Corte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel2.setText("Registros:");
        jLabel2.setFocusable(false);
        jLabel2.setRequestFocusEnabled(false);

        reg.setEditable(false);
        reg.setBackground(new java.awt.Color(204, 204, 255));
        reg.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        reg.setFocusable(false);
        reg.setRequestFocusEnabled(false);

        corte.setEditable(false);
        corte.setBackground(new java.awt.Color(204, 204, 255));
        corte.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        corte.setFocusable(false);
        corte.setRequestFocusEnabled(false);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel3.setText("Corte:");
        jLabel3.setFocusable(false);
        jLabel3.setRequestFocusEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(corte, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reg, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(reg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(corte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
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
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE))
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        // TODO add your handling code here:
        if(codigo.getText().length()==17){
            conexion();
            recepcion();
             if(cnxO.Conec != null){
                pendientes();
                liberadas();
                cerrarCon();
             }else{
                jTabbedPane1.setSelectedIndex(2);
                readXML();
             }
             codigo.setText("");
             codigo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        }
    }//GEN-LAST:event_codigoKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        conexion();
        pendientes();
        liberadas();
        cerrarCon();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2KeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

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
        ifrmTurno ifrmTrn=new ifrmTurno(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmTrn.getSize();
        panel1.add(ifrmTrn);
        ifrmTrn.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmTrn.toFront();
        ifrmTrn.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField corte;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JTextField reg;
    // End of variables declaration//GEN-END:variables
}
