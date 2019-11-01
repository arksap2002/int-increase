public class Unary {

    int a /* BigInteger */ = -2;

    int b /* BigInteger */ = +3;

    int c /* BigInteger */ = -2 + 3;

    int d /* BigInteger */ = +3 - -2;

    int e /* BigInteger */ = -(2 * 3);

    int f /* BigInteger */ = -2 + 9 + 12 * +8 - 200 / 100;

    public static void main(String[] args) {

        int g /* BigInteger */ = -4;
        int k /* BigInteger */ = +10;
        int l /* BigInteger */ = -7 + 10;
        int m /* BigInteger */ = +90 - -1;
        int n /* BigInteger */ = -(21 * 53);
        int o /* BigInteger */ = 1 + (2 * 3) - 100 * -(12 - 11) - (+13 * +(12 / 3) + 1) / 3;
    }
}
