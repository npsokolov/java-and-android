import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        setUndecorated(true);
        setResizable(false);
        setSize(sSize);
        add(new GamePanel());
        setVisible(true);
    }
}
