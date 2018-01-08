import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MAX {
    public static final int END_X = 8;
    public static final int END_Y = 8;
    public static final int START_X = 1;
    public static final int START_Y = 1;
    public static final int SCORE_TARGET = 80;
    private static Fraction sum=new Fraction(0,1);
    private static Fraction eighty=new Fraction(80,1);
    public static void main(String[] args) throws IOException {
        Boolean end = false;
        Matrix<Fraction> mat = initMatrix();
        /*
        PlayerManager pFactory = new PlayerManager(START_X, START_Y, END_X, END_Y);
        Player p1 = pFactory.createPlayer("Weiß", "W");
        Player p2 = pFactory.createPlayer("Schwarz", "S");
        ArrayList<Player> players = new ArrayList<>();
        players.add(pFactory.createPlayer("Weiß", "W"));
        players.add(pFactory.createPlayer("Schwarz", "S"));
        /*


         */
        Player p1 = new Player(new Position(4, 4), "Schwarz", "S");
        Player p2 = new Player(new Position(5, 5), "Weiß", "W");

        Player currentPlayer = p1;
        Player otherPlayer = p2;
        Board.draw(p1, p2, currentPlayer, mat);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (!end) {
            line = in.readLine();


            while (!line.equalsIgnoreCase("quit")) {

                if (line.equalsIgnoreCase("W")
                        && currentPlayer.position.y > START_Y
                        && !isSamePosition(currentPlayer.peekDirection(Direction.UP), otherPlayer)) {
                    currentPlayer.moveDirection(Direction.UP);
                    break;
                }
                if (line.equalsIgnoreCase("A")
                        && currentPlayer.position.x > START_X
                        && !isSamePosition(currentPlayer.peekDirection(Direction.LEFT), otherPlayer)) {
                    currentPlayer.moveDirection(Direction.LEFT);
                    break;
                }
                if (line.equalsIgnoreCase("S")
                        && currentPlayer.position.y < END_Y
                        && !isSamePosition(currentPlayer.peekDirection(Direction.DOWN), otherPlayer)) {
                    currentPlayer.moveDirection(Direction.DOWN);
                    break;
                }
                if (line.equalsIgnoreCase("D")
                        && currentPlayer.position.x < END_X
                        && !isSamePosition(currentPlayer.peekDirection(Direction.RIGHT), otherPlayer)) {
                    currentPlayer.moveDirection(Direction.RIGHT);
                    break;
                }

                System.out.println("Nicht möglich! Neu eingeben");
                line = in.readLine();

            }

            currentPlayer.score = currentPlayer.score.add(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
            sum=sum.subtract(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
            mat.setValue(currentPlayer.position.x, currentPlayer.position.y, Fraction.ZERO);

            if (currentPlayer == p1) {
                currentPlayer = p2;
                otherPlayer = p1;
            } else {
                currentPlayer = p1;
                otherPlayer = p2;
            }


            Board.draw(p1, p2, currentPlayer, mat);

            if (p1.score.intValue() >= SCORE_TARGET) {
                System.out.println(p1.getName() + " has reached 80 points. You need " +eighty.subtract(p2.score).doubleValue()+" Points to tie the game"  );
                end = true;
            }
            if (p2.score.intValue() >= SCORE_TARGET) {
                System.out.println(p2.getName() + " wins!");
                end = true;
            }

            if(sum.equals(Fraction.ZERO)){
                int i=p1.score.compareTo(p2.score);
                if(i==0) System.out.println("Unentschieden");
                else {
                    System.out.println(i == 1 ? p1.getName() + " wins!" : p2.getName() + " wins!");
                }
                end=true;
            }

        }
        in.close();

    }
    public static Boolean isPlayerCollision(Player currentPlayer, ArrayList<Player> allPlyers) {
        for(Player p : allPlyers) {
            if(currentPlayer != p && isSamePosition(currentPlayer, p))  {
                return true;
            }
        }

        return false;
    }

    public static Boolean isSamePosition(Player p1, Player p2) {
        return p1.position.equals(p2.position);
    }

    public static Fraction getGameFraction() {
        int minNumerator, maxNumerator, randomNumerator;
        int minDenominator, maxDenominator, randomDenominator;

        minNumerator = 10;
        maxNumerator = 99;
        randomNumerator = MathUtil.randomRange(minNumerator, maxNumerator);

        minDenominator = randomNumerator / 10;
        maxDenominator = randomNumerator;
        randomDenominator = MathUtil.randomRange(minDenominator, maxDenominator);

        Fraction frac = new Fraction(randomNumerator, randomDenominator);
        sum = sum.add(frac);
        return frac;
    }



    public static Matrix<Fraction> initMatrix() {
        int rows = END_X - START_X + 1;
        int columns = END_Y - START_Y + 1;

        Matrix<Fraction> mat = new Matrix<>(rows, columns, Fraction.DEFAULT);

        mat.map(MAX::getGameFraction);
        System.out.println(mat.reduce((acc, curr) -> acc.add(curr), Fraction.DEFAULT).intValue());
        return mat;
    }
}
