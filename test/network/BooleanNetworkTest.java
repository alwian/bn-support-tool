package network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class BooleanNetworkTest {
    @org.junit.Test
    public void network1Traces() {
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