package com.Library.Item;

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

public class MyItemRegistration extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String itemType = req.getParameter("itemType");
        String itemName = req.getParameter("itemName");
        String itemId = req.getParameter("itemId");
        String itemPrice = req.getParameter("itemPrice");
        String itemQuantity = req.getParameter("itemQuantity");

        try {
            String url = "jdbc:mysql://localhost:3306/Library";
            String username = "root";
            String sqlpassword = "1234";

            res.setContentType("text/html");
            PrintWriter pw = res.getWriter();

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, username, sqlpassword)) {
                String query = "INSERT INTO items (item_type, item_name, item_id, item_price, item_quantity) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pStatement = connection.prepareStatement(query)) {
                    pStatement.setString(1, itemType);
                    pStatement.setString(2, itemName);
                    pStatement.setString(3, itemId);
                    pStatement.setString(4, itemPrice);
                    pStatement.setString(5, itemQuantity);

                    int resultSet = pStatement.executeUpdate();
                    if (resultSet > 0) {
                        res.sendRedirect("bookAdd.html");
                    } else {
                        pw.println("<h3>Failed to Register Item!</h3>");
                    }
                }
            } catch (SQLException e) {
                pw.println("<h3>Database Error: " + e.getMessage() + "</h3>");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
