package org.example.demo2.repository;

import org.example.demo2.service.SQLQuerries;
import org.example.demo2.util.Database;
import org.example.demo2.util.JwtUtil;
import org.example.demo2.util.Password;

import java.sql.*;
import java.time.LocalDateTime;

public class AuthRepository {
    public String loginUser(String email, String password) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.LOGIN);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String hashedPassword = rs.getString("password");
                boolean isAdmin = rs.getBoolean("admin");
                boolean passwordsMatch = Password.checkPassword(password, hashedPassword);
                if (passwordsMatch) {
                    return JwtUtil.generateToken(email, isAdmin);
                }
            }

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean changePassword(String email, String password) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.CHANGE_PASSWORD);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);

            int rows = preparedStatement.executeUpdate();

            connection.close();

            return rows >= 1;

        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Date getLastLogin(String email) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.GET_LAST_LOGIN);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Timestamp lastLogin = rs.getTimestamp("LastLogin");
                System.out.println("lastLogin");
                System.out.println(lastLogin);
                if (lastLogin == null) return null;
                return new Date(lastLogin.getTime());
            }

        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateLastLogin(String email) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.UPDATE_LAST_LOGIN);
            preparedStatement.setString(1, LocalDateTime.now().toString());
            preparedStatement.setString(2, email);

            int rows = preparedStatement.executeUpdate();

            connection.close();

            return rows >= 1;
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        return false;
    }
}
