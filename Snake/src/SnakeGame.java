import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SnakeGame {
    final String TITLE_OF_PROGRAM = "The Snake Game";
    final int BLOCK_SIZE = 25;
    final int FIELD_WIDTH = 30;
    final int FIELD_HIGHT = 30;
    final int FIELD_DX = 7;
    final int FIELD_DY = 26;
    int show_delay = 300;
    boolean gameOver = false;
    final int LEFT = 37;
    final int UP = 38;
    final int RIGTH = 39;
    final int DOWN = 40;
    int gameScores = 0;
    private Mouse mouse;
    private Snake snake = new Snake(15, 15);
    final int[][] GAME_OVER_MSG = {
            {0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0},
            {1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1},
            {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0},
            {0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0},
            {1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0},
            {1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0}};

    public static SnakeGame game;

    public Mouse getMouse() {
        return mouse;
    }

    public int getFIELD_WIDTH() {
        return FIELD_WIDTH;
    }

    public int getFIELD_HIGHT() {
        return FIELD_HIGHT;
    }

    public void eatMouse() {
        createMouse();
    }

    public void createMouse() {
        boolean tmp = true;
        int x = 0, y = 0;
        while (tmp) {
            x = (int) (Math.random() * FIELD_WIDTH);
            y = (int) (Math.random() * FIELD_HIGHT);
            if (!isBusy(x, y)) tmp = false;
        }
        mouse = new Mouse(x, y);
    }

    public boolean isBusy(int x, int y) {
        for (SnakeSection section : snake.getSections()) {
            if (section.getX() == x && section.getY() == y) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        game = new SnakeGame();
        game.start();
    }

    void start() {
        JFrame mainWindow = new JFrame(TITLE_OF_PROGRAM);
        Canvas canvasPanel = new Canvas();
        mainWindow.setDefaultCloseOperation(mainWindow.EXIT_ON_CLOSE); //mainWindow.EXIT_ON_CLOSE???
        mainWindow.setSize(FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HIGHT * BLOCK_SIZE + FIELD_DY + 12);
        mainWindow.setLocationRelativeTo(null); //по центру экрана
        mainWindow.setResizable(false);
        canvasPanel.setBackground(Color.decode("0xDEB887"));
        snake.setDirection(SnakeDirection.DOWN);
        createMouse();
        mainWindow.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        mainWindow.setVisible(true);
        while (snake.isAlive()) {
            mainWindow.setTitle(TITLE_OF_PROGRAM + ". Score: " + gameScores);
            try {
                if (snake.getSections().size() >= 40) {
                    show_delay = 100;
                } else {
                    show_delay = 300 - snake.getSections().size() * 5;
                }
                Thread.sleep(show_delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mainWindow.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (!gameOver) {
                        if (e.getKeyCode() == DOWN) snake.setDirection(SnakeDirection.DOWN);
                        if (e.getKeyCode() == UP) snake.setDirection(SnakeDirection.UP);
                        if (e.getKeyCode() == LEFT) snake.setDirection(SnakeDirection.LEFT);
                        if (e.getKeyCode() == RIGTH) snake.setDirection(SnakeDirection.RIGHT);
                    }
                }
            });
            snake.move();
            canvasPanel.repaint();
        }
    }

    void setGameScores(int scores) {
        gameScores += scores;
    }

    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (!snake.isAlive()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                g.setColor(Color.white);
                for (int y = 0; y < GAME_OVER_MSG.length; y++)
                    for (int x = 0; x < GAME_OVER_MSG[y].length; x++)
                        if (GAME_OVER_MSG[y][x] == 1) g.fill3DRect(x * 25 + 130, y * 25 + 230, 20, 20, true);
            } else {
                ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
                for (int i = 0; i < sections.size(); i++) {
                    if (i == 0) new Block(sections.get(i).getY(), sections.get(i).getX()).paint(g, 0x006400);
                    else new Block(sections.get(i).getY(), sections.get(i).getX()).paint(g, 0x228B22);
                }
                mouse.paint(g, 0xFF0000);
            }
        }
    }
}
