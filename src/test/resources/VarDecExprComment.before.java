public class VarDecExprComment {

    public static void main(String[] args) {
        int a /* BigInteger */ = 10;
        int b = 10;
        int c /* BigInteger */ = 1, d = 19, e = 1;
        int f, g = 2;
        int h = 90, i /* BigInteger */ = 71, j = 3;
        int k /* BigInteger */ = c + d + e + h + i + j + b;
    }
}
