package me.mikethesupertramp.employeemanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.mikethesupertramp.employeemanager.dao.Employee;
import me.mikethesupertramp.employeemanager.ui.controllers.DashboardController;
import me.mikethesupertramp.toolkit.database.SQLConnectionProvider;
import me.mikethesupertramp.toolkit.database.SQLiteConnectionProvider;

import java.io.IOException;

public class App extends Application {
    private DashboardController dashboardController;
    private EmployeeDatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUI(primaryStage);
        initDB();

        for (int i = 0; i < 39; i++) {
            Employee e = new Employee();
            e.setId(i);
            e.setCardID1(i * 100);
            e.setCardID2(i);
            e.setPresent(Math.random() > 0.5);
            e.setLate(Math.random() < 0.3);

            dbManager.employee.update(e);

        }
    }

    private void initUI(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "/fxml/dashboard.fxml"));
        Parent root = fxmlLoader.load();
        dashboardController = fxmlLoader.getController();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    private void initDB() {
        SQLConnectionProvider connectionProvider = new SQLiteConnectionProvider("employees.db");
        dbManager = new EmployeeDatabaseManager(connectionProvider, System.out::println);
        dbManager.employee.addListener(e -> {
            Platform.runLater(() ->
                    dashboardController.updateEmployees(dbManager.employee.getAll())
            );
        });
    }
}
