package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    public JMenuItem getExitOption() {
        return exitOption;
    }

    public JMenuItem getOpenOption() {
        return openOption;
    }

    JMenuItem exitOption;
    JMenuItem openOption;

    public MenuBar() {
        createFileMenu();
    }

    private void createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        exitOption = new JMenuItem("Exit");
        openOption = new JMenuItem("Open");

        fileMenu.add(openOption);
        fileMenu.add(openOption);
        add(fileMenu);
    }
}
