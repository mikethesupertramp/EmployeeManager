package me.mikethesupertramp.employeemanager.persistence;

import me.mikethesupertramp.toolkit.persistence.DatabaseManager;
import me.mikethesupertramp.toolkit.persistence.SQLConnectionProvider;
import me.mikethesupertramp.toolkit.persistence.SQLExceptionListener;

public class EmployeeDatabaseManager extends DatabaseManager {
    public final EmployeeDao employee = new EmployeeDao();

    public EmployeeDatabaseManager(SQLConnectionProvider connectionProvider, SQLExceptionListener exceptionListener) {
        super(connectionProvider, exceptionListener);

        addDao(employee);
    }


}
