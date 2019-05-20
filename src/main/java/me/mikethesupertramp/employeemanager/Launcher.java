package me.mikethesupertramp.employeemanager;

import com.airhacks.afterburner.injection.Injector;
import com.airhacks.afterburner.views.FXMLView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.mikethesupertramp.employeemanager.domain.EnrollmentEngine;
import me.mikethesupertramp.employeemanager.presentation.adminpanel.AdminPanelPresenter;
import me.mikethesupertramp.employeemanager.presentation.adminpanel.AdminPanelView;
import me.mikethesupertramp.employeemanager.presentation.dashboard.DashboardPresenter;
import me.mikethesupertramp.employeemanager.presentation.dashboard.DashboardView;

import java.util.HashMap;
import java.util.Map;

public class Launcher extends Application {
    private static final String DATABASE_FILE = "db/employee-manager.db";
    private Map<Object, Object> context = new HashMap<>();

    private EnrollmentEngine enrollmentEngine;
    private Stage mainStage;
    private Scene mainScene;
    private Parent dashboardView;
    private DashboardPresenter dashboardPresenter;
    private Parent adminPanelView;
    private AdminPanelPresenter adminPanelPresenter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initEngine();

        //init DI
        context.put("enrollmentEngine", enrollmentEngine);
        context.put("employeeDao", enrollmentEngine.db.employee);
        Injector.setConfigurationSource(context::get);


        initUI(primaryStage);

        //postInit
        setMainView(dashboardView);

    }

    private void initUI(Stage primaryStage) {
        initMainStage(primaryStage);
        initDashboard();
        initAdminPanel();
        primaryStage.show();
    }

    private void initMainStage(Stage primaryStage) {
        System.out.println("Initializing main stage");
        mainStage = primaryStage;
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        mainScene = new Scene(new Pane());
        mainScene.getStylesheets().add(Launcher.class.getResource("/css/root.css").toString());
        primaryStage.setScene(mainScene);
    }

    private void initDashboard() {
        FXMLView container = new DashboardView();
        dashboardView = container.getView();
        dashboardPresenter = (DashboardPresenter) container.getPresenter();
        dashboardPresenter.setOnExitButtonPressed(e -> mainStage.close());
        dashboardPresenter.setOnOpenAdminPanelButtonPressed(e -> setMainView(adminPanelView));
    }

    private void initAdminPanel() {
        FXMLView container = new AdminPanelView();
        adminPanelView = container.getView();
        adminPanelPresenter = (AdminPanelPresenter) container.getPresenter();
        adminPanelPresenter.setOnBackButtonPressed(e -> setMainView(dashboardView));
    }

    private void setMainView(Parent view) {
        mainScene.setRoot(view);
    }

    private void initEngine() {
        enrollmentEngine = new EnrollmentEngine(DATABASE_FILE);
        enrollmentEngine.setOnSqlError(e -> System.out.println(e.getMessage()));
    }
}
