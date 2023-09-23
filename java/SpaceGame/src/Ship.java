import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class Ship {
    private int x = 0, y = 0;
    private Image avatar;
    private Image bullet;
    private boolean left = false, right = false, up = false, down = false;
    private double r = 0; // угол поворота
    private LinkedList<Bullet> bullets;
    private int health;
    private boolean isShooting;


    public Ship(int x, int y, Image avatar, Image bullet) {
        Random random = new Random();
        this.x = x;
        this.y = y;
        this.avatar = avatar;
        this.bullet = bullet;
        this.bullets = new LinkedList<>();
        this.health = 20;
        this.isShooting = false;
    }

    public boolean isAlive(){
        return this.health>0;
    }
    public void makeDead(int damage){
        this.health-=damage;
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int intersected(int x, int y){
        int damage = 0;
        int m = bullets.size();
        for (int i = 0; i < m; i++) {
            Bullet b = bullets.get(i);
            if (Math.abs(x-b.x)<30 && Math.abs(y-b.y)<30){
                bullets.remove(i);
                m--;
                damage++;
                i--;
            }
        }
        return damage;
    }

    public void moveTo(int posx,int posy) {
        if (isShooting) {
            bullets.addFirst(new Bullet(x + avatar.getWidth(null) / 2, y + avatar.getHeight(null) / 2, 2, r - 3.14));
        }
        for (Bullet b: bullets) {
            double d = (Math.sqrt(Math.abs(posx-b.x)*Math.abs(posx-b.x)+Math.abs(posy-b.y)*Math.abs(posy-b.y)));
            double ncs = ((double)posx-(double)b.x)/d;
            double nsn = ((double)posy-(double)b.y)/d;
            double dx = (int) (b.cs * (8 ));
            double dy = (int) (b.sn * (8 ));
            double gip = (double)(dx*dx+dy*dy);
            if (dx>0) {
                b.cs = Math.sqrt(dx / gip)*0.8+ ncs*0.2;
            }else{
                b.cs = -Math.sqrt(-dx / gip)*0.8+ ncs*0.2;
            }
            if (dy>0) {
                b.sn = Math.sqrt(dy / gip)*0.8+ nsn*0.2;
            }else{
                b.sn = -Math.sqrt(-dy / gip)*0.8+ nsn*0.2;
            }
            b.sn = nsn;
            b.cs = ncs;
            b.x = b.x + (int)dx;
            b.y = b.y + (int)dy;
            b.timeLived++;
        }
        int dx = (int) (Math.cos(r) * 10);
        int dy = (int) (Math.sin(r) * 10);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = sSize.height;
        int width= sSize.width;
        if (up) {
            x = x - dx;
            y = y - dy;
            x = (x + width) % width;
            y = (y + height) % height;
        }

        if (down){
            x = x + dx;
            y = y + dy;
            x = (x + width) % width;
            y = (y + height) % height;
        }
        if (right)
            r = r + 0.1;
        if (left)
            r = r - 0.1;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(r, x + avatar.getWidth(null) / 2, y + avatar.getHeight(null) / 2);
        g2d.drawImage(avatar, this.x, this.y, null);
        g2d.rotate(-r, x + avatar.getWidth(null) / 2, y + avatar.getHeight(null) / 2);
        int m = bullets.size();
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = sSize.height;
        int width= sSize.width;
        for (int i = 0; i < m; i++) {
            if (bullets.get(i).timeLived%250==249 || bullets.get(i).x>width || bullets.get(i).x<0 || bullets.get(i).y<0 || bullets.get(i).y>height){
                bullets.remove(i);
                m--;
                i--;
            }
        }
        for (Bullet b: bullets) {
            g2d.drawImage(this.bullet,b.x,b.y,null);
        }
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x,y-10,(int)((double)avatar.getWidth(null)*((double)this.health/(double)20)),7);
    }
    public void shoot(boolean fire){
        this.isShooting = fire;
    }



}
