import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * MAX Game
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */
public class MAX {
    // Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    // Variables for playing ground properties
    public static final int END_X = 8;
    public static final int END_Y = 8;
    public static final int START_X = 1;
    public static final int START_Y = 1;
    public static final Fraction SCORE_TARGET = new Fraction(80, 1);
    public static Fraction sum = new Fraction(0,1);



    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        Boolean end = false;
        Matrix<Fraction> mat = initMatrix();
        Player p1 = new Player(new Position(4, 4), "red", ANSI_RED + "R" + ANSI_RESET);
        Player p2 = new Player(new Position(5, 5), "green", ANSI_GREEN + "G" + ANSI_RESET);
        Player currentPlayer = p1;
        Player otherPlayer = p2;

        // Inital draw
        Board.draw(p1, p2, currentPlayer, mat);

        while (!end) {
            // Keyboard command
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

                // Border reached or unknown command
                System.out.println("Nicht möglich! Neu eingeben");
                // Retry input
                line = in.readLine();
            }

            // Update player score
            currentPlayer.score = currentPlayer.score.add(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
            // Update score of remaining playing field points
            sum = sum.subtract(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
            // When player arrives field set field value to 0
            mat.setValue(currentPlayer.position.x, currentPlayer.position.y, Fraction.ZERO);

            // Rotate current player
            if (currentPlayer == p1) {
                currentPlayer = p2;
                otherPlayer = p1;
            } else {
                currentPlayer = p1;
                otherPlayer = p2;
            }

            // Update console output
            Board.draw(p1, p2, currentPlayer, mat);

            // Announce winner
            if (p1.score.compareTo(SCORE_TARGET) >= 1) {
                System.out.println(p1.getName() + " wins!" );
                end = true;
            }
            if (p2.score.compareTo(SCORE_TARGET) >= 1) {
                System.out.println(p2.getName() + " wins!");
                end = true;
            }
            // Announce tie
            if(sum.equals(Fraction.ZERO)){
                int i=p1.score.compareTo(p2.score);
                if(i==0) System.out.println("Unentschieden");
                else {
                    System.out.println(i == 1 ? p1.getName() + " wins!" : p2.getName() + " wins!");
                }
                end=true;
            }

        }

        // Close buffered reader
        in.close();
    }


    // Check if players have the same position
    public static Boolean isSamePosition(Player p1, Player p2) {
        return p1.position.equals(p2.position);
    }

    // Create Fraction with random (more or less) value
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


    // create Matrix representing the playing field
    public static Matrix<Fraction> initMatrix() {
        int rows = END_X - START_X + 1;
        int columns = END_Y - START_Y + 1;

        Matrix<Fraction> mat = new Matrix<>(rows, columns, Fraction.DEFAULT);

        mat.map(MAX::getGameFraction);
        System.out.println(mat.reduce((acc, curr) -> acc.add(curr), Fraction.DEFAULT).intValue());
        return mat;
    }
}
