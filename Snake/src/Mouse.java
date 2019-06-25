import java.awt.*;

public class Mouse {
    private int x;
    private int y;
    final int BLOCK_SIZE = 25;

    public Mouse(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void paint(Graphics g, int color) {
        g.setColor(new Color(color));
        g.fillOval(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE, BLOCK_SIZE);
    }
}
