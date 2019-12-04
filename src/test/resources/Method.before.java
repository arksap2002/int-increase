public class Method {

    public static void main(String[] args) {
        int /* BigInteger */ a = 10;
        int b = 10;
        int[][][] c = new int[a][b][a];
        int[][] /* BigInteger */ f = new int[10][a];
        int /* BigInteger */ d = foo(a, b, c);
        int e = foo(foo(foo(c[0][0][0] * f[0][0], b, c) + 10 - a, b + a, c), a + b, c);
    }

    private static int /* BigInteger */ foo(int a, int /* BigInteger */ b, int[][][] c) {
        return a + b * c[0][0][0] - foo(a + b, a + b, c);
    }
}
