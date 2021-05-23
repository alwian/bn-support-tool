package ui;

import network.BooleanNetwork;
import network.State;
import network.Trace;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * The panel for displaying traces and attractors.
 * @author Alex Anderson
 */
public class InfoPanel extends JPanel {

    /**
     * The currently loaded network.
     */
    private final BooleanNetwork network;

    /**
     * The currently selected tab.
     */
    private final int selectedTab;

    /**
     * The currently selected attractor index.
     */
    private int selectedAttractor;

    /**
     * The attractors being displayed.
     */
    private JList attractorsList;

    /**
     * The tabs on the panel.
     */
    private JTabbedPane tabs;

    /**
     * Constructor for an info panel.
     * @param network the currently loaded network.
     * @param selectedTab The tab which should be selected.
     * @param selectedAttractor The attractor which should be selected.
     */
    public InfoPanel(final BooleanNetwork network, final int selectedTab, final int selectedAttractor) {
        this.network = network;
        this.selectedTab = selectedTab;
        this.selectedAttractor = selectedAttractor;
        build();
    }

    /**
     * Getter for the currently selected attractor.
     * @param selectedAttractor The selected attractor.
     */
    public void setSelectedAttractor(final int selectedAttractor) {
        this.selectedAttractor = selectedAttractor;
    }

    /**
     * Getter for the currently selected attractor.
     * @return The index of the currently selected attractor.
     */
    public int getSelectedAttractor() {
        return this.selectedAttractor;
    }

    /**
     * Getter for displayed attractors.
     * @return The displayed attractors.
     */
    public JList getAttractorsList() {
        return attractorsList;
    }

    /**
     * Getter for the tabs on the panel.
     * @return The tabs on the panel.
     */
    public JTabbedPane getTabs() {
        return tabs;
    }

    /**
     * Puts the panel together.
     */
    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.black);
        setPreferredSize(new Dimension(100, 200));

        tabs = new JTabbedPane();
        tabs.addTab("Trace", createTraceTab());
        tabs.addTab("Attractors", createAttractorTab());

        tabs.setSelectedIndex(this.selectedTab);
        add(tabs);
    }

    /**
     * Creates a table of the networks current trace.
     * @return The tab containing the trace.
     */
    private JPanel createTraceTab() {
        JPanel panel = new JPanel(new GridLayout());

        Trace trace = network.getCurrentTrace();
        int length = trace.getTrace().size();

        // Add the column headings to the table.
        String[] columns = new String[length + 1];
        columns[0] = "Node";
        for (int x = 0; x < length; x++) {
            columns[x + 1] = "t = " + x;
        }

        // Add the data to the table.
        Object[][] data = new Object[network.getNodes().length][length + 1];
        for (int x = 0; x < network.getNodes().length; x++) {
            data[x][0] = network.getNodes()[x];
            for (int y = 0; y < trace.getTrace().size(); y++) {
                State state = trace.getTrace().get(y);
                data[x][y + 1] = state.getNodeStates()[network.getNodeIndexes().get(network.getNodes()[x])];
            }
        }


        // Create the table and disable editing.
        JTable table = new JTable(data, columns);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int x = 0; x < table.getColumnCount(); x++) {
            table.setDefaultRenderer(table.getColumnClass(x), cellRenderer);
        }
        table.setEnabled(false);

        // Put the table in a scroll pane and return it.
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        return panel;
    }

    /**
     * Creates the attractor tab with a list of attractors.
     * @return The attractor tab.
     */
    private JPanel createAttractorTab() {
        JPanel tab = new JPanel();
        tab.setLayout(new BorderLayout());
        tab.setBorder(createBorder());

        // Create and return the list of attractors.
        JList attractorsList = extractAttractors();
        this.attractorsList = attractorsList;
        tab.add(attractorsList);

        return tab;
    }

    /**
     * Creates a list of attractors for the current network.
     * @return A list of attractors for the current state.
     */
    private JList extractAttractors() {
        String[] lines = new String[network.getAttractors().size()];

        // Go through all attractors.
        for (int x = 0; x < network.getAttractors().size(); x++) {
            StringBuilder line = new StringBuilder();
            line.append("Attractor ").append(x).append(": ");

            // Add each state of the current attractor, separated by an arrow.
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

    /**
     * Creates the border for the panel.
     * @return The border for the panel.
     */
    private TitledBorder createBorder() {
        Border solidBorder = BorderFactory.createLineBorder(Color.white);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(solidBorder, "The current network contains the following attractors");
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        return titledBorder;
    }
}
