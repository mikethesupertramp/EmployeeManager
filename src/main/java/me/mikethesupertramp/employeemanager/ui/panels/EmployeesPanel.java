package me.mikethesupertramp.employeemanager.ui.panels;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import me.mikethesupertramp.employeemanager.ui.controls.EmployeeView;

import java.io.IOException;

public class EmployeesPanel extends VBox implements Displayable {
    public EmployeesPanel() {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeView.class.getResource(
                "/fxml/employees-panel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShowing() {

    }

    @Override
    public void onClosing() {

    }
}
