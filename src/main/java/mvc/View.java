package mvc;

import ui.InfoPanel;
import ui.MenuBar;
import ui.NetworkPanel;
import ui.TransitionPanel;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Model model;

    private String title;
    private MenuBar frameMenuBar;

    public NetworkPanel getNetworkPanel() {
        return networkPanel;
    }

    private NetworkPanel networkPanel;
    private TransitionPanel transitionPanel;
    private InfoPanel infoPanel;
    private JMenuItem openMenuItem;
    private JButton exportButton;

    public View(String title, Model m) {
        this.model = m;
        this.title = title;
        this.frameMenuBar = new MenuBar();
        this.networkPanel = new NetworkPanel(model.getNetwork(), 0);
        this.exportButton = new JButton("Export State Graph");
        this.transitionPanel = new TransitionPanel(model.getNetwork().getTransitions(), this.exportButton);
        this.infoPanel = new InfoPanel(model.getNetwork());
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

        pack();
        setResizable(false);
        setVisible(true);
    }

    public String getTitle() {
        return title;
    }

    public JPanel getTransitionPanel() {
        return transitionPanel;
    }

    public void setTransitionPanel(TransitionPanel transitionPanel) {
        remove(this.transitionPanel);
        this.transitionPanel = transitionPanel;
        add(this.transitionPanel, BorderLayout.EAST);
        revalidate();
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public JButton getExportButton() { return exportButton; }

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
}
