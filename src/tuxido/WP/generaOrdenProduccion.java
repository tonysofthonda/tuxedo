/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.sql.SQLException;


/**
 *
 * @author vj002349
 */
public class generaOrdenProduccion extends Thread{
    conexionWPPostgre cnxO;
    String Eng,Mod, Desc, Tpm, Opc, Clr, prf, Tipo, Opcion;
    int ini, fn, ord, x;
    
    public generaOrdenProduccion(
                                    int Inicio, 
                                    int fin,
                                    String Prefijo,
                                    String Engine, 
                                    String Modelo, 
                                    String Descripcion, 
                                    String TipoM, 
                                    String Color,
                                    int Orden
                                ){
        Eng=Engine;
        Mod=Modelo; 
        Desc=Descripcion; 
        Tpm=TipoM; 
        Clr=Color;
        ini=Inicio;
        fn=fin;
        ord=Orden;
        prf=Prefijo;
        
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
    public void genera(){
        conexion();
        
        cnxO.consulta("select TIPO, OPCION from WP_TIPOS where status='Activo' and TMOTOR='"+Tpm+"'");
        try{
            while(cnxO.reg.next()){
                Tipo=cnxO.reg.getString(1);
                Opcion=cnxO.reg.getString(2);
            }
            cnxO.reg.close();
        } catch (SQLException ex) {
        
        }
        
        for(x=ini;x<=fn;x++){
                      cnxO.ejecutar("INSERT INTO WP_PRODUCCION"
                            + "(FRAME,ENGINE,MODELO,DESCRIPCION,TIPO,OPCION,COLOR,PK_STATUS,SHP_STATUS,EMPAQUE,ORDEN,INVOICE,STATUS) VALUES"
                            + "('"+prf+"-"+x+"','"+Eng+"','"+Mod+"','"+Desc+"','"+Tipo+"',"
                              + "'"+Opcion+"','"+Clr+"','Pendiente','Pendiente','0','"+ord+"','0','Pendiente')");          
        }
        cnxO.ejecutar("UPDATE WP_MODELO SET ACT_SERIE_PROD='"+(fn+1)+"' WHERE CODIGO_MODELO='"+Mod+"' AND STATUS='Activo'");
        desconexion();
    }
    
    @Override
    public void run(){
            genera();
    }
    
}
