/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author XinheHuang
 */
public class query extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8"); 
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String querry =request.getParameter("querry");
     try {
         Class.forName("org.postgresql.Driver").newInstance();
         String url ="jdbc:postgresql://localhost:5432/CreditCardDB";
         Connection conn= DriverManager.getConnection(url,"postgres","hxh8526622");
         PreparedStatement ps = conn.prepareStatement("SELECT * FROM \"person\"");
         ps.execute();
         ResultSet rs = ps.getResultSet();
         while (rs.next())
         {
            out.println("123"); 
         }
     } catch (Exception ex) {
        ex.printStackTrace();
     }

      
 }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    

}
