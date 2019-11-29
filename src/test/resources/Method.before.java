public class Method {

    public static void main(String[] args) {
        int /* BigInteger */ a = 10;
        int b = 10;
        int[][][] c = new int[a][b][a];
        int[][] /* BigInteger */ f = new int[10][a];
        int /* BigInteger */ d = foo(a, b, c, f);
        int e = foo(foo(foo(a, b, c, f) + 10 - a, b + a, c, f), a + b, c, f);
    }

    private static int /* BigInteger */ foo(int a, int /* BigInteger */ b, int[][][] c, int[][] /* BigInteger */ d) {
        return a + b * c[0][0][0] * d[0][0];
    }
}
