package mvc;

import ui.InfoPanel;
import ui.MenuBar;
import ui.NetworkPanel;
import ui.ModifierPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The UI for the tool.
 * @author Alex Anderson
 */
public class View extends JFrame {

    /**
     * The title of the tool.
     */
    private final String title;

    /**
     * The menu bar of the tool.
     */
    private final MenuBar frameMenuBar;

    /**
     * The graph / description section of the tool.
     */
    private NetworkPanel networkPanel;

    /**
     * The panel for over-expression and knock-out.
     */
    private ModifierPanel modifierPanel;

    /**
     * The trace and attractor panel of the tool.
     */
    private InfoPanel infoPanel;

    /**
     * Constructor the UI.
     * @param title The title of the tool.
     * @param m The model to use for the tool.
     */
    public View(String title, Model m) {
        this.title = title;

        // Create UI components.
        this.frameMenuBar = new MenuBar();
        this.infoPanel = new InfoPanel(m.getNetwork(), 0, -1);
        this.networkPanel = new NetworkPanel(m.getNetwork(), 0, infoPanel.getSelectedAttractor());

        Map<String, Integer> buttonStates = new HashMap<>();
        for (String node : m.getNetwork().getNodes()) {
            buttonStates.put(node,0);
        }
        this.modifierPanel = new ModifierPanel(m.getNetwork(), buttonStates);

        // Put the UI together.
        setTitle(this.title);
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setJMenuBar(this.frameMenuBar);

        add(this.networkPanel, BorderLayout.CENTER);
        add(this.infoPanel, BorderLayout.SOUTH);
        add(this.modifierPanel, BorderLayout.EAST);

        pack();
        setVisible(true);
    }

    /**
     * Getter for title.
     * @return The tool title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the menu bar.
     * @return The tool menu bar.
     */
    public MenuBar getFrameMenuBar() {
        return frameMenuBar;
    }

    /**
     * Getter for the network panel.
     * @return The tool network panel.
     */
    public NetworkPanel getNetworkPanel() {
        return networkPanel;
    }

    /**
     * Getter for the modifier panel.
     * @return The tool modifier panel.
     */
    public ModifierPanel getModifierPanel() {
        return modifierPanel;
    }

    /**
     * Getter for the info panel.
     * @return The tool info panel.
     */
    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * Setter for the network panel to replace it.
     * @param networkPanel The new panel.
     */
    public void setNetworkPanel(NetworkPanel networkPanel) {
        remove(this.networkPanel);
        this.networkPanel = networkPanel;
        add(this.networkPanel, BorderLayout.CENTER);
        revalidate();
    }

    /**
     * Setter for the modifier panel to replace it.
     * @param modifierPanel The new panel.
     */
    public void setModifierPanel(ModifierPanel modifierPanel) {
        remove(this.modifierPanel);
        this.modifierPanel = modifierPanel;
        add(this.modifierPanel, BorderLayout.EAST);
        revalidate();
    }

    /**
     * Setter for the info panel to replace it.
     * @param infoPanel The new panel.
     */
    public void setInfoPanel(InfoPanel infoPanel) {
        remove(this.infoPanel);
        this.infoPanel = infoPanel;
        add(this.infoPanel, BorderLayout.SOUTH);
        revalidate();
    }
}
