import java.awt.*;

public class Block {
    final int BLOCK_SIZE = 25;

    private int x, y;

    public Block(int x, int y) {
        setX(x);
        setY(y);
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void paint(Graphics g, int color) {
        g.setColor(new Color(color));
        g.fillRoundRect(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2, 12,12);
    }
}
