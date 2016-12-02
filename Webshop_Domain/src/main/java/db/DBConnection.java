package db;

import db.exception.DBException;

import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

    //An example dbinfo.properties file can be found in the root directory of the 'Webshop Domain' project

    private static DBConnection uniqueInstance;

    private Connection conn;
    private String scheme;
    private Properties properties;

    private DBConnection(Properties properties) {
        this.properties = properties;
    }

    public static DBConnection getInstance(Properties properties) {

        if(uniqueInstance == null) {
            synchronized (DBConnection.class) {
                if(uniqueInstance == null) {
                    uniqueInstance = new DBConnection(properties);
                }
            }
        }
        return uniqueInstance;

    }

    private void initConn() {
        try {
            Class.forName(properties.getProperty("driver"));
            Class.forName(properties.getProperty("sslfactory"));
            conn = DriverManager.getConnection("jdbc:" + properties.getProperty("type") + "://" + properties.getProperty("host")
                    + ":" + properties.getProperty("port") + "/" + properties.getProperty("db"), properties);
            this.scheme = properties.getProperty("scheme");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConn() {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private void initConnIfNeeded() {
        try {
            if (conn == null || conn.isClosed()) { // Check if connection is not initialized or if it was closed
                initConn();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement getPreparedStatement(String sql) {
        initConnIfNeeded();
        try {
            return conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(PreparedStatement preparedStatement) {
        initConnIfNeeded();
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void executeUpdate(PreparedStatement preparedStatement) {
        initConnIfNeeded();
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setStatementParameters(PreparedStatement preparedStatement, Object... params) {
        for (int i = 0; i < params.length; i++) {
            try {
                preparedStatement.setObject(i + 1, params[i]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getScheme() {
        initConnIfNeeded();
        return this.scheme;
    }

}
