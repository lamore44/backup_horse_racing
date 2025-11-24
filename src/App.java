import javax.swing.SwingUtilities;
import view.GameFrame;

public class App {
    public static void main(String[] args) {
        // Run the game in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
            gameFrame.setVisible(true);
        });
    }
}
