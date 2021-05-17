package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        createFileMenu();
    }

    private void createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createExitOption());
        add(fileMenu);
    }

    private JMenuItem createExitOption() {
        JMenuItem exitOption = new JMenuItem("Exit");
        exitOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return exitOption;
    }
}
