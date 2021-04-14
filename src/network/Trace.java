package network;

import java.util.List;

public class Trace {
    public int[] startingState;
    public List<State> trace;
    public List<State> attractor;

    public Trace(int[] startingState, List<State> trace) {
        this.startingState = startingState;
        this.trace = trace;
        this.attractor = findAttractor();
    }

    private List<State> findAttractor() {
        int start = trace.indexOf(trace.get(trace.size() - 1));
        return trace.subList(start, trace.size());
    }

    @Override
    public String toString() {
        return trace.toString();
    }
}
