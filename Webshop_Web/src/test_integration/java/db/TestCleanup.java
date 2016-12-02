package db;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Properties;

public class TestCleanup {

    private static final String PROPERTIES_PATH = "C:\\Users\\leviv\\Google Drive\\School\\Fase 2\\Webontwikkeling 3\\Properties\\dbinfo.properties";

    private static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(PROPERTIES_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static void cleanTestPersons(String idPrefix) {

        DBConnection dbConnection = DBConnection.getInstance(getProperties());

        String sql =    "delete from " + dbConnection.getScheme() + ".person\n" +
                        "where userid like '" + idPrefix + "%'";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);

        dbConnection.executeUpdate(preparedStatement);

    }

    public static void cleanTestProducts(String idPrefix) {

        DBConnection dbConnection = DBConnection.getInstance(getProperties());

        String sql =    "delete from " + dbConnection.getScheme() + ".product\n" +
                        "where id like '" + idPrefix + "%'";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);

        dbConnection.executeUpdate(preparedStatement);

    }

}
