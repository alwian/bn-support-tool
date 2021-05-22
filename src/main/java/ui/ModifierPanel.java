package ui;

import network.BooleanNetwork;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The panel for applying modifiers to a network.
 * @author Alex Anderson
 */
public class ModifierPanel extends JPanel {

    /**
     * Font size to use on this panel.
     */
    public static final float FONT_SIZE = 16f;

    /**
     * Number of columns on this panel.
     */
    public static final int COLUMNS = 4;

    /**
     * The currently loaded network.
     */
    private final BooleanNetwork network;

    /**
     * The current positions of the buttons on this panel.
     */
    private Map<String, Integer> buttonStates;

    /**
     * The buttons on this panel.
     */
    private List<Object[]> modifierRows;

    /**
     * Constructor for a modifier panel.
     * @param network The currently loaded network.
     * @param buttonStates The buttons states to display.
     */
    public ModifierPanel(final BooleanNetwork network, final Map<String, Integer> buttonStates) {
        this.buttonStates = buttonStates;
        this.network = network;
        build();
    }

    /**
     * Getter for the current button states.
     * @return The current button states.
     */
    public Map<String, Integer> getButtonStates() {
        return buttonStates;
    }

    /**
     * Getter for the buttons on this panel.
     * @return The buttons on the panel.
     */
    public List<Object[]> getModifierRows() {
        return modifierRows;
    }

    /**
     * Setter for the button states.
     * @param buttonStates The desired states.
     */
    public void setButtonStates(final Map<String, Integer> buttonStates) {
        this.buttonStates = buttonStates;
    }

    /**
     * Creates the panel.
     */
    private void build() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 100));
        setBorder(createBorder());

        add(createTable());
    }

    /**
     * Creates the border for panel.
     * @return The border for the panel.
     */
    private TitledBorder createBorder() {
        Border solidBorder = BorderFactory.createLineBorder(Color.white);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(solidBorder, "Over Expression/Knock-Outs");
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        return titledBorder;
    }

    /**
     * Lays out the headings and buttons on the panel.
     * @return The constructed panel.
     */
    private JPanel createTable() {
        modifierRows = new ArrayList<>();

        JPanel containerPanel = new JPanel(new BorderLayout());

        // Create the headings.
        JPanel headerPanel = new JPanel(new GridLayout(0, COLUMNS));
        JLabel nodeHeading = new JLabel("Node", SwingConstants.CENTER);
        JLabel noneHeading = new JLabel("None", SwingConstants.CENTER);
        JLabel overHeading = new JLabel("Overexpress", SwingConstants.CENTER);
        JLabel knockHeading = new JLabel("Knock-Out", SwingConstants.CENTER);

        // Set the heading fonts.
        nodeHeading.setFont(nodeHeading.getFont().deriveFont(FONT_SIZE));
        noneHeading.setFont(noneHeading.getFont().deriveFont(FONT_SIZE));
        overHeading.setFont(overHeading.getFont().deriveFont(FONT_SIZE));
        knockHeading.setFont(knockHeading.getFont().deriveFont(FONT_SIZE));

        // Add the headings to the panel.
        headerPanel.add(nodeHeading);
        headerPanel.add(noneHeading);
        headerPanel.add(overHeading);
        headerPanel.add(knockHeading);

        containerPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, COLUMNS));

        // Go through every node.
        for (Map.Entry entry : network.getModifiers().entrySet()) {
            System.out.println(entry);
            Object[] rowItems = new Object[4];
            rowItems[0] = entry.getKey();

            // Create the node name.
            JLabel nodeName = new JLabel((String) entry.getKey(), SwingConstants.CENTER);
            nodeName.setFont(nodeName.getFont().deriveFont(FONT_SIZE));

            // Create the none button for the current node.
            JRadioButton noneRadio = new JRadioButton();
            noneRadio.setHorizontalAlignment(SwingConstants.CENTER);
            rowItems[1] = noneRadio;
            noneRadio.setSelected(buttonStates.get((String) entry.getKey()) == 0);

            // Create the OE button for the current node.
            JRadioButton overRadio = new JRadioButton();
            overRadio.setHorizontalAlignment(SwingConstants.CENTER);
            rowItems[2] = overRadio;
            overRadio.setSelected(buttonStates.get((String) entry.getKey()) == 1);

            // Create the KO button for the current node.
            JRadioButton knockRadio = new JRadioButton();
            knockRadio.setHorizontalAlignment(SwingConstants.CENTER);
            rowItems[3] = knockRadio;
            knockRadio.setSelected(buttonStates.get((String) entry.getKey()) == -1);

            // Create a group for the buttons.
            ButtonGroup radioGroup = new ButtonGroup();
            radioGroup.add(noneRadio);
            radioGroup.add(overRadio);
            radioGroup.add(knockRadio);

            // Add the buttons to the panel.
            grid.add(nodeName);
            grid.add(noneRadio);
            grid.add(overRadio);
            grid.add(knockRadio);

            // Store the buttons so listeners can be added.
            modifierRows.add(rowItems);
        }

        containerPanel.add(grid);

        return containerPanel;
    }
}
