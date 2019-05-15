package me.mikethesupertramp.employeemanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.mikethesupertramp.employeemanager.ui.controllers.AdminPanelController;
import me.mikethesupertramp.employeemanager.ui.controllers.DashboardController;
import me.mikethesupertramp.toolkit.database.SQLConnectionProvider;
import me.mikethesupertramp.toolkit.database.SQLiteConnectionProvider;
import me.mikethesupertramp.toolkit.rfid.JavaFxDispatchService;
import me.mikethesupertramp.toolkit.rfid.RFIDReader;
import org.jnativehook.NativeHookException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    private DashboardController dashboardController;
    private AdminPanelController adminPanelController;
    private EmployeeDatabaseManager db;
    private RFIDReader rfidReader;
    private EnrollmentController enrollmentController;
    private Stage mainStage;
    private Stage loadingStage;
    private ExecutorService executorService;
    private Parent dashboardView;
    private Parent adminPanelView;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        executorService = Executors.newFixedThreadPool(1);
        initUI(primaryStage);
        loadingStage.show();
        Task task = new Task() {
            @Override
            protected Object call() {
                initDB();
                initRFID();
                initSystems();
                return null;
            }
        };
        task.setOnSucceeded((e) -> {
            postInit();
        });
        executorService.execute(task);
    }

    private void postInit() {
        System.out.println("Post initialization");
        dashboardController.updateEmployees(db.employee.getAll());
        loadingStage.hide();
    }

    private void initUI(Stage primaryStage) throws IOException {
        System.out.println("Initializing user interface..");
        initMainStage(primaryStage);
        initLoadingStage();
        loadDashboard();
        loadAdminPanel();
        setMainView(dashboardView);
        mainStage.show();
        dashboardController.setOnExitButtonPressed(e -> mainStage.close());
        dashboardController.setOnOpenAdminPanelButtonPressed((e) -> setMainView(adminPanelView));
        adminPanelController.setOnBackButtonPressed((e) -> setMainView(dashboardView));
        System.out.println("User interface initialised successfully");
    }

    private void setMainView(Parent root) {
        mainStage.getScene().setRoot(root);
    }


    private void initMainStage(Stage primaryStage) {
        System.out.println("Initializing main stage");
        mainStage = primaryStage;
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(new Pane()));
    }

    private void initLoadingStage() throws IOException {
        System.out.println("Initializing loading stage");
        loadingStage = new Stage(StageStyle.UNDECORATED);
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.initOwner(mainStage);
        loadingStage.setAlwaysOnTop(true);
        loadingStage.setOnCloseRequest((e) -> {
            mainStage.close();
        });
        loadingStage.setOnShown((e) -> {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            loadingStage.setX(screenBounds.getWidth() / 2 - loadingStage.getWidth() / 2);
            loadingStage.setY(screenBounds.getHeight() / 2 - loadingStage.getHeight() / 2);
        });
        System.out.println("Initializing loading-bar");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "/fxml/loading.fxml"));
        Parent root = fxmlLoader.load();
        loadingStage.setScene(new Scene(root));
    }

    private void loadDashboard() throws IOException {
        System.out.println("Initializing dashboard");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "/fxml/dashboard.fxml"));
        Parent root = fxmlLoader.load();
        dashboardController = fxmlLoader.getController();
        dashboardView = root;
    }

    private void loadAdminPanel() throws IOException {
        System.out.println("Initializing admin-panel");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "/fxml/admin-panel.fxml"));
        Parent root = fxmlLoader.load();
        adminPanelController = fxmlLoader.getController();
        adminPanelView = root;
    }


    private void initDB() {
        System.out.println("Initializing database");
        SQLConnectionProvider connectionProvider = new SQLiteConnectionProvider("employees.db");
        db = new EmployeeDatabaseManager(connectionProvider, System.out::println);
        db.employee.addListener(e -> {
            Platform.runLater(() ->
                    dashboardController.updateEmployees(db.employee.getAll())
            );
        });
    }

    private void initSystems() {
        System.out.println("Initializing systems");
        enrollmentController = new EnrollmentController(db);
        rfidReader.addListener(enrollmentController);
    }

    private void initRFID() {
        //
        try {
            RFIDReader.init(new JavaFxDispatchService());
        } catch (NativeHookException e) {
            e.printStackTrace();
            //todo show error message
        }
        rfidReader = RFIDReader.getInstance();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        RFIDReader.shutdown();
        executorService.shutdown();
    }


}