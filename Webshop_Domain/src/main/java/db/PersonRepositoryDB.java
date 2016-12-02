package db;

import db.exception.DBException;
import domain.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PersonRepositoryDB implements PersonRepository {

    private DBConnection dbConnection;

    public PersonRepositoryDB(Properties properties) {
        dbConnection = DBConnection.getInstance(properties);
    }

    @Override
    public Person get(String personId) {

        String sql =    "select * from " + dbConnection.getScheme() + ".person\n" +
                        "where userid = ?";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);
        dbConnection.setStatementParameters(preparedStatement, personId);

        ResultSet resultSet;
        try {
            resultSet = dbConnection.executeQuery(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to fetch person.");
        }

        Person[] persons = resultSetToPersonArray(resultSet);
        return persons.length != 0 ? persons[0] : null;

    }

    @Override
    public List<Person> getAll() {

        String sql =    "select * from " + dbConnection.getScheme() + ".person";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);

        ResultSet resultSet;
        try {
            resultSet = dbConnection.executeQuery(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to fetch people.");
        }

        return Arrays.asList(resultSetToPersonArray(resultSet));

    }

    @Override
    public void add(Person person) {

        if(get(person.getUserid()) != null) throw new DBException("User already exists");

        String sql =    "insert into " + dbConnection.getScheme() + ".person (userid, email, password, firstName, lastName, salt)\n" +
                        "values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);
        dbConnection.setStatementParameters(preparedStatement,
                person.getUserid(),
                person.getEmail(),
                person.getPassword(),
                person.getFirstName(),
                person.getLastName(),
                person.getSalt());

        try {
            dbConnection.executeUpdate(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to add person.");
        }

    }

    @Override
    public void update(Person person) {

        String sql =    "update " + dbConnection.getScheme() + ".person\n" +
                        "set userid = ?, email = ?, password = ?, firstName = ?, lastName = ? salt = ?\n" +
                        "where userid = ?";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);
        dbConnection.setStatementParameters(preparedStatement,
                person.getUserid(),
                person.getEmail(),
                person.getPassword(),
                person.getFirstName(),
                person.getLastName(),
                person.getSalt(),
                person.getUserid());

        try {
            dbConnection.executeUpdate(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to update person.");
        }

    }

    @Override
    public void delete(String personId) {

        String sql =    "delete from " + dbConnection.getScheme() + ".person\n" +
                        "where userid = ?";

        PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql);
        dbConnection.setStatementParameters(preparedStatement, personId);

        try {
            dbConnection.executeUpdate(preparedStatement);
        } catch (Exception e) {
            throw new DBException("Unable to delete person.");
        }

    }

    @Override
    public void close() {
        dbConnection.closeConn();
    }

    private Person[] resultSetToPersonArray(ResultSet resultSet) {

        List<Person> persons = new ArrayList<>();

        try {
            while(resultSet.next()) {
                String userid = resultSet.getString("userid");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password").trim();
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String salt = resultSet.getString("salt").trim();
                Person person = new Person(userid, email, password, firstName, lastName, salt);
                persons.add(person);
            }
        } catch (Exception e) {
            throw new DBException(e);
        }

        return persons.toArray(new Person[persons.size()]);

    }

}
