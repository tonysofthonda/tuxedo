/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.Dimension;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ifrmEmpaque extends javax.swing.JInternalFrame {
    PrintService  printService = PrintServiceLookup.lookupDefaultPrintService();
    JDesktopPane panel1;
    private int fila;
    conexionPostgres cnx;
    private String sttspk;
    private String sttsvq;
    private String fecha;
    private String hora;
    int band=0;
    String temp="";
    private DefaultTableModel modelo;
    private TableRowSorter ordenar;
    private Object[] filasx;
    String destino;
    String estacion="Empaque";
    private String tempEmp;


    /**
     * Creates new form ifrmEmpaque
     * @param jDesktopPane1 
     */
    public ifrmEmpaque(JDesktopPane jDesktopPane1) {
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
        } catch (SQLException ex) {
            Logger.getLogger(ifrmEnsamble.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *
     */
    public void lectura(){
            try {
                cnx.consulta("select vq_status, pk_status, id_modelo, mod_desc, anio, col_desc, motor, numero, destino from TX_ORDEN_PROD WHERE vin='"+codigo.getText().toUpperCase()+"'");
                while(cnx.reg.next()){
                    sttsvq=cnx.reg.getString(1);
                    sttspk=cnx.reg.getString(2);
                    if("Liberado".equals(sttsvq)){
                        if("Pendiente".equals(sttspk)){
                            cmodelo.setText("");
                            desc.setText("");
                            anio.setText("");
                            color.setText("");
                            pref.setText("");
                            motor.setText("");
                            VIN.setText("");
                            empaque.setText("");
                            
                            cmodelo.setText(cnx.reg.getString(3));
                            desc.setText(cnx.reg.getString(4));
                            anio.setText(cnx.reg.getString(5));
                            color.setText(cnx.reg.getString(6));
                            pref.setText(cnx.reg.getString(7));
                            motor.setText(cnx.reg.getString(8));
                            VIN.setText(codigo.getText());
                            btnG.setEnabled(true);
                            //btnRP.setEnabled(false);
                            btnI.setEnabled(true);
                            destino=cnx.reg.getString(9);
                        }else{
                            JOptionPane.showMessageDialog(null,"El codigo de barras ya fue Registrado en esta Estacion.");
                            btnG.setEnabled(false);
                            btnI.setEnabled(false);
                            //btnRP.setEnabled(false);
                            
                            cmodelo.setText("");
                            desc.setText("");
                            anio.setText("");
                            color.setText("");
                            pref.setText("");
                            motor.setText("");
                            VIN.setText("");
                            empaque.setText("");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"El codigo de barras aun no se encuentra registrado en la Estación Anterior.");
                        btnG.setEnabled(false);
                        btnI.setEnabled(false);
                        //btnRP.setEnabled(false);
                        
                            cmodelo.setText("");
                            desc.setText("");
                            anio.setText("");
                            color.setText("");
                            pref.setText("");
                            motor.setText("");
                            VIN.setText("");
                            empaque.setText("");
                    }
                }
                empaque.setText("");
                temp="";
                if(anio.getText()!= null && !anio.getText().equals("")){
                    cnx.consulta("select ACT_SERIE_EMP from TX_MODELO WHERE CODIGO_MODELO='"+cmodelo.getText()+"' and ANIO ='"+anio.getText()+"' and STATUS='Activo'");
                    while(cnx.reg.next()){
                        if(cnx.reg.getString(1).length()<6){
                            for(int z=0;z<(6-cnx.reg.getString(1).length());z++){
                                temp=temp+"0";
                            }
                        }
                        temp=temp+String.valueOf(cnx.reg.getString(1));
                        empaque.setText(temp);
                    }               
                }
        } catch (SQLException ex) {}
    }
    /**
     *
     */
    public void corte(){
        conexion();
        try {
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
        } catch (SQLException ex) {
        }
        cerrarCon();
    }
    /**
     *
     */
    public void guardar(){
        //btnRP.setEnabled(true);
        btnG.setEnabled(false);
        btnI.setEnabled(true);
        
        Date fechaActual=new Date();
        SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat forHora=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
        fecha=forFecha.format(fechaActual);
        hora=forHora.format(fechaActual);
        
        cnx.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
        try {
            while(cnx.reg.next()){
                corte.setText(cnx.reg.getString(1));
                reg.setText(cnx.reg.getString(2));
            }
        } catch (SQLException ex) {
                 System.out.println("1.-"+ex);

        }
        
        cnx.ejecutar("update TX_ORDEN_PROD SET pk='"+fecha+"' where vin='"+VIN.getText().toUpperCase()+"'");
        cnx.ejecutar("update TX_ORDEN_PROD SET pk_tim='"+hora+"' where vin='"+VIN.getText().toUpperCase()+"'");
        cnx.ejecutar("update TX_ORDEN_PROD SET pk_status='Liberado' where vin='"+VIN.getText().toUpperCase()+"'");
        
        System.out.println("reg.getText() "+reg.getText());
        System.out.println("corte.getText() "+corte.getText());  
        
        cnx.ejecutar("update TX_PROD_TURNO SET produccion='"+(Integer.parseInt(reg.getText())+1)+"' where id='"+corte.getText()+"'");

        cnx.ejecutar("update TX_MODELO SET ACT_SERIE_EMP='"+(Integer.parseInt(empaque.getText())+1)+"' where codigo_modelo='"+cmodelo.getText()+"' and ANIO ='"+anio.getText()+"'");
        cnx.ejecutar("update TX_ORDEN_PROD SET num_emp='"+empaque.getText()+"' where vin='"+VIN.getText().toUpperCase()+"'");
        for(int x=0;x<2;x++){
            imprimir();
        }
        
        cnx.consulta("select id, produccion from TX_PROD_TURNO WHERE ESTACION='"+estacion+"' and STATUS='Abierto'");
        try {
            while(cnx.reg.next()){
                corte.setText(cnx.reg.getString(1));
                reg.setText(cnx.reg.getString(2));
            }
        } catch (SQLException ex) {
                System.out.println("3.-"+ex);
        }
        
        cmodelo.setText("");
        desc.setText("");
        anio.setText("");
        color.setText("");
        pref.setText("");
        motor.setText("");
        VIN.setText("");
        empaque.setText("");
    } 
    /**
     *
     */
    public void imprimir(){
        try {
            Date fechaActual=new Date();
            SimpleDateFormat forFecha=new SimpleDateFormat("dd-MM-yyyy");
            fecha=forFecha.format(fechaActual);
            
            String comando ="^XA"
                            +"^FO50,10^A@N,15,15,R:ARIAL.TTF^FDINFO. EMPAQUE:^FS"
                            +"^FO50,30^A@N,15,15,B:ARIAL.TTF^FDMODELO:^FS"
                            +"^FO220,30^A@N,15,15,B:ARIAL.TTF^FD"+desc.getText()+"^FS"
                            +"^FO50,50^A@N,15,15,E:ARIAL.TTF^FDMOTOR:^FS"
                            +"^FO220,50^A@N,15,15,B:ARIAL.TTF^FD"+pref.getText()+motor.getText()+"^FS"
                            +"^FO50,70^A@N,15,15,B:ARIAL.TTF^FDCOLOR:^FS"
                            +"^FO220,70^A@N,15,15,B:ARIAL.TTF^FD"+color.getText()+"^FS"
                            +"^FO50,90^A@N,15,15,B:ARIAL.TTF^FDDESTINO:^FS"
                            +"^FO220,90^A@N,15,15,B:ARIAL.TTF^FD"+destino+"^FS"
                            +"^FO370,70^A@N,15,15,B:ARIAL.TTF^FDFECHA:^FS"
                            +"^FO520,70^A@N,15,15,B:ARIAL.TTF^FD"+fecha+"^FS"
                            +"^FO370,90^A@N,15,15,B:ARIAL.TTF^FDEMPAQUE:^FS"
                            +"^FO520,90^A@N,15,15,B:ARIAL.TTF^FD"+empaque.getText()+"^FS"
                            +"^FO50,110^B3N,N,50,Y^FD"+VIN.getText()+"^FS"
                            +"^XZ";
            
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(comando.getBytes(), flavor, null);
            DocPrintJob docPrintJob =  printService.createPrintJob();
            
                docPrintJob.print(doc, null);
            
        } catch (PrintException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        
    }
    /**
     *
     */
    public void pendientes(){
                 cnx.consulta("select /*+ FIRST_ROWS(10)*/ id, VIN, MOD_DESC, ANIO, num_emp, to_char(pk,'dd-MM-yyyy'), to_char(pk_tim,'HH24:MI:SS'), pk_STATUS "
                + "from TX_ORDEN_PROD WHERE status='Terminada' and pk_status='Pendiente' order by ID DESC");
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
                        modelo.addColumn("Num. Empaque");
                        modelo.addColumn("Fecha");
                        modelo.addColumn("Hora");
                        modelo.addColumn("Status Terminal");   
                        //Asiganomos un ancho de columnas por default
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
                        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
                        jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
                        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);

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
                 cnx.consulta("select /*+ FIRST_ROWS(10)*/ id, VIN, MOD_DESC, MOTOR||NUMERO, COL_DESC, DESTINO,num_emp, to_char(pk,'dd-MM-yyyy'), to_char(pk_tim,'HH24:MI:SS'), pk_STATUS "
                + "from TX_ORDEN_PROD WHERE NUM_EMP<>0 and SHP_STATUS='Pendiente' order by ID DESC");
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
                        modelo.addColumn("ID");//0
                        modelo.addColumn("VIN");//1
                        modelo.addColumn("Descripcion");//2
                        modelo.addColumn("Motor");//3
                        modelo.addColumn("Color");//4
                        modelo.addColumn("Destino");//5
                        modelo.addColumn("Empaque");//6
                        modelo.addColumn("Fecha");//7
                        modelo.addColumn("Hora");//8
                        modelo.addColumn("Status Terminal");//9
                        //Asiganomos un ancho de columnas por default
                        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
                        jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
                        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(3).setPreferredWidth(120);
                        jTable2.getColumnModel().getColumn(4).setPreferredWidth(60);
                        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(6).setPreferredWidth(60);
                        jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(8).setPreferredWidth(80);
                        jTable2.getColumnModel().getColumn(9).setPreferredWidth(100);

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
    public void reimprimir(){
         try {
            Date fechaActual=new Date();
            SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
            fecha=forFecha.format(fechaActual);
            
            String emp=jTable2.getValueAt(fila, 6).toString();
            String mod=jTable2.getValueAt(fila, 2).toString();
            String mtr=jTable2.getValueAt(fila, 3).toString();
            String clr=jTable2.getValueAt(fila, 4).toString();
            String dst=jTable2.getValueAt(fila, 5).toString();
            String fch=jTable2.getValueAt(fila, 7).toString();
            String vn=jTable2.getValueAt(fila, 1).toString();
            
            String comando ="^XA"
                            +"^FO50,10^A@N,15,15,R:ARIAL.TTF^FDINFO. EMPAQUE:^FS"
                            +"^FO50,30^A@N,15,15,B:ARIAL.TTF^FDMODELO:^FS"
                            +"^FO210,30^A@N,15,15,B:ARIAL.TTF^FD"+mod+"^FS"
                            +"^FO50,50^A@N,15,15,E:ARIAL.TTF^FDMOTOR:^FS"
                            +"^FO210,50^A@N,15,15,B:ARIAL.TTF^FD"+mtr+"^FS"
                            +"^FO50,70^A@N,15,15,B:ARIAL.TTF^FDCOLOR:^FS"
                            +"^FO210,70^A@N,15,15,B:ARIAL.TTF^FD"+clr+"^FS"
                            +"^FO50,90^A@N,15,15,B:ARIAL.TTF^FDDESTINO:^FS"
                            +"^FO210,90^A@N,15,15,B:ARIAL.TTF^FD"+dst+"^FS"
                            +"^FO370,70^A@N,15,15,B:ARIAL.TTF^FDFECHA:^FS"
                            +"^FO520,70^A@N,15,15,B:ARIAL.TTF^FD"+fch+"^FS"
                            +"^FO370,90^A@N,15,15,B:ARIAL.TTF^FDEMPAQUE:^FS"
                            +"^FO520,90^A@N,15,15,B:ARIAL.TTF^FD"+emp+"^FS"
                            +"^FO50,110^B3N,N,50,Y^FD"+vn+"^FS"
                            +"^XZ";
            
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(comando.getBytes(), flavor, null);
            DocPrintJob docPrintJob =  printService.createPrintJob();
            docPrintJob.print(doc, null);
            
        } catch (PrintException ex) {
            JOptionPane.showMessageDialog(null,ex);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
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
        color = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        VIN = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        empaque = new javax.swing.JTextField();
        btnG = new javax.swing.JButton();
        btnRP = new javax.swing.JButton();
        btnI = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        reg = new javax.swing.JTextField();
        corte = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        codigo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        jMenuItem1.setText("Re-Impresion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setTitle("Tuxedo | Empaque");

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

        jLabel5.setText("Motor:");

        pref.setEditable(false);
        pref.setBackground(new java.awt.Color(204, 204, 255));
        pref.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setText("Num. Motor:");

        motor.setEditable(false);
        motor.setBackground(new java.awt.Color(204, 204, 255));
        motor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setText("Color:");

        color.setEditable(false);
        color.setBackground(new java.awt.Color(204, 204, 255));
        color.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel9.setText("VIN:");

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
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(VIN)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(desc, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(anio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pref))
                    .addComponent(motor))
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
                    .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(pref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(motor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(VIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recepcion y Etiquetado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel7.setText("Num. Empaque:");

        empaque.setEditable(false);
        empaque.setBackground(new java.awt.Color(204, 204, 255));
        empaque.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk_black.png"))); // NOI18N
        btnG.setToolTipText("Guardar e Imprimir");
        btnG.setEnabled(false);
        btnG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGActionPerformed(evt);
            }
        });

        btnRP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_go.png"))); // NOI18N
        btnRP.setToolTipText("Re-Imprimir");
        btnRP.setEnabled(false);
        btnRP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRPActionPerformed(evt);
            }
        });

        btnI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editar2.gif"))); // NOI18N
        btnI.setToolTipText("Incidencia");
        btnI.setEnabled(false);
        btnI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(empaque)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .addComponent(btnG, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnI, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(empaque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnRP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnG, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnI, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Corte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 10))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel10.setText("Registros:");

        reg.setEditable(false);
        reg.setBackground(new java.awt.Color(204, 204, 255));
        reg.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        corte.setEditable(false);
        corte.setBackground(new java.awt.Color(204, 204, 255));
        corte.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        corte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corteActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel11.setText("Corte:");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user_alert.png"))); // NOI18N
        jButton5.setToolTipText("Registro de Turno");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(corte, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reg, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(corte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(reg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(261, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Info.", jPanel2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Descripcion", "Año Modelo", "Num. Empaque", "Fecha", "Hora", "Status Terminal"
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
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);

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

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editar2.gif"))); // NOI18N
        jButton2.setToolTipText("Incidencia");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Pendientes", jPanel4);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIN", "Descripcion", "Motor", "Color", "Destino", "Empaque", "Fecha", "Hora", "Status Terminal"
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
        jTable2.setComponentPopupMenu(jPopupMenu1);
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
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(8).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(9).setPreferredWidth(100);

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        jButton3.setToolTipText("Actualizar");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/editar2.gif"))); // NOI18N
        jButton4.setToolTipText("Incidencia");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ultimos Empacados", jPanel5);

        codigo.setBackground(new java.awt.Color(204, 204, 255));
        codigo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        jLabel8.setText("Codigo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        fila=jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        fila=jTable2.getSelectedRow();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        // TODO add your handling code here:
        fila=jTable2.getSelectedRow();
    }//GEN-LAST:event_jTable2KeyReleased

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        // TODO add your handling code here:
                // TODO add your handling code here:
        if(codigo.getText().length()==17){
            empaque.setText("");
            conexion();
            lectura();
            codigo.setText("");
            pendientes();
            liberadas();
            cerrarCon();
            codigo.requestFocus();
        }
    }//GEN-LAST:event_codigoKeyReleased

    private void btnGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGActionPerformed
        // TODO add your handling code here:
        conexion();
        guardar();
        pendientes();
        liberadas();
        cerrarCon();
        codigo.requestFocus();
    }//GEN-LAST:event_btnGActionPerformed

    private void btnIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIActionPerformed
        // TODO add your handling code here:

        ifrmIncidentes ifrmInc=new ifrmIncidentes(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmInc.getSize();
        panel1.add(ifrmInc);
        ifrmInc.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmInc.toFront();
        ifrmInc.show();
    }//GEN-LAST:event_btnIActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
                conexion();
                pendientes();
                liberadas();
                cerrarCon();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
                conexion();
                pendientes();
                liberadas();
                cerrarCon();                
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        ifrmIncidentes ifrmInc=new ifrmIncidentes(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmInc.getSize();
        panel1.add(ifrmInc);
        ifrmInc.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmInc.toFront();
        ifrmInc.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        ifrmIncidentes ifrmInc=new ifrmIncidentes(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmInc.getSize();
        panel1.add(ifrmInc);
        ifrmInc.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmInc.toFront();
        ifrmInc.show();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        reimprimir();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        ifrmTurno ifrmTrn=new ifrmTurno(estacion);
        Dimension desktopSize = panel1.getSize();
        Dimension jInternalFrameSize = ifrmTrn.getSize();
        panel1.add(ifrmTrn);
        ifrmTrn.setLocation((desktopSize.width - jInternalFrameSize.width+20)/2,(desktopSize.height- jInternalFrameSize.height+20)/2);
        ifrmTrn.toFront();
        ifrmTrn.show();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnRPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRPActionPerformed
        // TODO add your handling code here:
        imprimir();
    }//GEN-LAST:event_btnRPActionPerformed

    private void corteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_corteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField VIN;
    private javax.swing.JTextField anio;
    private javax.swing.JButton btnG;
    private javax.swing.JButton btnI;
    private javax.swing.JButton btnRP;
    private javax.swing.JTextField cmodelo;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField color;
    private javax.swing.JTextField corte;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField empaque;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTextField motor;
    private javax.swing.JTextField pref;
    private javax.swing.JTextField reg;
    // End of variables declaration//GEN-END:variables
}
