import network.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of nodes: ");
        int numOfNodes = scanner.nextInt();
        String[] paths = new String[numOfNodes];


        for (int x = 0; x < numOfNodes; x++) {
            String selectedFile = null;
            System.out.printf("Selecting file for node %d...", x + 1);
            while (selectedFile == null) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile().toString();
                    System.out.println("File Selected");
                    paths[x] = selectedFile;
                } else {
                    System.out.println("No file selected, please try again.");
                }
            }
        }

        BooleanNetwork network = null;
        System.out.print("Creating network...");
        try {
            network = new BooleanNetwork(paths);
        } catch (IOException | NetworkCreationException e) {
            System.out.println("\n\n" + e.getMessage());
            System.out.println("\nTerminated.");
            System.exit(-1);
        }
        System.out.println("Network Created.");

        scanner = new Scanner(System.in);

        mainLoop:
        while (true) {
            System.out.printf("\nEnter starting state (as %d consecutive integers): ", numOfNodes);
            String startingStateStr = scanner.nextLine();

            if (startingStateStr.equalsIgnoreCase("all")) {
                File outputFile = new File("output.txt");
                FileWriter writer;
                try {
                    outputFile.createNewFile();
                    writer = new FileWriter(outputFile);
                    writer.write("Node Order: " + Arrays.toString(network.nodes) + "\n\n");

                    int highestBinaryVal = (int) Math.pow(2, paths.length);
                    for (int x = 0; x < highestBinaryVal; x++) {
                        String[] strStates = String.format("%" + paths.length + "s", Integer.toBinaryString(x)).replace(" ", "0").split("");
                        int[] intStates = new int[strStates.length];
                        for (int y = 0; y < strStates.length; y++) {
                            intStates[y] = Integer.parseInt(strStates[y]);
                        }

                        Trace trace = network.trace(intStates);

                        writer.append("Starting State: ").append(Arrays.toString(intStates)).append("\n");
                        writer.append("Trace: ").append(String.valueOf(trace.trace)).append("\n");
                        writer.append("Attractor: ").append(String.valueOf(trace.attractor)).append("\n\n");
                        writer.flush();
                    }
                    break;
                } catch (IOException | NetworkTraceException e ) {
                    System.out.println("There was an error.\n\nTerminated.");
                    System.exit(-1);
                }
            }

            String[] startingStateStrings = startingStateStr.split("");

            if (startingStateStrings.length == 1 && startingStateStrings[0].trim().equals("")) {
                break;
            }

            // Make sure the starting state was the right length.
            if (startingStateStrings.length != numOfNodes) {
                System.out.println("Wrong number of node states entered.");
                continue;
            }

            // Make sure only 0 or 1 was entered for each state.
            int[] startingState = new int[startingStateStrings.length];
            for (int x = 0; x < startingStateStrings.length; x++) {
                try {
                    startingState[x] = Integer.parseInt(startingStateStrings[x]);
                    if (startingState[x] != 1 && startingState[x] != 0) {
                        System.out.println("States can only be 0 or 1.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("States can only be 0 or 1.");
                    continue mainLoop;
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
        }

        System.out.println("\nDone.");
    }

}
