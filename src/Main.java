import controller.EventController;
import javax.swing.*;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        // Use system look & feel as base, then override with custom styling
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainFrame(new EventController()));
    }
}