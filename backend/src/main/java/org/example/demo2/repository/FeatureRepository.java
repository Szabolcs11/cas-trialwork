package org.example.demo2.repository;

import org.example.demo2.model.Environment;
import org.example.demo2.model.EnvironmentFeature;
import org.example.demo2.model.Feature;
import org.example.demo2.model.User;
import org.example.demo2.service.SQLQuerries;
import org.example.demo2.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeatureRepository {
    public List<Feature> getFeaturesByEnvironment(String environment) {
        List<Feature> featureList = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.GET_FEATURES_BY_ENVIRONMENT);
            preparedStatement.setString(1, environment);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Feature feature = new Feature(rs.getInt("Id"), rs.getString("Identifier"), rs.getString("Name"), rs.getString("Description"), rs.getBoolean("Enabled"));
                featureList.add(feature);
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return featureList;
    }

    public List<Feature> getFeatures() {
        List<Feature> featureList = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.GET_FEATURES);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Feature feature = new Feature(rs.getInt("Id"), rs.getString("Identifier"), rs.getString("Name"), rs.getString("Description"), false);
                featureList.add(feature);
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return featureList;
    }

    public boolean toggleFeatures(String featureId, String environmentId, boolean status) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.TOGGLE_FEATUE);
            String statusStr = status ? "1" : "0";
            preparedStatement.setString(1, statusStr);
            preparedStatement.setString(2, environmentId);
            preparedStatement.setString(3, featureId);

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

    public String createFeature(String identifier, String name, String description) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SQLQuerries.CREATE_FEATURE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, identifier);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);

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

    public boolean insertFeatureToEnvironments(String featureId, List<Environment> environmentList) {

        String sql = SQLQuerries.INSERT_ENVIRONMENT_FEATURE;

        try (
                Connection connection = Database.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            for (Environment environment : environmentList) {
                ps.setInt(1, environment.getId());
                ps.setString(2, featureId);
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteFeature(String featureId) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.DELETE_FEATURE);
            preparedStatement.setString(1, featureId);

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

    // returns a statistics for given feature by environments
    public List<EnvironmentFeature> getFeatureEnvironments(String featureId) {
        List<EnvironmentFeature> featureList = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.GET_FEATUES_ENVIRONMENTS);
            preparedStatement.setString(1, featureId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                EnvironmentFeature feature = new EnvironmentFeature(rs.getString("id"), rs.getString("EnvironmentName"), rs.getString("FeatureName"), rs.getBoolean("Enabled"));
                featureList.add(feature);
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return featureList;
    }

    public Feature getFeature(String featureId) {
        Feature feature = new Feature();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuerries.GET_FEATURE_BY_ID);
            preparedStatement.setString(1, featureId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                feature = new Feature(rs.getInt("Id"), rs.getString("Identifier"), rs.getString("Name"), rs.getString("Description"), false);
                return feature;
            }
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while connecting MySQL databse");
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return feature;
    }
}
