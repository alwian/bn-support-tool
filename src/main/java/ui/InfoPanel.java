package ui;

import network.BooleanNetwork;
import network.State;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class InfoPanel extends JPanel {
    BooleanNetwork network;

    public InfoPanel(BooleanNetwork network) {
        this.network = network;
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.black);
        setPreferredSize(new Dimension(100,200));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Trace", createTraceTab());
        tabbedPane.addTab("Attractors", createAttractorTab());

        add(tabbedPane);
    }

    private JPanel createTraceTab() {
        return new JPanel();
    }

    private JPanel createAttractorTab() {
        JPanel tab = new JPanel();
        tab.setLayout(new BorderLayout());
        tab.setBorder(createBorder("The current network contains the following attractors"));

        JList attractorsList = extractAttractors();
        tab.add(attractorsList);

        return tab;
    }

    private JList extractAttractors() {
        String[] lines = new String[network.getAttractors().size()];
        for (int x = 0; x < network.getAttractors().size(); x++) {
            StringBuilder line = new StringBuilder();
            line.append("Attractor ").append(x).append(": ");

            for (int y = 0; y < network.getAttractors().get(x).size(); y++) {
                line.append(network.getAttractors().get(x).get(y));
                if (network.getAttractors().get(x).size() - y > 1) {
                    line.append(" -> ");
                }
            }

            lines[x] = line.toString();
        }
        return new JList(lines);
    }

    private TitledBorder createBorder(String title) {
        Border solidBorder = BorderFactory.createLineBorder(Color.white);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(solidBorder, title);
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        return titledBorder;
    }
}
