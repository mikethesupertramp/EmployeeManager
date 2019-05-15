package me.mikethesupertramp.employeemanager.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.Parent;

public class SidebarMenuItem {
    private Parent root;
    private FontAwesomeIcon icon;
    private String text;

    public SidebarMenuItem(Parent root, FontAwesomeIcon icon, String text) {
        this.root = root;
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

    public Parent getRoot() {
        return root;
    }
}
