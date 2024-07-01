package com.Library.ShowItems;

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

public class ShowItems extends HttpServlet {

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

            String sql = "SELECT * FROM items";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            out.println("<html><body><center><div>");
            out.println("<h1>Items</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Item Type</th><th>Item Name</th><th>Item Id</th><th>Item Price</th><th>Item Qnt</th></tr>");
            
            while (rs.next()) {
                String itemType = rs.getString("item_type");
                String itemName = rs.getString("item_name");
                String itemId = rs.getString("item_id");
                float itemPrice = rs.getFloat("item_price");
                int itemQnt = rs.getInt("item_quantity");

                out.println("<tr>");
                out.println("<td>" + itemType + "</td>");
                out.println("<td>" + itemName + "</td>");
                out.println("<td>" + itemId + "</td>");
                out.println("<td>" + itemPrice + "</td>");
                out.println("<td>" + itemQnt + "</td>");
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
