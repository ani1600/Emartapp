/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emart.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author AKAN
 */
public class DBConnection {
    private static Connection con;
    
    static{
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE","grocery","ani");
            JOptionPane.showMessageDialog(null,"Connection open Sucessfully","Connection Success",JOptionPane.INFORMATION_MESSAGE);
        }
     
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,"Error in loading the driver","Driver Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error in opening Connection","DB Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static Connection getConnection(){
    return con;
}
    public static void closeConnection(){
        try{
            con.close();
            JOptionPane.showMessageDialog(null,"Connection closed Sucessfully","Success",JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error in closing connection","DB Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
}
