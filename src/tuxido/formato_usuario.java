/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author vj002349
 */
public class formato_usuario extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object value, boolean selected, boolean focused, int row, int column){
        super.getTableCellRendererComponent (tabla, value, selected, focused, row, column);
        if(selected){
                this.setBackground(new java.awt.Color(0,43,74));
                this.setForeground(new java.awt.Color(255,255,255));
                this.setFont(new java.awt.Font("Arial", 1, 11));
        }else{        
                    if(row%2==0){
                        this.setOpaque(true);
                        this.setBackground(new java.awt.Color(216,229,255));
                        this.setForeground(Color.BLACK);
                        this.setFont(new java.awt.Font("Arial", 1, 11));
                    }else{
                        this.setOpaque(true);
                        this.setBackground(Color.WHITE);
                        this.setForeground(Color.BLACK);
                        this.setFont(new java.awt.Font("Arial", 0, 11));
                    }
                    if(String.valueOf(tabla.getValueAt(row,column)).equals("Abierto") ||
                       String.valueOf(tabla.getValueAt(row,column)).equals("Activo") ||
                       String.valueOf(tabla.getValueAt(row,column)).equals("Listo") ||
                       String.valueOf(tabla.getValueAt(row,column)).equals("Recibido") || 
                       String.valueOf(tabla.getValueAt(row,column)).equals("Liberado") ||
                       String.valueOf(tabla.getValueAt(row,column)).equals("Terminada")     ){
                        this.setOpaque(true);
                        this.setBackground(new java.awt.Color(102, 153, 0));
                        this.setForeground(Color.white);
                    }else{
                        if(String.valueOf(tabla.getValueAt(row,column)).equals("Desactivado") || 
                           String.valueOf(tabla.getValueAt(row,column)).equals("Cerrado") || 
                           String.valueOf(tabla.getValueAt(row,column)).equals("No")) {
                            this.setOpaque(true);
                            this.setBackground(new java.awt.Color(102, 0, 0));
                            this.setForeground(Color.white);
                        }else{
                            if(String.valueOf(tabla.getValueAt(row,column)).equals("Pendiente")){
                                this.setOpaque(true);
                                this.setBackground(new java.awt.Color(255, 153, 0));
                                this.setForeground(Color.BLACK);
                            }else{
                               if(String.valueOf(tabla.getValueAt(row,column)).equals("Pendiente-c/Motor")){
                                this.setOpaque(true);
                                this.setBackground(new java.awt.Color(255,255,153));
                                this.setForeground(Color.BLACK);
                               }
                            }
                        }
                   }
        }
        return this;
    }
    
}
