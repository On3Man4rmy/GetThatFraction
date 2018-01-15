import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.*;

/**
 * Matrix to represent playing field filled with fractions
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */
public class Matrix<T> {
    T[][] data;


    //Konstruktor
    public Matrix(int rows, int columns, T initalFieldValue) {
        data =  (T[][])new Object[rows][columns];
        map(() -> initalFieldValue);
    }

    @Override
    public String toString() {
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

    T setValue(int i, int j, T x) {
        return data[i - 1][j - 1] = x;
    }

    public T getValue(int i, int j) {
        return data[i-1][j-1];
    }

    public int getHeight() {
        return Array.getLength(data);
    }

    public int getWidth() {
        return (getHeight() > 0) ? Array.getLength(data[0]) : 0;
    }


    // plotter
    public void forEach(BiFunction<Integer, Integer, T> func) {
        for(int x = 1; x <= getWidth(); x++) {
            for (int y = 1; y <= getHeight(); y++) {
                func.apply(x,y);
            }
        }
    }

    public void forEach(Consumer<T> func) {
        for(int x = 1; x <= getWidth(); x++) {
            for (int y = 1; y <= getHeight(); y++) {
                func.accept(getValue(x,y));
            }
        }
    }


    // plotter
    public Matrix<T> map(Supplier<T> func) {
        return map(x -> func.get());
    }

    // plotter
    public Matrix<T> map(Function<T, T> func) {
        forEach((Integer x, Integer y)
                -> setValue(x, y, func.apply(getValue(x,y))));

        return this;
    }

    // sum of elements
    public T reduce(BiFunction<T, T, T> func, T initalValue) {
        T accumulator = initalValue;

        for(int x = 1; x <= getWidth(); x++) {
            for(int y = 1; y <= getWidth(); y++) {
                accumulator = func.apply(accumulator, getValue(x,y));
            }
        }

        return accumulator;
    }


}