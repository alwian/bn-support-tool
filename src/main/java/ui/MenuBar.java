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

    public JMenuItem getExportOption() {
        return exportOption;
    }

    JMenuItem exportOption;

    public MenuBar() {
        createFileMenu();
    }

    private void createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        exitOption = new JMenuItem("Exit");
        openOption = new JMenuItem("Open");
        exportOption = new JMenuItem("Export Graphs");

        fileMenu.add(openOption);
        fileMenu.add(exportOption);
        fileMenu.add(exitOption);
        add(fileMenu);
    }
}
