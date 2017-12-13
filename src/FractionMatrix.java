public class FractionMatrix {
    Fraction[][] data;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for(Fraction[] dataX : data) {
            for (Fraction dataY : dataX) {
                int num = dataY.getNumerator().intValue();
                int det = dataY.getDenominator().intValue();
                int numDigits = MathUtil.digits(num);
                int detDigits = MathUtil.digits(det);
                int maxDigits = 2;
                s.append(num)
                        .append("/")
                        .append(det)
                        .append(StringUtil.repeat(" ", maxDigits * 2 - numDigits - detDigits))
                        .append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    void setValue(int i, int j, Fraction x) {
        data[i - 1][j - 1] = x;
    }

    public Fraction getValue(int i, int j) {
        return data[i-1][j-1];
    }


    public FractionMatrix(int n, int m) {
        data = new Fraction[m][n];

        for(int i = 1; i <= data.length; i++) {
            for(int y = 1; y <= data[i-1].length; y++) {
                setValue(i, y, new Fraction(1, 1));
            }
        }
    }
}