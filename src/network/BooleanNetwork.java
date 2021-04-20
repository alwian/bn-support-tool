package network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BooleanNetwork {
    public List<Node> nodes = new ArrayList<>();
    Map<State, State> transitions = new HashMap<>();

    public BooleanNetwork(String path) throws IOException, NetworkCreationException {
        int extensionStart = path.lastIndexOf(".");
        String extension = path.substring(extensionStart + 1).toUpperCase();

        if (extension.equals("CSV")) {
            createFromCSV(path);
        } else {
            throw new NetworkCreationException("File type must be CSV.");
        }
    }

    private void createFromCSV(String path) throws IOException, NetworkCreationException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        List<List<String>> lines = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            lines.add(Arrays.asList(line.split(",")));
        }

        List<String> headings = lines.remove(0);

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

        if (headingCounts.values().stream().anyMatch(c -> c != 2)) {
            throw new NetworkCreationException("There must be a before and after heading for each node.");
        }


        for (int x = 0; x < headings.size() / 2; x++) {
            nodes.add(new Node(x, headings.get(x)));
        }

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

    public Trace trace(int[] startingState) throws NetworkTraceException {
        if (startingState.length != nodes.size()) {
            throw new NetworkTraceException(String.format("You must provide %d starting states.", nodes.size()));
        }

        State currentState = new State(startingState);
        List<State> trace = new ArrayList<>();
        trace.add(currentState);
        State newState;

        while (true) {
            newState = transitions.get(currentState);

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
