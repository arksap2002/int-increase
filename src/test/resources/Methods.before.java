public class Methods {

    public static void main(String[] args) {
        int /* BigInteger */ a = 10;
        int b = 10;
        int[][][] c = int[a][b][a];
        int /* BigInteger */ d = foo(a, b, c);
        int d = foo(foo(foo(a, b, c) + 10 - a, b + a, c), a + b, c);
    }

    private int foo (int a, int b, int[][][] c) {
        return a + b * c[0][0][0];
    }
}
