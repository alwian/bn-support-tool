package ui;

import javax.swing.*;

/**
 * The menu bar of the tool.
 * @author Alex Anderson
 */
public class MenuBar extends JMenuBar {

    /**
     * The exit menu option.
     */
    private JMenuItem exitOption;

    /**
     * The open menu option.
     */
    private JMenuItem openOption;

    /**
     * The export menu option.
     */
    private JMenuItem exportOption;

    /**
     * Constructor for the menu bar.
     */
    public MenuBar() {
        createFileMenu();
    }

    /**
     * Getter for the exit option.
     * @return The exit option button.
     */
    public JMenuItem getExitOption() {
        return exitOption;
    }

    /**
     * Getter for the open option.
     * @return The open option button.
     */
    public JMenuItem getOpenOption() {
        return openOption;
    }

    /**
     * Getter for the export option.
     * @return The export option button.
     */
    public JMenuItem getExportOption() {
        return exportOption;
    }

    /**
     * Constructs the file menu.
     */
    private void createFileMenu() {
        // Create the menu options.
        JMenu fileMenu = new JMenu("File");
        exitOption = new JMenuItem("Exit");
        openOption = new JMenuItem("Open");
        exportOption = new JMenuItem("Export Graphs");

        // Add them to the menu bar.
        fileMenu.add(openOption);
        fileMenu.add(exportOption);
        fileMenu.add(exitOption);
        add(fileMenu);
    }
}
