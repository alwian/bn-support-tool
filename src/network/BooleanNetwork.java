package network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BooleanNetwork {
    private State currentState;
    List<Node> nodes = new ArrayList<>();
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
        System.out.println(lines);

        List<String> headings = lines.remove(0);

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
        System.out.println(nodes);

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
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
            }

            int[] from = Arrays.copyOfRange(states, 0, nodes.size());
            int[] to = Arrays.copyOfRange(states, nodes.size(), states.length);

            // Store state transitions as map.
            System.out.printf("%s -> %s\n", Arrays.toString(from), Arrays.toString(to));
            transitions.put(new State(from), new State(to));

            System.out.println(currentState);
            for (State i : transitions.keySet()) {
                System.out.printf("%s -----> %s\n", i, transitions.get(i));
            }
        }
    }

    public List<State> trace(int[] startingState) {
        currentState = new State(startingState);
        List<State> trace = new ArrayList<>();
        trace.add(currentState);
        State newState;

        while (true) {
            System.out.println(currentState);
            System.out.println(transitions.get(currentState));

            newState = transitions.get(currentState);

            if (trace.contains(newState)) {
                trace.add(newState);
                currentState = newState;
                break;
            }
            trace.add(newState);
            currentState = newState;
        }
        System.out.println(trace);
        return trace;
    }
}
