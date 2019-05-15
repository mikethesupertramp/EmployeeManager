package me.mikethesupertramp.employeemanager.ui.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import me.mikethesupertramp.employeemanager.ui.SidebarMenuCell;
import me.mikethesupertramp.employeemanager.ui.SidebarMenuItem;

public class AdminPanelController {
    public ListView lvMenuItems;
    private EventHandler<ActionEvent> onBackButtonPressedListener;

    @FXML
    private void initialize() {
        lvMenuItems.setCellFactory(view -> new SidebarMenuCell());
        lvMenuItems.getItems().addAll(
                new SidebarMenuItem(null, FontAwesomeIcon.GROUP, "თანამშრომლები"),
                new SidebarMenuItem(null, FontAwesomeIcon.HOURGLASS_2, "განრიგი"),
                new SidebarMenuItem(null, FontAwesomeIcon.FILE_TEXT, "დღიური ანგარიში"),
                new SidebarMenuItem(null, FontAwesomeIcon.CALENDAR_CHECK_ALT, "თვიური ანგარიში"),
                new SidebarMenuItem(null, FontAwesomeIcon.COG, "პარამეტრები")
        );
    }


    public void onBackButtonPressed(ActionEvent e) {
        if (onBackButtonPressedListener != null) onBackButtonPressedListener.handle(e);
    }

    public void setOnBackButtonPressed(EventHandler<ActionEvent> listener) {
        this.onBackButtonPressedListener = listener;
    }
}
