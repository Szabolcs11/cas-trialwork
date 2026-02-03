package org.example.demo2.repository;

import org.example.demo2.model.Environment;
import org.example.demo2.model.Feature;
import org.example.demo2.service.SQLQuerries;
import org.example.demo2.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentRepository {
    public List<Environment> getEnvironments() {
        List<Environment> environmentList = new ArrayList<>();
        try {

            Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQLQuerries.GET_ENVIRONMENTS);

            while (rs.next()) {
                Environment environment = new Environment(rs.getInt("Id"), rs.getString("Name"), rs.getBoolean("Protected"), rs.getDate("CreatedAt"));
                environmentList.add(environment);
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return environmentList;
    }

    public String createEnvironment(String name) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SQLQuerries.CREATE_ENVIRONMENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);

            int rows = preparedStatement.executeUpdate();

            if (rows == 0) return "E-500";

            String insertedId = "E-500";
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    long insertIdLong = rs.getLong(1);
                    insertedId = String.valueOf(insertIdLong);
                }
            }

            return insertedId;

        }
        catch (SQLIntegrityConstraintViolationException ex) {
            return "E-409";
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "E-500";
    }

    public boolean createEnvironmentFeatures(String id) {
        FeatureRepository featureRepository = new FeatureRepository();
        List<Feature> features = featureRepository.getFeatures();
        System.out.println(features);


        if (features == null || features.isEmpty()) {
            return true;
        }

        String sql = SQLQuerries.INSERT_ENVIRONMENT_FEATURE;

        try (
                Connection connection = Database.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            for (Feature feature : features) {
                ps.setString(1, id);
                ps.setInt(2, feature.getId());
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteEnvironment(String id) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.DELETE_ENVIRONMENT);
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
