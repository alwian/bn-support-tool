package ui;

import network.BooleanNetwork;
import network.State;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifierPanel extends JPanel {
    BooleanNetwork network;

    public Map<String, Integer> getButtonStates() {
        return buttonStates;
    }

    private Map<String, Integer> buttonStates;

    public List<Object[]> getModifierRows() {
        return modifierRows;
    }

    private List<Object[]> modifierRows;

    public ModifierPanel(BooleanNetwork network, Map<String, Integer> buttonStates) {
        this.buttonStates = buttonStates;
        this.network = network;
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400,100));
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
        modifierRows = new ArrayList<>();

        JPanel containerPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new GridLayout(0,4));
        JLabel nodeHeading = new JLabel("Node", SwingConstants.CENTER);
        JLabel noneHeading = new JLabel("None", SwingConstants.CENTER);
        JLabel overHeading = new JLabel("Overexpress", SwingConstants.CENTER);
        JLabel knockHeading = new JLabel("Knock-Out", SwingConstants.CENTER);

        nodeHeading.setFont(nodeHeading.getFont().deriveFont(16f));
        noneHeading.setFont(noneHeading.getFont().deriveFont(16f));
        overHeading.setFont(overHeading.getFont().deriveFont(16f));
        knockHeading.setFont(knockHeading.getFont().deriveFont(16f));

        headerPanel.add(nodeHeading);
        headerPanel.add(noneHeading);
        headerPanel.add(overHeading);
        headerPanel.add(knockHeading);

        containerPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0,4));


        for (Map.Entry entry : network.getModifiers().entrySet()) {
            System.out.println(entry);
            Object[] rowItems = new Object[4];
            rowItems[0] = entry.getKey();

            JLabel nodeName = new JLabel((String) entry.getKey(), SwingConstants.CENTER);
            nodeName.setFont(nodeName.getFont().deriveFont(16f));

            JRadioButton noneRadio = new JRadioButton();
            noneRadio.setHorizontalAlignment(SwingConstants.CENTER);
            rowItems[1] = noneRadio;
            noneRadio.setSelected(buttonStates.get((String) entry.getKey()) == 0);

            JRadioButton overRadio = new JRadioButton();
            overRadio.setHorizontalAlignment(SwingConstants.CENTER);
            rowItems[2] = overRadio;
            overRadio.setSelected(buttonStates.get((String) entry.getKey()) == 1);

            JRadioButton knockRadio = new JRadioButton();
            knockRadio.setHorizontalAlignment(SwingConstants.CENTER);
            rowItems[3] = knockRadio;
            knockRadio.setSelected(buttonStates.get((String) entry.getKey()) == -1);

            ButtonGroup radioGroup = new ButtonGroup();
            radioGroup.add(noneRadio);
            radioGroup.add(overRadio);
            radioGroup.add(knockRadio);

            grid.add(nodeName);
            grid.add(noneRadio);
            grid.add(overRadio);
            grid.add(knockRadio);

            modifierRows.add(rowItems);
        }

        containerPanel.add(grid);

        return containerPanel;
    }
}
