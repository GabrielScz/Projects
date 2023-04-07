/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author carlossanchez
 */
public class ConexionBD {
    
    Connection conn;
    
    public Connection open(){
        String user = "root";
        String password = "12345678";
        String url = "jdbc:mysql://127.0.0.1:3306/HelpDesk?useSSL=false&useUnicode=true&characterEncoding=utf-8";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void close()
    {
        try {
            conn.close();
            
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
}
