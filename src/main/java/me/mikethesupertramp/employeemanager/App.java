package me.mikethesupertramp.employeemanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.mikethesupertramp.employeemanager.ui.controllers.DashboardController;
import me.mikethesupertramp.toolkit.database.SQLConnectionProvider;
import me.mikethesupertramp.toolkit.database.SQLiteConnectionProvider;
import me.mikethesupertramp.toolkit.rfid.RFIDReader;
import org.jnativehook.NativeHookException;

import java.io.IOException;

public class App extends Application {
    private DashboardController dashboardController;
    private EmployeeDatabaseManager dbManager;
    private RFIDReader rfidReader;

    private Stage dashboardStage;
    private Stage loadingStage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUI(primaryStage);
        initDB();
        initRFID();
    }

    private void initUI(Stage primaryStage) throws IOException {
        initDashboardStage(primaryStage);
        initLoadingStage();
    }


    private void initDashboardStage(Stage primaryStage) throws IOException {
        dashboardStage = primaryStage;
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

    private void initLoadingStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "/fxml/loading.fxml"));
        Scene loadingScene = new Scene(fxmlLoader.load());
        loadingStage = new Stage(StageStyle.UNDECORATED);
        loadingStage.setScene(loadingScene);
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.initOwner(dashboardStage);
        loadingStage.setAlwaysOnTop(true);
        loadingStage.setOnCloseRequest((e) -> {
            dashboardStage.close();
        });
        loadingStage.setOnShown((e) -> {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            loadingStage.setX(screenBounds.getWidth() / 2 - loadingStage.getWidth() / 2);
            loadingStage.setY(screenBounds.getHeight() / 2 - loadingStage.getHeight() / 2);
        });
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

    private void initRFID() {
        try {
            RFIDReader.init();
        } catch (NativeHookException e) {
            e.printStackTrace();
            //todo show error message
        }
        rfidReader = RFIDReader.getInstance();
        rfidReader.addListener(System.out::println);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        RFIDReader.shutdown();
    }
}