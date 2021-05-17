package mvc;

public class Controller {
    Model model;
    View view;

    public Controller(Model m, View v) {
        this.model = m;
        this.view = v;
    }
}
