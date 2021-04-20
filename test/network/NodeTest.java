package network;

import junit.framework.TestCase;

public class NodeTest extends TestCase {
    @org.junit.Test
    public void testToString() {
        Node node = new Node(0, "test node");
        assertEquals("Node{id=0, name='test node'}", node.toString());

        node = new Node(1, "test node 2");
        assertEquals("Node{id=1, name='test node 2'}", node.toString());

        node = new Node(2, "test node 3");
        assertEquals("Node{id=2, name='test node 3'}", node.toString());
    }
}