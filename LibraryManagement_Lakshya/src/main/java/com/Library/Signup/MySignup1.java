package com.Library.Signup;

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


public class MySignup1 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/Library";
        String username = "root";
        String sqlpassword = "1234";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, sqlpassword);

            String query = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, request.getParameter("name"));
            preparedStatement.setString(2, request.getParameter("email"));
            preparedStatement.setString(3, request.getParameter("password"));

            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                response.sendRedirect("login.html");
            } else {
                out.println("Form Not Submitted Successfully");
            }

            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            out.println("Error loading database driver: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
