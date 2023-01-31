/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;
import  tuxido.WP.*;
import  java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author vj002349
 */
public class principal extends javax.swing.JFrame {
    public String bandStatus;
    public String user;
    /**
     * Creates new form principal
     * @param perfil 
     */
    public principal(String perfil, String usr) throws PropertyVetoException {
        initComponents();
        user=usr;
        cargaPerfil(perfil);
        validaCon();
    }
    /**
     *
     * @param perfil
     */
    public void cargaPerfil(String perfil) throws PropertyVetoException{
        System.out.println(perfil);
        switch(perfil){
            
            case "Administrador":
                busqueda();
                calendario();
                jTabbedPane1.setEnabledAt(1, false);
                break;
            case "Supervisor":
                jMenu3.setEnabled(false);
                busqueda();
                calendario();
                jTabbedPane1.setEnabledAt(1, false);
                break;
            case "Linea Soldadura":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, false);
                jTabbedPane1.setSelectedIndex(3);
                 btnPA.setEnabled(false);
                 btnAF.setEnabled(false);
                 btnVQ.setEnabled(false);
                 btnPK.setEnabled(false);
                 btnSHP.setEnabled(false);
                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                 
                ifrmSoldadura ifrmsol=new ifrmSoldadura(jDesktopPane2);
                jDesktopPane2.add(ifrmsol);
                ifrmsol.setLocation(50,50);
                ifrmsol.show();
                ifrmsol.toBack();
                
                break;
            case "Linea Pintura":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, false);
                jTabbedPane1.setSelectedIndex(3);
                btnWE.setEnabled(false);
                btnAF.setEnabled(false);
                btnVQ.setEnabled(false);
                btnPK.setEnabled(false);
                btnSHP.setEnabled(false);
                                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                ifrmPintura imfrpn=new ifrmPintura(jDesktopPane2);
                jDesktopPane2.add(imfrpn);
                imfrpn.setLocation(50,50);
                imfrpn.show();
                imfrpn.toBack();
                break;
            case "Linea Ensamble":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, false);
                jTabbedPane1.setSelectedIndex(3);
                btnWE.setEnabled(false);
                btnPA.setEnabled(false);
                btnVQ.setEnabled(false);
                btnPK.setEnabled(false);
                btnSHP.setEnabled(false);
                                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                 ifrmEnsamble ifrmen=new ifrmEnsamble(jDesktopPane2);
                 jDesktopPane2.add(ifrmen);
                 ifrmen.setLocation(50,50);
                 ifrmen.show();
                 ifrmen.toBack();
                break;
            case "Linea Calidad":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, false);
                jTabbedPane1.setSelectedIndex(3);
                btnWE.setEnabled(false);
                btnPA.setEnabled(false);
                btnAF.setEnabled(false);
                btnPK.setEnabled(false);
                btnSHP.setEnabled(false);
                                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                ifrmCalidad ifrmcl=new ifrmCalidad(jDesktopPane2);
                jDesktopPane2.add(ifrmcl);
                ifrmcl.setLocation(50,50);
                ifrmcl.show();
                ifrmcl.toBack();
                break;
            case "Linea Empaque":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, false);
                jTabbedPane1.setSelectedIndex(3);
                btnWE.setEnabled(false);
                btnPA.setEnabled(false);
                btnAF.setEnabled(false);
                btnVQ.setEnabled(false);
                btnSHP.setEnabled(false);
                                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                ifrmEmpaque ifrmem=new ifrmEmpaque(jDesktopPane2);
                jDesktopPane2.add(ifrmem);
                ifrmem.setLocation(50,50);
                ifrmem.show();
                ifrmem.toBack();
                break;
            case "Linea WaterPump":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, false);
                jTabbedPane1.setEnabledAt(3, false);
                jTabbedPane1.setSelectedIndex(4);
                btnMMWP.setEnabled(false);
                btnCPWP.setEnabled(false);
                btINVWP.setEnabled(false);
                btCPSWP.setEnabled(false);
                btEMBWP.setEnabled(false);
                                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                ifrmWPProduccion ifrWPmem=new ifrmWPProduccion(jDesktopPane5);
                jDesktopPane5.add(ifrWPmem);
                ifrWPmem.setLocation(50,50);
                ifrWPmem.show();
                ifrWPmem.toBack();
                ifrWPmem.setMaximum(true);
                break;
            case "Embarque":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, false);
                btnSeg.setEnabled(false);
                btnSegTabla.setEnabled(false);
                btnINC.setEnabled(false);
                btnCT.setEnabled(false);
                btnRPRD.setEnabled(false);
                jTabbedPane1.setSelectedIndex(3);
                btnWE.setEnabled(false);
                btnPA.setEnabled(false);
                btnAF.setEnabled(false);
                btnVQ.setEnabled(false);
                btnPK.setEnabled(false);
                
                btnMMWP.setEnabled(false);
                btnCPWP.setEnabled(false);
                btINVWP.setEnabled(false);
                btCPSWP.setEnabled(false);
                btPRWP.setEnabled(false);        
                                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                 
                ifrmRepEmbarque ifrmre=new ifrmRepEmbarque(jDesktopPane2);
                jDesktopPane2.add(ifrmre);
                ifrmre.setLocation(50,50);
                ifrmre.show();
                break;
            case "Seguimiento Linea":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(3, false);
                jTabbedPane1.setSelectedIndex(2);
                btnSegTabla.setEnabled(false);
                btnINC.setEnabled(false);
                btnCT.setEnabled(false);
                btnSHP1.setEnabled(false);
                btnRPRD.setEnabled(false);
                                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                break;
            case "Reportes":
                jMenu3.setEnabled(false);
                jTabbedPane1.setEnabledAt(0, false);
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(3, false);
                                 /* Water Pump*/
                 btnSeg1.setEnabled(false);
                 btnCT1.setEnabled(false);
                 btnSHP2.setEnabled(false);
                 btnRPRD1.setEnabled(false);
                jTabbedPane1.setSelectedIndex(2);
                btnCT.setEnabled(false);
                btnSHP1.setEnabled(false);
                break;    
        }
    }
    /**
     *
     */
    public void busqueda(){
        ifrmBusqueda ifrmB=new ifrmBusqueda(jDesktopPane3);
        jDesktopPane3.add(ifrmB);
        ifrmB.setLocation(20,20);
        ifrmB.show();
        
        ifrmWPBusqueda ifrmWB=new ifrmWPBusqueda(jDesktopPane5);
        jDesktopPane5.add(ifrmWB);
        ifrmWB.setLocation(20,20);
        ifrmWB.show();
    }
    
    public void validaCon(){
        validaConexion v=new validaConexion(jLabel4,lblError,bandStatus);
        v.start();
    }
    /**
     *
     */
    public void calendario(){
        ifrmCalendario ifrmC=new ifrmCalendario(jDesktopPane4);
        jDesktopPane4.add(ifrmC);
        ifrmC.setLocation(20,20);
        ifrmC.show();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jToolBar2 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jDesktopPane4 = new javax.swing.JDesktopPane();
        jPanel3 = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        btnSeg = new javax.swing.JButton();
        btnSegTabla = new javax.swing.JButton();
        btnINC = new javax.swing.JButton();
        btnCT = new javax.swing.JButton();
        btnSHP1 = new javax.swing.JButton();
        btnRPRD = new javax.swing.JButton();
        jDesktopPane3 = new javax.swing.JDesktopPane();
        jPanel4 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnWE = new javax.swing.JButton();
        btnPA = new javax.swing.JButton();
        btnAF = new javax.swing.JButton();
        btnVQ = new javax.swing.JButton();
        btnPK = new javax.swing.JButton();
        btnSHP = new javax.swing.JButton();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jPanel5 = new javax.swing.JPanel();
        jToolBar5 = new javax.swing.JToolBar();
        btnMMWP = new javax.swing.JButton();
        btnCPWP = new javax.swing.JButton();
        btINVWP = new javax.swing.JButton();
        btCPSWP = new javax.swing.JButton();
        btPRWP = new javax.swing.JButton();
        btEMBWP = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSeg1 = new javax.swing.JButton();
        btnSegTabla1 = new javax.swing.JButton();
        btnCT1 = new javax.swing.JButton();
        btnSHP2 = new javax.swing.JButton();
        btnRPRD1 = new javax.swing.JButton();
        jDesktopPane5 = new javax.swing.JDesktopPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tuxedo Project | Production Control");

        jPanel1.setEnabled(false);

        jToolBar2.setFloatable(false);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bookmark_add.png"))); // NOI18N
        jButton4.setToolTipText("Nuevo Modelo");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton4);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/color.png"))); // NOI18N
        jButton1.setToolTipText("Colores");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cog_start.png"))); // NOI18N
        jButton6.setToolTipText("Motores");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        jButton14.setToolTipText("Lineas Transportistas");
        jButton14.setFocusable(false);
        jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton14);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/time_red.png"))); // NOI18N
        jButton7.setEnabled(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton7);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_open_mark.png"))); // NOI18N
        jButton2.setToolTipText("Modelo Maestro");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer_go.png"))); // NOI18N
        jButton5.setToolTipText("Reimpresion  Etiquetas");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addComponent(jDesktopPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modelo Maestro", new javax.swing.ImageIcon(getClass().getResource("/icons/book_addresses_error.png")), jPanel1); // NOI18N

        jToolBar3.setFloatable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addComponent(jDesktopPane4)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Calendario", new javax.swing.ImageIcon(getClass().getResource("/icons/calendar_select_day.png")), jPanel2); // NOI18N

        jToolBar4.setFloatable(false);

        btnSeg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/map_start.png"))); // NOI18N
        btnSeg.setToolTipText("Seguimiento Linea Produccion");
        btnSeg.setFocusable(false);
        btnSeg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSeg.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSegActionPerformed(evt);
            }
        });
        jToolBar4.add(btnSeg);

        btnSegTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar_select_week.png"))); // NOI18N
        btnSegTabla.setToolTipText("Tabla de Seguimiento");
        btnSegTabla.setFocusable(false);
        btnSegTabla.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSegTabla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSegTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSegTablaActionPerformed(evt);
            }
        });
        jToolBar4.add(btnSegTabla);

        btnINC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_addresses_error.png"))); // NOI18N
        btnINC.setToolTipText("Incidencias");
        btnINC.setFocusable(false);
        btnINC.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnINC.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnINC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnINCActionPerformed(evt);
            }
        });
        jToolBar4.add(btnINC);

        btnCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user_alert.png"))); // NOI18N
        btnCT.setToolTipText("Cortes de Turnos");
        btnCT.setFocusable(false);
        btnCT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCTActionPerformed(evt);
            }
        });
        jToolBar4.add(btnCT);

        btnSHP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        btnSHP1.setToolTipText("Info. Embarque");
        btnSHP1.setFocusable(false);
        btnSHP1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSHP1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSHP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHP1ActionPerformed(evt);
            }
        });
        jToolBar4.add(btnSHP1);

        btnRPRD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/layout_lightning.png"))); // NOI18N
        btnRPRD.setToolTipText("Reportes de Produccion");
        btnRPRD.setFocusable(false);
        btnRPRD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRPRD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRPRD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRPRDActionPerformed(evt);
            }
        });
        jToolBar4.add(btnRPRD);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addComponent(jDesktopPane3)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reportes", new javax.swing.ImageIcon(getClass().getResource("/icons/report_start.png")), jPanel3); // NOI18N

        jToolBar1.setFloatable(false);

        btnWE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/css_error.png"))); // NOI18N
        btnWE.setToolTipText("Soldadura");
        btnWE.setFocusable(false);
        btnWE.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnWE.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnWE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWEActionPerformed(evt);
            }
        });
        jToolBar1.add(btnWE);

        btnPA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/shield_rainbow.png"))); // NOI18N
        btnPA.setToolTipText("Pintura");
        btnPA.setFocusable(false);
        btnPA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPAActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPA);

        btnAF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/brick_magnify.png"))); // NOI18N
        btnAF.setToolTipText("Ensamble");
        btnAF.setFocusable(false);
        btnAF.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAF.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAFActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAF);

        btnVQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/check_error.png"))); // NOI18N
        btnVQ.setToolTipText("Calidad");
        btnVQ.setFocusable(false);
        btnVQ.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVQ.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVQActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVQ);

        btnPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/box_world.png"))); // NOI18N
        btnPK.setToolTipText("Empaque");
        btnPK.setFocusable(false);
        btnPK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPK.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPK);

        btnSHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        btnSHP.setToolTipText("Info. Embarque");
        btnSHP.setFocusable(false);
        btnSHP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSHP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHPActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSHP);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addComponent(jDesktopPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Produccion", new javax.swing.ImageIcon(getClass().getResource("/icons/chart_org_inverted.png")), jPanel4); // NOI18N

        jToolBar5.setFloatable(false);
        jToolBar5.setRollover(true);

        btnMMWP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_addresses_add.png"))); // NOI18N
        btnMMWP.setToolTipText("Modelo Maestro");
        btnMMWP.setFocusable(false);
        btnMMWP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMMWP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMMWP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMMWPActionPerformed(evt);
            }
        });
        jToolBar5.add(btnMMWP);

        btnCPWP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_open_mark.png"))); // NOI18N
        btnCPWP.setToolTipText("Control Produccion");
        btnCPWP.setFocusable(false);
        btnCPWP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCPWP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCPWP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPWPActionPerformed(evt);
            }
        });
        jToolBar5.add(btnCPWP);

        btINVWP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/build.png"))); // NOI18N
        btINVWP.setToolTipText("Inventario");
        btINVWP.setFocusable(false);
        btINVWP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btINVWP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btINVWP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btINVWPActionPerformed(evt);
            }
        });
        jToolBar5.add(btINVWP);

        btCPSWP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/box_picture.png"))); // NOI18N
        btCPSWP.setToolTipText("Control de Partes");
        btCPSWP.setFocusable(false);
        btCPSWP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCPSWP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btCPSWP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCPSWPActionPerformed(evt);
            }
        });
        jToolBar5.add(btCPSWP);

        btPRWP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/chart_org_inverted.png"))); // NOI18N
        btPRWP.setToolTipText("Produccion");
        btPRWP.setFocusable(false);
        btPRWP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btPRWP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btPRWP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPRWPActionPerformed(evt);
            }
        });
        jToolBar5.add(btPRWP);

        btEMBWP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        btEMBWP.setToolTipText("Embarque");
        btEMBWP.setFocusable(false);
        btEMBWP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btEMBWP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btEMBWP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEMBWPActionPerformed(evt);
            }
        });
        jToolBar5.add(btEMBWP);
        jToolBar5.add(jSeparator1);

        btnSeg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/map_start.png"))); // NOI18N
        btnSeg1.setToolTipText("Seguimiento Linea Produccion");
        btnSeg1.setFocusable(false);
        btnSeg1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSeg1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSeg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeg1ActionPerformed(evt);
            }
        });
        jToolBar5.add(btnSeg1);

        btnSegTabla1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar_select_week.png"))); // NOI18N
        btnSegTabla1.setToolTipText("Tabla de Seguimiento");
        btnSegTabla1.setFocusable(false);
        btnSegTabla1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSegTabla1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSegTabla1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSegTabla1ActionPerformed(evt);
            }
        });
        jToolBar5.add(btnSegTabla1);

        btnCT1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user_alert.png"))); // NOI18N
        btnCT1.setToolTipText("Cortes de Turnos");
        btnCT1.setFocusable(false);
        btnCT1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCT1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCT1ActionPerformed(evt);
            }
        });
        jToolBar5.add(btnCT1);

        btnSHP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lorry_start.png"))); // NOI18N
        btnSHP2.setToolTipText("Info. Embarque");
        btnSHP2.setFocusable(false);
        btnSHP2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSHP2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSHP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHP2ActionPerformed(evt);
            }
        });
        jToolBar5.add(btnSHP2);

        btnRPRD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/layout_lightning.png"))); // NOI18N
        btnRPRD1.setToolTipText("Reportes de Produccion");
        btnRPRD1.setFocusable(false);
        btnRPRD1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRPRD1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRPRD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRPRD1ActionPerformed(evt);
            }
        });
        jToolBar5.add(btnRPRD1);

        javax.swing.GroupLayout jDesktopPane5Layout = new javax.swing.GroupLayout(jDesktopPane5);
        jDesktopPane5.setLayout(jDesktopPane5Layout);
        jDesktopPane5Layout.setHorizontalGroup(
            jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jDesktopPane5Layout.setVerticalGroup(
            jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 317, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar5, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addComponent(jDesktopPane5)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jToolBar5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane5))
        );

        jTabbedPane1.addTab("Water Pump", new javax.swing.ImageIcon(getClass().getResource("/icons/wp mini.png")), jPanel5); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 102, 0));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ajax-loader2.gif"))); // NOI18N
        jLabel4.setToolTipText("Conectado");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel1.setText("Status:");

        lblError.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bullet_cross.png"))); // NOI18N
        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Administracion");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user_suit_black.png"))); // NOI18N
        jMenuItem3.setText("Usuarios");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rss_error.png"))); // NOI18N
        jMenuItem4.setText("Tipo Incidencias");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Ayuda");

        jMenuItem2.setText("Acerca de ...");
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ifrmColor ifrmC=new ifrmColor(jDesktopPane1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = ifrmC.getSize();
        jDesktopPane1.add(ifrmC);
        ifrmC.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmC.show();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ifrmMMaestro ifrmmm=new ifrmMMaestro(jDesktopPane1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = ifrmmm.getSize();
        jDesktopPane1.add(ifrmmm);
        ifrmmm.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmmm.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        ifrmNModelo ifrmnm=new ifrmNModelo(jDesktopPane1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = ifrmnm.getSize();
        jDesktopPane1.add(ifrmnm);
        ifrmnm.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmnm.show();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        ifrmMotores ifrmm=new ifrmMotores(jDesktopPane1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = ifrmm.getSize();
        jDesktopPane1.add(ifrmm);
        ifrmm.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmm.show();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        ifrmUsuarios ifrmu=new ifrmUsuarios(jDesktopPane1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = ifrmu.getSize();
        jDesktopPane1.add(ifrmu);
        ifrmu.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmu.show();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void btnSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSegActionPerformed
        // TODO add your handling code here:
        ifrmSegPlan ifrmspl=new ifrmSegPlan(jDesktopPane3);
        Dimension desktopSize = jDesktopPane3.getSize();
        Dimension jInternalFrameSize = ifrmspl.getSize();
        jDesktopPane3.add(ifrmspl);
        ifrmspl.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmspl.show();
    }//GEN-LAST:event_btnSegActionPerformed

    private void btnWEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWEActionPerformed
        // TODO add your handling code here:
        ifrmSoldadura ifrmsol=new ifrmSoldadura(jDesktopPane2);
        Dimension desktopSize = jDesktopPane2.getSize();
        Dimension jInternalFrameSize = ifrmsol.getSize();
        jDesktopPane2.add(ifrmsol);
        ifrmsol.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmsol.show();
        ifrmsol.toFront();
    }//GEN-LAST:event_btnWEActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        ifrmReImpresion imfrri=new ifrmReImpresion();
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = imfrri.getSize();
        jDesktopPane1.add(imfrri);
        imfrri.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        imfrri.show();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPAActionPerformed
        // TODO add your handling code here:
        ifrmPintura imfrpn=new ifrmPintura(jDesktopPane2);
        Dimension desktopSize = jDesktopPane2.getSize();
        Dimension jInternalFrameSize = imfrpn.getSize();
        jDesktopPane2.add(imfrpn);
        imfrpn.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        imfrpn.show();
        imfrpn.toFront();
    }//GEN-LAST:event_btnPAActionPerformed

    private void btnAFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAFActionPerformed
        // TODO add your handling code here:
        ifrmEnsamble ifrmen=new ifrmEnsamble(jDesktopPane2);
        Dimension desktopSize = jDesktopPane2.getSize();
        Dimension jInternalFrameSize = ifrmen.getSize();
        jDesktopPane2.add(ifrmen);
        ifrmen.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmen.show();
        ifrmen.toFront();
    }//GEN-LAST:event_btnAFActionPerformed

    private void btnVQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVQActionPerformed
        // TODO add your handling code here:
        ifrmCalidad ifrmcl=new ifrmCalidad(jDesktopPane2);
        Dimension desktopSize = jDesktopPane2.getSize();
        Dimension jInternalFrameSize = ifrmcl.getSize();
        jDesktopPane2.add(ifrmcl);
        ifrmcl.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmcl.show();
        ifrmcl.toFront();
    }//GEN-LAST:event_btnVQActionPerformed

    private void btnPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKActionPerformed
        // TODO add your handling code here:
        ifrmEmpaque ifrmem=new ifrmEmpaque(jDesktopPane2);
        Dimension desktopSize = jDesktopPane2.getSize();
        Dimension jInternalFrameSize = ifrmem.getSize();
        jDesktopPane2.add(ifrmem);
        ifrmem.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmem.show();
        ifrmem.toFront();
    }//GEN-LAST:event_btnPKActionPerformed

    private void btnSHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSHPActionPerformed
        // TODO add your handling code here:
        ifrmRepEmbarque ifrmre=new ifrmRepEmbarque(jDesktopPane2);
        Dimension desktopSize = jDesktopPane2.getSize();
        Dimension jInternalFrameSize = ifrmre.getSize();
        jDesktopPane2.add(ifrmre);
        ifrmre.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmre.show();
        ifrmre.toFront();
    }//GEN-LAST:event_btnSHPActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        ifrmTransportistas ifrmtrns=new ifrmTransportistas(jDesktopPane1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = ifrmtrns.getSize();
        jDesktopPane1.add(ifrmtrns);
        ifrmtrns.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmtrns.show();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void btnSegTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSegTablaActionPerformed
        // TODO add your handling code here:
        ifrmTablaSeg ifrmts=new ifrmTablaSeg(jDesktopPane3);
        Dimension desktopSize = jDesktopPane3.getSize();
        Dimension jInternalFrameSize = ifrmts.getSize();
        jDesktopPane3.add(ifrmts);
        ifrmts.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmts.show();
    }//GEN-LAST:event_btnSegTablaActionPerformed

    private void btnINCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnINCActionPerformed
        // TODO add your handling code here:
        ifrmRepIncidentes ifrmrin=new ifrmRepIncidentes(jDesktopPane3);
        Dimension desktopSize = jDesktopPane3.getSize();
        Dimension jInternalFrameSize = ifrmrin.getSize();
        jDesktopPane3.add(ifrmrin);
        ifrmrin.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmrin.show();
    }//GEN-LAST:event_btnINCActionPerformed

    private void btnCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCTActionPerformed
        // TODO add your handling code here:
        ifrmRepCorte ifrmrc=new ifrmRepCorte(jDesktopPane3);
        Dimension desktopSize = jDesktopPane3.getSize();
        Dimension jInternalFrameSize = ifrmrc.getSize();
        jDesktopPane3.add(ifrmrc);
        ifrmrc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmrc.show();
    }//GEN-LAST:event_btnCTActionPerformed

    private void btnRPRDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRPRDActionPerformed
        // TODO add your handling code here:
        ifrmRepProduccion ifrmrp=new ifrmRepProduccion(jDesktopPane3);
        Dimension desktopSize = jDesktopPane3.getSize();
        Dimension jInternalFrameSize = ifrmrp.getSize();
        jDesktopPane3.add(ifrmrp);
        ifrmrp.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmrp.show();
    }//GEN-LAST:event_btnRPRDActionPerformed

    private void btnSHP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSHP1ActionPerformed
        // TODO add your handling code here:
        ifrmCtrlBtc ifrmcbt=new ifrmCtrlBtc(jDesktopPane3);
        Dimension desktopSize = jDesktopPane3.getSize();
        Dimension jInternalFrameSize = ifrmcbt.getSize();
        jDesktopPane3.add(ifrmcbt);
        ifrmcbt.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmcbt.show();
    }//GEN-LAST:event_btnSHP1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        ifrmAdminTurno ifrmat=new ifrmAdminTurno(jDesktopPane1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = ifrmat.getSize();
        jDesktopPane1.add(ifrmat);
        ifrmat.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmat.show();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        ifrmTipoIncidentes ifrti=new ifrmTipoIncidentes(jDesktopPane1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension jInternalFrameSize = ifrti.getSize();
        jDesktopPane1.add(ifrti);
        ifrti.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrti.show();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnMMWPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMMWPActionPerformed
        // TODO add your handling code here:
        ifrmWPModeloMaestro ifrmWPM = new ifrmWPModeloMaestro(jDesktopPane5);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmWPM.getSize();
        jDesktopPane5.add(ifrmWPM);
        ifrmWPM.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmWPM.show();
    }//GEN-LAST:event_btnMMWPActionPerformed

    private void btnCPWPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPWPActionPerformed
        // TODO add your handling code here:
        ifrmWPControlProduccion ifrmWPCP = new ifrmWPControlProduccion(jDesktopPane5,user);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmWPCP.getSize();
        jDesktopPane5.add(ifrmWPCP);
        ifrmWPCP.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmWPCP.show();
    }//GEN-LAST:event_btnCPWPActionPerformed

    private void btINVWPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btINVWPActionPerformed
        // TODO add your handling code here:
        ifrmWPInventario ifrmWPI = new ifrmWPInventario(jDesktopPane5,user);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmWPI.getSize();
        jDesktopPane5.add(ifrmWPI);
        ifrmWPI.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmWPI.show();
    }//GEN-LAST:event_btINVWPActionPerformed

    private void btPRWPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPRWPActionPerformed
        // TODO add your handling code here:
        ifrmWPProduccion ifrmWPP = new ifrmWPProduccion(jDesktopPane5);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmWPP.getSize();
        jDesktopPane5.add(ifrmWPP);
        ifrmWPP.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmWPP.show();
        try {
            ifrmWPP.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        ifrmWPP.toFront();
    }//GEN-LAST:event_btPRWPActionPerformed

    private void btEMBWPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEMBWPActionPerformed
        // TODO add your handling code here:
        ifrmWPRepEmbarque ifrmre=new ifrmWPRepEmbarque(jDesktopPane5);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmre.getSize();
        jDesktopPane5.add(ifrmre);
        ifrmre.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmre.show();
        ifrmre.toFront();
    }//GEN-LAST:event_btEMBWPActionPerformed

    private void btnSeg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeg1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnSeg1ActionPerformed

    private void btnCT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCT1ActionPerformed
        // TODO add your handling code here:
        ifrmWPRepCorte ifrmrc=new ifrmWPRepCorte(jDesktopPane5);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmrc.getSize();
        jDesktopPane5.add(ifrmrc);
        ifrmrc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmrc.show();
    }//GEN-LAST:event_btnCT1ActionPerformed

    private void btnSHP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSHP2ActionPerformed
        // TODO add your handling code here:
        ifrmWPCtrlBtc ifrmcbt=new ifrmWPCtrlBtc(jDesktopPane5);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmcbt.getSize();
        jDesktopPane5.add(ifrmcbt);
        ifrmcbt.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmcbt.show();
    }//GEN-LAST:event_btnSHP2ActionPerformed

    private void btnRPRD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRPRD1ActionPerformed
        // TODO add your handling code here:
        ifrmWPRepProduccion ifrmrp=new ifrmWPRepProduccion(jDesktopPane5);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmrp.getSize();
        jDesktopPane5.add(ifrmrp);
        ifrmrp.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmrp.show();
    }//GEN-LAST:event_btnRPRD1ActionPerformed

    private void btCPSWPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCPSWPActionPerformed
        // TODO add your handling code here:
        ifrmWPControlPartes ifrmwpcp=new ifrmWPControlPartes(jDesktopPane5);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmwpcp.getSize();
        jDesktopPane5.add(ifrmwpcp);
        ifrmwpcp.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmwpcp.show();
        try {
            ifrmwpcp.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btCPSWPActionPerformed

    private void btnSegTabla1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSegTabla1ActionPerformed
        // TODO add your handling code here:
        ifrmTablaSegWP ifrmtswp=new ifrmTablaSegWP(jDesktopPane5);
        Dimension desktopSize = jDesktopPane5.getSize();
        Dimension jInternalFrameSize = ifrmtswp.getSize();
        jDesktopPane5.add(ifrmtswp);
        ifrmtswp.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        ifrmtswp.show();
    }//GEN-LAST:event_btnSegTabla1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCPSWP;
    private javax.swing.JButton btEMBWP;
    private javax.swing.JButton btINVWP;
    private javax.swing.JButton btPRWP;
    private javax.swing.JButton btnAF;
    private javax.swing.JButton btnCPWP;
    private javax.swing.JButton btnCT;
    private javax.swing.JButton btnCT1;
    private javax.swing.JButton btnINC;
    private javax.swing.JButton btnMMWP;
    private javax.swing.JButton btnPA;
    private javax.swing.JButton btnPK;
    private javax.swing.JButton btnRPRD;
    private javax.swing.JButton btnRPRD1;
    private javax.swing.JButton btnSHP;
    private javax.swing.JButton btnSHP1;
    private javax.swing.JButton btnSHP2;
    private javax.swing.JButton btnSeg;
    private javax.swing.JButton btnSeg1;
    private javax.swing.JButton btnSegTabla;
    private javax.swing.JButton btnSegTabla1;
    private javax.swing.JButton btnVQ;
    private javax.swing.JButton btnWE;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    public javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane3;
    private javax.swing.JDesktopPane jDesktopPane4;
    private javax.swing.JDesktopPane jDesktopPane5;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JToolBar.Separator jSeparator1;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JLabel lblError;
    // End of variables declaration//GEN-END:variables
}
