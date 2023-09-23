public class Bullet {
    public int x;
    public int y;
    public int speed;
    public double angle;
    public double cs;
    public double sn;
    public int timeLived = 0;

    public Bullet(int x, int y, int speed, double angle) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
        this.cs = Math.cos(angle);
        this.sn = Math.sin(angle);
    }
}
