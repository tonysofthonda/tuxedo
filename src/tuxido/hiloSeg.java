/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JLabel;
/**
 *
 * @author vj002349
 */
public class hiloSeg extends Thread{
    conexionPostgres cnx;
    String query;
    boolean band=true;
    JLabel pwe;JLabel lwe;JLabel ppa;JLabel lpa;
    JLabel paf;JLabel laf;JLabel naf;JLabel pvq;
    JLabel lvq;JLabel ppk;JLabel lpk;JLabel pshp;
    JLabel lshp;JLabel hpk;JLabel hvq;JLabel taf;
    JLabel tvq;JLabel tpk;JLabel tshp;
    Calendar calFechaInicial=Calendar.getInstance();
    Calendar calFechaFinal=Calendar.getInstance();
    SimpleDateFormat fFecha=new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
    private int segundos;
    private int tmp;
    private int minutos;
    private String tiempo;

    /**
     *
     * @param pw
     * @param lw
     * @param pp
     * @param lp
     * @param pa
     * @param la
     * @param na
     * @param pq
     * @param lq
     * @param pk
     * @param lk
     * @param ps
     * @param ls
     * @param hk
     * @param hq
     * @param ta
     * @param tq
     * @param tk
     * @param ts
     */
    public hiloSeg(JLabel pw,JLabel lw,JLabel pp,JLabel lp,
                   JLabel pa,JLabel la,JLabel na,JLabel pq,
                   JLabel lq,JLabel pk,JLabel lk,JLabel ps,
                   JLabel ls,JLabel hk,JLabel hq,JLabel ta,
                   JLabel tq,JLabel tk,JLabel ts){
        pwe=pw;lwe=lw;ppa=pp;lpa=lp;
        paf=pa;laf=la;naf=na;pvq=pq;
        lvq=lq;ppk=pk;lpk=lk;pshp=ps;
        lshp=ls;hpk=hk;hvq=hq;taf=ta;
        tvq=tq;tpk=tk;tshp=ts;
        
    }
    /**
     *
     */
    public void deterHilo(){
        band=false;
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
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    public static long cantidadTotalSegundos(Calendar fechaInicial ,Calendar fechaFinal){
        long totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000);
        return totalMinutos;
    }
    /**
     *
     * @param fecha
     * @param caracter
     * @param op
     * @return
     */
    public static Date StringToDate(String fecha,String caracter,int op){
        String formatoHora=" HH:mm:ss";
        String formato="yyyy"+caracter+"MM"+caracter+"dd"+formatoHora;
        if(op==1)

        formato="yyyy"+caracter+"dd"+caracter+"MM"+formatoHora;
        else if(op==2)
        formato="MM"+caracter+"yyyy"+caracter+"dd"+formatoHora;
        else if(op==3)
        formato="MM"+caracter+"dd"+caracter+"yyyy"+formatoHora;
        else if(op==4)
        formato="dd"+caracter+"yyyy"+caracter+"MM"+formatoHora;
        else if(op==5)
        formato="dd"+caracter+"MM"+caracter+"yyyy"+formatoHora;
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
        Date fechaFormato=null;
            try {
                sdf.setLenient(false);
                fechaFormato=sdf.parse(fecha);
            } catch (ParseException ex) {

            }
        return fechaFormato;
    }
    /**
     *
     */
    public void consulta(){
        Date fechaFinal = null;
        Date fechaInicial = null;
        Date fechaActual=new Date();
        SimpleDateFormat forFecha=new SimpleDateFormat("MM-dd-yyyy");
        String fecha=forFecha.format(fechaActual);
        conexion();
        try {
        //Soldadura
                cnx.consulta("select COUNT(*) from tx_orden_prod where we_status='Pendiente' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    pwe.setText(cnx.reg.getString(1));
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where we_status='Liberado' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    lwe.setText(cnx.reg.getString(1));
                }
        //Pintura
                cnx.consulta("select COUNT(*) from tx_orden_prod where pa_status='Pendiente' AND we_status='Liberado' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    ppa.setText(cnx.reg.getString(1));
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where pa_status='Liberado' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    lpa.setText(cnx.reg.getString(1));
                }
                
        //Ensamble
                cnx.consulta("select COUNT(*) from tx_orden_prod where af_status='Pendiente' AND pa_status='Liberado' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    paf.setText(cnx.reg.getString(1));
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where af_status='Liberado' AND  ID_ENVIO=0");
                while(cnx.reg.next()){
                    laf.setText(cnx.reg.getString(1));
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where af_status='Pendiente-c/Motor' AND STATUS='Pendiente'"); 
                while(cnx.reg.next()){
                    naf.setText(cnx.reg.getString(1));
                }
                //Takt Time Ensamble
                    int unidades=0;
                    cnx.consulta("select TO_CHAR(AF_TIM,'YYYY/MM/DD HH24:MI:SS')" +
                                " from (" +
                                " select AF_TIM " +
                                " from TX_ORDEN_PROD WHERE AF='"+fecha+"' order" +
                                " by AF_TIM ASC" +
                                " )" +
                                " as s LIMIT 1");
                    while(cnx.reg.next()){
                        fechaInicial= hiloSeg.StringToDate(cnx.reg.getString(1),"/", 0);
                    }
                    cnx.consulta("select TO_CHAR(AF_TIM,'YYYY/MM/DD HH24:MI:SS')" +
                                " from (" +
                                " select AF_TIM " +
                                " from TX_ORDEN_PROD WHERE AF='"+fecha+"' order" +
                                " by AF_TIM DESC" +
                                " )" +
                                " as s LIMIT 1");
                    while(cnx.reg.next()){
                        fechaFinal= hiloSeg.StringToDate(cnx.reg.getString(1),"/", 0);
                    }

                    cnx.consulta("select COUNT(*) from tx_orden_prod where af_status IN ('Liberado','Pendiente-c/Motor') and AF='"+fecha+"'");
                    while(cnx.reg.next()){
                        unidades=cnx.reg.getInt(1);
                    }                    
                    calFechaFinal.setTime(fechaFinal);
                    calFechaInicial.setTime(fechaInicial);
                    if(unidades>0){
                        segundos=(int)cantidadTotalSegundos(calFechaInicial, calFechaFinal);
                        tmp=segundos/unidades;
                        minutos=(int)tmp/60;
                        if(tmp%60<10){
                                tiempo=String.valueOf(minutos)+":0"+String.valueOf(tmp%60);
                        }else{
                                tiempo=String.valueOf(minutos)+":"+String.valueOf(tmp%60);
                        }
                        taf.setText(tiempo);
                    }else{
                        taf.setText("00:00");
                    }
        //Calidad
                cnx.consulta("select COUNT(*) from tx_orden_prod where vq_status='Pendiente' AND af_status='Pendiente-c/Motor' AND STATUS='Pendiente'");
                while(cnx.reg.next()){
                    pvq.setText(cnx.reg.getString(1));
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where vq_status='Liberado' AND  ID_ENVIO=0");
                while(cnx.reg.next()){
                    lvq.setText(cnx.reg.getString(1));
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where vq_status='Liberado' AND VQ='"+fecha+"'");
                while(cnx.reg.next()){
                   hvq.setText(cnx.reg.getString(1));
                }
                //Takt time Calidad
                    cnx.consulta("select TO_CHAR(VQ_TIM,'YYYY/MM/DD HH24:MI:SS')" +
                                " from (" +
                                " select VQ_TIM " +
                                " from TX_ORDEN_PROD WHERE VQ='"+fecha+"' order" +
                                " by VQ_TIM ASC" +
                                " )" +
                                " as s LIMIT 1");
                    while(cnx.reg.next()){
                        fechaInicial= hiloSeg.StringToDate(cnx.reg.getString(1),"/", 0);
                    }
                    cnx.consulta("select TO_CHAR(VQ_TIM,'YYYY/MM/DD HH24:MI:SS')" +
                                " from (" +
                                " select VQ_TIM " +
                                " from TX_ORDEN_PROD WHERE VQ='"+fecha+"' order" +
                                " by VQ_TIM DESC" +
                                " )" +
                                " as s LIMIT 1");
                    while(cnx.reg.next()){
                        fechaFinal= hiloSeg.StringToDate(cnx.reg.getString(1),"/", 0);
                    }

                    cnx.consulta("select COUNT(*) from tx_orden_prod where vq_status='Liberado' AND  vq='"+fecha+"'");
                    while(cnx.reg.next()){
                        unidades=cnx.reg.getInt(1);
                    }
                    calFechaFinal.setTime(fechaFinal);
                    calFechaInicial.setTime(fechaInicial);
                    if(unidades>0){
                    segundos=(int)cantidadTotalSegundos(calFechaInicial, calFechaFinal);
                    tmp=segundos/unidades;
                    minutos=(int)tmp/60;
                        if(tmp%60<10){
                                tiempo=String.valueOf(minutos)+":0"+String.valueOf(tmp%60);
                        }else{
                                tiempo=String.valueOf(minutos)+":"+String.valueOf(tmp%60);
                        }
                    tvq.setText(tiempo);
                    }else{
                        tvq.setText("00:00");
                    }
        //Empaque
                cnx.consulta("select COUNT(*) from tx_orden_prod where pk_status='Pendiente' AND vq_status='Liberado' AND ID_ENVIO=0");
                                while(cnx.reg.next()){
                    ppk.setText(cnx.reg.getString(1));
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where pk_status='Liberado' AND  ID_ENVIO=0");
                while(cnx.reg.next()){
                    lpk.setText(cnx.reg.getString(1));
                }
                cnx.consulta("select COUNT(*) from tx_orden_prod where pk_status='Liberado' AND PK='"+fecha+"'");
                while(cnx.reg.next()){
                   hpk.setText(cnx.reg.getString(1));
                }
                //Takt time Empaque
                    cnx.consulta("select TO_CHAR(PK_TIM,'YYYY/MM/DD HH24:MI:SS')" +
                                " from (" +
                                " select PK_TIM " +
                                " from TX_ORDEN_PROD WHERE PK='"+fecha+"' order" +
                                " by PK_TIM ASC" +
                                " )" +
                                " as s LIMIT 1");
                    while(cnx.reg.next()){
                        fechaInicial= hiloSeg.StringToDate(cnx.reg.getString(1),"/", 0);
                    }
                    cnx.consulta("select TO_CHAR(PK_TIM,'YYYY/MM/DD HH24:MI:SS')" +
                                " from (" +
                                " select PK_TIM " +
                                " from TX_ORDEN_PROD WHERE PK='"+fecha+"' order" +
                                " by PK_TIM DESC" +
                                " )" +
                                " as s LIMIT 1");
                    while(cnx.reg.next()){
                        fechaFinal= hiloSeg.StringToDate(cnx.reg.getString(1),"/", 0);
                    }

                    cnx.consulta("select COUNT(*) from tx_orden_prod where pk_status='Liberado' AND  pk='"+fecha+"'");
                    while(cnx.reg.next()){
                        unidades=cnx.reg.getInt(1);
                    }
                    calFechaFinal.setTime(fechaFinal);
                    calFechaInicial.setTime(fechaInicial);
                    if(unidades>0){
                        segundos=(int)cantidadTotalSegundos(calFechaInicial, calFechaFinal);
                        tmp=segundos/unidades;
                        minutos=(int)tmp/60;
                        if(tmp%60<10){
                                tiempo=String.valueOf(minutos)+":0"+String.valueOf(tmp%60);
                        }else{
                                tiempo=String.valueOf(minutos)+":"+String.valueOf(tmp%60);
                        }
                        tpk.setText(tiempo);
                    }else{
                        tpk.setText("00:00");
                    }
        //Embarque
                cnx.consulta("select COUNT(*) from tx_orden_prod where shp_status='Pendiente' AND pk_status='Liberado' AND ID_ENVIO=0"); 
                while(cnx.reg.next()){
                    pshp.setText(cnx.reg.getString(1));
                }

                cnx.consulta("select COUNT(*) from tx_orden_prod where shp_status='Liberado' AND ID_ENVIO>0 AND SHP='"+fecha+"'");
                while(cnx.reg.next()){
                    lshp.setText(cnx.reg.getString(1));
                }
                //Takt time Embarque
                    cnx.consulta("select TO_CHAR(HORA,'YYYY/MM/DD HH24:MI:SS')" +
                                " from (" +
                                " select HORA " +
                                " from TX_INFO_EMBARQUE WHERE STATUS='Listo' AND FECHA='"+fecha+"' order" +
                                " by HORA ASC" +
                                " )" +
                                " as s LIMIT 1");
                    while(cnx.reg.next()){
                        fechaInicial= hiloSeg.StringToDate(cnx.reg.getString(1),"/", 0);
                    }
                    cnx.consulta("select TO_CHAR(HORA,'YYYY/MM/DD HH24:MI:SS')" +
                                " from (" +
                                " select HORA " +
                                " from TX_INFO_EMBARQUE WHERE STATUS='Listo' AND FECHA='"+fecha+"' order" +
                                " by HORA DESC" +
                                " )" +
                                " as s LIMIT 1");
                    while(cnx.reg.next()){
                        fechaFinal= hiloSeg.StringToDate(cnx.reg.getString(1),"/", 0);
                    }

                    cnx.consulta("select COUNT(*) from TX_INFO_EMBARQUE where STATUS='Listo' AND  fecha='"+fecha+"'");
                    while(cnx.reg.next()){
                        unidades=cnx.reg.getInt(1);
                    }
                    calFechaFinal.setTime(fechaFinal);
                    calFechaInicial.setTime(fechaInicial);
                    if(unidades>0){
                        segundos=(int)cantidadTotalSegundos(calFechaInicial, calFechaFinal);
                        tmp=segundos/unidades;
                        minutos=(int)tmp/60;
                            if(tmp%60<10){
                                tiempo=String.valueOf(minutos)+":0"+String.valueOf(tmp%60);
                            }else{
                                tiempo=String.valueOf(minutos)+":"+String.valueOf(tmp%60);
                            }
                        tshp.setText(tiempo);
                    }else{
                        tshp.setText("00:00");
                    }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        desconexion();
    }
    @Override
    public void run(){
        while(band){
                consulta();
                try{
                    Thread.sleep(5000);
                }catch(InterruptedException ex){}
        }
    }
}
