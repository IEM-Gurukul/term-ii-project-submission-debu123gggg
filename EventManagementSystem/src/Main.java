import src.controller.EventController;
import javax.swing.SwingUtilities;
import src.view.EventView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EventView view = new EventView();
            new EventController(view);
            view.setVisible(true);
        });
    }
}
