package util;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<int[]> getStartingStates(int nodeCount) {
        List<int[]> states = new ArrayList<>();

        int highestBinaryVal = (int) Math.pow(2, nodeCount);
        for (int x = 0; x < highestBinaryVal; x++) {
            String[] strStates = String.format("%" + nodeCount + "s", Integer.toBinaryString(x)).replace(" ", "0").split("");
            int[] intStates = new int[strStates.length];
            for (int y = 0; y < strStates.length; y++) {
                intStates[y] = Integer.parseInt(strStates[y]);
            }
            states.add(intStates);
        }
        return states;
    }
}
