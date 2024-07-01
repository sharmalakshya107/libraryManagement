	package com.Library.Borrow;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BorrowItems extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String itemName = request.getParameter("item_name");
        String itemId = request.getParameter("item_id");
        String itemType = request.getParameter("item_type");
        String fullName = request.getParameter("full_name");
        String collegeId = request.getParameter("college_id");
        String branch = request.getParameter("branch");
        String year = request.getParameter("year");
        String address = request.getParameter("address");
        String timePeriod = request.getParameter("time_period");

        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement statement = null;

        try {
            String url = "jdbc:mysql://localhost:3306/Library";
            String user = "root";
            String password = "1234";

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, password);
            
	            String sql = "INSERT INTO borrowers (item_name, item_id, item_type, full_name, college_id, branch, year, address, time_period) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, itemName);
	            stmt.setString(2, itemId);
	            stmt.setString(3, itemType);
	            stmt.setString(4, fullName);
	            stmt.setString(5, collegeId);
	            stmt.setString(6, branch);
	            stmt.setString(7, year);
	            stmt.setString(8, address);
	            stmt.setString(9, timePeriod);
	
	            int rowsAffected = stmt.executeUpdate();
	
	            if (rowsAffected > 0) {
	                response.sendRedirect("Itemborrow.html");
	            } else {
	                response.setContentType("text/html");
	                PrintWriter out = response.getWriter();
	                out.println("<html><body><h2>Borrowing failed</h2></body></html>");
	            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body><h2>Error: " + e.getMessage() + "</h2></body></html>");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
