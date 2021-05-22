package mvc;

import network.BooleanNetwork;
import network.NetworkCreationException;
import network.NetworkTraceException;
import network.State;
import ui.InfoPanel;
import ui.MenuBar;
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

public class Controller {
    Model model;
    View view;

    public Controller(Model m, View v) {
        this.model = m;
        this.view = v;
    }

    public void initController() {
        setMenuListeners();
        setNetworkListeners();
        setInfoListeners();
        setModifierListeners();
    }

    private void displayError(String error) {
        JOptionPane.showMessageDialog(view, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void close() {
        System.exit(0);
    }

    private void loadNetwork() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("csv", "csv"));

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                model.setNetwork(new BooleanNetwork(fileChooser.getSelectedFile().toString()));
                Map<String, Integer> buttonStates = new HashMap<>();
                for (String node : model.getNetwork().getNodes()) {
                    buttonStates.put(node,0);
                }
                view.getModifierPanel().setButtonStates(buttonStates);
                updateInfoPanel(true);
                updateNetworkPanel();
                updateModiferPanel();
                System.out.println(model.getNetwork());
            } catch (IOException | NetworkCreationException | NetworkTraceException ex) {
                System.out.println("Twas an error");
                System.out.println("Error: " + ex.getMessage());
                displayError(ex.getMessage());
            }
        }
    }

    private void flipWiringNode(ItemEvent e) {
        Object selected = e.getItem();

        if (selected instanceof String) {
            String vertex = (String) selected;
            System.out.println(vertex + "selected.");
            int currentState = model.getNetwork().getCurrentState().getNodeStates()[model.getNetwork().getNodeIndexes().get(vertex)];
            currentState = currentState == 0 ? 1 : 0;
            model.getNetwork().getCurrentState().getNodeStates()[model.getNetwork().getNodeIndexes().get(vertex)] = currentState;

            reTraceNetwork();
            updateInfoPanel(false);
            updateNetworkPanel();
        }
    }

    private void changeState(ItemEvent e) {
        Object selected = e.getItem();

        if (selected instanceof State) {
            State vertex = (State) selected;
            System.out.println(vertex + "selected.");
            model.getNetwork().setCurrentState(vertex);

            reTraceNetwork();
            updateInfoPanel(false);
            updateNetworkPanel();
        }
    }

    private void updateNetwork() {
        model.getNetwork().update();
        updateNetworkPanel();
    }

    private void exportGraphs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                String path = fileChooser.getSelectedFile().toString();
                writeWiringGraph(path + File.separator + "wiring.dot");
                writeTransitionGraph(path + File.separator + "transition.dot");
                JOptionPane.showMessageDialog(view, "Graphs exported successfully.", "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                System.out.println("Twas an error");
                System.out.println("Error: " + ex.getMessage());
                displayError(ex.getMessage());
            }
        }
    }

    private void writeWiringGraph(String path) throws IOException {
        FileWriter writer = new FileWriter(path);

        writer.write("digraph export {\n");
        for (Map.Entry entry : model.getNetwork().getDeterminants().entrySet()) {
            for (String determinant : (List<String>) entry.getValue()) {
                writer.append(String.format("\t%s -> %s\n", determinant, entry.getKey()));
            }
        }
        writer.append("}");
        writer.close();
    }
    private void writeTransitionGraph(String path) throws IOException {
        FileWriter writer = new FileWriter(path);

        writer.write("digraph export {\n");
        for (State s : model.getNetwork().getOriginalTransitions().keySet()) {
            String from = s.toString().replaceAll("[\\[\\], ]", "");
            String to = model.getNetwork().getOriginalTransitions().get(s).toString().replaceAll("[\\[\\], ]", "");
            writer.append(String.format("\t%s -> %s\n", from, to));
        }
        writer.append("}");
        writer.close();
    }

    private void updateModifier(String nodeName, int value) {
        model.getNetwork().getModifiers().replace(nodeName, value);
        view.getModifierPanel().getButtonStates().replace(nodeName, value);

        for (Map.Entry entry : model.getNetwork().getCurrentTransitions().entrySet()) {
            if (value == 0) {
                ((State) entry.getValue()).getNodeStates()[model.getNetwork().getNodeIndexes().get(nodeName)] = model.getNetwork().getOriginalTransitions().get((State) entry.getKey()).getNodeStates()[model.getNetwork().getNodeIndexes().get(nodeName)];
            } else if (value == 1 || value == -1) {
                ((State) entry.getValue()).getNodeStates()[model.getNetwork().getNodeIndexes().get(nodeName)] = (value == 1 ? 1 : 0);
            }
        }

        model.getNetwork().getAttractors().clear();
        try {
            model.getNetwork().getAllAttractors();
        } catch (NetworkTraceException e) {
            displayError(e.getMessage());
        }

        reTraceNetwork();
        updateInfoPanel(true);
        updateNetworkPanel();
    }

    private void reTraceNetwork() {
        try {
            model.getNetwork().trace(model.getNetwork().getCurrentState().getNodeStates());
        } catch (NetworkTraceException e) {
            displayError(e.getMessage());
        }
    }

    private void attractorSelected(MouseEvent e) {
        Point point = e.getPoint();
        int index = view.getInfoPanel().getAttractorsList().locationToIndex(point);

        if (index == view.getInfoPanel().getSelectedAttractor()) {
            view.getInfoPanel().setSelectedAttractor(-1);
            view.getInfoPanel().getAttractorsList().clearSelection();
            System.out.println("Attractor " + index + " deselected.");
        } else {
            view.getInfoPanel().setSelectedAttractor(index);
            model.getNetwork().setCurrentState(model.getNetwork().getAttractors().get(index).get(0));
            System.out.println("Attractor " + index + " selected.");
        }
        updateNetworkPanel();
    }

    private void updateNetworkPanel() {
        view.setNetworkPanel(new NetworkPanel(model.getNetwork(), view.getNetworkPanel().getTabs().getSelectedIndex(), view.getInfoPanel().getSelectedAttractor()));
        setNetworkListeners();
    }

    private void updateInfoPanel(boolean attractorsReset) {
        view.setInfoPanel(new InfoPanel(model.getNetwork(), view.getInfoPanel().getTabs().getSelectedIndex(), attractorsReset ? -1 : view.getInfoPanel().getSelectedAttractor()));
        setInfoListeners();
    }

    private void updateModiferPanel() {
        HashMap<String, Integer> buttonStates = new HashMap<>();
        for (String node : model.getNetwork().getNodes()) {
            buttonStates.put(node, 0);
        }
        view.setModifierPanel(new ModifierPanel(model.getNetwork(), buttonStates));
        setModifierListeners();
    }

//    private void updateModifierPanel() {
//        view.setModifierPanel(new ModifierPanel(model.getNetwork(), view.getModifierPanel().getButtonStates()));
//        setModifierListeners();
//    }

    private void setNetworkListeners() {
        view.getNetworkPanel().getWiringViewer().getPickedVertexState().addItemListener(e -> flipWiringNode(e));
        view.getNetworkPanel().getWiringViewer().addKeyListener(view.getNetworkPanel().getWiringMouse().getModeKeyListener());
        view.getNetworkPanel().getTransitionViewer().getPickedVertexState().addItemListener(e -> changeState(e));
        view.getNetworkPanel().getTransitionViewer().addKeyListener(view.getNetworkPanel().getTransitionMouse().getModeKeyListener());
        view.getNetworkPanel().getForwardButton().addActionListener(e -> updateNetwork());
    }

    private void setMenuListeners() {
        view.getFrameMenuBar().getExitOption().addActionListener(e -> close());
        view.getFrameMenuBar().getOpenOption().addActionListener(e -> loadNetwork());
        view.getFrameMenuBar().getExportOption().addActionListener(e -> exportGraphs());
    }

    private void setInfoListeners() {
        view.getInfoPanel().getAttractorsList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                attractorSelected(e);
            }
        });
    }

    private void setModifierListeners() {
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
