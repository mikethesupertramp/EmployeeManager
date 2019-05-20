package me.mikethesupertramp.employeemanager.presentation.adminpanel;

import com.airhacks.afterburner.views.FXMLView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import me.mikethesupertramp.employeemanager.presentation.employeepanel.EmployeesPanelView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelPresenter implements Initializable {
    private Parent currentView;

    @FXML
    public AnchorPane contentBox;
    @FXML
    private ListView<SidebarMenuItem> lvMenuItems;

    private EventHandler<ActionEvent> onBackButtonPressedListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvMenuItems.setCellFactory(listView -> new SidebarMenuCell());
        FXMLView c1 = new EmployeesPanelView();

        lvMenuItems.getItems().addAll(
                new SidebarMenuItem(c1.getView(), FontAwesomeIcon.GROUP, "თანამშრომლები"),
                new SidebarMenuItem(null, FontAwesomeIcon.HOURGLASS_2, "განრიგი"),
                new SidebarMenuItem(null, FontAwesomeIcon.CALENDAR_CHECK_ALT, "ანგარიში"),
                new SidebarMenuItem(null, FontAwesomeIcon.COG, "პარამეტრები")
        );

        lvMenuItems.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> setView(newValue.getView()));
    }

    private void setView(Parent view) {
        if (currentView != null) {
            contentBox.getChildren().remove(currentView);
        }

        if (view == null) return; //todo remove


        fitView(view);
        contentBox.getChildren().add(view);

        currentView = view;
    }

    private void fitView(Parent view) {
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
    }


    public void onBackButtonPressed(ActionEvent e) {
        if (onBackButtonPressedListener != null) onBackButtonPressedListener.handle(e);
    }

    public void setOnBackButtonPressed(EventHandler<ActionEvent> listener) {
        this.onBackButtonPressedListener = listener;
    }
}
