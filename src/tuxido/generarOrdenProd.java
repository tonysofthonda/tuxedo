/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author vj002349
 */
public class generarOrdenProd {
    conexionPostgres cnxO;
    String[] vinCad=new String[5];
    int[][] cadena=new int[2][17];
    String[][] myear=new String[30][2];
    String[][] mval=new String[23][2];
    String id=null;
    
    
    /**
     *
     * @param prod
     * @param ini
     * @param pref
     * @param anio
     * @param val
     */
    public void ordenProd(int prod,int ini, String pref, String anio, String val){
        inimyear();
        inimval();
        id=val;
        for(int x=0; x<prod;x++){
            
            inicadena();
            int cons;
            String temp="";
            String cad;
            String valTemp;
            int valor=0;
            temp="";
            int band=0;
            //1-PREFIJO
            vinCad[0]=pref;
            //2-AÑO MODELO
            for(int y=0;y<30 || band==0;y++){
                if(myear[y][0].equals(anio)){
                    vinCad[2]=myear[y][1];
                    band=1;
                }
            }
            //3-MANUFACTURERA
            //default es HDM = D
            vinCad[3]="D";
            //CONSECUTIVO PRODUCCION
            cons=ini+x;
            if(String.valueOf(cons).length()<6){
                for(int z=0;z<(6-String.valueOf(cons).length());z++){
                    temp=temp+"0";
                }
            }
            temp=temp+String.valueOf(cons);
            vinCad[4]=temp;
            cad=vinCad[0]+"0"+vinCad[2]+vinCad[3]+vinCad[4];
            
            //Generacion CheckDigit
            for(int i=0;i<cad.length();i++){
                if(StringUtils.isNumeric(String.valueOf(cad.charAt(i)))){
                    cadena[0][i]=Integer.valueOf(String.valueOf(cad.charAt(i)));
                }else{
                    for(int j=0;j<23 || band==0;j++){
                        if(mval[j][0].equals(String.valueOf(cad.charAt(i)))){
                            band=1;
                            cadena[0][i]=Integer.valueOf(mval[j][1]);
                        }
                    }
                }
                valor=valor+(cadena[1][i]*cadena[0][i]);
            }
            valor=valor%11;
            if(valor==10){
                valTemp="X";
            }else{
                valTemp=String.valueOf(valor);
            }
            //CHECK DIGIT
            vinCad[1]=valTemp;
            System.out.println(vinCad[0]+vinCad[1]+vinCad[2]+vinCad[3]+vinCad[4]);
            insertar(vinCad[0]+vinCad[1]+vinCad[2]+vinCad[3]+vinCad[4]);
        }
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
    public void inimyear(){
        //AÑO               //DIGITO
        myear[0][0]="2010";myear[0][1]="A";
        myear[1][0]="2011";myear[1][1]="B";
        myear[2][0]="2012";myear[2][1]="C";
        myear[3][0]="2013";myear[3][1]="D";
        myear[4][0]="2014";myear[4][1]="E";
        myear[5][0]="2015";myear[5][1]="F";
        myear[6][0]="2016";myear[6][1]="G";
        myear[7][0]="2017";myear[7][1]="H";
        myear[8][0]="2018";myear[8][1]="J";
        myear[9][0]="2019";myear[9][1]="K";
        myear[10][0]="2020";myear[10][1]="L";
        myear[11][0]="2021";myear[11][1]="M";
        myear[12][0]="2022";myear[12][1]="N";
        myear[13][0]="2023";myear[13][1]="P";
        myear[14][0]="2024";myear[14][1]="R";
        myear[15][0]="2025";myear[15][1]="S";
        myear[16][0]="2026";myear[16][1]="T";
        myear[17][0]="2027";myear[17][1]="V";
        myear[18][0]="2028";myear[18][1]="W";
        myear[19][0]="2029";myear[19][1]="X";
        myear[20][0]="2030";myear[20][1]="Y";
        myear[21][0]="2031";myear[21][1]="1";
        myear[22][0]="2032";myear[22][1]="2";
        myear[23][0]="2033";myear[23][1]="3";
        myear[24][0]="2034";myear[24][1]="4";
        myear[25][0]="2035";myear[25][1]="5";
        myear[26][0]="2036";myear[26][1]="6";
        myear[27][0]="2037";myear[27][1]="4";
        myear[28][0]="2038";myear[28][1]="8";
        myear[29][0]="2039";myear[29][1]="9"; 
    }
    /**
     *
     */
    public void inimval(){
        mval[0][0]="A";mval[0][1]="1";
        mval[1][0]="B";mval[1][1]="2";
        mval[2][0]="C";mval[2][1]="3";
        mval[3][0]="D";mval[3][1]="4";
        mval[4][0]="E";mval[4][1]="5";
        mval[5][0]="F";mval[5][1]="6";
        mval[6][0]="G";mval[6][1]="7";
        mval[7][0]="H";mval[7][1]="8";
        mval[8][0]="J";mval[8][1]="1";
        mval[9][0]="K";mval[9][1]="2";
        mval[10][0]="L";mval[10][1]="3";
        mval[11][0]="M";mval[11][1]="4";
        mval[12][0]="N";mval[12][1]="5";
        mval[13][0]="P";mval[13][1]="7";
        mval[14][0]="R";mval[14][1]="9";
        mval[15][0]="S";mval[15][1]="2";
        mval[16][0]="T";mval[16][1]="3";
        mval[17][0]="U";mval[17][1]="4";
        mval[18][0]="V";mval[18][1]="5";
        mval[19][0]="W";mval[19][1]="6";
        mval[20][0]="X";mval[20][1]="7";
        mval[21][0]="Y";mval[21][1]="8";
        mval[22][0]="Z";mval[22][1]="9";
    }
    /**
     *
     */
    public void inicadena(){
        cadena[1][0]=8;cadena[0][0]=0;
        cadena[1][1]=7;cadena[0][1]=0;
        cadena[1][2]=6;cadena[0][2]=0;
        cadena[1][3]=5;cadena[0][3]=0;
        cadena[1][4]=4;cadena[0][4]=0;
        cadena[1][5]=3;cadena[0][5]=0;
        cadena[1][6]=2;cadena[0][6]=0;
        cadena[1][7]=10;cadena[0][7]=0;
        cadena[1][8]=0;cadena[0][8]=0;
        cadena[1][9]=9;cadena[0][9]=0;
        cadena[1][10]=8;cadena[0][10]=0;
        cadena[1][11]=7;cadena[0][11]=0;
        cadena[1][12]=6;cadena[0][12]=0;
        cadena[1][13]=5;cadena[0][13]=0;
        cadena[1][14]=4;cadena[0][14]=0;
        cadena[1][15]=3;cadena[0][15]=0;
        cadena[1][16]=2;cadena[0][16]=0;
    }
    /**
     *
     * @param vin
     */
    public void insertar(String vin){
        conexion();
        String id_modelo= null;
        String mod_desc= null;
        String anio= null;
        String motor= null;
        String id_color= null;
        String col_desc= null;
        String produccion= null;
        String destino=null;
        cnxO.consulta("select modelo,descripcion,anio,motor,color,desc_color,destino,TO_CHAR(fecha_prod,'mm/dd/yyyy') from TX_MMAESTRO WHERE id='"+id+"'");
        try {
            while(cnxO.reg.next()){
                id_modelo=cnxO.reg.getString(1);
                mod_desc=cnxO.reg.getString(2);
                anio=cnxO.reg.getString(3);
                motor=cnxO.reg.getString(4);
                id_color=cnxO.reg.getString(5);
                col_desc=cnxO.reg.getString(6);
                produccion=cnxO.reg.getString(8);
                destino=cnxO.reg.getString(7);
            }                                   
            cnxO.ejecutar("update TX_MMAESTRO SET status='Listo' WHERE id='"+id+"'");
            cnxO.ejecutar("insert into TX_ORDEN_PROD "
                        + "(id_orden,vin,id_modelo,mod_desc,anio,motor,numero,id_color,"
                        + "col_desc,destino,produccion,"
                        + "num_emp,tarima, contenedor,status,envio,id_envio) "
                        + "VALUES"
                        + "('"+id+"','"+vin+"','"+id_modelo+"','"+mod_desc+"',"
                        + "'"+anio+"','"+motor+"','0','"+id_color+"','"+col_desc+"',"
                        + "'"+destino+"','"+produccion+"','0','0','0','Pendiente','0','0')");
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"VIN ERROR: "+ex);
        }
        desconexion();
    }
    
}
