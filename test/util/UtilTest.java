package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void getStartingStates() {
        int nodeCount = 3;
        List<int[]> expectedStates = new ArrayList<>();
        expectedStates.add(new int[]{0,0,0});
        expectedStates.add(new int[]{0,0,1});
        expectedStates.add(new int[]{0,1,0});
        expectedStates.add(new int[]{0,1,1});
        expectedStates.add(new int[]{1,0,0});
        expectedStates.add(new int[]{1,0,1});
        expectedStates.add(new int[]{1,1,0});
        expectedStates.add(new int[]{1,1,1});

        assertArrayEquals(expectedStates.toArray(), Util.getStartingStates(nodeCount).toArray());
        expectedStates.clear();

        nodeCount = 4;
        expectedStates.add(new int[]{0,0,0,0});
        expectedStates.add(new int[]{0,0,0,1});
        expectedStates.add(new int[]{0,0,1,0});
        expectedStates.add(new int[]{0,0,1,1});
        expectedStates.add(new int[]{0,1,0,0});
        expectedStates.add(new int[]{0,1,0,1});
        expectedStates.add(new int[]{0,1,1,0});
        expectedStates.add(new int[]{0,1,1,1});
        expectedStates.add(new int[]{1,0,0,0});
        expectedStates.add(new int[]{1,0,0,1});
        expectedStates.add(new int[]{1,0,1,0});
        expectedStates.add(new int[]{1,0,1,1});
        expectedStates.add(new int[]{1,1,0,0});
        expectedStates.add(new int[]{1,1,0,1});
        expectedStates.add(new int[]{1,1,1,0});
        expectedStates.add(new int[]{1,1,1,1});

        assertArrayEquals(expectedStates.toArray(), Util.getStartingStates(nodeCount).toArray());

    }
}