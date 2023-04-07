package org.utl.dsm.bd;

import java.sql.DriverManager;
import java.sql.Connection;


public class ConexionBD {
    
    Connection conn;
    
    public Connection open(){
        String user = "root";
        String password = "12345678";
        String url = "jdbc:mysql://127.0.0.1:3306/optiqalumnos?useSSL=false&useUnicode=true&characterEncoding=utf-8";
        
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
