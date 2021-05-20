package ui;

import network.BooleanNetwork;
import network.State;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Map;

public class ModifierPanel extends JPanel {
    BooleanNetwork network;
    Map<State, State> transitionTable;
    JButton exportButton;

    public ModifierPanel(BooleanNetwork network) {
        this.network = network;
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.RED);
        setPreferredSize(new Dimension(500,100));
        setBorder(createBorder());

        add(createTable());
    }

    private TitledBorder createBorder() {
        Border solidBorder = BorderFactory.createLineBorder(Color.white);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(solidBorder, "Over Expression/Knock-Outs");
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        return titledBorder;
    }

    private JPanel createTable() {
        JPanel containerPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new GridLayout(0,3));
        JLabel nodeHeading = new JLabel("Node", SwingConstants.CENTER);
        JLabel overHeading = new JLabel("Overexpress", SwingConstants.CENTER);
        JLabel knockHeading = new JLabel("Knock-Out", SwingConstants.CENTER);

        nodeHeading.setFont(nodeHeading.getFont().deriveFont(20f));
        overHeading.setFont(overHeading.getFont().deriveFont(20f));
        knockHeading.setFont(knockHeading.getFont().deriveFont(20f));

        headerPanel.add(nodeHeading);
        headerPanel.add(overHeading);
        headerPanel.add(knockHeading);

        containerPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0,3));

        for (Map.Entry entry : network.getModifiers().entrySet()) {
            JLabel nodeName = new JLabel((String) entry.getKey(), SwingConstants.CENTER);
            nodeName.setFont(nodeName.getFont().deriveFont(20f));

            JRadioButton overRadio = new JRadioButton();
            overRadio.setHorizontalAlignment(SwingConstants.CENTER);
            JRadioButton knockRadio = new JRadioButton();
            knockRadio.setHorizontalAlignment(SwingConstants.CENTER);

            ButtonGroup radioGroup = new ButtonGroup();
            radioGroup.add(overRadio);
            radioGroup.add(knockRadio);

            grid.add(nodeName);
            grid.add(overRadio);
            grid.add(knockRadio);
        }

        containerPanel.add(grid);

        return containerPanel;
    }
}
