public class Player {
    private String name;
    private String shortName;

    Fraction score = new Fraction(0,1);
    Position position;

    Player (int posX, int posY) {
        this.position = new Position(posX, posY);
    }

    Player (Position position) {
        this.position = position;
    }

    Player (Position position, String name, String shortName) {
        this.position = position;
        this.name = name;
        this.shortName = shortName;
    }

    public Player moveDirection(Direction direction) {
        position.moveDirection(direction);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public Player peekDirection(Direction direction) {
        return new Player(position.peekDirection(direction));
    }
}
