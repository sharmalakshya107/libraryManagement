package com.Library.Login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyLogin extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String adminCheck = req.getParameter("adminCheck");

        try {
            String url = "jdbc:mysql://localhost:3306/Library";
            String username = "root";
            String sqlpassword = "1234";

            res.setContentType("text/html");
            PrintWriter pw = res.getWriter();

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, username, sqlpassword)) {
                if (adminCheck == null) {
                    
                    String query = "SELECT * FROM user WHERE email = ? AND password = ?";
                    try (PreparedStatement pStatement = connection.prepareStatement(query)) {
                        pStatement.setString(1, email);
                        pStatement.setString(2, password);

                        try (ResultSet resultSet = pStatement.executeQuery()) {
                            if (resultSet.next()) {
                               
                                res.sendRedirect("index_3.html");
                                return;
                            } else {
                                pw.print("Login Not Successful");
                            }
                        }
                    }
                } else {
                    String query = "SELECT * FROM admin WHERE email = ? AND password = ?";
                    try (PreparedStatement pStatement = connection.prepareStatement(query)) {
                        pStatement.setString(1, email);
                        pStatement.setString(2, password);

                        try (ResultSet resultSet = pStatement.executeQuery()) {
                            if (resultSet.next()) {
                                
                                res.sendRedirect("index_2.html");
                                return; 
                            } else {
                                pw.print("Login Not Successful");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
