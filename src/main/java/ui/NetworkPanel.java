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

public class NetworkPanel extends JPanel {
    private BooleanNetwork network;

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    private int selectedTab;

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

    public void setDisplayedAttractor(int displayedAttractor) {
        this.displayedAttractor = displayedAttractor;
    }

    private int displayedAttractor;

    public VisualizationViewer getTransitionViewer() {
        return transitionViewer;
    }

    public DefaultModalGraphMouse getTransitionMouse() {
        return transitionMouse;
    }

    private DefaultModalGraphMouse transitionMouse;

    public NetworkPanel(BooleanNetwork network, int selectedTab, int toDisplay) {
        this.network = network;
        this.selectedTab = selectedTab;
        this.displayedAttractor = toDisplay;
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
        tabs.setSelectedIndex(this.selectedTab);
        add(tabs, BorderLayout.CENTER);

        add(createControls(), BorderLayout.SOUTH);
    }

    private JPanel createDescriptionPanel() {
        String title = network.getTitle();
        String description = network.getDescription();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setPreferredSize(new Dimension(100,125));

        JTextArea t = new JTextArea(title);
        t.setWrapStyleWord(true);
        t.setLineWrap(true);
        t.setEditable(false);
        t.setFocusable(false);
        t.setFont(UIManager.getFont("Label.font"));
        t.setFont(t.getFont().deriveFont(Font.BOLD).deriveFont(22f));
        t.setBorder(BorderFactory.createEmptyBorder());

        JTextArea d = new JTextArea(description);
        d.setLineWrap(true);
        d.setWrapStyleWord(true);
        d.setEditable(false);
        d.setFocusable(false);
        d.setFont(d.getFont().deriveFont(14f));


        JScrollPane titleScrollPane = new JScrollPane(t);
        titleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        titleScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        titleScrollPane.setBorder(BorderFactory.createEmptyBorder());
        titleScrollPane.getViewport().setBackground(Color.WHITE);

        JScrollPane descriptionScrollPane = new JScrollPane(d);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setBorder(BorderFactory.createEmptyBorder());
        descriptionScrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(titleScrollPane);
        panel.add(Box.createRigidArea(new Dimension(10,10)));
        panel.add(descriptionScrollPane);

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
        vv.getRenderContext().setArrowDrawPaintTransformer(arrowPaint);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);
        this.wiringViewer = vv;
        this.wiringMouse = gm;

        GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(vv);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.add(scrollPane, BorderLayout.CENTER);

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
        vv.getRenderContext().setArrowDrawPaintTransformer(arrowPaint);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm);
        this.transitionViewer = vv;
        this.transitionMouse = gm;

        GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(vv);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.add(scrollPane, BorderLayout.CENTER);

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

        List<State> toDisplay = displayedAttractor == -1 ? new ArrayList<>(network.getCurrentTransitions().keySet()) : network.getAttractors().get(displayedAttractor);

        for (State s : toDisplay) {
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
