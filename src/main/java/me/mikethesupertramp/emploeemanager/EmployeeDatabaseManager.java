package me.mikethesupertramp.emploeemanager;

import me.mikethesupertramp.emploeemanager.dao.EmployeeDao;
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
