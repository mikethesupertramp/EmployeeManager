package me.mikethesupertramp.employeemanager;
import me.mikethesupertramp.toolkit.database.SQLConnectionProvider;
import me.mikethesupertramp.toolkit.database.SQLiteConnectionProvider;

public class App {
    public static void main(String[] args) {
        SQLConnectionProvider connectionProvider = new SQLiteConnectionProvider("test.db");
        EmployeeDatabaseManager dbManager = new EmployeeDatabaseManager(connectionProvider, System.out::println);



    }
}
