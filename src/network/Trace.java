package network;

import java.util.List;

/**
 * Class for storing the trace of a network
 * and its attractor.
 *
 * @author Alex Anderson
 */
public class Trace {

    /**
     * The starting state of the network.
     */
    public int[] startingState;

    /**
     * The trace created from the starting state.
     */
    public List<State> trace;

    /**
     * The attractor for the given trace.
     */
    public List<State> attractor;

    /**
     * Constructor for a network trace.
     *
     * @param startingState The state the network started in.
     * @param trace The trace for the given starting state.
     */
    public Trace(int[] startingState, List<State> trace) {
        this.startingState = startingState;
        this.trace = trace;
        this.attractor = findAttractor();
    }

    /**
     * Finds the attractor in the trace.
     *
     * @return The attractor of the trace.
     */
    private List<State> findAttractor() {
        int start = trace.indexOf(trace.get(trace.size() - 1));
        return trace.subList(start, trace.size());
    }

    @Override
    public String toString() {
        return trace.toString();
    }
}
