package network;

import java.util.Arrays;

/**
 * Class for storing the state of a network.
 *
 * @author Alex Anderson
 */
public final class State {

    /**
     * The states of each node in the network.
     */
    private final int[] nodeStates;

    /**
     * Accessor for nodeStates.
     *
     * @return nodeStates
     */
    public int[] getNodeStates() {
        return nodeStates;
    }

    /**
     * Constructor for a network state.
     *
     * @param nodeStates The state of each node in the network.
     */
    public State(final int[] nodeStates) {
        this.nodeStates = nodeStates;
    }

    @Override
    public String toString() {
        return Arrays.toString(nodeStates);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        State state = (State) o;
        return Arrays.equals(nodeStates, state.nodeStates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodeStates);
    }
}
