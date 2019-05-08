package me.mikethesupertramp.employeemanager.dao;

import me.mikethesupertramp.toolkit.database.AdvDao;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EmployeeDao extends AdvDao<Employee> {
    private PreparedStatement createStatement;
    private PreparedStatement retrieveStatement;
    private PreparedStatement retrieveAllStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;

    @Override
    protected void prepareStatements(Connection connection) {
        try {
            createStatement = connection.prepareStatement("INSERT INTO employees " +
                    "(id, first_name, last_name, job_title, card_id1, card_id2) VALUES (?,?,?,?,?,?);");
            retrieveStatement = connection.prepareStatement("SELECT * FROM employees WHERE id=?;");
            retrieveAllStatement = connection.prepareStatement("SELECT * FROM employees;");
            updateStatement = connection.prepareStatement("UPDATE employees SET " +
                    "first_name=?, last_name=?, job_title=?, card_id1=?, card_id2=? WHERE id=?; ");
            deleteStatement = connection.prepareStatement("DELETE FROM employees WHERE id=?;");
        } catch (SQLException e) {
            onSQLException(e);
        }
    }

    @Override
    protected void createTableIfNotExists(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS employees (" +
                    "id INTEGER," +
                    "first_name TEXT," +
                    "last_name TEXT," +
                    "job_title TEXT," +
                    "card_id1 INTEGER UNIQUE," +
                    "card_id2 INTEGER UNIQUE," +
                    "PRIMARY KEY(id)" +
                    ");"
            );
            statement.close();
        } catch (SQLException e) {
            onSQLException(e);
        }
    }

    @Override
    public Optional<Employee> get(int id) {
        if(!isInitialized()) throw new IllegalStateException("DAO has not been initialized");

        try {
            retrieveStatement.setInt(1, id);
            ResultSet rs = retrieveStatement.executeQuery();
            if(rs.next()) {
                return Optional.of(extract(rs));
            }
        } catch (SQLException e) {
            onSQLException(e);
        }

        return Optional.empty();
    }

    @Override
    public Set<Employee> getAll() {
        if(!isInitialized()) throw new IllegalStateException("DAO has not been initialized");
        Set<Employee> result = new HashSet<>();

        try {
            ResultSet rs = retrieveAllStatement.executeQuery();
            while(rs.next()) {
                result.add(extract(rs));
            }
        } catch (SQLException e) {
            onSQLException(e);
        }

        return result;
    }

    @Override
    public boolean insert(Employee employee) {
        if(!isInitialized()) throw new IllegalStateException("DAO has not been initialized");
        try {
            createStatement.setInt(1, employee.getId());
            createStatement.setString(2, employee.getFirstName());
            createStatement.setString(3, employee.getLastName());
            createStatement.setString(4, employee.getJobTitle());
            createStatement.setInt(5, employee.getCardID1());
            createStatement.setInt(6, employee.getCardID2());
            return createStatement.executeUpdate()==1;
        } catch (SQLException e) {
            onSQLException(e);
        }

        return false;
    }

    @Override
    public boolean update(Employee employee) {
        if(!isInitialized()) throw new IllegalStateException("DAO has not been initialized");
        try {
            updateStatement.setString(1, employee.getFirstName());
            updateStatement.setString(2, employee.getLastName());
            updateStatement.setString(3, employee.getJobTitle());
            updateStatement.setInt(4, employee.getCardID1());
            updateStatement.setInt(5, employee.getCardID2());
            updateStatement.setInt(6, employee.getId());
            return updateStatement.executeUpdate()==1;
        } catch (SQLException e) {
            onSQLException(e);
        }

        return false;
    }

    @Override
    public boolean delete(Employee employee) {
        if(!isInitialized()) throw new IllegalStateException("DAO has not been initialized");

        try {
            deleteStatement.setInt(1, employee.getId());
            return deleteStatement.executeUpdate()==1;
        } catch (SQLException e) {
            onSQLException(e);
        }

        return false;
    }

    private Employee extract(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setJobTitle(rs.getString("job_title"));
        employee.setCardID1(rs.getInt("card_id1"));
        employee.setCardID2(rs.getInt("card_id2"));
        return employee;
    }

    @Override
    protected Class<Employee> getDTOClass() {
        return Employee.class;
    }
}
