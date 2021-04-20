package network;

import junit.framework.TestCase;

import java.util.Arrays;

public class StateTest extends TestCase {

    public void testToString() {
        State state = new State(new int[]{0,0,0});
        assertEquals("[0, 0, 0]", state.toString());

        state = new State(new int[]{1,1,1});
        assertEquals("[1, 1, 1]", state.toString());

        state = new State(new int[]{1,0,1});
        assertEquals("[1, 0, 1]", state.toString());
    }

    public void testEquals() {
        State state1 = new State(new int[]{0,0,0});
        State state2 = new State(new int[]{0,0,0});
        assertEquals(state1, state2);

        state1 = new State(new int[]{1,1,1});
        state2 = new State(new int[]{1,1,1});
        assertEquals(state1, state2);

        state1 = new State(new int[]{1,0,1});
        state2 = new State(new int[]{1,0,1});
        assertEquals(state1, state2);
    }

    public void testHashCode() {
        State state = new State(new int[]{0,0,0});
        assertEquals(Arrays.hashCode(new int[] {0,0,0}), state.hashCode());

        state = new State(new int[]{1,1,1});
        assertEquals(Arrays.hashCode(new int[] {1,1,1}), state.hashCode());

        state = new State(new int[]{1,0,1});
        assertEquals(Arrays.hashCode(new int[] {1,0,1}), state.hashCode());
    }
}