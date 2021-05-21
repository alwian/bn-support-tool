package ui;



import com.google.common.base.Function;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.*;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import network.BooleanNetwork;
import network.State;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class NetworkPanel extends JPanel {
    private BooleanNetwork network;
    private int selected;

    public JButton getExportButton() {
        return exportButton;
    }

    private JButton exportButton;

    public JButton getForwardButton() {
        return forwardButton;
    }


    private JButton forwardButton;

    public JTabbedPane getTabs() {
        return tabs;
    }

    private JTabbedPane tabs;

    public VisualizationViewer<String, String> getWiringViewer() {
        return wiringViewer;
    }

    private VisualizationViewer<String, String> wiringViewer;

    public DefaultModalGraphMouse getWiringMouse() {
        return wiringMouse;
    }

    private DefaultModalGraphMouse wiringMouse;

    private VisualizationViewer transitionViewer;

    public VisualizationViewer getTransitionViewer() {
        return transitionViewer;
    }

    public DefaultModalGraphMouse getTransitionMouse() {
        return transitionMouse;
    }

    private DefaultModalGraphMouse transitionMouse;

    public NetworkPanel(BooleanNetwork network, int selected) {
        this.network = network;
        this.selected = selected;
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createDescriptionPanel(), BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.setBackground(Color.WHITE);
        tabs.setForeground(Color.BLACK);

        tabs.addTab("Wiring Diagram", createWiringTab());
        tabs.addTab("Transition Diagram", createTransitionTab());
        tabs.setSelectedIndex(this.selected);
        add(tabs, BorderLayout.CENTER);

        add(createControls(), BorderLayout.SOUTH);
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

        Function<String, Paint> vertexPaint = i -> network.getCurrentState().getNodeStates()[network.getNodeIndexes().get(i)] == 0 ? Color.RED : Color.GREEN;
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        Function<String, String> vertexLabel = i -> i;
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        Function<String, Shape> vertexShape = i -> new Ellipse2D.Double(-35,-35,70,70);
        vv.getRenderContext().setVertexShapeTransformer(vertexShape);

        Function<String, Paint> edgePaint = i -> Color.WHITE;
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);

        Function<String, Paint> arrowPaint = i -> Color.WHITE;
        vv.getRenderContext().setArrowDrawPaintTransformer(edgePaint);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);
        this.wiringViewer = vv;
        this.wiringMouse = gm;

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(vv);
        return panel;
    }

    private JPanel createTransitionTab() {
        VisualizationViewer<State, String> vv = new VisualizationViewer(new CircleLayout(createTransitionGraph()));
        vv.setBackground(Color.DARK_GRAY);

        Function<State, Paint> vertexPaint = i -> i.equals(network.getCurrentState()) ? Color.GRAY : Color.WHITE;
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
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);
        this.transitionViewer = vv;
        this.transitionMouse = gm;

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(vv);
        return panel;
    }

    private Graph createWiringGraph() {
        Graph<String, String> graph = new DirectedSparseGraph<>();

        String[] nodeNames =  network.getDeterminants().keySet().toArray(new String[0]);
        for (String node : nodeNames) {
            graph.addVertex(node);

            String[] determinants = network.getDeterminants().get(node).toArray(new String[0]);
            for (String determinant : determinants) {
                graph.addEdge(node + " -> " + determinant, determinant, node);
            }
        }
        return graph;
    }
    private Graph createTransitionGraph() {
        Graph<State, String> graph = new DirectedSparseGraph<>();

        for (State s : network.getCurrentTransitions().keySet()) {
            graph.addVertex(s);

            State nextState = network.getCurrentTransitions().get(s);
            graph.addEdge(s.toString() + " -> " + nextState.toString(), s, nextState);
        }
        return graph;
    }

    private JPanel createControls() {
        JPanel panel = new JPanel(new GridLayout(1,1));

        forwardButton = new JButton("Update");
        panel.add(forwardButton);
        return panel;
    }
}
