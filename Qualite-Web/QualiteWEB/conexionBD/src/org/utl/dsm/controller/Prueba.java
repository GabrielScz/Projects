/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controller;

/**
 *
 * @author carlossanchez
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.utl.dsm.bd.ConexionBD;
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.Persona;
import org.utl.dsm.model.Usuario;
import org.utl.dsm.controller.ControllerAccesorio;
import org.utl.dsm.model.Accesorio;
import org.utl.dsm.model.Producto;

public class Prueba {

    public static void main(String[] x) {
       //probarConexion();
       //probarInsert();
       probarGetAll();
       //probarUpdate();
       //probarDelete();
       //probarActivate();
       //probarSearch();
    }

    public static void probarConexion() {
        try {
            ConexionBD objConexion = new ConexionBD();
            Connection conexion = objConexion.open();
            System.out.println(conexion.toString());
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void probarInsert() {
        //1. Crear un objeto de persona y cargarlo
        Producto p = new Producto("Estuche", "Gucci", 1200.5, 4500.5, 4);

        //3. Crear un objeto Empleado
        Accesorio a = new Accesorio();
        a.setProducto(p);

        //System.out.println(e);
        //4. Invocar el metodo de insercion del empleado
        ControllerAccesorio objCA = new ControllerAccesorio();
        try {
            objCA.insert(a);
        } catch (Exception ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(a.toString());

    }
    
    public static void probarUpdate(){
        Producto p = new Producto("Estuche", "Gucci", 1200.5, 4500.5, 4, 1);
        p.setIdProducto(5);

        //3. Crear un objeto Empleado
        Accesorio a = new Accesorio();
        a.setProducto(p);
        //System.out.println(e);
        //4. Invocar el metodo de insercion del empleado
        ControllerAccesorio objCA = new ControllerAccesorio();
        try {
            objCA.update(a);
        } catch (Exception ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(a.toString());
        
    }
    
    public static void probarDelete(){

        Producto p = new Producto();
        Accesorio a = new Accesorio();
        
        a.setProducto(p);
        a.setIdAccesorio(2);

        ControllerAccesorio objCA = new ControllerAccesorio();
        try {
            objCA.delete(a);
        } catch (Exception ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(a.toString());
        
    }
    
    public static void probarActivate(){
        
        Producto p = new Producto();
        Accesorio a = new Accesorio();
        
        a.setProducto(p);
        a.setIdAccesorio(2);

        ControllerAccesorio objCA = new ControllerAccesorio();
        try {
            objCA.activate(a);
        } catch (Exception ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(a.toString());
        
    }
    
    public static void probarGetAll(){
        
        try {
            ControllerAccesorio objCA = new ControllerAccesorio();
            List<Accesorio> accesorios = objCA.getAll("1");
            for(int i = 0 ; i<accesorios.size(); i++ ){
                System.out.println(accesorios.get(i).toString());
            }
                    } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    public static void probarSearch(){
        
        try {
            ControllerAccesorio objCA = new ControllerAccesorio();
            List<Accesorio> accesorios = objCA.search("gu");
            for(int i = 0 ; i<accesorios.size(); i++ ){
                System.out.println(accesorios.get(i).toString());
            }
                    } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
