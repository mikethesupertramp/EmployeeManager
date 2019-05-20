package me.mikethesupertramp.employeemanager.presentation.dashboard;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import me.mikethesupertramp.employeemanager.domain.EnrollmentEngine;
import me.mikethesupertramp.employeemanager.persistence.Employee;
import me.mikethesupertramp.employeemanager.persistence.EmployeeDao;
import me.mikethesupertramp.employeemanager.presentation.dashboard.employee.EmployeeView;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class DashboardPresenter implements Initializable {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE MMM dd");

    @FXML
    private Button bAdmin;
    @FXML
    private Button bExit;
    @FXML
    private Label lDate;
    @FXML
    private Label lClock;
    @FXML
    private TilePane cDashboard;

    @Inject
    EnrollmentEngine enrollmentEngine;
    @Inject
    EmployeeDao employeeDao;

    private EventHandler<ActionEvent> onExitButtonPressedHandler;
    private EventHandler<ActionEvent> onOpenAdminPanelButtonPressedHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            lClock.setText(LocalTime.now().format(timeFormatter));
            lDate.setText(LocalDate.now().format(dateFormatter).toUpperCase());
        }));
        timeline.play();

        employeeDao.addListener(e -> refresh());
        refresh();
    }

    public void refresh() {
        List<Employee> employees = employeeDao.getAll();
        Iterator<Employee> employeeIterator = employees.iterator();
        ListIterator<Node> employeeViewIterator = cDashboard.getChildren().listIterator();

        while (employeeIterator.hasNext()) {
            if (employeeViewIterator.hasNext()) {
                //Update existing view
                EmployeeView employeeView = (EmployeeView) employeeViewIterator.next();
                employeeView.setEmployee(employeeIterator.next());
            } else {
                //Create new view if required
                EmployeeView employeeView = new EmployeeView();
                employeeView.setEmployee(employeeIterator.next());
                employeeViewIterator.add(employeeView);
            }
        }
        //Remove extra views
        while (employeeViewIterator.hasNext()) {
            employeeViewIterator.next();
            employeeViewIterator.remove();
        }
    }

    public void onAdminPanelButtonPressed(ActionEvent e) {
        if (onOpenAdminPanelButtonPressedHandler != null) onOpenAdminPanelButtonPressedHandler.handle(e);
    }

    public void onExitButtonPressed(ActionEvent e) {
        if (onExitButtonPressedHandler != null) onExitButtonPressedHandler.handle(e);
    }

    public void setOnExitButtonPressed(EventHandler<ActionEvent> listener) {
        this.onExitButtonPressedHandler = listener;
    }

    public void setOnOpenAdminPanelButtonPressed(EventHandler<ActionEvent> listener) {
        this.onOpenAdminPanelButtonPressedHandler = listener;
    }


}
