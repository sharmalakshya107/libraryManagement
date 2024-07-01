package com.Library.ShowBorrowers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowBorrowers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","1234");

            String sql = "SELECT * FROM borrowers";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            out.println("<html><body><center><div>");
            out.println("<h1>Items</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Item Name</th><th>Item Id</th><th>Item Type</th><th>Full Name</th><th>College Id</th><th>Branch</th><th>Year</th><th>Address</th><th>Time Period</th></tr>");
            
            while (rs.next()) {
            	String itemName = rs.getString("item_name");
            	String itemId = rs.getString("item_id");
                String itemType = rs.getString("item_type");
                String name = rs.getString("full_name");
                int clgId = rs.getInt("college_id");
                String branch = rs.getString("branch");
                int year = rs.getInt("year");
                String address = rs.getString("address");
                int timeperiod = rs.getInt("time_period");

                out.println("<tr>");
                out.println("<td>" + itemName + "</td>");
                out.println("<td>" + itemId + "</td>");
                out.println("<td>" + itemType + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + clgId + "</td>");
                out.println("<td>" + branch + "</td>");
                out.println("<td>" + year + "</td>");
                out.println("<td>" + address + "</td>");
                out.println("<td>" + timeperiod + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</div></center></body></html>");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
