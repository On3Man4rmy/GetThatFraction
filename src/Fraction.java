import java.math.BigInteger;

/**
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */
public class Fraction extends Number implements Comparable<Fraction>{
    public static final Fraction ZERO = new Fraction(0,1);
    public static final Fraction DEFAULT = new Fraction(0,1);

    private BigInteger numerator;
    private BigInteger denominator;

    public Fraction(BigInteger numerator, BigInteger denominator)  {
        super();

        if(denominator.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException("denominator is zero");
        }

        if(denominator.compareTo(BigInteger.ZERO) < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }

        BigInteger gcd = numerator.gcd(denominator);
        this.numerator = numerator.divide(gcd);
        this.denominator = denominator.divide(gcd);
    }

    public Fraction(int numerator, int denominator) {
        this(
                BigInteger.valueOf(numerator),
                BigInteger.valueOf(denominator)
        );
    }

    public Fraction(BigInteger numerator) {
        this.numerator = numerator;
        this.denominator = BigInteger.ONE;
    }

    Fraction add(Fraction r) throws DivisionByZeroException{
        BigInteger numerator = this.getNumerator().multiply(r.getDenominator())
                .add(r.getNumerator().multiply(this.getDenominator()));
        BigInteger denominator = this.getDenominator().multiply(r.getDenominator());
        return new Fraction(numerator, denominator);
    }

    Fraction subtract(Fraction r) throws DivisionByZeroException {
        BigInteger numerator = this.getNumerator().multiply(r.getDenominator())
                .subtract(r.getNumerator().multiply(this.getDenominator()));
        BigInteger denominator = this.getDenominator().multiply(r.getDenominator());
        return new Fraction(numerator, denominator);
    }

    Fraction multiply(Fraction r) {
        return new Fraction(
                this.getNumerator().multiply(r.getNumerator()),
                this.getDenominator().multiply(r.getDenominator())
        );
    }

    Fraction divide(Fraction r) throws DivisionByZeroException {
        return new Fraction(
                this.getNumerator().divide(r.getNumerator()),
                this.getDenominator().divide(r.getDenominator())
        );
    }

    public String toString(){
        long num = getNumerator().longValue();
        long det = getDenominator().longValue();
        return num + "/" + det;
    }

    boolean isInteger() {
        return getDenominator().equals(BigInteger.ONE);
    }

    public BigInteger getNumerator() {
        return this.numerator;
    }

    public BigInteger getDenominator() {
        return this.denominator;
    }


    public int compareTo(Fraction frac) {
        BigInteger t = this.getNumerator().multiply(frac.getDenominator());
        BigInteger f = frac.getNumerator().multiply(this.getDenominator());

        return t.compareTo(f);
    }

    public boolean equals(Object obj) {
        return compareTo((Fraction)obj) == 0;
    }

    public BigInteger bigIntValue() { return numerator.divide(denominator); }

    public double doubleValue() {
        return (numerator.doubleValue())/(denominator.doubleValue());
    }

    public float floatValue() {
        return (float) this.doubleValue();
    }

    public int intValue() {
        return (int) this.doubleValue();
    }

    public long longValue() {
        return (long) this.doubleValue();
    }
}

class DivisionByZeroException extends Error {
    DivisionByZeroException(String message) {
        super(message);
    }
}