package me.mikethesupertramp.employeemanager.domain;

import me.mikethesupertramp.employeemanager.persistence.Employee;
import me.mikethesupertramp.employeemanager.persistence.EmployeeDatabaseManager;
import me.mikethesupertramp.toolkit.persistence.SQLConnectionProvider;
import me.mikethesupertramp.toolkit.persistence.SQLiteConnectionProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class EnrollmentEngine implements CardListener {

    public final EmployeeDatabaseManager db;
    private final List<EnrollmentListener> listeners = new ArrayList<>();
    private Runnable onUnknownCardInput;
    private Runnable onDoubleEnterError;
    private Runnable onDoubleExitError;
    private Consumer<SQLException> onSqlError;

    public EnrollmentEngine(String databasePath) {
        SQLConnectionProvider connectionProvider = new SQLiteConnectionProvider(databasePath);
        db = new EmployeeDatabaseManager(connectionProvider, this::onSqlException);
    }

    private void onEmployeeEnter(Employee employee) {
        employee.setPresent(true);
        db.employee.update(employee);

        listeners.forEach(e -> e.onEmployeeEnter(employee));
    }

    private void onEmployeeExit(Employee employee) {
        employee.setPresent(false);
        db.employee.update(employee);

        listeners.forEach(e -> e.onEmployeeExit(employee));
    }

    @Override
    public void onCardEnter(long id) {
        Optional<Employee> optional = db.employee.getByCardId(id);

        if (optional.isPresent()) {
            Employee employee = optional.get();
            if (!employee.isPresent()) {
                onEmployeeEnter(employee);
            } else {
                onDoubleEnterError();
            }
        } else {
            onUnknownCardInput();
        }
    }

    @Override
    public void onCardExit(long id) {
        Optional<Employee> optional = db.employee.getByCardId(id);

        if (optional.isPresent()) {
            Employee employee = optional.get();
            if (employee.isPresent()) {
                onEmployeeExit(employee);
            } else {
                onDoubleExitError();
            }
        } else {
            onUnknownCardInput();
        }
    }

    private void onUnknownCardInput() {
        if (onUnknownCardInput != null) onUnknownCardInput.run();
    }

    private void onDoubleEnterError() {
        if (onDoubleEnterError != null) onDoubleEnterError.run();
    }

    private void onDoubleExitError() {
        if (onDoubleExitError != null) onDoubleExitError.run();
    }

    private void onSqlException(SQLException e) {
        if (onSqlError != null) onSqlError.accept(e);
    }

    public void addListener(EnrollmentListener listener) {
        listeners.add(listener);
    }

    public void removeListener(EnrollmentListener listener) {
        listeners.remove(listener);
    }

    public void setOnUnknownCardInput(Runnable onUnknownCardInput) {
        this.onUnknownCardInput = onUnknownCardInput;
    }

    public void setOnDoubleEnterError(Runnable onDoubleEnterError) {
        this.onDoubleEnterError = onDoubleEnterError;
    }

    public void setOnDoubleExitError(Runnable onDoubleExitError) {
        this.onDoubleExitError = onDoubleExitError;
    }

    public void setOnSqlError(Consumer<SQLException> onSqlError) {
        this.onSqlError = onSqlError;
    }
}
