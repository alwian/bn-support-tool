package network;

import java.util.Arrays;

public class State {
    int[] nodeStates;

    public State(int[] nodeStates) {
        this.nodeStates = nodeStates;
    }

    @Override
    public String toString() {
        return "State{" +
                "nodeStates=" + Arrays.toString(nodeStates) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Arrays.equals(nodeStates, state.nodeStates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodeStates);
    }
}
