package mvc;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import network.BooleanNetwork;
import network.NetworkCreationException;
import network.NetworkTraceException;
import network.State;
import ui.InfoPanel;
import ui.NetworkPanel;
import ui.TransitionPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        view.getOpenMenuItem().addActionListener(e -> loadNetwork());
        view.getExportButton().addActionListener(e -> exportStateGraph());
        view.getNetworkPanel().getWiringViewer().getPickedVertexState().addItemListener(e -> flipWiringNode(e));
        view.getNetworkPanel().getWiringViewer().addKeyListener(view.getNetworkPanel().getWiringMouse().getModeKeyListener());
        view.getNetworkPanel().getTransitionViewer().getPickedVertexState().addItemListener(e -> changeState(e));
        view.getNetworkPanel().getTransitionViewer().addKeyListener(view.getNetworkPanel().getTransitionMouse().getModeKeyListener());
        view.getNetworkPanel().getForwardButton().addActionListener(e -> updateNetwork(1));
        view.getNetworkPanel().getBackButton().addActionListener(e -> updateNetwork(0));
        view.getNetworkPanel().getExportButton().addActionListener(e -> exportGraphs());
    }

    private void displayError(String error) {
        JOptionPane.showMessageDialog(view, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void loadNetwork() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("csv", "csv"));

        int returnValue = fileChooser.showOpenDialog(view);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                model.setNetwork(new BooleanNetwork(fileChooser.getSelectedFile().toString()));
                updateView();
                System.out.println(model.getNetwork());
            } catch (IOException | NetworkCreationException | NetworkTraceException ex) {
                System.out.println("Twas an error");
                System.out.println("Error: " + ex.getMessage());
                displayError(ex.getMessage());
            }
        }
    }

    private void updateView() {
        view.setTransitionPanel(new TransitionPanel(model.getNetwork().getTransitions(), view.getExportButton()));
        view.setInfoPanel(new InfoPanel(model.getNetwork()));
        view.setNetworkPanel(new NetworkPanel(model.getNetwork(), view.getNetworkPanel().getTabs().getSelectedIndex()));
        initController();
    }

    private void exportStateGraph() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("dot", "dot"));

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                String path = fileChooser.getSelectedFile().toString();
                writeStateGraph(path);
                JOptionPane.showMessageDialog(view, "Graph exported successfully.", "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                System.out.println("Twas an error");
                System.out.println("Error: " + ex.getMessage());
                displayError(ex.getMessage());
            }
        }
    }

    private void writeStateGraph(String path) throws IOException {
        FileWriter writer = new FileWriter(path);

        writer.write("digraph export {\n");
        for (State s : model.getNetwork().getTransitions().keySet()) {
            String from = s.toString().replaceAll("[\\[\\], ]", "");
            String to = model.getNetwork().getTransitions().get(s).toString().replaceAll("[\\[\\], ]", "");
            writer.append(String.format("\t%s -> %s\n", from, to));
        }
        writer.append("}");
        writer.close();
    }

    private void flipWiringNode(ItemEvent e) {
        Object selected = e.getItem();

        if (selected instanceof String) {
            String vertex = (String) selected;
            System.out.println(vertex + "selected.");
            int currentState = model.getNetwork().getCurrentState().getNodeStates()[model.getNetwork().getNodeIndexes().get(vertex)];
            currentState = currentState == 0 ? 1 : 0;
            model.getNetwork().getCurrentState().getNodeStates()[model.getNetwork().getNodeIndexes().get(vertex)] = currentState;
            updateView();
        }
    }

    private void changeState(ItemEvent e) {
        Object selected = e.getItem();

        if (selected instanceof State) {
            State vertex = (State) selected;
            System.out.println(vertex + "selected.");
            model.getNetwork().setCurrentState(vertex);
            updateView();
        }
    }

    private void updateNetwork(int direction) {
        model.getNetwork().update(direction);
        updateView();
    }

    private void exportGraphs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("dot", "dot"));

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
        for (Map.Entry entry : model.getNetwork().determinants.entrySet()) {
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
        for (State s : model.getNetwork().getTransitions().keySet()) {
            String from = s.toString().replaceAll("[\\[\\], ]", "");
            String to = model.getNetwork().getTransitions().get(s).toString().replaceAll("[\\[\\], ]", "");
            writer.append(String.format("\t%s -> %s\n", from, to));
        }
        writer.append("}");
        writer.close();
    }
}
