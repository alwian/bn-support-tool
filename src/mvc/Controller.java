package mvc;

import network.BooleanNetwork;
import network.NetworkCreationException;
import network.NetworkTraceException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controller {
    Model model;
    View view;

    public Controller(Model m, View v) {
        this.model = m;
        this.view = v;
    }

    public void initController() {
        view.getOpenMenuItem().addActionListener(e -> loadNetwork());
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

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                model.setNetwork(new BooleanNetwork(fileChooser.getSelectedFile().toString()));
                System.out.println(model.getNetwork());
            } catch (IOException | NetworkCreationException | NetworkTraceException ex) {
                System.out.println("Twas an error");
                System.out.println("Error: " + ex.getMessage());
                displayError(ex.getMessage());
            }
        }
    }

}
