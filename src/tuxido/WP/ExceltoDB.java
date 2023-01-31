/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author VJ002349
 */
public class ExceltoDB {
    String ruta;
    conexionWPPostgre cnxO;
    int x;
    int band;
    
    public ExceltoDB(String r){
        ruta=r;
    }

    public void conexion(){
        cnxO=new conexionWPPostgre();
        //
    }
    
    public void desconexion(){
        try {
            cnxO.Conec.close();
        } catch (SQLException ex) {}
    }
    
    public boolean Import() {
        try{
            
        FileInputStream file = new FileInputStream(new File(ruta));
	// Crear el objeto que tendra el libro de Excel
	XSSFWorkbook workbook = new XSSFWorkbook(file);
	/*
	 * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
	 * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
	 * que nos permite recorrer cada una de las filas que contiene.
	 */
	XSSFSheet sheet = workbook.getSheetAt(0);
	Iterator<Row> rowIterator = sheet.iterator();
	Row row;
        conexion();
        cnxO.ejecutar("TRUNCATE TABLE WP_CARGA_TEMP");
	// Recorremos todas las filas para mostrar el contenido de cada celda
	while (rowIterator.hasNext()){
	    row = rowIterator.next();
            String insert="insert into WP_CARGA_TEMP VALUES (";
	    // Obtenemos el iterator que permite recorres todas las celdas de una fila
	    Iterator<Cell> cellIterator = row.cellIterator();
	    Cell celda;
            x=0;
            band=0;
                while (cellIterator.hasNext()){
                    celda=cellIterator.next();
                    if(celda.getColumnIndex()==8 && band==0){
                        insert=insert+"' ',";
                        x++;
                    }
                    switch(celda.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        if( DateUtil.isCellDateFormatted(celda) ){
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                           insert=insert+"'"+sdf.format(celda.getDateCellValue())+"'";
                        }else{
                           Double val=celda.getNumericCellValue();
                           insert=insert+"'"+val.intValue()+"'";
                        }
                        break;
                    case Cell.CELL_TYPE_STRING:
                        insert=insert+"'"+celda.getStringCellValue()+"'";
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        insert=insert+"'"+celda.getBooleanCellValue()+"'";
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        insert=insert+"' '";
                        break;
                    }
                    
                    x++;
                    if (x<10){
                        insert=insert+",";
                    }else{
                        insert=insert+")";
                    }
                }
               String message= cnxO.ejecutar(insert);
               if (message.startsWith("ERROR:")) {
                   JOptionPane.showMessageDialog(null,"Se encontró un error al guardar la información del Excel, por favor valide el archivo", 
                           "Error de lectura", JOptionPane.ERROR_MESSAGE);
                   cnxO.ejecutar("TRUNCATE TABLE WP_CARGA_TEMP");
                   break;
                }               
            }
	// cerramos el libro excel
	workbook.close();
        desconexion();
        }catch(IOException ex){
           ex.printStackTrace();
        }
        return false;
    }
}