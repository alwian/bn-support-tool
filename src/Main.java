import network.BooleanNetwork;
import network.NetworkCreationException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "test_net.csv";

        BooleanNetwork network;
        try {
            network = new BooleanNetwork(filePath);
            network.trace(new int[]{0,1,1});
        } catch (IOException | NetworkCreationException e) {
            System.out.println(e.getMessage());
        }
    }
}
