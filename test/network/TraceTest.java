package network;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TraceTest {
    @org.junit.Test
    public void network1Attractor() {
        String[] paths = new String[] {
                "test_files/test_case_1/g1.csv",
                "test_files/test_case_1/g2.csv",
                "test_files/test_case_1/g3.csv",
        };

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(paths);
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
        assertArrayEquals(expectedAttractor, t.attractor.toArray());
    }

    @org.junit.Test
    public void network2Attractor() {
        String[] paths = new String[] {
                "test_files/test_case_2/g1.csv",
                "test_files/test_case_2/g2.csv",
                "test_files/test_case_2/g3.csv",
                "test_files/test_case_2/g4.csv",
        };

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(paths);
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
        assertArrayEquals(expectedAttractor, t.attractor.toArray());
    }

    @org.junit.Test
    public void network3Attractor() {
        String[] paths = new String[] {
                "test_files/test_case_3/g1.csv",
                "test_files/test_case_3/g2.csv",
                "test_files/test_case_3/g3.csv",
                "test_files/test_case_3/g4.csv",
                "test_files/test_case_3/g5.csv"
        };

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(paths);
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
        assertArrayEquals(expectedAttractor, t.attractor.toArray());
    }

    @org.junit.Test
    public void network4Attractor() {
        String[] paths = new String[] {
                "test_files/test_case_4/g1.csv",
                "test_files/test_case_4/g2.csv",
                "test_files/test_case_4/g3.csv",
        };

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(paths);
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
        assertArrayEquals(expectedAttractor, t.attractor.toArray());
    }

    @org.junit.Test
    public void network5Attractor() {
        String[] paths = new String[] {
                "test_files/test_case_5/g1.csv",
                "test_files/test_case_5/g2.csv",
                "test_files/test_case_5/g3.csv",
                "test_files/test_case_5/g4.csv"
        };

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(paths);
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
        assertArrayEquals(expectedAttractor, t.attractor.toArray());
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