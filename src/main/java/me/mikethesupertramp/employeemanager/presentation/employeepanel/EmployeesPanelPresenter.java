package me.mikethesupertramp.employeemanager.presentation.employeepanel;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import me.mikethesupertramp.employeemanager.persistence.Employee;
import me.mikethesupertramp.employeemanager.persistence.EmployeeDao;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;


public class EmployeesPanelPresenter implements Initializable {
    @Inject
    EmployeeDao employeeDao;
    @FXML
    private TableView<Employee> tEmployeesTable;
    @FXML
    private TableColumn<Employee, String> firstNameColumn;
    @FXML
    private TableColumn<Employee, String> lastNameColumn;
    @FXML
    private TableColumn<Employee, String> statusColumn;
    @FXML
    private TableColumn<Employee, String> lateColumn;
    @FXML
    private TableColumn<Employee, String> cardID1Column;
    @FXML
    private TableColumn<Employee, String> cardID2Column;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        statusColumn.setCellValueFactory(param -> {
            String s = param.getValue().isPresent() ? "კი" : "არა";
            return new SimpleStringProperty(s);
        });

        lateColumn.setCellValueFactory(param -> {
            String s = param.getValue().isLate() ? "კი" : "არა";
            return new SimpleStringProperty(s);
        });

        cardID1Column.setCellValueFactory(new PropertyValueFactory<>("cardID1"));
        cardID2Column.setCellValueFactory(new PropertyValueFactory<>("cardID2"));

        employeeDao.addListener(e -> refresh());
        refresh();
    }

    private void refresh() {
        tEmployeesTable.getItems().clear();
        tEmployeesTable.getItems().addAll(employeeDao.getAll());
    }
}
