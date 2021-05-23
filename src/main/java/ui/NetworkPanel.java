package ui;

import com.google.common.base.Function;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.*;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import network.BooleanNetwork;
import network.State;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Network panel for displaying wiring/transition graphs.
 * @author Alex Anderson
 */
public class NetworkPanel extends JPanel {

    /**
     * The currently loaded network.
     */
    private final BooleanNetwork network;

    /**
     * The currently selected tab.
     */
    private final int selectedTab;

    /**
     * The index of the currently displayed attractor.
     */
    private final int displayedAttractor;

    /**
     * The button for updating the network.
     */
    private JButton forwardButton;

    /**
     * The tabs on the panel.
     */
    private JTabbedPane tabs;

    /**
     * The visualiser for wiring diagrams.
     */
    private VisualizationViewer<String, String> wiringViewer;

    /**
     * The mouse for interacting with wiring diagrams.
     */
    private DefaultModalGraphMouse wiringMouse;

    /**
     * The visualiser for transition diagrams.
     */
    private VisualizationViewer transitionViewer;

    /**
     * The mouse for interacting with transition diagrams.
     */
    private DefaultModalGraphMouse transitionMouse;

    /**
     * Constructor for a network panel.
     * @param network The currently loaded network.
     * @param selectedTab The tab to be selected.
     * @param toDisplay The attractor to display.
     */
    public NetworkPanel(final BooleanNetwork network, final int selectedTab, final int toDisplay) {
        this.network = network;
        this.selectedTab = selectedTab;
        this.displayedAttractor = toDisplay;
        build();
    }

    /**
     * Getter for the updating button.
     * @return The updating button.
     */
    public JButton getForwardButton() {
        return forwardButton;
    }

    /**
     * Getter for the tabs on the panel.
     * @return The tabs on the panel.
     */
    public JTabbedPane getTabs() {
        return tabs;
    }

    /**
     * Getter for the wiring diagram visualiser.
     * @return The wiring diagram visualiser.
     */
    public VisualizationViewer<String, String> getWiringViewer() {
        return wiringViewer;
    }

    /**
     * Getter for the wiring diagram mouse.
     * @return The wiring diagram mouse.
     */
    public DefaultModalGraphMouse getWiringMouse() {
        return wiringMouse;
    }

    /**
     * Getter for the transition diagram visualiser.
     * @return The transition diagram visualiser.
     */
    public VisualizationViewer getTransitionViewer() {
        return transitionViewer;
    }

    /**
     * Getter for the transition diagram mouse.
     * @return The transition diagram mouse.
     */
    public DefaultModalGraphMouse getTransitionMouse() {
        return transitionMouse;
    }

    /**
     * Puts the panel together.
     */
    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Add the title and description.
        add(createDescriptionPanel(), BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.setBackground(Color.WHITE);
        tabs.setForeground(Color.BLACK);

        // Construct and add each tab.
        tabs.addTab("Wiring Diagram", createWiringTab());
        tabs.addTab("Transition Diagram", createTransitionTab());
        tabs.setSelectedIndex(this.selectedTab);
        add(tabs, BorderLayout.CENTER);

        // Add network controls.
        add(createControls(), BorderLayout.SOUTH);
    }

    /**
     * Constructs the title and description panel.
     * @return The constructed title and description.
     */
    private JPanel createDescriptionPanel() {
        // Get the title and description.
        String title = network.getTitle();
        String description = network.getDescription();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setPreferredSize(new Dimension(100, 125));

        // Create the title.
        JTextArea t = new JTextArea(title);
        t.setWrapStyleWord(true);
        t.setLineWrap(true);
        t.setEditable(false);
        t.setFocusable(false);
        t.setFont(UIManager.getFont("Label.font"));
        t.setFont(t.getFont().deriveFont(Font.BOLD).deriveFont(22f));
        t.setBorder(BorderFactory.createEmptyBorder());

        // Create the description.
        JTextArea d = new JTextArea(description);
        d.setLineWrap(true);
        d.setWrapStyleWord(true);
        d.setEditable(false);
        d.setFocusable(false);
        d.setFont(d.getFont().deriveFont(14f));

        // Add scroll support for longer titles.
        JScrollPane titleScrollPane = new JScrollPane(t);
        titleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        titleScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        titleScrollPane.setBorder(BorderFactory.createEmptyBorder());
        titleScrollPane.getViewport().setBackground(Color.WHITE);

        // Add scroll support for longer descriptions.
        JScrollPane descriptionScrollPane = new JScrollPane(d);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setBorder(BorderFactory.createEmptyBorder());
        descriptionScrollPane.getViewport().setBackground(Color.WHITE);

        // Add title and description to panel with space in between.
        panel.add(titleScrollPane);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(descriptionScrollPane);

        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        return panel;
    }

    /**
     * Creates and renders the wiring graph.
     * @return A panel containing a wiring graph
     */
    private JPanel createWiringTab() {
        VisualizationViewer<String, String> vv = new VisualizationViewer(new CircleLayout(createWiringGraph()));
        vv.setBackground(Color.DARK_GRAY);

        // Set vertex colour.
        Function<String, Paint> vertexPaint = i -> network.getCurrentState().getNodeStates()[network.getNodeIndexes().get(i)] == 0 ? Color.RED : Color.GREEN;
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        // Set vertex label.
        Function<String, String> vertexLabel = i -> i;
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        // Set vertex shape.
        Function<String, Shape> vertexShape = i -> new Ellipse2D.Double(-35, -35, 70, 70);
        vv.getRenderContext().setVertexShapeTransformer(vertexShape);

        // Set edge colour.
        Function<String, Paint> edgePaint = i -> Color.WHITE;
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);

        // Set edge arrow colour.
        Function<String, Paint> arrowPaint = i -> Color.WHITE;
        vv.getRenderContext().setArrowDrawPaintTransformer(arrowPaint);

        // Add mouse support.
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);
        this.wiringViewer = vv;
        this.wiringMouse = gm;

        // Add scrollbars.
        GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(vv);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates and renders the transition graph.
     * @return A panel containing a transition graph
     */
    private JPanel createTransitionTab() {
        VisualizationViewer<State, String> vv = new VisualizationViewer(new CircleLayout(createTransitionGraph()));
        vv.setBackground(Color.DARK_GRAY);

        // Set vertex color.
        Function<State, Paint> vertexPaint = i -> i.equals(network.getCurrentState()) ? Color.GRAY : Color.WHITE;
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        // Set vertex label.
        Function<State, String> vertexLabel = i -> i.toString().replaceAll("[\\[\\], ]", "");
        vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        // Set vertex shape.
        Function<State, Shape> vertexShape = i -> new Ellipse2D.Double(-35, -35, 70, 70);
        vv.getRenderContext().setVertexShapeTransformer(vertexShape);

        // Set edge colour.
        Function<String, Paint> edgePaint = i -> Color.WHITE;
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);

        // Set edge arrow colour.
        Function<String, Paint> arrowPaint = i -> Color.WHITE;
        vv.getRenderContext().setArrowDrawPaintTransformer(arrowPaint);

        // Add mouse support.
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);
        this.transitionViewer = vv;
        this.transitionMouse = gm;

        // Add scrollbars.
        GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(vv);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Constructs a wiring graph for the network.
     * @return A wiring graph.
     */
    private Graph createWiringGraph() {
        Graph<String, String> graph = new DirectedSparseGraph<>();

        String[] nodeNames =  network.getDeterminants().keySet().toArray(new String[0]);

        // Go through the nodes to display.
        for (String node : nodeNames) {
            // Add the node.
            graph.addVertex(node);

            String[] determinants = network.getDeterminants().get(node).toArray(new String[0]);
            // Add an edge from each determinant to node.
            for (String determinant : determinants) {
                graph.addEdge(node + " -> " + determinant, determinant, node);
            }
        }
        return graph;
    }

    /**
     * Constructs a transition graph for the network.
     * @return A transition graph.
     */
    private Graph createTransitionGraph() {
        Graph<State, String> graph = new DirectedSparseGraph<>();

        // Check if displaying whole graph or just an attractor.
        List<State> toDisplay = displayedAttractor == -1 ? new ArrayList<>(network.getCurrentTransitions().keySet()) : network.getAttractors().get(displayedAttractor);

        // Go through each state that needs to be displayed.
        for (State s : toDisplay) {
            // Add the state.
            graph.addVertex(s);

            // Add an edge to the next state.
            State nextState = network.getCurrentTransitions().get(s);
            graph.addEdge(s.toString() + " -> " + nextState.toString(), s, nextState);
        }

        return graph;
    }

    /**
     * Creates the controls for the network.
     * @return The network controls.
     */
    private JPanel createControls() {
        JPanel panel = new JPanel(new GridLayout(1, 1));

        forwardButton = new JButton("Update");
        panel.add(forwardButton);
        return panel;
    }
}
