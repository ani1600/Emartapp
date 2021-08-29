/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emart.dao;

import Emart.dbutil.DBConnection;
import Emart.pojo.ProductsPojo;
import Emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author AKAN
 */
public class OrderDAO {
     public static String getNextOrderId()throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select max(order_id) from orders");
        rs.next();
        String orderid=rs.getString(1);
        if(orderid==null){
            return "O-101";
        }
        int orderno=Integer.parseInt(orderid.substring(2));
        orderno+=1;
        return "O-"+orderno;
    }
    public static boolean addOrder(ArrayList<ProductsPojo> al,String ordid)throws SQLException{
       Connection conn= DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("Insert into orders values(?,?,?,?)");
       int count=0;
       for(ProductsPojo p: al){
           ps.setString(1, ordid);
           ps.setString(2,p.getProductId());
           ps.setInt(3,p.getQuantity());
           ps.setString(4,UserProfile.getUserid());
           count+=ps.executeUpdate();
       }
      return count==al.size();
    } 
}
