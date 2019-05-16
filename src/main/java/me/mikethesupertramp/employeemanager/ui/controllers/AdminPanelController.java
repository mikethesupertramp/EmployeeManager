package me.mikethesupertramp.employeemanager.ui.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import me.mikethesupertramp.employeemanager.ui.SidebarMenuCell;
import me.mikethesupertramp.employeemanager.ui.SidebarMenuItem;
import me.mikethesupertramp.employeemanager.ui.panels.Displayable;
import me.mikethesupertramp.employeemanager.ui.panels.EmployeesPanel;

public class AdminPanelController {
    public final EmployeesPanel employeesPanel;

    @FXML
    public AnchorPane contentBox;
    @FXML
    private ListView<SidebarMenuItem> lvMenuItems;
    private EventHandler<ActionEvent> onBackButtonPressedListener;

    public AdminPanelController() {
        this.employeesPanel = new EmployeesPanel();
    }

    @FXML
    private void initialize() {
        lvMenuItems.setCellFactory(listView -> new SidebarMenuCell());
        lvMenuItems.getItems().addAll(
                new SidebarMenuItem(employeesPanel, FontAwesomeIcon.GROUP, "თანამშრომლები"),
                new SidebarMenuItem(null, FontAwesomeIcon.HOURGLASS_2, "განრიგი"),
                new SidebarMenuItem(null, FontAwesomeIcon.FILE_TEXT, "დღიური ანგარიში"),
                new SidebarMenuItem(null, FontAwesomeIcon.CALENDAR_CHECK_ALT, "თვიური ანგარიში"),
                new SidebarMenuItem(null, FontAwesomeIcon.COG, "პარამეტრები")
        );

        lvMenuItems.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> setPanel(newValue.getPanel()));
    }

    private void setPanel(Parent panel) {
        contentBox.getChildren().filtered(e -> e instanceof Displayable).forEach(e -> ((Displayable) e).onClosing());
        contentBox.getChildren().clear();
        if (panel instanceof Displayable) ((Displayable) panel).onShowing();
        contentBox.getChildren().add(panel);
    }


    public void onBackButtonPressed(ActionEvent e) {
        if (onBackButtonPressedListener != null) onBackButtonPressedListener.handle(e);
    }

    public void setOnBackButtonPressed(EventHandler<ActionEvent> listener) {
        this.onBackButtonPressedListener = listener;
    }
}
