public class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public Position moveDirection(Direction direction) {
        switch (direction) {
            case UP:
                x--;
                break;
            case LEFT:
                y--;
                break;
            case RIGHT:
                y++;
                break;
            case DOWN:
                x++;
                break;
        }

        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return x == ((Position)obj).x && y == ((Position)obj).y;
    }

    public Position peekDirection(Direction direction) {
        return new Position(x, y).moveDirection(direction);
    }
}
