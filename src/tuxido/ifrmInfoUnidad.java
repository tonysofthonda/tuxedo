/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.Dimension;
import java.sql.SQLException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author vj002349
 */
public class ifrmInfoUnidad extends javax.swing.JInternalFrame {
    JDesktopPane Pane3;
    conexionPostgres cnx;
    private DefaultTableModel modeloT;
    private TableRowSorter ordenar;
    private Object[] filasx;
    generarTarjeta genT=new generarTarjeta();
    generarSalidaEmb genE=new generarSalidaEmb();
    private String fecha;
    /**
     * Creates new form ifrmInfoUnidad
     * @param jDesktopPane3
     * @param vin  
     */
    public ifrmInfoUnidad(JDesktopPane jDesktopPane3, String vin) {
        initComponents();
        Pane3=jDesktopPane3;
        unidad.setText(vin);
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
            cnx.consulta("select "
                       + "ID_MODELO, MOD_DESC, ANIO, ID_COLOR, COL_DESC, "
                       + "ID_ORDEN, MOTOR, NUMERO, DESTINO, to_char(PRODUCCION,'dd-MM-yyyy'), "
                       + "ID_ENVIO, ENVIO, NUM_EMP, CONTENEDOR, TARIMA "
                       + " from TX_ORDEN_PROD WHERE VIN='"+unidad.getText()+"'");
            try {
                while(cnx.reg.next()){
                    //informacion modelo maestro
                        modelo.setText(cnx.reg.getString(1));
                        desc_mod.setText(cnx.reg.getString(2));
                        anio.setText(cnx.reg.getString(3));
                        desc_col.setText(cnx.reg.getString(4));
                        cod_color.setText(cnx.reg.getString(5));
                    //informacion orden produccion
                        ord_prod.setText(cnx.reg.getString(6));
                        motor.setText(cnx.reg.getString(7));
                        num_motor.setText(cnx.reg.getString(8));
                        destino.setText(cnx.reg.getString(9));
                        fecha_prod.setText(cnx.reg.getString(10));
                    //informacion embarque
                        envio.setText(cnx.reg.getString(11));
                        envio_mod.setText(cnx.reg.getString(12));
                        empaque.setText(cnx.reg.getString(13));
                        contenedor.setText(cnx.reg.getString(14));
                        tarima.setText(cnx.reg.getString(15));
                }
            } catch (SQLException ex) {}
            
            cnx.consulta("select "
                        + "WE_STATUS, to_char(WE,'dd-MM-yyyy'), to_char(WE_tim,'HH24:MI:SS'),"
                        + "PA_STATUS, to_char(PA,'dd-MM-yyyy'), to_char(PA_tim,'HH24:MI:SS'),"
                        + "AF_STATUS, to_char(AF,'dd-MM-yyyy'), to_char(AF_tim,'HH24:MI:SS'),"
                        + "VQ_STATUS, to_char(VQ,'dd-MM-yyyy'), to_char(VQ_tim,'HH24:MI:SS'),"
                        + "PK_STATUS, to_char(PK,'dd-MM-yyyy'), to_char(PK_tim,'HH24:MI:SS'),"
                        + "SHP_STATUS, to_char(SHP,'dd-MM-yyyy'), to_char(SHP_tim,'HH24:MI:SS') " 
                        + " from TX_ORDEN_PROD WHERE VIN='"+unidad.getText()+"'");
        //Reedefinimos un Modelo de Tabla
                        modeloT = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modeloT);
                        info.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        info.setModel(modeloT);
                        //Agregamos las columnas al nuevo modelo
                        modeloT.addColumn("Soldadura");
                        modeloT.addColumn("Fecha");
                        modeloT.addColumn("Hora");
                        modeloT.addColumn("Pintura");
                        modeloT.addColumn("Fecha"); 
                        modeloT.addColumn("Hora");
                        modeloT.addColumn("Ensamble");
                        modeloT.addColumn("Fecha");
                        modeloT.addColumn("Hora");
                        modeloT.addColumn("Calidad");
                        modeloT.addColumn("Fecha");
                        modeloT.addColumn("Hora");
                        modeloT.addColumn("Empaque");
                        modeloT.addColumn("Fecha");
                        modeloT.addColumn("Hora");
                        modeloT.addColumn("Embarque");
                        modeloT.addColumn("Fecha");
                        modeloT.addColumn("Hora");
                        //Asiganomos un ancho de columnas por default
                        //asigno el tamaño del arreglo con la cantidad de columnas:
                        filasx = new Object[modeloT.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                for (int i=0;i<modeloT.getColumnCount();i++){
                                     filasx[i] = cnx.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modeloT.addRow(filasx);
                                    info.setModel(modeloT);
                                    info.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                        } catch (SQLException ex) {}
                        cnx.consulta("SELECT "
                                        + "id, terminal, tipo, descr, "
                                        + "TO_char(fecha,'dd-MM-yyyy'), "
                                        + "to_char(hora,'HH24:MI:SS') FROM "
                                        + "TX_INCIDENTES WHERE VIN='"+unidad.getText()+"' ORDER BY id ASC");
                        modeloT = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int rowIndex, int vColIndex) {
                                return false;
                            }
                        };
                        ordenar = new TableRowSorter(modeloT);
                        inci.setRowSorter(ordenar);
                        //Asignamos el nuevo Modelo
                        inci.setModel(modeloT);
                        modeloT.addColumn("ID");
                        modeloT.addColumn("Terminal");
                        modeloT.addColumn("Tipo");
                        modeloT.addColumn("Descripcion");
                        modeloT.addColumn("Fecha"); 
                        modeloT.addColumn("Hora");
                        filasx = new Object[modeloT.getColumnCount()];
                        try {
                            //escribo las filas:
                            while (cnx.reg.next()){
                                for (int i=0;i<modeloT.getColumnCount();i++){
                                     filasx[i] = cnx.reg.getObject(i+1);
                                } 
                                //escribo las filas del resultado SQL//
                                    modeloT.addRow(filasx);
                                    inci.setModel(modeloT);
                                    inci.setDefaultRenderer(Object.class, new formato_usuario());

                            }
                        } catch (SQLException ex) {}
        desconexion();
    }
    
        /**
     *
     */
    public void imprimir(){
        try {
            conexion();
                cnx.consulta("select to_char(PK,'dd-MM-yyyy') from TX_ORDEN_PROD WHERE VIN='"+unidad.getText()+"'");
                try {
                    while(cnx.reg.next()){
                        fecha=cnx.reg.getString(1);
                    }
                } catch (SQLException ex) {}
            desconexion();
            String comando ="^XA"
                            +"^FO50,10^A@N,15,15,R:ARIAL.TTF^FDINFO. EMPAQUE:^FS"
                            +"^FO50,30^A@N,15,15,B:ARIAL.TTF^FDMODELO:^FS"
                            +"^FO220,30^A@N,15,15,B:ARIAL.TTF^FD"+desc_mod.getText()+"^FS"
                            +"^FO50,50^A@N,15,15,E:ARIAL.TTF^FDMOTOR:^FS"
                            +"^FO220,50^A@N,15,15,B:ARIAL.TTF^FD"+motor.getText()+num_motor.getText()+"^FS"
                            +"^FO50,70^A@N,15,15,B:ARIAL.TTF^FDCOLOR:^FS"
                            +"^FO220,70^A@N,15,15,B:ARIAL.TTF^FD"+desc_col.getText()+"^FS"
                            +"^FO50,90^A@N,15,15,B:ARIAL.TTF^FDDESTINO:^FS"
                            +"^FO220,90^A@N,15,15,B:ARIAL.TTF^FD"+destino.getText()+"^FS"
                            +"^FO370,70^A@N,15,15,B:ARIAL.TTF^FDFECHA:^FS"
                            +"^FO520,70^A@N,15,15,B:ARIAL.TTF^FD"+fecha+"^FS"
                            +"^FO370,90^A@N,15,15,B:ARIAL.TTF^FDEMPAQUE:^FS"
                            +"^FO520,90^A@N,15,15,B:ARIAL.TTF^FD"+empaque.getText()+"^FS"
                            +"^FO50,110^B3N,N,50,Y^FD"+unidad.getText()+"^FS"
                            +"^XZ";
            PrintService  printService = PrintServiceLookup.lookupDefaultPrintService();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(comando.getBytes(), flavor, null);
            DocPrintJob docPrintJob =  printService.createPrintJob();
            
                docPrintJob.print(doc, null);
            
        } catch (PrintException ex) {
            JOptionPane.showMessageDialog(null,"ERROR: "+ex);
        }
        
    }
    
    public void reasiganarEmp(){
        String temp="";
        String tempEmp=null;
        try{
             conexion();
                cnx.consulta("select ACT_SERIE_EMP from TX_MODELO WHERE CODIGO_MODELO='"+modelo.getText()+"' and ANIO ='"+anio.getText()+"' and STATUS='Activo'");
                while(cnx.reg.next()){
                    if(cnx.reg.getString(1).length()<6){
                        for(int z=0;z<(6-cnx.reg.getString(1).length());z++){
                            temp=temp+"0";
                        }
                    }
                    temp=temp+String.valueOf(cnx.reg.getString(1));
                    tempEmp=temp;
                }
                cnx.ejecutar("update TX_MODELO SET ACT_SERIE_EMP='"+(Integer.parseInt(tempEmp)+1)+"' where codigo_modelo='"+modelo.getText()+"' and ANIO ='"+anio.getText()+"'");
                cnx.ejecutar("update TX_ORDEN_PROD SET num_emp='"+tempEmp+"' where vin='"+unidad.getText().toUpperCase()+"'");
            desconexion();
        } catch (SQLException ex) {}
        informacion();
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
        info = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        inci = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        envio = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        envio_mod = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        empaque = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tarima = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        contenedor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        ord_prod = new javax.swing.JTextField();
        motor = new javax.swing.JTextField();
        destino = new javax.swing.JTextField();
        num_motor = new javax.swing.JTextField();
        fecha_prod = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        unidad = new javax.swing.JTextField();
        modelo = new javax.swing.JTextField();
        desc_mod = new javax.swing.JTextField();
        anio = new javax.swing.JTextField();
        desc_col = new javax.swing.JTextField();
        cod_color = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();

        setClosable(true);
        setTitle("Tuxedo | Informacion de Unidad");

        info.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        info.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        info.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(info);

        jTabbedPane1.addTab("Info. Produccion", jScrollPane1);

        inci.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        inci.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(inci);

        jTabbedPane1.addTab("Incidentes", jScrollPane2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion Embarque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        envio.setEditable(false);
        envio.setBackground(new java.awt.Color(204, 204, 255));
        envio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel12.setText("ID Envio");

        envio_mod.setEditable(false);
        envio_mod.setBackground(new java.awt.Color(204, 204, 255));
        envio_mod.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel13.setText("Envio Modelo:");

        empaque.setEditable(false);
        empaque.setBackground(new java.awt.Color(204, 204, 255));
        empaque.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel14.setText("Empaque:");

        tarima.setEditable(false);
        tarima.setBackground(new java.awt.Color(204, 204, 255));
        tarima.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel15.setText("Tarima:");

        contenedor.setEditable(false);
        contenedor.setBackground(new java.awt.Color(204, 204, 255));
        contenedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel16.setText("Contenedor:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/box_world.png"))); // NOI18N
        jButton1.setToolTipText("Re-asignar numero de empaque.");
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(envio, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(envio_mod)
                    .addComponent(contenedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(empaque, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(tarima, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(envio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(envio_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(empaque, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tarima, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion Orden Produccion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel7.setText("Ord. Produccion:");

        jLabel8.setText("Motor:");

        jLabel9.setText("Fecha Produccion:");

        jLabel10.setText("Numero Motor:");

        jLabel11.setText("Destino:");

        ord_prod.setEditable(false);
        ord_prod.setBackground(new java.awt.Color(204, 204, 255));
        ord_prod.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        motor.setEditable(false);
        motor.setBackground(new java.awt.Color(204, 204, 255));
        motor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        destino.setEditable(false);
        destino.setBackground(new java.awt.Color(204, 204, 255));
        destino.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        num_motor.setEditable(false);
        num_motor.setBackground(new java.awt.Color(204, 204, 255));
        num_motor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        fecha_prod.setEditable(false);
        fecha_prod.setBackground(new java.awt.Color(204, 204, 255));
        fecha_prod.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(motor, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(num_motor, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fecha_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ord_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ord_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(motor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(num_motor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(fecha_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion Modelo Maestro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel1.setText("Unidad:");

        jLabel2.setText("Modelo:");

        jLabel3.setText("Año Modelo:");

        jLabel4.setText("Color:");

        jLabel5.setText("Descripcion:");

        jLabel6.setText("Codigo Color:");

        unidad.setEditable(false);
        unidad.setBackground(new java.awt.Color(204, 204, 255));
        unidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        modelo.setEditable(false);
        modelo.setBackground(new java.awt.Color(204, 204, 255));
        modelo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        desc_mod.setEditable(false);
        desc_mod.setBackground(new java.awt.Color(204, 204, 255));
        desc_mod.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        anio.setEditable(false);
        anio.setBackground(new java.awt.Color(204, 204, 255));
        anio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        desc_col.setEditable(false);
        desc_col.setBackground(new java.awt.Color(204, 204, 255));
        desc_col.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cod_color.setEditable(false);
        cod_color.setBackground(new java.awt.Color(204, 204, 255));
        cod_color.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(desc_col, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(desc_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cod_color, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addComponent(unidad))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(desc_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(desc_col, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cod_color, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jMenu1.setText("Opciones");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cog_start.png"))); // NOI18N
        jMenuItem1.setText("Cambio Motor");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rewind_blue.png"))); // NOI18N
        jMenuItem2.setText("Retroceso Unidad");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jMenuItem4.setText("Refrescar Informacion");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Impresion");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_color.png"))); // NOI18N
        jMenuItem3.setText("Hoja de Vehiculo");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_mono.png"))); // NOI18N
        jMenuItem5.setText("Hoja de Informacion");
        jMenu2.add(jMenuItem5);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/imprimir.png"))); // NOI18N
        jMenuItem6.setText("Etiqueta de Embarque");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        jMenuItem7.setText("Bitacora de Embarque");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/table_column_add.png"))); // NOI18N
        jMenuItem8.setText("Reporte de Unidades");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        ifrmCmotor ifrmcm=new ifrmCmotor(unidad.getText());
        Dimension desktopSize = Pane3.getSize();
        Dimension jInternalFrameSize = ifrmcm.getSize();
        Pane3.add(ifrmcm);
        ifrmcm.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmcm.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        ifrmResetStatus ifrmrs=new ifrmResetStatus(unidad.getText());
        Dimension desktopSize = Pane3.getSize();
        Dimension jInternalFrameSize = ifrmrs.getSize();
        Pane3.add(ifrmrs);
        ifrmrs.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmrs.show();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        informacion();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        genT.conReporte(unidad.getText());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        imprimir();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        genE.generar(envio.getText());
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        ifrmTablaEmbarque ifrmtb=new ifrmTablaEmbarque(envio.getText());
        Dimension desktopSize = Pane3.getSize();
        Dimension jInternalFrameSize = ifrmtb.getSize();
        Pane3.add(ifrmtb);
        ifrmtb.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmtb.show();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int ax = JOptionPane.showConfirmDialog(null, "<html><strong>Advertencia:</strong><P>"
                                                   + "Deseas reasignar la <strong>Unidad "+unidad.getText()+"</strong><br>"
                                                   + "con el numero de <strong>Empaque "+empaque.getText()+"</strong><br>"
                                                   + "por el ultimo numero de empaque del consecutivo?</html>");
        if(ax == JOptionPane.YES_OPTION){
            reasiganarEmp();
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anio;
    private javax.swing.JTextField cod_color;
    private javax.swing.JTextField contenedor;
    private javax.swing.JTextField desc_col;
    private javax.swing.JTextField desc_mod;
    private javax.swing.JTextField destino;
    private javax.swing.JTextField empaque;
    private javax.swing.JTextField envio;
    private javax.swing.JTextField envio_mod;
    private javax.swing.JTextField fecha_prod;
    private javax.swing.JTable inci;
    private javax.swing.JTable info;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField modelo;
    private javax.swing.JTextField motor;
    private javax.swing.JTextField num_motor;
    private javax.swing.JTextField ord_prod;
    private javax.swing.JTextField tarima;
    private javax.swing.JTextField unidad;
    // End of variables declaration//GEN-END:variables
}
