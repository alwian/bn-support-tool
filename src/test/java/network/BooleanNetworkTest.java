package network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class BooleanNetworkTest {
    @org.junit.Test
    public void network1Traces() {
        String path = "test_files/test_case_1.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        List<int[]> startingStates = new ArrayList<>();
        startingStates.add(new int[] {0,0,0});
        startingStates.add(new int[] {0,0,1});
        startingStates.add(new int[] {0,1,0});
        startingStates.add(new int[] {0,1,1});
        startingStates.add(new int[] {1,0,0});
        startingStates.add(new int[] {1,0,1});
        startingStates.add(new int[] {1,1,0});
        startingStates.add(new int[] {1,1,1});

        List<State[]> expectedTraces = new ArrayList<>();
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,}), new State(new int[]{0,0,0,})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,1}), new State(new int[]{0,0,0}), new State(new int[]{0,0,0})});
        expectedTraces.add(new State[] {new State(new int[]{0,1,0}), new State(new int[]{1,0,1}), new State(new int[]{0,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{0,1,1}), new State(new int[]{1,0,1}), new State(new int[]{0,1,0}), new State(new int[]{1,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0}), new State(new int[]{0,1,0}), new State(new int[]{1,0,1}), new State(new int[]{0,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,1}), new State(new int[]{0,1,0}), new State(new int[]{1,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,1,0}), new State(new int[]{1,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,1,1}), new State(new int[]{1,1,0}), new State(new int[]{1,1,0})});

        for (int x = 0; x < startingStates.size(); x++) {
            Trace t = null;
            try {
                t = network.trace(startingStates.get(x));
            } catch (NetworkTraceException e) {
                fail(e.getMessage());
            }
            assertArrayEquals(expectedTraces.get(x), t.getTrace().toArray());
        }
    }

    @org.junit.Test
    public void network2Traces() {
        String path = "test_files/test_case_2.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        List<int[]> startingStates = new ArrayList<>();
        startingStates.add(new int[] {0,0,0,0});
        startingStates.add(new int[] {0,0,0,1});
        startingStates.add(new int[] {0,0,1,0});
        startingStates.add(new int[] {0,0,1,1});
        startingStates.add(new int[] {1,0,0,0});
        startingStates.add(new int[] {1,0,0,1});
        startingStates.add(new int[] {1,0,1,0});
        startingStates.add(new int[] {1,0,1,1});

        List<State[]> expectedTraces = new ArrayList<>();
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,0}), new State(new int[]{0,0,0,1}), new State(new int[]{0,0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,1}), new State(new int[]{0,0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,1,0}), new State(new int[]{1,0,0,0}), new State(new int[]{0,1,0,1}), new State(new int[]{0,0,1,1}), new State(new int[]{1,0,0,0})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,1,1}), new State(new int[]{1,0,0,0}), new State(new int[]{0,1,0,1}), new State(new int[]{0,0,1,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0,0}), new State(new int[]{0,1,0,1}), new State(new int[]{0,0,1,1}), new State(new int[]{1,0,0,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0,1}), new State(new int[]{0,1,0,1}), new State(new int[]{0,0,1,1}), new State(new int[]{1,0,0,0}), new State(new int[]{0,1,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,1,0}), new State(new int[]{1,1,0,0}), new State(new int[]{0,1,1,1}), new State(new int[]{1,0,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,1,1}), new State(new int[]{1,1,0,0}), new State(new int[]{0,1,1,1}), new State(new int[]{1,0,1,0}), new State(new int[]{1,1,0,0})});

        for (int x = 0; x < startingStates.size(); x++) {
            Trace t = null;
            try {
                t = network.trace(startingStates.get(x));
            } catch (NetworkTraceException e) {
                fail(e.getMessage());
            }
            assertArrayEquals(expectedTraces.get(x), t.getTrace().toArray());
        }
    }

    @org.junit.Test
    public void network3Traces() {
        String path = "test_files/test_case_3.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        List<int[]> startingStates = new ArrayList<>();
        startingStates.add(new int[] {0,0,0,0,0});
        startingStates.add(new int[] {0,0,0,0,1});
        startingStates.add(new int[] {0,0,0,1,0});
        startingStates.add(new int[] {0,0,0,1,1});
        startingStates.add(new int[] {1,0,0,0,0});
        startingStates.add(new int[] {1,0,0,0,1});
        startingStates.add(new int[] {1,0,0,1,0});
        startingStates.add(new int[] {1,0,0,1,1});

        List<State[]> expectedTraces = new ArrayList<>();
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,0,0}), new State(new int[]{0,0,1,1,0}), new State(new int[]{1,0,1,1,1}), new State(new int[]{1,1,0,0,1}), new State(new int[]{0,1,0,0,0}), new State(new int[]{0,0,1,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,0,1}), new State(new int[]{0,1,1,1,0}), new State(new int[]{1,0,1,1,1}), new State(new int[]{1,1,0,0,1}), new State(new int[]{0,1,0,0,0}), new State(new int[]{0,0,1,1,0}), new State(new int[]{1,0,1,1,1})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,1,0}), new State(new int[]{1,0,1,1,0}), new State(new int[]{1,0,0,1,1}), new State(new int[]{1,1,0,0,0}), new State(new int[]{0,0,0,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,1,1}), new State(new int[]{1,1,1,1,0}), new State(new int[]{1,0,0,1,1}), new State(new int[]{1,1,0,0,0}), new State(new int[]{0,0,0,1,0}), new State(new int[]{1,0,1,1,0}), new State(new int[]{1,0,0,1,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0,0,0}), new State(new int[]{0,0,0,1,0}), new State(new int[]{1,0,1,1,0}), new State(new int[]{1,0,0,1,1}), new State(new int[]{1,1,0,0,0}), new State(new int[]{0,0,0,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0,0,1}), new State(new int[]{0,1,0,0,0}), new State(new int[]{0,0,1,1,0}), new State(new int[]{1,0,1,1,1}), new State(new int[]{1,1,0,0,1}), new State(new int[]{0,1,0,0,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0,1,0}), new State(new int[]{1,0,0,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0,1,1}), new State(new int[]{1,1,0,0,0}), new State(new int[]{0,0,0,1,0}), new State(new int[]{1,0,1,1,0}), new State(new int[]{1,0,0,1,1})});

        for (int x = 0; x < startingStates.size(); x++) {
            Trace t = null;
            try {
                t = network.trace(startingStates.get(x));
            } catch (NetworkTraceException e) {
                fail(e.getMessage());
            }
            assertArrayEquals(expectedTraces.get(x), t.getTrace().toArray());
        }
    }

    @org.junit.Test
    public void network4Traces() {
        String path = "test_files/test_case_4.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        List<int[]> startingStates = new ArrayList<>();
        startingStates.add(new int[] {0,0,0});
        startingStates.add(new int[] {0,0,1});
        startingStates.add(new int[] {0,1,0});
        startingStates.add(new int[] {0,1,1});
        startingStates.add(new int[] {1,0,0});
        startingStates.add(new int[] {1,0,1});
        startingStates.add(new int[] {1,1,0});
        startingStates.add(new int[] {1,1,1});

        List<State[]> expectedTraces = new ArrayList<>();
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,}), new State(new int[]{0,0,0,})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,1}), new State(new int[]{1,0,0}), new State(new int[]{0,1,0}), new State(new int[]{0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{0,1,0}), new State(new int[]{0,0,1}), new State(new int[]{1,0,0}), new State(new int[]{0,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{0,1,1}), new State(new int[]{1,0,1}), new State(new int[]{1,1,0}), new State(new int[]{0,1,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0}), new State(new int[]{0,1,0}), new State(new int[]{0,0,1}), new State(new int[]{1,0,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,1}), new State(new int[]{1,1,0}), new State(new int[]{0,1,1}), new State(new int[]{1,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,1,0}), new State(new int[]{0,1,1}), new State(new int[]{1,0,1}), new State(new int[]{1,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,1,1}), new State(new int[]{1,1,1})});

        for (int x = 0; x < startingStates.size(); x++) {
            Trace t = null;
            try {
                t = network.trace(startingStates.get(x));
            } catch (NetworkTraceException e) {
                fail(e.getMessage());
            }
            assertArrayEquals(expectedTraces.get(x), t.getTrace().toArray());
        }
    }

    @org.junit.Test
    public void network5Traces() {
        String path = "test_files/test_case_5.csv";

        BooleanNetwork network = null;
        try {
            network = new BooleanNetwork(path);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        List<int[]> startingStates = new ArrayList<>();
        startingStates.add(new int[] {0,0,0,0});
        startingStates.add(new int[] {0,0,0,1});
        startingStates.add(new int[] {0,0,1,0});
        startingStates.add(new int[] {0,0,1,1});
        startingStates.add(new int[] {1,0,0,0});
        startingStates.add(new int[] {1,0,0,1});
        startingStates.add(new int[] {1,0,1,0});
        startingStates.add(new int[] {1,0,1,1});

        List<State[]> expectedTraces = new ArrayList<>();
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,0}), new State(new int[]{1,0,1,0}), new State(new int[]{0,1,1,1}), new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,0,1}), new State(new int[]{1,0,1,0}), new State(new int[]{0,1,1,1}), new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,1,0}), new State(new int[]{1,0,1,1}), new State(new int[]{0,1,1,1}), new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{0,0,1,1}), new State(new int[]{1,0,1,1}), new State(new int[]{0,1,1,1}), new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0,0}), new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,1,0}), new State(new int[]{0,1,1,1}), new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1})});
        expectedTraces.add(new State[] {new State(new int[]{1,0,1,1}), new State(new int[]{0,1,1,1}), new State(new int[]{1,0,0,1}), new State(new int[]{0,1,1,0}), new State(new int[]{1,0,0,1})});

        for (int x = 0; x < startingStates.size(); x++) {
            Trace t = null;
            try {
                t = network.trace(startingStates.get(x));
            } catch (NetworkTraceException e) {
                fail(e.getMessage());
            }
            assertArrayEquals(expectedTraces.get(x), t.getTrace().toArray());
        }
    }
}