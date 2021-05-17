package ui;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class NetworkPanel extends JPanel {
    public NetworkPanel() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBorder(createBorder());

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(500,500));
        add(createControlBar(), BorderLayout.SOUTH);
    }

    private JPanel createControlBar() {
        JPanel controlBar = new JPanel();
        controlBar.setBackground(Color.GREEN);
        controlBar.setPreferredSize(new Dimension(100,75));
        controlBar.setBorder(createControlBarBorder());
        return controlBar;
    }

    private TitledBorder createControlBarBorder() {
        Border solidBorder = BorderFactory.createLineBorder(Color.white);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(solidBorder, "Controls");
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        return titledBorder;
    }

    private TitledBorder createBorder() {
        Border solidBorder = BorderFactory.createLineBorder(Color.white);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(solidBorder, "Graph goes here");
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        return titledBorder;
    }
}
