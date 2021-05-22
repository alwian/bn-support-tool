package mvc;

import network.BooleanNetwork;

/**
 * The data model for the tool.
 * @author Alex Anderson
 */
public class Model {

    /**
     * The currently loaded network.
     */
    private BooleanNetwork network;

    /**
     * Constructor for the model.
     * @param network The network to initialise with.
     */
    public Model(final BooleanNetwork network) {
        this.network = network;
    }

    /**
     * Setter for network.
     * @param network The network to update with.
     */
    public void setNetwork(final BooleanNetwork network) {
        this.network = network;
    }

    /**
     * Getter for network.
     * @return The loaded network.
     */
    public BooleanNetwork getNetwork() {
        return this.network;
    }
}
