import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private final Snake snake;
    private final Timer timer;
    private int cnt = 0;
    private int x;
    private int y;
    private final Random random;
    private int eaten = 0;
    private JTextField field;
    public GamePanel() {
        snake = new Snake(100, 100, 50);
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(200, this);
        field = new JTextField();
        timer.start();
        random = new Random();
        spawnNewOne();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        g.setColor(Color.RED);
        g.fillRect(this.x, this.y, 50, 50);
        g.setColor(Color.BLACK);
        g.drawRect(50,50,2,1000);
        g.drawRect(1050,50,2,1000);
        g.drawRect(50,50,1000,2);
        g.drawRect(50,1050,1000,2);
        g.setFont(new Font("Dialog", Font.PLAIN, 50));
        g.drawString(""+eaten,45,45);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
                snake.move(Direction.left);
                break;
            case 'd':
                snake.move(Direction.right);
                break;
            case 'w':
                snake.move(Direction.up);
                break;
            case 's':
                snake.move(Direction.down);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void spawnNewOne() {
        while (true) {
            int x1 = random.nextInt(50,1050);
            int y1 = random.nextInt(50,1050);
            x1 /= 50;
            x1 *= 50;
            y1 /= 50;
            y1 *= 50;
            if (snake.notInSnake(x1, y1)) {
                x = x1;
                y = y1;
                return;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (snake.ate(x, y)) {
            spawnNewOne();
            cnt = 0;
            snake.getBulk();
            eaten++;
            if (eaten%5==0){
                timer.setDelay(timer.getDelay()-timer.getDelay()/5);
            }
        }else {
            cnt++;
            if (cnt % 30 == 29) {
                spawnNewOne();
            }
        }
        repaint();
    }
}
