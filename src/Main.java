import network.BooleanNetwork;
import network.NetworkCreationException;
import network.NetworkTraceException;
import network.Trace;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main program class.
 *
 * @author Alex Anderson
 */
public class Main {

    /**
     * Allows the user to create a network from a file,
     * and create a trace from a given starting state.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // File chooser setup.
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("csv", "csv"));

        // Get user to choose the network file.
        System.out.print("Selecting file...");
        String selectedFile = null;
        while (selectedFile == null) {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile().toString();
                System.out.println("File Selected.");
            } else {
                System.out.println("No file selected, please run again.");
                System.exit(0);
            }
        }

        // Create a network using the file.
        BooleanNetwork network = null;
        try {
            System.out.print("Creating network...");
            network = new BooleanNetwork(selectedFile);
            System.out.println("Network created.");
        } catch (IOException | NetworkCreationException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        // Get the desired startig state.
        int nodeCount = network.getNodes().size();
        System.out.printf("Enter starting state (as %d consecutive integers): ", nodeCount);
        Scanner scanner = new Scanner(System.in);
        String[] startingStateStrings = scanner.nextLine().split("");

        // Make sure te xtarting state was the right length.
        if (startingStateStrings.length != nodeCount) {
            System.out.println("Wrong number of node states entered.");
            System.exit(-1);
        }

        // Make sure only 0 or 1 was entered for each state.
        int[] startingState = new int[startingStateStrings.length];
        for (int x = 0; x < startingStateStrings.length; x++) {
            try {
                startingState[x] = Integer.parseInt(startingStateStrings[x]);
                if (startingState[x] != 1 && startingState[x] != 0) {
                    System.out.println("States can only be 0 or 1.");
                    System.exit(-1);
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }

        // Perform a trace of the network.
        System.out.print("Tracing network...");
        Trace trace = null;
        try {
            trace = network.trace(startingState);
        } catch (NetworkTraceException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        System.out.println("Network traced.\n");

        // Display the network trace and attractor.
        System.out.printf("Trace: %s\n", trace);
        System.out.printf("Attractor: %s\n", trace.attractor);

        System.out.println("\nDone.");
    }
}
