/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emart.dao;

import Emart.dbutil.DBConnection;
import Emart.pojo.EmployeePojo;
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
public class EmployeeDAO {
    public static String getNextEmpId()throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select max(empid) from employees");
        rs.next();
        String empid=rs.getString(1);
        int empno=Integer.parseInt(empid.substring(1));
        empno+=1;
        return "E"+empno;
    }
    public static boolean addEmployee(EmployeePojo emp)throws SQLException{
        Connection conn= DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into employees values(?,?,?,?)");
        ps.setString(1,emp.getEmpid());
        ps.setString(2,emp.getEmpname());
        ps.setString(3,emp.getJob());
        ps.setDouble(4,emp.getSal());
        int result=ps.executeUpdate();
        return result==1;
    }
    public static List<EmployeePojo> getAllEmployees() throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select * from employees order by empid");
        ArrayList<EmployeePojo> empList= new ArrayList<>();
        while(rs.next()){
            EmployeePojo emp=new EmployeePojo();
            emp.setEmpid(rs.getString(1));
            emp.setEmpname(rs.getString(2));
            emp.setJob(rs.getString(3));
            emp.setSal(rs.getDouble(4));
            empList.add(emp);
        }
        return empList;
    }
    public static List<String> getAllEmpId()throws SQLException{
      Connection conn= DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select empid from employees order by empid"); 
        ArrayList<String> allId= new ArrayList<>();
        while(rs.next()){
            allId.add(rs.getString(1));
        }
        return allId;
    }
    public static EmployeePojo findEmployeeById(String id)throws SQLException{
    Connection con=DBConnection.getConnection();
    PreparedStatement ps=con.prepareStatement("Select * from employees where empid=?");
    ps.setString(1, id);
    ResultSet rs=ps.executeQuery();
    EmployeePojo e=null;
    if(rs.next()){
        e=new EmployeePojo();
        e.setEmpid(rs.getString(1));
        e.setEmpname(rs.getString(2));
        e.setJob(rs.getString(3));
        e.setSal(rs.getDouble(4));
     }
    return e;
  }
    public static boolean updateEmployee(EmployeePojo e)throws SQLException{
      Connection con=DBConnection.getConnection();
      PreparedStatement ps=con.prepareStatement("Update employees set empname=? , job=?, salary=? where empid=?");
      ps.setString(1,e.getEmpname());
      ps.setString(2,e.getJob());
      ps.setDouble(3,e.getSal());
      ps.setString(4,e.getEmpid());
      int x=ps.executeUpdate();
      if(x==0){
             return false;
      }
      else{
          boolean result= UserDAO.isUserPresent(e.getEmpid());
          if(result==false)
              return true;
      ps=con.prepareStatement("Update users set username=?,usertype=? where empid=? "); 
         ps.setString(1,e.getEmpname());
         ps.setString(2,e.getJob());
         ps.setString(3,e.getEmpid());
         int y=ps.executeUpdate();
         return y==1;
      }
    }
    public static boolean deleteEmployee(String empid)throws SQLException{
         Connection con=DBConnection.getConnection();
      PreparedStatement ps=con.prepareStatement("Delete from employees where empid=?");
      ps.setString(1,empid);
      int x=ps.executeUpdate();
      return x==1;
    }
}
