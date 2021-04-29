package network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class for creating and tracing a Boolean network.
 *
 * @author Alex Anderson
 */
public class BooleanNetwork {

    /**
     * The transitions from each global state to the next.
     */
    private final Map<int[], int[]> transitions = new HashMap<>();

    private final Map<String, Integer> nodeIndexes = new HashMap<>();


    /**
     * Constructor for BooleanNetwork.
     *
     * @param paths The paths to node truth tables.
     * @throws IOException When reading a file fails.
     * @throws NetworkCreationException When there is an error in a files syntax.
     */
    public BooleanNetwork(final String[] paths) throws IOException, NetworkCreationException {
        int highestBinaryVal = (int) Math.pow(2, paths.length);
        for (int x = 0; x < highestBinaryVal; x++) {
            String[] strStates = String.format("%" + paths.length + "s", Integer.toBinaryString(x)).replace(" ", "0").split("");
            int[] intStates = new int[strStates.length];
            for (int y = 0; y < strStates.length; y++) {
                intStates[y] = Integer.parseInt(strStates[y]);
            }
            transitions.put(intStates, intStates.clone());
        }

        for (String path : paths) {
            int extensionStart = path.lastIndexOf(".");
            String extension = path.substring(extensionStart + 1).toUpperCase();

            // Make sure the file is a CSV.
            if (extension.equals("CSV")) {
                addNode(path);
            } else {
                String fileName = new File(path).getName();
                throw new NetworkCreationException(String.format("%s is not a CSV.",fileName));
            }
        }
    }

    private void addNode(String path) throws IOException, NetworkCreationException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        List<List<String>> lines = new ArrayList<>();

        // Read the file line by line.
        while ((line = reader.readLine()) != null) {
            lines.add(Arrays.asList(line.split(",")));
        }

        // Extract the headings from the file.
        List<String> headings = lines.remove(0);

        String nodeName = headings.get(headings.size() - 1);
        if (!nodeIndexes.containsKey(nodeName)) {
            nodeIndexes.put(nodeName, nodeIndexes.size());
        }

        for (List<String> l : lines) {
            int result;
            Map<String, Integer> determinants = new HashMap<>();
            try {
                for (int x = 0; x < l.size() - 1; x++) {
                    determinants.put(headings.get(x), Integer.parseInt(l.get(x)));
                }
                result = Integer.parseInt(l.get(l.size() - 1));
                if (result != 0 && result != 1) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new NetworkCreationException("Node states must be 0 or 1.");
            }

            for (int[] startingState : transitions.keySet()) {
                boolean allDeterminantsMatch = true;
                for (String determinant : determinants.keySet()) {
                    if (!nodeIndexes.containsKey(determinant)) {
                        nodeIndexes.put(determinant, nodeIndexes.size());
                    }

                    try {
                        if (!(startingState[nodeIndexes.get(determinant)] == determinants.get(determinant))) {
                            allDeterminantsMatch = false;
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        throw new NetworkCreationException("Too many nodes found in truth tables.");
                    }
                }

                if (allDeterminantsMatch) {
                    int[] finalState = transitions.get(startingState);
                    finalState[nodeIndexes.get(nodeName)] = result;
                    transitions.replace(startingState, finalState);
                }
            }
        }

    }

//    /**
//     * Creates a trace of the network.
//     *
//     * @param startingState Initial state of the network.
//     * @return A trace object containing the trace.
//     * @throws NetworkTraceException When the starting state is invalid.
//     */
//    public Trace trace(final int[] startingState) throws NetworkTraceException {
//        // Check the star state is valid.
//        if (startingState.length != nodes.size()) {
//            throw new NetworkTraceException(String.format("You must provide %d starting states.", nodes.size()));
//        }
//
//        State currentState = new State(startingState);
//        List<State> trace = new ArrayList<>();
//        trace.add(currentState);
//        State newState;
//
//        // Trace until an attractor is found.
//        while (true) {
//            newState = transitions.get(currentState);
//
//            // Check if state has been seen before.
//            if (trace.contains(newState)) {
//                trace.add(newState);
//                break;
//            }
//            trace.add(newState);
//            currentState = newState;
//        }
//        return new Trace(startingState, trace);
//    }
}
