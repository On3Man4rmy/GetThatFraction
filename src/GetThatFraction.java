import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GetThatFraction {
    public static void main(String[] args) throws IOException {
        Boolean end = false;
        Matrix<Fraction> mat = initMatrix();
        Player p1 = new Player(3, 3);
        Player p2 = new Player(5, 5);
        Player currentPlayer = p1;
        Player otherPlayer = p2;
        draw(p1, p2, currentPlayer, mat);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";


        while (line.equalsIgnoreCase("quit") == false  && !end) {
            line = in.readLine();

            if(line.equalsIgnoreCase("W")
                    && currentPlayer.position.y > 1
                    && !isSamePosition(currentPlayer.peekDirection(Direction.UP), otherPlayer.position)) {
                currentPlayer.moveDirection(Direction.UP);
            }
            if(line.equalsIgnoreCase("A")
                    && currentPlayer.position.x > 1
                    && !isSamePosition(currentPlayer.peekDirection(Direction.LEFT), otherPlayer.position)) {
                currentPlayer.moveDirection(Direction.LEFT);
            }
            if(line.equalsIgnoreCase("S")
                    && currentPlayer.position.y < 8
                    && !isSamePosition(currentPlayer.peekDirection(Direction.DOWN), otherPlayer.position)) {
                currentPlayer.moveDirection(Direction.DOWN);
            }
            if(line.equalsIgnoreCase("D")
                    && currentPlayer.position.x < 8
                    && !isSamePosition(currentPlayer.peekDirection(Direction.RIGHT), otherPlayer.position)) {
                currentPlayer.moveDirection(Direction.RIGHT);
            }


            currentPlayer.score = currentPlayer.score.add(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
            mat.setValue(currentPlayer.position.x, currentPlayer.position.y, new Fraction(0, 1));

            if(currentPlayer == p1) {
                currentPlayer = p2;
                otherPlayer = p1;
            } else {
                currentPlayer = p1;
                otherPlayer = p2;
            }


            draw(p1, p2, currentPlayer, mat);

            if(p1.score.intValue() >= 80) {
                System.out.println("Weiß wins!");
                end = true;
            }
            if(p2.score.intValue() >= 80) {
                System.out.println("Schwarz wins!");
                end = true;
            }
        }

        in.close();
    }

    public static Boolean isSamePosition(Position p1, Position p2) {
        return p1.equals(p2);
    }


    public static void draw(Player p1, Player p2, Player currentPlayer, Matrix<Fraction> mat) {
        StringBuilder s = new StringBuilder();
        s.append("Weiß Score: " + p1.score.floatValue()).append("\n");
        s.append("Schwarz Score: " + p2.score.floatValue()).append("\n");
        s.append("Current Player: " + (currentPlayer == p1 ? "Weiß" : "Schwarz")).append("\n\n");

        for(int y = 1; y <= 8; y++) {
            for(int x = 1; x <= 8; x++) {
                if(p1.position.x == x && p1.position.y == y) {
                    s.append("  W   ");
                } else if( p2.position.x == x && p2.position.y == y) {
                    s.append("  S   ");
                } else {
                    int num = mat.getValue(x, y).getNumerator().intValue();
                    int det = mat.getValue(x, y).getDenominator().intValue();
                    int numDigits = MathUtil.digits(num);
                    int detDigits = MathUtil.digits(det);
                    int maxDigits = 2;
                    if(det == 1) {
                        s.append("  " + num +  "   ");
                    } else {
                        s.append(num)
                                .append("/")
                                .append(det)
                                .append(StringUtil.repeat(" ", maxDigits * 2 - numDigits - detDigits))
                                .append(" ");
                    }
                }
            }
            s.append("\n");
        }
        System.out.println(s.toString());
    }

    public static Fraction getGameFraction() {
        int minNumerator, maxNumerator, randomNumerator;
        int minDenominator, maxDenominator, randomDenominator;

        minNumerator = 10;
        maxNumerator = 99;
        randomNumerator = randomRange(minNumerator, maxNumerator);

        minDenominator = randomNumerator / 10;
        maxDenominator = randomNumerator;
        randomDenominator = randomRange(minDenominator, maxDenominator);

        return new Fraction(randomNumerator, randomDenominator);
    }

    // https://stackoverflow.com/a/363732
    public static int randomRange(int min, int max) {
        Random ran = new Random();
        return  min + ran.nextInt(max - min+ 1);
    }

    public static Matrix<Fraction> initMatrix() {
        Matrix<Fraction> mat = new Matrix<Fraction>(8, 8, Fraction.DEFAULT);
        mat.map(GetThatFraction::getGameFraction);
        System.out.println(mat.reduce((acc, curr) -> acc.add(curr), Fraction.DEFAULT).intValue());
        return mat;
    }
}
