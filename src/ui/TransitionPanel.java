package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TransitionPanel extends JPanel {
    public TransitionPanel() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout());
        setBackground(Color.RED);
        setPreferredSize(new Dimension(200,100));

        setBorder(createBorder());
    }

    private TitledBorder createBorder() {
        Border solidBorder = BorderFactory.createLineBorder(Color.white);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(solidBorder, "Transition Table");
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        return titledBorder;
    }

}
