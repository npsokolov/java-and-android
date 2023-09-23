import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame() throws HeadlessException {
        setBounds(500, 200, 1150, 1150);
        add(new GamePanel());
        setVisible(true);
    }
}
