public class Generalization {

    public static void main(String[] args) {
        int a /* BigInteger */ = 10;
        int b = 9;
        int c = 8;
        int d /* BigInteger */ = Integer.hashCode(a) + b + Integer.hashCode(c);
        int e /* BigInteger */ = Integer.parseInt("100") * Integer.MAX_VALUE + c;
        int f /* BigInteger */ = Integer.hashCode(a + c);
    }
}
