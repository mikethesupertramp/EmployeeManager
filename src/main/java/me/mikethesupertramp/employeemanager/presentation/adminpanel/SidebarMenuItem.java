package me.mikethesupertramp.employeemanager.presentation.adminpanel;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.Parent;

public class SidebarMenuItem {
    private Parent view;
    private FontAwesomeIcon icon;
    private String text;

    public SidebarMenuItem(Parent view, FontAwesomeIcon icon, String text) {
        this.view = view;
        this.icon = icon;
        this.text = text;
    }

    public FontAwesomeIcon getIcon() {
        return icon;
    }

    public void setIcon(FontAwesomeIcon icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Parent getView() {
        return view;
    }
}
