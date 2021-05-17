package ui;

import network.BooleanNetwork;
import network.State;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Map;

public class TransitionPanel extends JPanel {
    Map<State, State> transitionTable;

    public TransitionPanel(Map<State,State> transitions) {
        this.transitionTable = transitions;
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.RED);
        setPreferredSize(new Dimension(200,100));
        setBorder(createBorder());

        System.out.println(this.transitionTable);
        if (this.transitionTable != null) {
            add(createTransitionTable());
        }
    }

    private TitledBorder createBorder() {
        Border solidBorder = BorderFactory.createLineBorder(Color.white);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(solidBorder, "Transition Table");
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        return titledBorder;
    }

    private JTable createTransitionTable() {
        String[] columns = {"To", "From"};
        String[][] data = new String[transitionTable.size()][2];

        Object[] keys = transitionTable.keySet().toArray();

        for (int x = 0; x < keys.length; x++) {
            State key = (State) keys[x];
            data[x][0] = key.toString();
            data[x][1] = transitionTable.get(key).toString();
        }

        return new JTable(data, columns);
    }
}
