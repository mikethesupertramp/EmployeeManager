package me.mikethesupertramp.employeemanager;

import me.mikethesupertramp.employeemanager.dao.EmployeeDao;
import me.mikethesupertramp.toolkit.database.DatabaseManager;
import me.mikethesupertramp.toolkit.database.SQLConnectionProvider;
import me.mikethesupertramp.toolkit.database.SQLExceptionListener;

public class EmployeeDatabaseManager extends DatabaseManager {
    public final EmployeeDao employee = new EmployeeDao();

    public EmployeeDatabaseManager(SQLConnectionProvider connectionProvider, SQLExceptionListener exceptionListener) {
        super(connectionProvider, exceptionListener);

        addDao(employee);
    }


}
