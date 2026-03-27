import controller.EventController;
import view.EventView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            EventView view = new EventView();
            new EventController(view);
            view.setVisible(true);
        });
    }
}
