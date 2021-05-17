package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing utility methods.
 *
 * @author Alex Anderson
 */
public class Util {

    /**
     * Generates all the possible starting states of a given size.
     *
     * @param nodeCount Number of nodes in the network.
     * @return All possible starting states.
     */
    public static List<int[]> getStartingStates(final int nodeCount) {
        List<int[]> states = new ArrayList<>();

        // Get the highest value needed.
        int highestBinaryVal = (int) Math.pow(2, nodeCount);

        // Count up to the highest value.
        for (int x = 0; x < highestBinaryVal; x++) {
            // Get the digits of the binary version of the
            // current number, padded with 0's on the left.
            String[] strStates = String.format("%" + nodeCount + "s", Integer.toBinaryString(x)).replace(" ", "0").split("");

            // Store the binary digits as integers representing a starting state.
            int[] intStates = new int[strStates.length];
            for (int y = 0; y < strStates.length; y++) {
                intStates[y] = Integer.parseInt(strStates[y]);
            }
            states.add(intStates);
        }
        return states;
    }
}
