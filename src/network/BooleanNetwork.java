package network;

import java.io.BufferedReader;
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
     * The nodes which make up the network.
     */
    private final List<Node> nodes = new ArrayList<>();

    /**
     * Getter for nodes.
     *
     * @return The nodes of the network.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * The transitions from each global state to the next.
     */
    private final Map<State, State> transitions = new HashMap<>();

    /**
     * Constructor for BooleanNetwork.
     *
     * @param path The path to the file storing the network transitions.
     * @throws IOException When reading the file at path fails.
     * @throws NetworkCreationException When there is an error in the file syntax.
     */
    public BooleanNetwork(final String path) throws IOException, NetworkCreationException {
        int extensionStart = path.lastIndexOf(".");
        String extension = path.substring(extensionStart + 1).toUpperCase();

        // Make sure the file is a CSV.
        if (extension.equals("CSV")) {
            createFromCSV(path);
        } else {
            throw new NetworkCreationException("File type must be CSV.");
        }
    }

    /**
     * Creates a network from a CSV file.
     *
     * @param path The path to the file storing the network transitions.
     * @throws IOException When reading the file at path fails.
     * @throws NetworkCreationException When there is an error in the file syntax.
     */
    private void createFromCSV(final String path) throws IOException, NetworkCreationException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        List<List<String>> lines = new ArrayList<>();

        // Read the file line by line.
        while ((line = reader.readLine()) != null) {
            lines.add(Arrays.asList(line.split(",")));
        }

        // Extract the headings from the file.
        List<String> headings = lines.remove(0);

        // Check that there is 2 of each heading, in the same order.
        if (headings.size() % 2 != 0) {
            throw new NetworkCreationException("There must be 2 headings per node, example - [g1,g2,g3,g1,g2,g3].");
        } else if (!headings.subList(0, headings.size() / 2).equals(headings.subList(headings.size() / 2, headings.size()))) {
            throw new NetworkCreationException("Headings must be in the same order for before and after, example - [g1,g2,g3,g1,g2,g3].");
        }

        // Check headings are correct.
        Map<String, Integer> headingCounts = new HashMap<>();
        for (String heading : headings) {
            headingCounts.compute(heading, (key, value) -> value == null ? 1 : value + 1);
        }

        // Create the nodes of the network.
        for (int x = 0; x < headings.size() / 2; x++) {
            nodes.add(new Node(x, headings.get(x)));
        }

        // Go through each state transition.
        for (List<String> t : lines) {
            // Check all values are present.
            if (t.size() != headings.size()) {
                throw new NetworkCreationException("There are missing state values.");
            }

            int[] states = new int[headings.size()];

            // Turn states into integers.
            for (int x = 0; x < t.size(); x++) {
                try {
                    states[x] = Integer.parseInt(t.get(x));
                } catch (NumberFormatException e) {
                    throw new NetworkCreationException("States can only be 1 or 0.");
                }
            }

            int[] from = Arrays.copyOfRange(states, 0, nodes.size());
            int[] to = Arrays.copyOfRange(states, nodes.size(), states.length);

            // Store state transitions as map.
            transitions.put(new State(from), new State(to));
        }
    }

    /**
     * Creates a trace of the network.
     *
     * @param startingState Initial state of the network.
     * @return A trace object containing the trace.
     * @throws NetworkTraceException When the starting state is invalid.
     */
    public Trace trace(final int[] startingState) throws NetworkTraceException {
        // Check the star state is valid.
        if (startingState.length != nodes.size()) {
            throw new NetworkTraceException(String.format("You must provide %d starting states.", nodes.size()));
        }

        State currentState = new State(startingState);
        List<State> trace = new ArrayList<>();
        trace.add(currentState);
        State newState;

        // Trace until an attractor is found.
        while (true) {
            newState = transitions.get(currentState);

            // Check if state has been seen before.
            if (trace.contains(newState)) {
                trace.add(newState);
                break;
            }
            trace.add(newState);
            currentState = newState;
        }
        return new Trace(startingState, trace);
    }
}
