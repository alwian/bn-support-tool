package ui;



import com.google.common.base.Function;
import com.sun.org.apache.xml.internal.serializer.ToSAXHandler;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.BasicEdgeArrowRenderingSupport;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import network.BooleanNetwork;
import network.State;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;

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
        VisualizationViewer<String, String> vv = new VisualizationViewer(new CircleLayout(createWiringGraph()));
        vv.setBackground(Color.DARK_GRAY);

        Function<String, Paint> vertexPaint = i -> Color.WHITE;
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        Function<String, String> vertexLabel = i -> i.toString();
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        Function<String, Shape> vertexShape = i -> new Ellipse2D.Double(-35,-35,70,70);
        vv.getRenderContext().setVertexShapeTransformer(vertexShape);

        Function<String, Paint> edgePaint = i -> Color.WHITE;
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);

        Function<String, Paint> arrowPaint = i -> Color.WHITE;
        vv.getRenderContext().setArrowDrawPaintTransformer(edgePaint);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(vv);
        return panel;
    }

    private JPanel createTransitionTab() {
        VisualizationViewer<State, String> vv = new VisualizationViewer(new CircleLayout(createTransitionGraph()));
        vv.setBackground(Color.DARK_GRAY);

        Function<State, Paint> vertexPaint = i -> Color.WHITE;
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        Function<State, String> vertexLabel = i -> i.toString().replaceAll("[\\[\\], ]", "");
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        Function<State, Shape> vertexShape = i -> new Ellipse2D.Double(-35,-35,70,70);
        vv.getRenderContext().setVertexShapeTransformer(vertexShape);

        Function<String, Paint> edgePaint = i -> Color.WHITE;
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);

        Function<String, Paint> arrowPaint = i -> Color.WHITE;
        vv.getRenderContext().setArrowDrawPaintTransformer(edgePaint);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(vv);
        return panel;
    }

    private Graph createWiringGraph() {
        Graph<String, String> graph = new DirectedSparseGraph<>();

        String[] nodeNames =  network.determinants.keySet().toArray(new String[0]);
        for (String node : nodeNames) {
            graph.addVertex(node);

            String[] determinants = network.determinants.get(node).toArray(new String[0]);
            for (String determinant : determinants) {
                graph.addEdge(node + " -> " + determinant, determinant, node);
            }
        }
        return graph;
    }
    private Graph createTransitionGraph() {
        Graph<State, String> graph = new DirectedSparseGraph<>();

        for (State s : network.getTransitions().keySet()) {
            graph.addVertex(s);

            State nextState = network.getTransitions().get(s);
            graph.addEdge(s.toString() + " -> " + nextState.toString(), s, nextState);
        }
        return graph;
    }
}
