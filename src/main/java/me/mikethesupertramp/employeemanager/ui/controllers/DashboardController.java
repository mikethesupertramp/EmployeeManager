package me.mikethesupertramp.employeemanager.ui.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import me.mikethesupertramp.employeemanager.dao.Employee;
import me.mikethesupertramp.employeemanager.ui.controls.EmployeeView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

public class DashboardController {
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE MMM dd");
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

    private EventHandler<ActionEvent> onExitButtonPressedListener;
    private EventHandler<ActionEvent> onOpenAdminPanelButtonPressedListener;

    @FXML
    private void initialize() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            lClock.setText(LocalTime.now().format(timeFormatter));
            lDate.setText(LocalDate.now().format(dateFormatter).toUpperCase());
        }));
        timeline.play();
        bAdmin.disarm();
        bExit.disarm();
        lDate.requestFocus();
    }

    public void updateEmployees(Set<Employee> employees) {
        Iterator<Employee> employeeIterator = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getId))
                .iterator();

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
            EmployeeView view = (EmployeeView) employeeViewIterator.next();
            Platform.runLater(() -> {
                cDashboard.getChildren().remove(view);
            });
        }
    }

    public void onAdminPanelButtonPressed(ActionEvent e) {
        if (onOpenAdminPanelButtonPressedListener != null) onOpenAdminPanelButtonPressedListener.handle(e);
    }

    public void onExitButtonPressed(ActionEvent e) {
        if (onExitButtonPressedListener != null) onExitButtonPressedListener.handle(e);
    }

    public void setOnExitButtonPressed(EventHandler<ActionEvent> listener) {
        this.onExitButtonPressedListener = listener;
    }

    public void setOnOpenAdminPanelButtonPressed(EventHandler<ActionEvent> listener) {
        this.onOpenAdminPanelButtonPressedListener = listener;
    }
}
