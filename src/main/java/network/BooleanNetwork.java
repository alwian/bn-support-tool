package network;

import util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class for creating and tracing a Boolean network.
 * @author Alex Anderson
 */
public class BooleanNetwork {

    /**
     * Optional title for the network.
     */
    private String title;

    /**
     * Optional description for the network.
     */
    private String description;

    /**
     * The transitions from each global state to the next.
     */
    private Map<State, State> originalTransitions = new HashMap<>();

    /**
     * Transition map based on any modifiers applied to the network.
     */
    private Map<State, State> currentTransitions = new HashMap<>();

    /**
     * The most recent trace performed.
     */
    private Trace currentTrace;

    /**
     * Which nodes determine the state of a given node.
     */
    private Map<String, List<String>> determinants = new HashMap<>();

    /**
     * Modifiers (OE and KO) applied the nodes in the network.
     */
    private Map<String, Integer> modifiers = new HashMap<>();

    /**
     * All of the attractors in the network.
     */
    private final List<List<State>> attractors = new ArrayList<>();

    /**
     * Which indexes in a state represent which node.
     */
    private final Map<String, Integer> nodeIndexes = new HashMap<>();

    /**
     * The nodes making up the network.
     */
    private String[] nodes;

    /**
     * The current state of the network.
     */
    private State currentState;

    /**
     * Constructor for BooleanNetwork.
     * @param path The path to node truth tables.
     * @throws IOException When reading a file fails.
     * @throws NetworkCreationException When there is an error in a files syntax.
     */
    public BooleanNetwork(final String path) throws IOException, NetworkCreationException, NetworkTraceException {
        int extensionStart = path.lastIndexOf(".");
        String extension = path.substring(extensionStart + 1).toUpperCase();

        // Make sure the file is a CSV.
        if (!extension.equals("CSV")) {
            String fileName = new File(path).getName();
            throw new NetworkCreationException(String.format("%s is not a CSV.", fileName));
        }

        List<List<List<String>>> tables = deconstructFile(path);

        for (int[] state : Util.getStartingStates(tables.size())) {
            originalTransitions.put(new State(state), new State(state.clone()));
            currentTransitions.put(new State(state), new State(state.clone()));
        }

        for (List<List<String>> table : tables) {
            addNode(table);
        }

        reorder();

        System.out.println(currentTransitions);
        System.out.println(originalTransitions);

        getAllAttractors();

        int[] currentNodeStates = new int[nodes.length];
        Arrays.fill(currentNodeStates, 0);
        currentState = new State(currentNodeStates);

        for (String node : nodes) {
            modifiers.put(node, 0);
        }
    }

    /**
     * Accessor for title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor for description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the original transition table.
     * @return The original transition table.
     */
    public Map<State, State> getOriginalTransitions() {
        return originalTransitions;
    }

    /**
     * Getter for the current updated transition table.
     * @return The current transition table.
     */
    public Map<State, State> getCurrentTransitions() {
        return currentTransitions;
    }

    /**
     * Getter for the most recent trace.
     * @return The most recent trace.
     */
    public Trace getCurrentTrace() {
        return currentTrace;
    }

    /**
     * Getter the node determinants.
     * @return Node determinants.
     */
    public Map<String, List<String>> getDeterminants() {
        return determinants;
    }

    /**
     * Getter for the modifiers applied to the network.
     * @return The network modifiers.
     */
    public Map<String, Integer> getModifiers() {
        return modifiers;
    }

    /**
     * Gets all of the attractors in the network.
     * @return All of the attractors in the network.
     */
    public List<List<State>> getAttractors() {
        return attractors;
    }

    /**
     * Getter for the indexes of each node.
     * @return The indexes of each node.
     */
    public Map<String, Integer> getNodeIndexes() {
        return nodeIndexes;
    }

    /**
     * Accessor for nodes.
     * @return nodes
     */
    public String[] getNodes() {
        return nodes;
    }

    /**
     * Getter for the current state of the network.
     * @return The current state of the network.
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Setter for the current state of the network.
     * @param currentState The new state.
     */
    public void setCurrentState(final State currentState) {
        this.currentState = currentState;
    }

    /**
     * Splits up a network file into individual truth tables.
     * @param path The path to the network file.
     * @return List of truth tables.
     * @throws IOException When an error occurs reading the file.
     */
    private List<List<List<String>>> deconstructFile(final String path) throws IOException {
        List<List<List<String>>> tables = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        List<List<String>> lines = new ArrayList<>();

        // Read the file line by line.
        while ((line = reader.readLine()) != null) {
            lines.add(Arrays.asList(line.split(",")));
        }

        if (lines.get(0).get(0).startsWith("#") && lines.get(0).get(0).length() > 1) {
            this.title = lines.remove(0).get(0).substring(1);
        }

        if (lines.get(0).get(0).startsWith("#") && lines.get(0).get(0).length() > 1) {
            this.description = lines.remove(0).get(0).substring(1);
        }


        while (!lines.isEmpty()) {
            if (lines.get(0).size() == 1) {
                lines.remove(0);
                continue;
            }

            List<List<String>> table = new ArrayList<>();
            List<String> headings = lines.remove(0);
            table.add(headings);

            for (int x = 0; x < Math.pow(2, headings.size() - 1); x++) {
                table.add(lines.remove(0));
            }

            tables.add(table);
        }

        return tables;
    }

    /**
     * Creates all possible traces.
     * @throws NetworkTraceException When an error occurs performing a trace.
     */
    public void getAllAttractors() throws NetworkTraceException {
        for (State startingState : currentTransitions.keySet()) {
            List<State> attractor = trace(startingState.getNodeStates()).getAttractor();
            boolean newAttractor = true;

            for (List<State> attractor2 : attractors) {
               // Set<State> newAttractorStates = new HashSet<>(attractor);
                List<State> tempAttractor = new ArrayList<>(attractor);
                tempAttractor.retainAll(attractor2);

                if (tempAttractor.size() > 0) {
                    newAttractor = false;
                    break;
                }
            }

            if (newAttractor) {
                attractors.add(attractor);
            }
        }
    }

    /**
     * Takes in a truth table for a node and
     * updates the transition map to reflect the truth table.
     * @param table Truth table for the node to add.
     * @throws NetworkCreationException When there is an error in the truth table syntax.
     */
    private void addNode(final List<List<String>> table) throws NetworkCreationException {
        // Extract the headings from the file.
        List<String> headings = table.remove(0);

        String nodeName = headings.get(headings.size() - 1);

        determinants.put(nodeName, headings.subList(0, headings.size() - 1));

        if (!nodeIndexes.containsKey(nodeName)) {
            nodeIndexes.put(nodeName, nodeIndexes.size());
        }

        // Go through each entry in the truth table.
        for (List<String> l : table) {
            int result;
            Map<String, Integer> determinants = new HashMap<>();
            try {
                // Get the determinant nodes.
                for (int x = 0; x < l.size() - 1; x++) {
                    determinants.put(headings.get(x), Integer.parseInt(l.get(x)));
                }

                // Get the node the truth table is for.
                result = Integer.parseInt(l.get(l.size() - 1));
                if (result != 0 && result != 1) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new NetworkCreationException("Node states must be 0 or 1.");
            }

            // Update the transition map based on the current truth table.
            for (State startingState : originalTransitions.keySet()) {
                boolean allDeterminantsMatch = true;

                // Go through all determinants.
                for (String determinant : determinants.keySet()) {
                    // If a new node is found as a determinant, assign it an index.
                    if (!nodeIndexes.containsKey(determinant)) {
                        nodeIndexes.put(determinant, nodeIndexes.size());
                    }

                    // Check if the current determinant matches the current starting state.
                    try {
                        if (!(startingState.getNodeStates()[nodeIndexes.get(determinant)] == determinants.get(determinant))) {
                            allDeterminantsMatch = false;
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        throw new NetworkCreationException("Too many nodes found in truth tables.");
                    }
                }

                // If all determinants match the current starting state, update the resulting state.
                if (allDeterminantsMatch) {
                    State originalFinalState = originalTransitions.get(startingState);
                    originalFinalState.getNodeStates()[nodeIndexes.get(nodeName)] = result;

                    State currentFinalState = currentTransitions.get(startingState);
                    currentFinalState.getNodeStates()[nodeIndexes.get(nodeName)] = result;
                }
            }
        }
    }

    /**
     * Reorders the states in the transition map to reflect the
     * nodes of the network being in alphabetical order.
     */
    private void reorder() {
        // New map for the ordered transitions.
        Map<State, State> orderedTransitions = new HashMap<>();

        // Order the nodes alphabetically.
        String[] orderedNodes = nodeIndexes.keySet().toArray(new String[0]);
        Arrays.sort(nodeIndexes.keySet().toArray());

        nodes = orderedNodes;

        // Go through the unordered starting states.
        for (State unorderedStart : originalTransitions.keySet()) {
            // New arrays to store the ordered states.
            int[] orderedStart = new int[nodeIndexes.size()];
            int[] orderedEnd = new int[nodeIndexes.size()];

            int[] unorderedEnd = originalTransitions.get(unorderedStart).getNodeStates();

            // Go through each node and put it's corresponding value in the correct place.
            for (int x = 0; x < orderedNodes.length; x++) {
                orderedStart[x] = unorderedStart.getNodeStates()[nodeIndexes.get(orderedNodes[x])];
                orderedEnd[x] = unorderedEnd[nodeIndexes.get(orderedNodes[x])];
            }

            // Store the ordered transition in the new map.
            orderedTransitions.put(new State(orderedStart), new State(orderedEnd));
        }

        // Update the node indexes to reflect the new positions.
        for (int x = 0; x < orderedNodes.length; x++) {
            nodeIndexes.replace(orderedNodes[x], x);
        }

        // Replace the old transition map.
        originalTransitions = orderedTransitions;
    }

    /**
     * Creates a trace of the network.
     * @param startingState Initial state of the network.
     * @return A trace object containing the trace.
     * @throws NetworkTraceException When the starting state is invalid.
     */
    public Trace trace(final int[] startingState) throws NetworkTraceException {
        // Check the start state is valid.
        if (startingState.length != nodeIndexes.size()) {
            throw new NetworkTraceException(String.format("You must provide %d starting states.", nodeIndexes.size()));
        }

        State currentState = new State(startingState);
        List<State> trace = new ArrayList<>();
        trace.add(currentState);
        State newState;

        // Trace until an attractor is found.
        while (true) {
            newState = currentTransitions.get(currentState);

            // Check if state has been seen before.
            if (trace.contains(newState)) {
                trace.add(newState);
                break;
            }

            trace.add(newState);
            currentState = newState;
        }
        this.currentTrace = new Trace(trace);
        return new Trace(trace);
    }

    /**
     * Advances the network a step based on the defined next stage rules.
     */
    public void update() {
        this.currentState = new State(this.currentTransitions.get(this.currentState).getNodeStates().clone());
    }
}
