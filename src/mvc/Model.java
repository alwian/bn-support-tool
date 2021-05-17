package mvc;

import network.BooleanNetwork;

public class Model {
    private BooleanNetwork network;

    public Model() {}

    public void setNetwork(BooleanNetwork network) {
        this.network = network;
    }

    public BooleanNetwork getNetwork() {
        return this.network;
    }
}
