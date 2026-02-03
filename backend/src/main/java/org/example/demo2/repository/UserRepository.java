package org.example.demo2.repository;

import org.example.demo2.model.User;
import org.example.demo2.service.SQLQuerries;
import org.example.demo2.util.Database;
import org.example.demo2.util.Password;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();

        try {
             Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQLQuerries.GET_ALL_USERS);

            while (rs.next()) {
                User user = new User(rs.getInt("Id"), rs.getString(("Email")), rs.getBoolean("Admin"), rs.getDate("LastLogin"));
                userList.add(user);
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
        }
        return userList;
    }

    public String createUser(String email, String password) {

        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.CREATE_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            int rows = preparedStatement.executeUpdate();
            return rows >= 0 ? "200" : "500";

        }
        catch (SQLIntegrityConstraintViolationException ex) {
            return "409";
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

    public User getUserById(String id) {
        User user = new User();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.GET_USER_BY_ID);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("Id"), rs.getString(("Email")), rs.getBoolean("Admin"), rs.getDate("LastLogin"));
                return user;
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByEmail(String email) {
        User user = new User();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.GET_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("Id"), rs.getString(("Email")), rs.getBoolean("Admin"), rs.getDate("LastLogin"));
                return user;
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean deleteUser(String id) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.DELETE_USER);
            preparedStatement.setString(1, id);

            int rows = preparedStatement.executeUpdate();

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
}
