public class Player {

    Fraction score = new Fraction(0,1);
    Position position;

    Player (int posX, int posY) {
        this.position = new Position(posX, posY);
    }

    Player (Position position) {
        this.position = position;
    }

    public Player moveDirection(Direction direction) {
        position.moveDirection(direction);
        return this;
    }

    public Position peekDirection(Direction direction) {
        return position.peekDirection(direction);
    }
}
