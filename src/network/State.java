package network;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing the state of a network.
 *
 * @author Alex Anderson
 */
public class State {

    /**
     * The states of each node in the network.
     */
    int[] nodeStates;

    /**
     * Constructor for a network state.
     *
     * @param nodeStates The state of each node in the network.
     */
    public State(int[] nodeStates) {
        this.nodeStates = nodeStates;
    }

    @Override
    public String toString() {
        return Arrays.toString(nodeStates);
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
