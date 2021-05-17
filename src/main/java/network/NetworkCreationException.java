package network;

/**
 * Custom exception for errors in network creation.
 *
 * @author Alex Anderson
 */
public class NetworkCreationException extends Exception {
    /**
     * Constructor for a NetworkCreationException.
     *
     * @param message The error that occurred.
     */
    public NetworkCreationException(final String message) {
        super(message);
    }
}
