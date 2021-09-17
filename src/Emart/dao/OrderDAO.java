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
import java.util.List;

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
    public static boolean updateOrders(List<ProductsPojo> pList,String oId)throws SQLException
    {
        
           Connection conn=DBConnection.getConnection();
           conn.setAutoCommit(false);
           PreparedStatement ps=conn.prepareStatement("insert into orders values(?,?,?,?)");
           for(ProductsPojo p:pList)
           {
             ps.setString(1,oId);
             ps.setString(2,p.getProductId());
             ps.setInt(3,p.getQuantity());
             ps.setString(4,UserProfile.getUserid());
             ps.addBatch();
           }
           int []count=ps.executeBatch();
           for(int i:count)
           {
             if(i!=-2)
             {
                conn.rollback();
                return false;
             }
           }
           conn.commit();
           return true;
    
    }
    
     public static List<ProductsPojo> getOrdersDetail(String oid)throws SQLException
    {
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("select o.p_id,p_name,p_companyname,p_price,our_price,p_tax,o.quantity from products p,orders o where p.p_id=o.p_id and order_id=? order by o.p_id");
       ps.setString(1,oid);
       ResultSet rs= ps.executeQuery();
       ArrayList<ProductsPojo> oList=new ArrayList<ProductsPojo>();
       while(rs.next())
       {
           ProductsPojo p=new ProductsPojo();
           p.setProductId(rs.getString(1));
           p.setProductName(rs.getString(2));
           p.setProductCompany(rs.getString(3));
           p.setProductPrice(rs.getDouble(4));
           p.setOurPrice(rs.getDouble(5));
           p.setTax(rs.getInt(6));
           p.setQuantity(rs.getInt(7));
           oList.add(p);
       }
       return oList;
    }
    
     public static List<String> getAllOrdersId(String userId)throws SQLException
    {
       
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("select distinct order_id from orders where userid=?");
       ps.setString(1,userId);
       ResultSet rs= ps.executeQuery();
       ArrayList<String> oList=new ArrayList<String>();
       while(rs.next())
       {
           oList.add(rs.getString(1));
       }
       return oList;
    }
     
     
    public static List<String> getAllUsersId()throws SQLException
    {
       
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("select distinct userid from orders");
       ResultSet rs= ps.executeQuery();
       ArrayList<String> uList=new ArrayList<String>();
       while(rs.next())
       {
           uList.add(rs.getString(1));
       }
       return uList;
    }
}
