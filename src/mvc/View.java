package mvc;

import network.BooleanNetwork;
import network.NetworkCreationException;
import network.NetworkTraceException;
import ui.InfoPanel;
import ui.MenuBar;
import ui.NetworkPanel;
import ui.TransitionPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class View extends JFrame {
    private String title;
    private JMenuBar frameMenuBar;
    private JPanel networkPanel;
    private JPanel transitionPanel;
    private JPanel infoPanel;
    private JMenuItem openMenuItem;

    public View(String title) {
        this.title = title;
        this.frameMenuBar = createMenuBar();
        this.networkPanel = createNetworkPanel();
        this.transitionPanel = createTransitionPanel();
        this.infoPanel = createInfoPanel();
        this.openMenuItem = createOpenMenuItem();

        setTitle(this.title);
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000,800));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        setJMenuBar(this.frameMenuBar);
        this.frameMenuBar.getMenu(0).add(this.openMenuItem);

        add(this.networkPanel, BorderLayout.CENTER);
        add(this.infoPanel, BorderLayout.SOUTH);
        add(this.transitionPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public JMenuBar createMenuBar() {
        return new MenuBar();
    }

    public JPanel createNetworkPanel() {
        return new NetworkPanel();
    }

    public JPanel createTransitionPanel() {
        return new TransitionPanel();
    }

    public JPanel createInfoPanel() {
        return new InfoPanel();
    }

    public JMenuItem createOpenMenuItem() {
        return new JMenuItem("Open");
    }

    public String getTitle() {
        return title;
    }

    public JMenuBar getFrameMenuBar() {
        return frameMenuBar;
    }

    public JPanel getNetworkPanel() {
        return networkPanel;
    }

    public JPanel getTransitionPanel() {
        return transitionPanel;
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }
}
