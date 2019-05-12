package me.mikethesupertramp.employeemanager.ui.controls;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import me.mikethesupertramp.employeemanager.dao.Employee;

import java.io.IOException;


public class EmployeeView extends HBox {
    private static final String STYLE_GREEN = "-fx-fill: -green-color;";
    private static final String STYLE_RED = "-fx-fill: -red-color;";
    private static final String STYLE_GOLD = "-fx-fill: -gold-color;";
    private static final String STYLE_DISABLED = "-fx-fill: -disabled-color;";

    @FXML
    private Label lEmployeeName;
    @FXML
    private FontAwesomeIconView icStatusIndicator;
    @FXML
    private FontAwesomeIconView icTimeIndicator;
    private Employee employee;

    public EmployeeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeView.class.getResource(
                "/fxml/employee-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        lEmployeeName.setText(employee.getFirstName() + " " + employee.getLastName());
        icStatusIndicator.setStyle(employee.isPresent() ? STYLE_GREEN : STYLE_RED);
        icTimeIndicator.setStyle(employee.isLate() ? STYLE_GOLD : STYLE_DISABLED);
    }
}
