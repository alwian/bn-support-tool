package network;

import java.util.List;

/**
 * Class for storing the trace of a network
 * and its attractor.
 * @author Alex Anderson
 */
public final class Trace {
    /**
     * The trace created from the starting state.
     */
    private final List<State> trace;


    /**
     * The attractor for the given trace.
     */
    private final List<State> attractor;

    /**
     * Constructor for a network trace.
     * @param trace The trace for the given starting state.
     */
    public Trace(final List<State> trace) {
        this.trace = trace;
        this.attractor = findAttractor();
    }

    /**
     * Getter for trace.
     * @return The stored trace.
     */
    public List<State> getTrace() {
        return trace;
    }

    /**
     * Getter for the attractor of the trace.
     * @return The attractor for the trace.
     */
    public List<State> getAttractor() {
        return attractor;
    }

    /**
     * Finds the attractor in the trace.
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
