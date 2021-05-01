package network;

import java.util.List;

/**
 * Class for storing the trace of a network
 * and its attractor.
 *
 * @author Alex Anderson
 */
public final class Trace {

    /**
     * The starting state of the network.
     */
    private final int[] startingState;

    /**
     * The trace created from the starting state.
     */
    private List<State> trace;

    /**
     * The attractor for the given trace.
     */
    public List<State> attractor;

    /**
     * Accessor for trace.
     *
     * @return trace
     */
    public List<State> getTrace() {
        return trace;
    }

    /**
     * Constructor for a network trace.
     *
     * @param trace The trace for the given starting state.
     */
    public Trace(final List<State> trace) {
        this.startingState = trace.get(0).getNodeStates();
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
