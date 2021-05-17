import mvc.Controller;
import mvc.Model;
import mvc.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model m = new Model();
        View v = new View("Support Tool");

        Controller c = new Controller(m,v);
    }
}
