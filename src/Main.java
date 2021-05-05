import network.*;
import util.Util;

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
    public static void main(final String[] args) {
        // File chooser setup.
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("csv", "csv"));

        System.out.println("Selecting network file...");
        String selectedFile = null;
        while (selectedFile == null) {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile().toString();
                System.out.println("File Selected");
            } else {
                System.out.println("No file selected, please try again.");
            }
        }



        BooleanNetwork network = null;
        System.out.print("Creating network...");
        try {
            network = new BooleanNetwork(selectedFile);
        } catch (IOException | NetworkCreationException | NetworkTraceException e) {
            System.out.println("\n\n" + e.getMessage());
            System.out.println("\nTerminated.");
            System.exit(-1);
        }
        System.out.println("Network Created.");

        Scanner scanner = new Scanner(System.in);

        mainLoop:
        while (true) {
            System.out.printf("\nEnter starting state (as %d consecutive integers): ", network.getNodes().length);
            String startingStateStr = scanner.nextLine();

            // Trace all starting states and output the results to a file.
            if (startingStateStr.equalsIgnoreCase("all")) {
                File outputFile = new File("output.txt");
                FileWriter writer;
                try {
                    boolean appending = false;

                    outputFile.createNewFile();
                    writer = new FileWriter(outputFile);

                    if (network.getTitle() != null) {
                        writer.write(String.format("****** %s ******\n\n", network.getTitle()));
                        appending = true;
                    }

                    if (network.getDescription() != null) {
                        writer.write(network.getDescription());
                        writer.write("\n");
                        appending = true;
                    }

                    // Write the node order of states.
                    if (appending) {
                        writer.append("\nNode Order: ").append(Arrays.toString(network.getNodes())).append("\n\n");
                    } else {
                        writer.write("Node Order: " + Arrays.toString(network.getNodes()) + "\n\n");
                    }


                    // For all starting states trace the network.
                    List<int[]> startingStates = Util.getStartingStates(network.getNodes().length);
                    for (int[] startingState : startingStates) {
                        List<State> trace = network.trace(startingState).getTrace();

                        writer.append(String.format("------ Trace for %s ------\n", Arrays.toString(startingState)));
                        for (int y = 0; y < trace.size(); y++) {
                            writer.append(trace.get(y).toString());
                            if (trace.size() - y > 1) {
                                writer.append(" -> ");
                            }
                        }
                        writer.append("\n\n");
                        writer.flush();
                    }

                    writer.append("\n------ Attractors ------\n");
                    for (List<State> attractor : network.getAttractors()) {
                        for (int y = 0; y < attractor.size(); y++) {
                            writer.append(attractor.get(y).toString());
                            if (attractor.size() - y > 1) {
                                writer.append(" -> ");
                            }
                        }
                        writer.append("\n");
                    }
                    writer.flush();
                    writer.close();

                    // Exit the program once file writing is complete.
                    break;
                } catch (IOException | NetworkTraceException e) {
                    System.out.println("There was an error.\n\nTerminated.");
                    System.exit(-1);
                }
            }

            String[] startingStateStrings = startingStateStr.split("");

            if (startingStateStrings.length == 1 && startingStateStrings[0].trim().equals("")) {
                break;
            }

            // Make sure the starting state was the right length.
            if (startingStateStrings.length != network.getNodes().length) {
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
