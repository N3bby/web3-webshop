package db;

import db.exception.DBException;
import domain.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ProductRepositoryDB implements ProductRepository {

    private DBConnection dbConnection;

    public ProductRepositoryDB(Properties properties) {
        dbConnection = DBConnection.getInstance(properties);
    }

    @Override
    public Product get(String productId) {

        String sql =    "select * from " + dbConnection.getScheme() + ".product\n" +
                        "where id = ?";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);
        dbConnection.setStatementParameters(preparedStatement, productId);

        ResultSet resultSet;
        try {
            resultSet = dbConnection.executeQuery(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to fetch product.");
        }

        Product[] products = resultSetToProductArray(resultSet);
        return products.length != 0 ? products[0] : null;

    }

    @Override
    public List<Product> getAll() {

        String sql =    "select * from " + dbConnection.getScheme() + ".product\n";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);

        ResultSet resultSet;
        try {
            resultSet = dbConnection.executeQuery(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to fetch products.");
        }

        return Arrays.asList(resultSetToProductArray(resultSet));

    }

    @Override
    public void add(Product product) {

        if(get(product.getId()) != null) throw new DBException("Product already exists");

        String sql =    "insert into " + dbConnection.getScheme() + ".product (id, description, price)\n" +
                        "values (?, ?, ?)";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);
        dbConnection.setStatementParameters(preparedStatement,
                product.getId(),
                product.getDescription(),
                product.getPrice());

        try {
            dbConnection.executeUpdate(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to add product.");
        }

    }

    @Override
    public void update(Product product) {

        String sql =    "update " + dbConnection.getScheme() + ".product\n" +
                        "set id = ?, description = ?, price = ?\n" +
                        "where id = ?";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);
        dbConnection.setStatementParameters(preparedStatement,
                product.getId(),
                product.getDescription(),
                product.getPrice(),
                product.getId());

        try {
            dbConnection.executeUpdate(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to update product.");
        }

    }

    @Override
    public void delete(String productId) {

        String sql =    "delete from " + dbConnection.getScheme() + ".product\n" +
                        "where id = ?";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);
        dbConnection.setStatementParameters(preparedStatement, productId);

        try {
            dbConnection.executeUpdate(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to delete product.");
        }

    }

    @Override
    public void close() {
        dbConnection.closeConn();
    }

    private Product[] resultSetToProductArray(ResultSet resultSet) {

        List<Product> products = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String description = resultSet.getString("description");
                Double price = resultSet.getDouble("price");
                Product product = new Product(id, description, price);
                products.add(product);
            }
        } catch (Exception e) {
            throw new DBException(e);
        }

        return products.toArray(new Product[products.size()]);

    }

}
