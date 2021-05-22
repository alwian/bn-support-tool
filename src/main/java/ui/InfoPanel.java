package ui;

import network.BooleanNetwork;
import network.State;
import network.Trace;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;

public class InfoPanel extends JPanel {
    BooleanNetwork network;

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    int selectedTab;

    public void setSelectedAttractor(int selectedAttractor) {
        this.selectedAttractor = selectedAttractor;
    }

    private int selectedAttractor;

    public JList getAttractorsList() {
        return attractorsList;
    }

    private JList attractorsList;

    public JTabbedPane getTabs() {
        return tabs;
    }

    JTabbedPane tabs;

    public InfoPanel(BooleanNetwork network, int selectedTab, int selectedAttractor) {
        this.network = network;
        this.selectedTab = selectedTab;
        this.selectedAttractor = selectedAttractor;
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.black);
        setPreferredSize(new Dimension(100,200));

        tabs = new JTabbedPane();
        tabs.addTab("Trace", createTraceTab());
        tabs.addTab("Attractors", createAttractorTab());

        tabs.setSelectedIndex(this.selectedTab);
        add(tabs);
    }

    private JPanel createTraceTab() {
        System.out.println("Making table");
        JPanel panel = new JPanel(new GridLayout());

        Trace trace = network.getCurrentTrace();
        int length = trace.getTrace().size();

        System.out.println(trace);
        String[] columns = new String[length + 1];
        columns[0] = "Node";
        for (int x = 0; x < length; x++) {
            columns[x + 1] = "t = " + x;
            System.out.println("Added Column - t = " + x);
        }
        System.out.println(Arrays.toString(columns));

        Object[][] data = new Object[network.getNodes().length][length + 1];
        for (int x = 0; x < network.getNodes().length; x++) {
            data[x][0] = network.getNodes()[x];
            for (int y = 0; y < trace.getTrace().size(); y++) {
                State state = trace.getTrace().get(y);
                data[x][y + 1] = state.getNodeStates()[network.getNodeIndexes().get(network.getNodes()[x])];
            }
        }

        for (Object[] row : data) {
            System.out.println(Arrays.toString(row));
        }

        JTable table = new JTable(data, columns);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int x = 0; x < table.getColumnCount(); x++) {
            table.setDefaultRenderer(table.getColumnClass(x), cellRenderer);
        }
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        return panel;
    }

    private JPanel createAttractorTab() {
        JPanel tab = new JPanel();
        tab.setLayout(new BorderLayout());
        tab.setBorder(createBorder("The current network contains the following attractors"));

        JList attractorsList = extractAttractors();
        this.attractorsList = attractorsList;
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

    public int getSelectedAttractor() {
        return this.selectedAttractor;
    }
}
