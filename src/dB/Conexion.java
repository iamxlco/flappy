/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    public Conexion(String table){
        this.table = table;
    }
    
    public void conectar(){
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flappy?characterEncoding=latin1&useConfigs=maxPerformance", "root", "olakase");
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
    
    public int max() throws SQLException{
        ResultSet resultado;
        int maximo = 0;
        try{
           PreparedStatement consulta = cnx.prepareStatement("select max(score) from puntaje");
           resultado = consulta.executeQuery();
           while(resultado.next()){
               maximo = resultado.getInt(1);
           }
        }catch(SQLException ex){
           throw new SQLException(ex);
        }
        return maximo;
    }
    
    public int maxuser(String user) throws SQLException{
        ResultSet resultado;
        int maximo = 0;
        try{
           PreparedStatement consulta = cnx.prepareStatement("select max(score) from puntaje where nick='"+user+"'");
           resultado = consulta.executeQuery();
           while(resultado.next()){
               maximo = resultado.getInt(1);
           }
        }catch(SQLException ex){
           throw new SQLException(ex);
        }
        return maximo;
    }
    
    public void cerrar() throws SQLException{
        cnx.close();
    }
            
}
