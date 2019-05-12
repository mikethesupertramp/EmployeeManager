package me.mikethesupertramp.employeemanager.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import me.mikethesupertramp.employeemanager.dao.Employee;
import me.mikethesupertramp.employeemanager.ui.controls.EmployeeView;

public class DashboardController {

    public Label lClock;
    public TilePane cDashboard;


    @FXML
    private void initialize() {

        Employee testEmployee = new Employee();
        testEmployee.setFirstName("გიორგი");
        testEmployee.setLastName("პაპიძე");
        testEmployee.setPresent(true);
        testEmployee.setLate(true);

        EmployeeView employeeView = new EmployeeView();
        employeeView.setEmployee(testEmployee);
        cDashboard.getChildren().add(employeeView);

    }
}
