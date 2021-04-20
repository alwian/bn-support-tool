package network;

/**
 * Class for storing network node information.
 *
 * @author Alex Anderson
 */
public class Node {

    /**
     * The id of the node.
     */
    final int id;

    /**
     * The name of the node.
     */
    final String name;

    /**
     * Constructor for a node.
     *
     * @param id The id of te node.
     * @param name The name of the node.
     */
    public Node(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
