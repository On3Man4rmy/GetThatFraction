import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;

public class Matrix<T> {
    T[][] data;

    @Override
    public String toString() {
        /*
        StringBuilder s = new StringBuilder();

        for(T[] dataX : data) {
            for (T dataY : dataX) {
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
        */
        StringBuilder s = new StringBuilder();

        for(T[] dataX : data) {
            for (T dataY : dataX) {
                s.append(" | ")
                        .append(dataY.toString())
                        .append(" | ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    void setValue(int i, int j, T x) {
        data[i - 1][j - 1] = x;
    }

    public T getValue(int i, int j) {
        return data[i-1][j-1];
    }



    public Matrix<T> map(Supplier<T> func) {
        for(int x = 1; x <= 8; x++) {
            for(int y = 1; y <= 8; y++) {
                setValue(x, y, func.get());
            }
        }

        return this;
    }

    public T reduce(BiFunction<T, T, T> func, T initalValue) {
        T accumulator = initalValue;

        for(int x = 1; x <= 8; x++) {
            for(int y = 1; y <= 8; y++) {
                accumulator = func.apply(accumulator, getValue(x,y));
            }
        }

        return accumulator;
    }

    public Matrix(int rows, int columns, T initalFieldValue) {
        data =  (T[][])new Object[rows][columns];

        for(int i = 1; i <= data.length; i++) {
            for(int y = 1; y <= data[i-1].length; y++) {
                setValue(i, y, initalFieldValue);
            }
        }
    }
}