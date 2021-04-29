package network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StateTransitions {
    public Map<int[], int[]> transitions = new HashMap<>();
    public Map<String, Integer> nodeIndexes = new HashMap<>();
    int nodesAdded = 0;

    public StateTransitions(int numberOfNodes) {
        int highestBinaryVal = (int) Math.pow(2, numberOfNodes);
        for (int x = 0; x < highestBinaryVal; x++) {
            String[] strStates = String.format("%" + numberOfNodes + "s", Integer.toBinaryString(x)).replace(" ", "0").split("");
            int[] intStates = new int[strStates.length];
            for (int y = 0; y < strStates.length; y++) {
                intStates[y] = Integer.parseInt(strStates[y]);
            }
            transitions.put(intStates, intStates.clone());
        }
    }

    public void addNode(String path) throws IOException, NetworkCreationException {
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
            nodeIndexes.put(nodeName, nodesAdded++);
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
                        nodeIndexes.put(determinant, nodesAdded++);
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

}
