/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.bd;

/**
 *
 * @author carlossanchez
 */
public class prueba {
    
    public static void main(String[] args) {
        
        ConexionBD connMySQL = new ConexionBD();
        
        try{
            connMySQL.open();
            System.out.println("Conexion establecida con MySQL");
            
            connMySQL.close();
            System.out.println("Se ha cerrado la conexion con MySQL");
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
