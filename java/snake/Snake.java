import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Snake {
    private class s {
        public int x;
        public int y;

        public s(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int x = 0;
    private int y = 0;
    private int h = 0;
    private s last;
    private LinkedList<s> lst;
    private boolean isDead = false;
    //private int change = 50;
    private Direction direction = Direction.up;

    private Direction nextDir = Direction.up;

    private void changeDirection(Direction d) {
        this.direction = d;
    }

    public boolean ate(int x, int y) {
        return this.x == x && this.y == y;
    }

    private void drawSnake(Graphics g) {
        int c = 1;
        for (s obj : lst) {
            g.setColor(new Color(((c*10)/255)%2==1 ? 255-((c * 10) % 256) : (c * 10) % 256, (((c*11)/255)%2)==1 ? 255- ((c * 11) % 256) : c * 11 % 256, (((c*12)/255)%2)==1 ? 255- ((c * 12) % 256) : (c * 12) % 256));
            g.fillRect(obj.x, obj.y, h, h);
            c++;
        }
    }

    public void getBulk() {
        lst.addLast(last);
    }

    public Snake(int x, int y, int h) {
        this.x = x;
        this.y = y;
        this.h = h;
        lst = new LinkedList<>();
        lst.addFirst(new s(x, y));
    }

    public void draw(Graphics g) {
        last = lst.removeLast();
        if (this.nextDir != Direction.nowhere) {
            this.direction = this.nextDir;
            this.nextDir = Direction.nowhere;
        }
        switch (this.direction) {
            case left:
                x -= h;
                if (x<50){
                    x+=1000;
                }
                break;
            case right:
                x += h;
                if (x>1000){
                    x-=1000;
                }
                break;
            case up:
                y -= h;
                if (y<50){
                    y+=1000;
                }
                break;
            case down:
                y += h;
                if (y>1000){
                    y-=1000;
                }
                break;
        }
        for (s square : lst) {
            if (square.x == x && square.y == y) {
                isDead = true;
            }
        }
        lst.addFirst(new s(x, y));
        if (!isDead) {
            drawSnake(g);
        }
    }

    public boolean notInSnake(int x, int y) {
        for (s square : lst) {
            if (square.x == x && square.y == y) {
                return false;
            }
        }
        return true;
    }

    public void move(Direction direction) {
        if (!(direction == Direction.up && this.direction == Direction.down || direction == Direction.down && this.direction == Direction.up
                || direction == Direction.left && this.direction == Direction.right || direction == Direction.right && this.direction == Direction.left)) {
            if (nextDir == Direction.nowhere) {
                nextDir = direction;
            }
        }
    }
}
