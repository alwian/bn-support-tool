import mvc.Controller;
import mvc.Model;
import mvc.View;
import network.BooleanNetwork;
import network.NetworkCreationException;
import network.NetworkTraceException;

import javax.swing.*;
import java.io.IOException;

/**
 * Main class for the program.
 * @author Alex Anderson
 */
public class Main {
    /**
     * Main method.
     * @param args Specified arguments.
     */
    public static void main(String[] args) {
        // Initialise the model.
        Model m = null;
        try {
            m = new Model(new BooleanNetwork("test_files/test_case_1.csv"));
        } catch (IOException | NetworkCreationException | NetworkTraceException e) {
            JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        // Create the view and register it with the controller.
        View v = new View("Support Tool", m);
        Controller c = new Controller(m,v);
        c.initController();
    }
}
