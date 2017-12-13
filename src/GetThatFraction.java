import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GetThatFraction {
    public static void main(String[] args) throws IOException {
        Boolean end = false;
        FractionMatrix mat = initMatrix();
        Player p1 = new Player(3, 3);
        Player p2 = new Player(5, 5);
        Player currentPlayer = p1;
        draw(p1, p2, currentPlayer, mat);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";


        while (line.equalsIgnoreCase("quit") == false  && !end) {
            line = in.readLine();

            if(line.equalsIgnoreCase("W") && currentPlayer.posX > 1) {
                currentPlayer.posX -= 1;
            }
            if(line.equalsIgnoreCase("S") && currentPlayer.posX < 8) {
                currentPlayer.posX += 1;
            }
            if(line.equalsIgnoreCase("A") && currentPlayer.posY > 1) {
                currentPlayer.posY -= 1;
            }
            if(line.equalsIgnoreCase("D") && currentPlayer.posY < 8) {
                currentPlayer.posY += 1;
            }

            currentPlayer.score = currentPlayer.score.add(mat.getValue(currentPlayer.posX, currentPlayer.posY));
            mat.setValue(currentPlayer.posX, currentPlayer.posY, new Fraction(0, 1));

            if(currentPlayer == p1) {
                currentPlayer = p2;
            } else {
                currentPlayer = p1;
            }


            draw(p1, p2, currentPlayer, mat);

            if(p1.score.intValue() >= 80) {
                System.out.println("Player 1 wins!");
                end = true;
            }
            if(p2.score.intValue() >= 80) {
                System.out.println("Player 2 wins!");
                end = true;
            }
        }

        in.close();
    }


    public static void draw(Player p1, Player p2, Player currentPlayer, FractionMatrix mat) {
        StringBuilder s = new StringBuilder();
        s.append("PlayerA Score: " + p1.score.floatValue()).append("\n");
        s.append("PlayerB Score: " + p2.score.floatValue()).append("\n");
        s.append("Current Player: " + (currentPlayer == p1 ? "PlayerA" : "PlayerB")).append("\n\n");

        for(int x = 1; x <= 8; x++) {
            for(int y = 1; y <= 8; y++) {
                if(p1.posX == x && p1.posY == y) {
                    s.append("  A   ");
                } else if( p2.posX == x && p2.posY == y) {
                    s.append("  B   ");
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

    public static FractionMatrix initMatrix() {
        FractionMatrix mat = new FractionMatrix(8, 8);

        int min = 1;
        int max = 99;
        for(int x = 1; x <= 8; x++) {
            for(int y = 1; y <= 8; y++) {
                Random random = new Random();

                int d = random.nextInt(max - min + 1) + min;
                int maxN = ((d * 10) > max) ? max : d * 10;
                int minN = d;
                int n = random.nextInt(maxN - minN + 1) + minN;

                mat.setValue(x, y, new Fraction(n, d));
            }
        }

        return mat;
    }
}
