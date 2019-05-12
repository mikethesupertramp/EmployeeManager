package me.mikethesupertramp.employeemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.mikethesupertramp.employeemanager.ui.controllers.DashboardController;

import java.io.IOException;

public class App extends Application {
    private DashboardController dashboardController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUI(primaryStage);
    }

    private void initUI(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "/fxml/dashboard.fxml"));
        Parent root = fxmlLoader.load();
        dashboardController = fxmlLoader.getController();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void initDB() {
        //SQLConnectionProvider connectionProvider = new SQLiteConnectionProvider("test.db");
        //EmployeeDatabaseManager dbManager = new EmployeeDatabaseManager(connectionProvider, System.out::println);
    }
}
