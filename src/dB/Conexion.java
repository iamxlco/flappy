/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author axel_
 */
public class Conexion {
    
    public static String db, port,user, pass, table;
    public static Connection cnx;
    
    public Conexion(String db, String port, String user, String pass, String table){
        this.db = db;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.table = table;
    }
    
    public void conectar(){
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:"+port+"/"+db+"?useSSL=false", user, pass);
            System.out.println("Conectado");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cnx = conn;
    }
    
    public void Guardar(String nick, int score) throws SQLException{
        try{
        PreparedStatement consulta;
        consulta = cnx.prepareStatement("INSERT INTO "+table+"(nick, score) VALUES(?, ?);");
        consulta.setString(1, nick);
        consulta.setInt(2, score);
        consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
    }
            
}
