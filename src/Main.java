import network.BooleanNetwork;
import network.NetworkCreationException;
import network.Trace;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("csv", "csv"));

        String selectedFile = null;
        while (selectedFile == null) {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile().toString();
            } else {
                System.out.println("No file selected, please run again.");
                System.exit(0);
            }
        }


        BooleanNetwork network;
        try {
            network = new BooleanNetwork(selectedFile);
            Trace t = network.trace(new int[]{0,1,1});
            System.out.println(t.attractor);
        } catch (IOException | NetworkCreationException e) {
            System.out.println(e.getMessage());
        }
    }
}
