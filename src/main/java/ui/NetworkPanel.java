package ui;



import com.google.common.base.Function;
import com.sun.org.apache.xml.internal.serializer.ToSAXHandler;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import network.BooleanNetwork;
import network.State;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NetworkPanel extends JPanel {
    private BooleanNetwork network;

    public NetworkPanel(BooleanNetwork network) {
        this.network = network;
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createDescriptionPanel(), BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(Color.BLACK);

        tabbedPane.addTab("Wiring Diagram", createWiringTab());
        tabbedPane.addTab("Transition Diagram", createTransitionTab());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createDescriptionPanel() {
        String title = network.getTitle();
        String description = network.getDescription();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JTextArea t = new JTextArea(title);
        t.setEditable(false);
        t.setForeground(Color.BLACK);
        t.setBackground(Color.WHITE);
        t.setFont(t.getFont().deriveFont(Font.BOLD));

        JTextArea d = new JTextArea(description);
        d.setEditable(false);
        d.setForeground(Color.BLACK);
        d.setBackground(Color.WHITE);

        panel.add(t);
        panel.add(d);

        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20,20,20,20));
        return panel;
    }

    private JPanel createWiringTab() {
        Graph<Integer, String> graph = new DirectedSparseGraph<>();
        Map<String, Integer> vertexIDs = new HashMap<>();
        String[] nodeNames =  network.determinants.keySet().toArray(new String[0]);

        for (int x = 0; x < nodeNames.length; x++) {
            vertexIDs.put(nodeNames[x], x);
            graph.addVertex(x);
        }

        for (String nodeName : nodeNames) {
            System.out.print(nodeName + " -> ");
            String[] determinants = network.determinants.get(nodeName).toArray(new String[0]);
            System.out.println(Arrays.toString(determinants));
            for (int y = 0; y < network.determinants.get(nodeName).size(); y++) {
                graph.addEdge(nodeName + " -> " + determinants[y], vertexIDs.get(determinants[y]), vertexIDs.get(nodeName));
            }
        }


        VisualizationViewer<Integer, Number> vv = new VisualizationViewer(new CircleLayout(graph));
        vv.getRenderContext().setVertexLabelTransformer(i -> nodeNames[i]);

        Function<Integer, Paint> vertexPaint = i -> Color.WHITE;
        Function<Integer, Shape> vertexSize = i -> new Ellipse2D.Double(-35,-35,70,70);

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.setForeground(Color.BLACK);
        vv.setBackground(Color.DARK_GRAY);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(vv);

        return panel;
    }

    private JPanel createTransitionTab() {
        Graph<Integer, String> graph = new DirectedSparseGraph<>();
        Map<String, Integer> vertexIDs = new HashMap<>();

        State[] states = network.getTransitions().keySet().toArray(new State[0]);
        for (int x = 0; x < states.length; x++) {
            vertexIDs.put(states[x].toString(), x);
            graph.addVertex(x);
        }

        for (State state : states) {
            graph.addEdge(state.toString() + " -> " + network.getTransitions().get(state).toString(), vertexIDs.get(state.toString()), vertexIDs.get(network.getTransitions().get(state).toString()));
        }

        VisualizationViewer<Integer, Number> vv = new VisualizationViewer(new CircleLayout(graph));

        vv.getRenderContext().setVertexLabelTransformer(i -> states[i].toString().replaceAll("[\\[\\], ]", ""));

        Function<Integer, Paint> vertexPaint = i -> Color.WHITE;
        Function<Integer, Shape> vertexSize = i -> new Ellipse2D.Double(-35,-35,70,70);

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.setForeground(Color.BLACK);
        vv.setBackground(Color.DARK_GRAY);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(vv);

        return panel;
    }
}
