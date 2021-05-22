package network;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TraceTest {
    @org.junit.Test
    public void network1Attractor() {
        String path = "test_files/test_case_1.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        State[] expectedAttractor = new State[]{new State(new int[]{1,0,1}), new State(new int[]{0,1,0}), new State(new int[]{1,0,1})};
        Trace t = null;
        try {
            t = network.trace(new int[]{0,1,1});
        } catch (NetworkTraceException e) {
            fail(e.getMessage());
        }
        assertArrayEquals(expectedAttractor, t.getAttractor().toArray());
    }

    @org.junit.Test
    public void network2Attractor() {
        String path = "test_files/test_case_2.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        State[] expectedAttractor = new State[]{new State(new int[]{1,0,0,0}), new State(new int[]{0,1,0,1}), new State(new int[]{0,0,1,1}), new State(new int[]{1,0,0,0})};
        Trace t = null;
        try {
            t = network.trace(new int[]{0,0,1,0});
        } catch (NetworkTraceException e) {
            fail(e.getMessage());
        }
        assertArrayEquals(expectedAttractor, t.getAttractor().toArray());
    }

    @org.junit.Test
    public void network3Attractor() {
        String path = "test_files/test_case_3.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        State[] expectedAttractor = new State[]{new State(new int[]{1,0,0,1,0}), new State(new int[]{1,0,0,1,0})};
        Trace t = null;
        try {
            t = network.trace(new int[]{1,0,0,1,0});
        } catch (NetworkTraceException e) {
            fail(e.getMessage());
        }
        assertArrayEquals(expectedAttractor, t.getAttractor().toArray());
    }

    @org.junit.Test
    public void network4Attractor() {
        String path = "test_files/test_case_4.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        State[] expectedAttractor = new State[]{new State(new int[]{0,1,1}), new State(new int[]{1,0,1}), new State(new int[]{1,1,0}), new State(new int[]{0,1,1})};
        Trace t = null;
        try {
            t = network.trace(new int[]{0,1,1});
        } catch (NetworkTraceException e) {
            fail(e.getMessage());
        }
        assertArrayEquals(expectedAttractor, t.getAttractor().toArray());
    }

    @org.junit.Test
    public void network5Attractor() {
        String path = "test_files/test_case_5.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        State[] expectedAttractor = new State[]{new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0})};
        Trace t = null;
        try {
            t = network.trace(new int[]{1,0,0,0});
        } catch (NetworkTraceException e) {
            fail(e.getMessage());
        }
        assertArrayEquals(expectedAttractor, t.getAttractor().toArray());
    }

    @org.junit.Test
    public void toStringTest() {
        List<State> stateList = new ArrayList<>();
        stateList.add(new State(new int[]{1,1,1}));
        stateList.add(new State(new int[]{1,0,1}));
        stateList.add(new State(new int[]{1,1,1}));

        Trace t = new Trace(stateList);

        assertEquals("[[1, 1, 1], [1, 0, 1], [1, 1, 1]]", t.toString());
    }
}