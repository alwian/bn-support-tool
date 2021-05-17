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
    private Model model;

    private String title;
    private JMenuBar frameMenuBar;
    private JPanel networkPanel;
    private JPanel transitionPanel;
    private JPanel infoPanel;
    private JMenuItem openMenuItem;
    private JButton exportButton;

    public View(String title, Model m) {
        this.model = m;
        this.title = title;
        this.frameMenuBar = new MenuBar();
        this.networkPanel = new NetworkPanel();
        this.exportButton = new JButton("Export State Graph");
        this.transitionPanel = new TransitionPanel(model.getNetwork().getTransitions(), this.exportButton);
        this.infoPanel = new InfoPanel();
        this.openMenuItem = new JMenuItem("Open");

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

    public String getTitle() {
        return title;
    }

    public JPanel getTransitionPanel() {
        return transitionPanel;
    }

    public void setTransitionPanel(JPanel transitionPanel) {
        remove(this.transitionPanel);
        this.transitionPanel = transitionPanel;
        add(this.transitionPanel, BorderLayout.EAST);
        revalidate();
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public JButton getExportButton() { return exportButton; }
}
