package mvc;

import ui.InfoPanel;
import ui.MenuBar;
import ui.NetworkPanel;
import ui.ModifierPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class View extends JFrame {
    private Model model;

    private String title;

    public void setFrameMenuBar(MenuBar frameMenuBar) {
        remove(this.frameMenuBar);
        this.frameMenuBar = frameMenuBar;
        setJMenuBar(this.frameMenuBar);
        revalidate();
    }

    public MenuBar getFrameMenuBar() {
        return frameMenuBar;
    }

    private MenuBar frameMenuBar;

    public NetworkPanel getNetworkPanel() {
        return networkPanel;
    }

    private NetworkPanel networkPanel;
    private ModifierPanel modifierPanel;

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    private InfoPanel infoPanel;

    public View(String title, Model m) {
        this.model = m;
        this.title = title;
        this.frameMenuBar = new MenuBar();
        this.infoPanel = new InfoPanel(model.getNetwork(), 0, -1);
        this.networkPanel = new NetworkPanel(model.getNetwork(), 0, infoPanel.getSelectedAttractor());

        Map<String, Integer> buttonStates = new HashMap<>();
        for (String node : model.getNetwork().getNodes()) {
            buttonStates.put(node,0);
        }
        this.modifierPanel = new ModifierPanel(model.getNetwork(), buttonStates);

        setTitle(this.title);
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        setJMenuBar(this.frameMenuBar);

        add(this.networkPanel, BorderLayout.CENTER);
        add(this.infoPanel, BorderLayout.SOUTH);
        add(this.modifierPanel, BorderLayout.EAST);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public String getTitle() {
        return title;
    }

    public ModifierPanel getModifierPanel() {
        return modifierPanel;
    }

    public void setInfoPanel(InfoPanel infoPanel) {
        remove(this.infoPanel);
        this.infoPanel = infoPanel;
        add(this.infoPanel, BorderLayout.SOUTH);
        revalidate();
    }

    public void setNetworkPanel(NetworkPanel networkPanel) {
        remove(this.networkPanel);
        this.networkPanel = networkPanel;
        add(this.networkPanel, BorderLayout.CENTER);
        revalidate();
    }

    public void setModifierPanel(ModifierPanel modifierPanel) {
        remove(this.modifierPanel);
        this.modifierPanel = modifierPanel;
        add(this.modifierPanel, BorderLayout.EAST);
        revalidate();
    }
}
