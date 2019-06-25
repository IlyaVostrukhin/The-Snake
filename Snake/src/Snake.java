import java.util.ArrayList;

public class Snake {
    // Направление движения змеи
    private SnakeDirection direction;
    // Состояние - жива змея или нет.
    private boolean isAlive;
    // Список кусочков змеи.
    private ArrayList<SnakeSection> sections;

    public Snake(int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    /**
     * Метод перемещает змею на один ход.
     * Направление перемещения задано переменной direction.
     */
    public void move() {
        if (!isAlive) return;

        if (direction == SnakeDirection.UP)
            move(-1, 0);
        else if (direction == SnakeDirection.RIGHT)
            move(0, 1);
        else if (direction == SnakeDirection.DOWN)
            move(1, 0);
        else if (direction == SnakeDirection.LEFT)
            move(0, -1);
    }

    /**
     * Метод перемещает змею в соседнюю клетку.
     * Координаты клетки заданы относительно текущей головы с помощью переменных (dx, dy).
     */
    private void move(int dx, int dy) {
        // Создаем новую голову - новый "кусочек змеи".
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        // Проверяем - не вылезла ли голова за границу комнаты
        checkBorders(head);
        if (!isAlive) return;

        // Проверяем - не пересекает ли змея  саму себя
        checkBody(head);
        if (!isAlive) return;

        // Проверяем - не съела ли змея мышь.
        Mouse mouse = SnakeGame.game.getMouse();
        if (head.getY() == mouse.getX() && head.getX() == mouse.getY()) // съела
        {
            sections.add(0, head);                  // Добавили новую голову
            SnakeGame.game.eatMouse();                   // Хвост не удаляем, но создаем новую мышь.
            if (sections.size() >= 20)
                SnakeGame.game.setGameScores(200);
            else
                SnakeGame.game.setGameScores(100);
        } else // просто движется
        {
            sections.add(0, head);                  // добавили новую голову
            sections.remove(sections.size() - 1);   // удалили последний элемент с хвоста
        }
    }

    /**
     * Метод проверяет - находится ли новая голова в пределах комнаты
     */
    private void checkBorders(SnakeSection head) {
        if ((head.getX() < 0 || head.getX() >= SnakeGame.game.getFIELD_WIDTH()) || head.getY() < 0 || head.getY() >= SnakeGame.game.getFIELD_HIGHT()) {
            isAlive = false;
        }
    }

    /**
     * Метод проверяет - не совпадает ли голова с каким-нибудь участком тела змеи.
     */
    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}
