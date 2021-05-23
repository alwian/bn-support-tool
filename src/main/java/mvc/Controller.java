package mvc;

import network.BooleanNetwork;
import network.NetworkCreationException;
import network.NetworkTraceException;
import network.State;
import ui.InfoPanel;
import ui.NetworkPanel;
import ui.ModifierPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller which handles UI listeners and updates the UI accordingly.
 * @author Alex Anderson
 */
public class Controller {

    /**
     * The data model for the tool.
     */
    private final Model model;

    /**
     * The UI of the tool.
     */
    private final View view;

    /**
     * Constructor for the controller.
     * @param m The data model for the tool.
     * @param v The UI of the tool.
     */
    public Controller(final Model m, final View v) {
        this.model = m;
        this.view = v;
    }

    /**
     * Sets the initial listeners for UI components.
     */
    public void initController() {
        setMenuListeners();
        setNetworkListeners();
        setInfoListeners();
        setModifierListeners();
    }

    /**
     * Shows an error to the user.
     * @param error The message to display.
     */
    private void displayError(final String error) {
        JOptionPane.showMessageDialog(view, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Exits the tool.
     */
    private void close() {
        System.exit(0);
    }

    /**
     * Loads a new network into the tool.
     */
    private void loadNetwork() {
        // Set up file chooser.
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("csv", "csv"));

        int returnValue = fileChooser.showOpenDialog(null);

        // If file selected load it in.
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                // Load the network from the file.
                model.setNetwork(new BooleanNetwork(fileChooser.getSelectedFile().toString()));

                // Setup the modifier panel.
                Map<String, Integer> buttonStates = new HashMap<>();
                for (String node : model.getNetwork().getNodes()) {
                    buttonStates.put(node, 0);
                }
                view.getModifierPanel().setButtonStates(buttonStates);

                // Update UI with new network.
                updateInfoPanel(true);
                updateNetworkPanel();
                updateModifierPanel();
            } catch (IOException | NetworkCreationException | NetworkTraceException ex) {
                displayError(ex.getMessage());
            }
        }
    }

    /**
     * Flips the state of a node when it is clicked in the wiring diagram.
     * @param e The selection event triggered when a node was clicked.
     */
    private void flipWiringNode(final ItemEvent e) {
        Object selected = e.getItem();

        // If node selected.
        if (selected instanceof String) {
            String vertex = (String) selected;

            // Change the node state in the network.
            int currentState = model.getNetwork().getCurrentState().getNodeStates()[model.getNetwork().getNodeIndexes().get(vertex)];
            currentState = currentState == 0 ? 1 : 0;
            model.getNetwork().getCurrentState().getNodeStates()[model.getNetwork().getNodeIndexes().get(vertex)] = currentState;

            // Update UI to show the new state.
            reTraceNetwork();
            updateInfoPanel(false);
            updateNetworkPanel();
        }
    }

    /**
     * Sets the state of the current network when a
     * state is clicked in the transition diagram.
     * @param e The selection event triggered when a state was clicked.
     */
    private void changeState(final ItemEvent e) {
        Object selected = e.getItem();

        // If state selected.
        if (selected instanceof State) {
            State vertex = (State) selected;

            // Update the state of the network.
            model.getNetwork().setCurrentState(vertex);

            // Update UI to show new state.
            reTraceNetwork();
            updateInfoPanel(false);
            updateNetworkPanel();
        }
    }

    /**
     * Advances the network a step based on the defined next-state rules.
     */
    private void updateNetwork() {
        // Advance the network a step.
        model.getNetwork().update();
        // Update UI to show new state.
        updateNetworkPanel();
    }

    /**
     * Exports the wiring and transition diagram to .dot files for use with Graphviz.
     */
    private void exportGraphs() {
        // Set up directory chooser.
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showSaveDialog(null);

        // If directory selected.
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                // Write the graphs to dot files.
                String path = fileChooser.getSelectedFile().toString();
                writeWiringGraph(path + File.separator + "wiring.dot");
                writeTransitionGraph(path + File.separator + "transition.dot");
                JOptionPane.showMessageDialog(view, "Graphs exported successfully.", "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                displayError(ex.getMessage());
            }
        }
    }

    /**
     * Writes the exported wiring diagram file.
     * @param path The path to write to.
     * @throws IOException When an error occurs writing the file.
     */
    private void writeWiringGraph(final String path) throws IOException {
        FileWriter writer = new FileWriter(path);

        // Start graph.
        writer.write("digraph export {\n");
        // Go through each node.
        for (Map.Entry entry : model.getNetwork().getDeterminants().entrySet()) {
            // Go through each determinant.
            for (String determinant : (List<String>) entry.getValue()) {
                // Write edge between nodes and their determinants.
                writer.append(String.format("\t%s -> %s\n", determinant, entry.getKey()));
            }
        }
        writer.append("}");
        writer.close();
    }

    /**
     * Writes the exported transition diagram file.
     * @param path The path to write to.
     * @throws IOException When an error occurs writing the file.
     */
    private void writeTransitionGraph(final String path) throws IOException {
        FileWriter writer = new FileWriter(path);

        // Start graph.
        writer.write("digraph export {\n");
        // Go through each starting state.
        for (State s : model.getNetwork().getCurrentTransitions().keySet()) {
            // Write edge between starting and resulting state.
            String from = s.toString().replaceAll("[\\[\\], ]", "");
            String to = model.getNetwork().getCurrentTransitions().get(s).toString().replaceAll("[\\[\\], ]", "");
            writer.append(String.format("\t%s -> %s\n", from, to));
        }
        writer.append("}");
        writer.close();
    }

    /**
     * Handles updates to over-expression / knockout modifiers.
     * @param nodeName The node being modified.
     * @param value The modification to apply.
     */
    private void updateModifier(final String nodeName, final int value) {
        // Update the modifier and the modifier panel.
        model.getNetwork().getModifiers().replace(nodeName, value);
        view.getModifierPanel().getButtonStates().replace(nodeName, value);

        // Update the transition table accordingly.
        for (Map.Entry entry : model.getNetwork().getCurrentTransitions().entrySet()) {
            if (value == 0) {
                ((State) entry.getValue()).getNodeStates()[model.getNetwork().getNodeIndexes().get(nodeName)] = model.getNetwork().getOriginalTransitions().get((State) entry.getKey()).getNodeStates()[model.getNetwork().getNodeIndexes().get(nodeName)];
            } else if (value == 1 || value == -1) {
                ((State) entry.getValue()).getNodeStates()[model.getNetwork().getNodeIndexes().get(nodeName)] = (value == 1 ? 1 : 0);
            }
        }


        // Trace the network and update its attractors.
        model.getNetwork().getAttractors().clear();
        try {
            model.getNetwork().getAllAttractors();
        } catch (NetworkTraceException e) {
            displayError(e.getMessage());
        }

        // Update UI to reflect changes.
        updateInfoPanel(true);
        updateNetworkPanel();
    }

    /**
     * Causes the current network to perform a trace from its current state.
     */
    private void reTraceNetwork() {
        try {
            model.getNetwork().trace(model.getNetwork().getCurrentState().getNodeStates());
        } catch (NetworkTraceException e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Handles displaying a specific attractor.
     * @param e The event triggered when an attractor is selected.
     */
    private void attractorSelected(final MouseEvent e) {
        // Get the selected attractor.
        Point point = e.getPoint();
        int index = view.getInfoPanel().getAttractorsList().locationToIndex(point);

        // If the attractor was already selected, deselect it.
        if (index == view.getInfoPanel().getSelectedAttractor()) {
            view.getInfoPanel().setSelectedAttractor(-1);
            view.getInfoPanel().getAttractorsList().clearSelection();
        // If attractor wasn't selected, select it.
        } else {
            view.getInfoPanel().setSelectedAttractor(index);
            model.getNetwork().setCurrentState(model.getNetwork().getAttractors().get(index).get(0));
        }

        // Update UI to show the selected attractor or transition graph.
        updateNetworkPanel();
    }

    /**
     * Replaces the current network panel with an updated version.
     */
    private void updateNetworkPanel() {
        view.setNetworkPanel(new NetworkPanel(model.getNetwork(), view.getNetworkPanel().getTabs().getSelectedIndex(), view.getInfoPanel().getSelectedAttractor()));
        setNetworkListeners();
    }

    /**
     * Replaces the current info panel with an updated version.
     * @param attractorsReset Whether the attractors to be displayed have changed.
     */
    private void updateInfoPanel(final boolean attractorsReset) {
        view.setInfoPanel(new InfoPanel(model.getNetwork(), view.getInfoPanel().getTabs().getSelectedIndex(), attractorsReset ? -1 : view.getInfoPanel().getSelectedAttractor()));
        setInfoListeners();
    }

    /**
     * Replaces the current modifier panel with an updated version.
     */
    private void updateModifierPanel() {
        // Reset modifier panel button states.
        HashMap<String, Integer> buttonStates = new HashMap<>();
        for (String node : model.getNetwork().getNodes()) {
            buttonStates.put(node, 0);
        }
        view.setModifierPanel(new ModifierPanel(model.getNetwork(), buttonStates));
        setModifierListeners();
    }

    /**
     * Sets the listeners for the network panel.
     */
    private void setNetworkListeners() {
        view.getNetworkPanel().getWiringViewer().getPickedVertexState().addItemListener(e -> flipWiringNode(e));
        view.getNetworkPanel().getWiringViewer().addKeyListener(view.getNetworkPanel().getWiringMouse().getModeKeyListener());
        view.getNetworkPanel().getTransitionViewer().getPickedVertexState().addItemListener(e -> changeState(e));
        view.getNetworkPanel().getTransitionViewer().addKeyListener(view.getNetworkPanel().getTransitionMouse().getModeKeyListener());
        view.getNetworkPanel().getForwardButton().addActionListener(e -> updateNetwork());
    }

    /**
     * Sets the listeners for the program menu.
     */
    private void setMenuListeners() {
        view.getFrameMenuBar().getExitOption().addActionListener(e -> close());
        view.getFrameMenuBar().getOpenOption().addActionListener(e -> loadNetwork());
        view.getFrameMenuBar().getExportOption().addActionListener(e -> exportGraphs());
    }

    /**
     * Sets the listeners for the info panel.
     */
    private void setInfoListeners() {
        view.getInfoPanel().getAttractorsList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                attractorSelected(e);
            }
        });
    }

    /**
     * Sets the listeners for the modifier panel.
     */
    private void setModifierListeners() {
        // Add listeners to all modifier panel buttons.
        for (Object[] modifierRow : view.getModifierPanel().getModifierRows()) {
            String nodeName = (String) modifierRow[0];
            JRadioButton noneButton = (JRadioButton) modifierRow[1];
            JRadioButton overButton = (JRadioButton) modifierRow[2];
            JRadioButton knockButton = (JRadioButton) modifierRow[3];

            noneButton.addActionListener(e -> updateModifier(nodeName, 0));
            overButton.addActionListener(e -> updateModifier(nodeName, 1));
            knockButton.addActionListener(e -> updateModifier(nodeName, -1));
        }
    }
}
