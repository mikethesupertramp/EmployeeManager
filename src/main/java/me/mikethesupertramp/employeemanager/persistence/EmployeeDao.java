package me.mikethesupertramp.employeemanager.persistence;

import me.mikethesupertramp.toolkit.persistence.AdvDao;
import me.mikethesupertramp.toolkit.persistence.DataChangeEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDao extends AdvDao<Employee> {
    private PreparedStatement createStatement;
    private PreparedStatement retrieveStatement;
    private PreparedStatement retrieveAllStatement;
    private PreparedStatement retrieveByCardIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;

    @Override
    protected void prepareStatements(Connection connection) {
        try {
            createStatement = connection.prepareStatement("INSERT INTO employees " +
                    "(id, first_name, last_name, present, late, card_id1, card_id2) VALUES (?,?,?,?,?,?,?);");
            retrieveStatement = connection.prepareStatement("SELECT * FROM employees WHERE id=?;");
            retrieveAllStatement = connection.prepareStatement("SELECT * FROM employees;");
            retrieveByCardIdStatement = connection.prepareStatement("SELECT * FROM employees " +
                    "WHERE card_id1=? OR card_id2=?;");
            updateStatement = connection.prepareStatement("UPDATE employees SET " +
                    "first_name=?, last_name=?, present=?, late=?, card_id1=?, card_id2=? WHERE id=?; ");
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
                    "present INTEGER," +
                    "late INTEGER," +
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
    public List<Employee> getAll() {
        if(!isInitialized()) throw new IllegalStateException("DAO has not been initialized");
        List<Employee> result = new ArrayList<>();

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

    public Optional<Employee> getByCardId(long cardId) {
        try {
            retrieveByCardIdStatement.setLong(1, cardId);
            retrieveByCardIdStatement.setLong(2, cardId);
            ResultSet rs = retrieveByCardIdStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(extract(rs));
            }
        } catch (SQLException e) {
            onSQLException(e);
        }

        return Optional.empty();
    }

    @Override
    public boolean insert(Employee employee) {
        if(!isInitialized()) throw new IllegalStateException("DAO has not been initialized");
        try {
            createStatement.setInt(1, employee.getId());
            createStatement.setString(2, employee.getFirstName());
            createStatement.setString(3, employee.getLastName());
            createStatement.setBoolean(4, employee.isPresent());
            createStatement.setBoolean(5, employee.isLate());
            createStatement.setLong(6, employee.getCardID1());
            createStatement.setLong(7, employee.getCardID2());
            if (createStatement.executeUpdate() == 1) {
                notifyListeners(new DataChangeEvent<>(DataChangeEvent.TYPE.INSERT, employee));
                return true;
            }
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
            updateStatement.setBoolean(3, employee.isPresent());
            updateStatement.setBoolean(4, employee.isLate());
            updateStatement.setLong(5, employee.getCardID1());
            updateStatement.setLong(6, employee.getCardID2());
            updateStatement.setInt(7, employee.getId());
            if (updateStatement.executeUpdate() == 1) {
                notifyListeners(new DataChangeEvent<>(DataChangeEvent.TYPE.UPDATE, employee));
                return true;
            }
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
            if (deleteStatement.executeUpdate() == 1) {
                notifyListeners(new DataChangeEvent<>(DataChangeEvent.TYPE.DELETE, employee));
                return true;
            }
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
        employee.setPresent(rs.getBoolean("present"));
        employee.setLate(rs.getBoolean("late"));
        employee.setCardID1(rs.getLong("card_id1"));
        employee.setCardID2(rs.getLong("card_id2"));
        return employee;
    }

    @Override
    protected Class<Employee> getDTOClass() {
        return Employee.class;
    }
}
