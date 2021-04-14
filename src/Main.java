import network.BooleanNetwork;
import network.NetworkCreationException;
import network.Trace;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "test_net.csv";

        BooleanNetwork network;
        try {
            network = new BooleanNetwork(filePath);
            Trace t = network.trace(new int[]{0,1,1});
            System.out.println(t.attractor);
        } catch (IOException | NetworkCreationException e) {
            System.out.println(e.getMessage());
        }
    }
}
