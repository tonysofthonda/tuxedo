/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido.WP;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author vj002349
 */
public class formato_Inventario extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object value, boolean selected, boolean focused, int row, int column){
        super.getTableCellRendererComponent (tabla, value, selected, focused, row, column);
        if(selected){
            //AZUL MARINO
                this.setBackground(new java.awt.Color(0,43,74));
                this.setForeground(new java.awt.Color(255,255,255));
                this.setFont(new java.awt.Font("Arial", 1, 11));
        }else{        
                    if(row%2==0){
                        this.setOpaque(true);
                        this.setBackground(new java.awt.Color(0,43,74));
                        this.setForeground(Color.BLACK);
                        this.setFont(new java.awt.Font("Arial", 1, 11));
                    }else{
                        this.setOpaque(true);
                        this.setBackground(Color.WHITE);
                        this.setForeground(Color.BLACK);
                        this.setFont(new java.awt.Font("Arial", 0, 11));
                    }
                    //VERDE
                    if(String.valueOf(tabla.getValueAt(row,5)).equals("0") ||
                       String.valueOf(tabla.getValueAt(row,6)).equals("100") ||
                       String.valueOf(tabla.getValueAt(row,7)).equals("0")){
                        this.setOpaque(true);
                        this.setBackground(new java.awt.Color(102, 153, 0));
                        this.setForeground(Color.white);
                    }else{
                        //ROJO
                        if(Double.valueOf(String.valueOf(tabla.getValueAt(row,6)))<=50 || 
                           Double.valueOf(String.valueOf(tabla.getValueAt(row,7)))>=50 ){
                            this.setOpaque(true);
                            this.setBackground(new java.awt.Color(102, 0, 0));
                            this.setForeground(Color.white);
                        }else{
                            //NARANJA
                            if(Double.valueOf(String.valueOf(tabla.getValueAt(row,6)))>50 || 
                               Double.valueOf(String.valueOf(tabla.getValueAt(row,7)))<50 ){
                                this.setOpaque(true);
                                this.setBackground(new java.awt.Color(255, 153, 0));
                                this.setForeground(Color.BLACK);
                            }
                            //AMARILLO 
                            if(Double.valueOf(String.valueOf(tabla.getValueAt(row,6)))>75 || 
                               Double.valueOf(String.valueOf(tabla.getValueAt(row,7)))<25 ){
                                this.setOpaque(true);
                                this.setBackground(new java.awt.Color(255,255,153));
                                this.setForeground(Color.BLACK);
                               }
                            
                        }
                   }
        }
        return this;
    }
    
}
