
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Ship beerShip; // корабль с медведями
    private Ship secondShip;
    private Image back;
    private Image ship_1,ship_2;
    private Image bullet,bullet2;
    public GamePanel() {
        ship_1 = null;
        back = null;
        ship_2 = null;
        try {
            ship_2 = ImageIO.read(new File("Humans_ship_negate.png"));
            ship_1 = ImageIO.read(new File("Humans ship.png"));
            back = ImageIO.read(new File("back.jpg"));
            bullet2 = ImageIO.read(new File("bullet2.png"));
            bullet = ImageIO.read(new File("bullet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        beerShip = new Ship(100, 100, ship_1,bullet);
        secondShip = new Ship(300, 300, ship_2,bullet2);

        setFocusable(true);

        addKeyListener(this);

        Timer t = new Timer(20, this);
        t.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = sSize.height;
        int width= sSize.width;
        g.drawImage(back,0,0,getWidth(),getHeight(),null);
        if (beerShip.isAlive()) {
            beerShip.draw(g);
        }else{
                g.setFont(new Font ("TimesRoman", Font.BOLD | Font.ITALIC, width/20));
                g.setColor(Color.WHITE);
                g.drawString("Second player won",width/6,height/2);
                return;
        }
        if (secondShip.isAlive()) {
            secondShip.draw(g);
        }else{
            g.setFont(new Font ("TimesRoman", Font.BOLD | Font.ITALIC, width/20));
            g.setColor(Color.WHITE);
            g.drawString("First player won",width/6,height/2);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (beerShip.isAlive()) {
            beerShip.moveTo(secondShip.getX() + ship_2.getWidth(null) / 2, secondShip.getY() + ship_2.getHeight(null) / 2);
        }
        if (secondShip.isAlive()) {
            secondShip.moveTo(beerShip.getX() + ship_1.getWidth(null) / 2, beerShip.getY() + ship_1.getHeight(null) / 2);
        }
        if (beerShip.isAlive() && secondShip.isAlive()) {
            secondShip.makeDead(beerShip.intersected(secondShip.getX() + ship_2.getWidth(null) / 2, secondShip.getY() + ship_2.getHeight(null) / 2));
            beerShip.makeDead(secondShip.intersected(beerShip.getX() + ship_1.getWidth(null) / 2, beerShip.getY() + ship_1.getHeight(null) / 2));
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (beerShip.isAlive() && secondShip.isAlive()) {
            if (e.getKeyChar() == 'x') beerShip.shoot(true);
            if (e.getKeyChar() == 'm') secondShip.shoot(true);
        }
        if (beerShip.isAlive()) {
            if (e.getKeyChar() == 'w') beerShip.setUp(true);
            if (e.getKeyChar() == 's') beerShip.setDown(true);
            if (e.getKeyChar() == 'a') beerShip.setLeft(true);
            if (e.getKeyChar() == 'd') beerShip.setRight(true);
        }
        if (secondShip.isAlive()) {
            if (e.getKeyChar() == 'i') secondShip.setUp(true);
            if (e.getKeyChar() == 'k') secondShip.setDown(true);
            if (e.getKeyChar() == 'j') secondShip.setLeft(true);
            if (e.getKeyChar() == 'l') secondShip.setRight(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'w') beerShip.setUp(false);
            if (e.getKeyChar() == 's') beerShip.setDown(false);
            if (e.getKeyChar() == 'a') beerShip.setLeft(false);
            if (e.getKeyChar() == 'd') beerShip.setRight(false);
            if (e.getKeyChar() == 'i') secondShip.setUp(false);
            if (e.getKeyChar() == 'k') secondShip.setDown(false);
            if (e.getKeyChar() == 'j') secondShip.setLeft(false);
            if (e.getKeyChar() == 'l') secondShip.setRight(false);
            if (e.getKeyChar() == 'x') beerShip.shoot(false);
            if (e.getKeyChar() == 'm') secondShip.shoot(false);
    }
}
