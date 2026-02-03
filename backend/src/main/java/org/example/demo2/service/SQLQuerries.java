package org.example.demo2.service;

public final class SQLQuerries {

    public static final String LOGIN = "SELECT Password, Admin FROM users WHERE email = ?";
    public static final String GET_LAST_LOGIN = "SELECT LastLogin FROM users WHERE email = ?";
    public static final String UPDATE_LAST_LOGIN = "UPDATE users SET LastLogin = ? WHERE email = ?";
    public static final String CHANGE_PASSWORD = "UPDATE users SET password = ? where email = ?";

    public static final String GET_ALL_USERS = "SELECT Id, Email, Admin, LastLogin FROM users";
    public static final String CREATE_USER = "INSERT INTO users (Email, Password) VALUES(?, ?)";
    public static final String GET_USER_BY_ID = "SELECT Id, Email, Admin, LastLogin FROM users WHERE Id = ?";
    public static final String GET_USER_BY_EMAIL = "SELECT Id, Email, Admin, LastLogin FROM users WHERE Email = ?";
    public static final String DELETE_USER = "DELETE FROM users WHERE Id = ?";

    public static final String GET_FEATURE_BY_ID = "SELECT Id, Identifier, Name, Description FROM features WHERE id = ?";
    public static final String GET_FEATURES = "SELECT Id, Identifier, Name, Description FROM features";
    public static final String GET_FEATURES_BY_ENVIRONMENT = "SELECT features.Id, features.Identifier, features.Name, features.Description, environment_features.Enabled FROM environment_features INNER JOIN features ON environment_features.FeatureId = features.id INNER JOIN environments ON environment_features.EnvironmentId = environments.Id WHERE environments.Name = ?";
    public static final String TOGGLE_FEATUE = "UPDATE environment_features SET Enabled = ? WHERE EnvironmentId = ? AND FeatureId = ?";
    public static final String INSERT_ENVIRONMENT_FEATURE = "INSERT INTO environment_features (EnvironmentId, FeatureId) VALUES (?, ?)";
    public static final String CREATE_FEATURE = "INSERT INTO features (Identifier, Name, Description) VALUES (?, ?, ?)";
    public static final String DELETE_FEATURE = "DELETE FROM features WHERE Id = ?";
    public static final String GET_FEATUES_ENVIRONMENTS = "SELECT environment_features.id, environments.Name as 'EnvironmentName', features.Name as 'FeatureName', environment_features.Enabled FROM `environment_features` INNER JOIN environments ON environments.Id = environment_features.EnvironmentId INNER JOIN features ON features.id = environment_features.FeatureId WHERE FeatureId = ?";

    public static final String GET_ENVIRONMENTS = "SELECT * FROM environments";
    public static final String CREATE_ENVIRONMENT = "INSERT INTO environments (Name) VALUES(?)";
    public static final String DELETE_ENVIRONMENT = "DELETE FROM environments WHERE Id = ? AND Protected = 0";
}
