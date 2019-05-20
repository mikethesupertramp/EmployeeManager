package me.mikethesupertramp.employeemanager.presentation.adminpanel;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.ListCell;

public class SidebarMenuCell extends ListCell<SidebarMenuItem> {

    @Override
    protected void updateItem(SidebarMenuItem item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty || item != null) {
            setText(item.getText());
            setGraphic(new FontAwesomeIconView(item.getIcon()));
        }
    }
}
