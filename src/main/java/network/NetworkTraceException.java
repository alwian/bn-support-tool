package network;

/**
 * Custom exception for errors in network tracing.
 * @author Alex Anderson
 */
public class NetworkTraceException extends Exception {
    /**
     * Constructor for a NetworkTraceException.
     * @param message The error that occurred.
     */
    public NetworkTraceException(final String message) {
        super(message);
    }
}
