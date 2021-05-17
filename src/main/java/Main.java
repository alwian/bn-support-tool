import mvc.Controller;
import mvc.Model;
import mvc.View;
import network.BooleanNetwork;
import network.NetworkCreationException;
import network.NetworkTraceException;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Model m = null;
        try {
            m = new Model(new BooleanNetwork("test_files/test_case_1.csv"));
        } catch (IOException | NetworkCreationException | NetworkTraceException e) {
            JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        View v = new View("Support Tool", m);
        Controller c = new Controller(m,v);
        c.initController();
    }
}
