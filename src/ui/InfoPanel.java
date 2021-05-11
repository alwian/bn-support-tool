package ui;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public InfoPanel() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.black);
        setPreferredSize(new Dimension(100,200));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Trace", createTraceTab());
        tabbedPane.addTab("Attractor", createAttractorTab());

        add(tabbedPane);
    }

    private JPanel createTraceTab() {
        return new JPanel();
    }

    private JPanel createAttractorTab() {
        return new JPanel();
    }
}
