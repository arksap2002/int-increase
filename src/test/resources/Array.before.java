import java.util.ArrayList;

public class Array {

    public static void main(String[] args) {
        int intNumber = 8;
        int /* BigInteger */ bigIntNumber = 8;
        int[] /* BigInteger */ a = new int[intNumber + 1];
        int[] b = new int[bigIntNumber + 1];
        int[][] c = new int[intNumber + 1][bigIntNumber + 1];
        int[][] /* BigInteger */ d = new int[intNumber + 1][bigIntNumber + 1];
        int[][][][][][][] /* BigInteger */ e = new int[10][10][10][10][10][10][10];
        for (int /* BigInteger */ i = 0; i < Math.min(a.length, b.length); i++) {
            a[i] = i + 1;
            b[i] = i + 1;
        }
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            a[i] = i + 1;
            b[i] = i + 1;
        }
        for (int i = 0; i < intNumber + 1; i++) {
            for (int /* BigInteger */ j = 0; j < bigIntNumber + 1; j++) {
                c[i][j] = i * j;
                d[i][j] = i * j;
            }
        }
        for (int /* BigInteger */ i = 0; i < intNumber + 1; i++) {
            for (int j = 0; j < bigIntNumber + 1; j++) {
                c[i][j] = i * j;
                d[i][j] = i * j;
            }
        }
        for (int /* BigInteger */ i = 0; i < intNumber + 1; i++) {
            for (int /* BigInteger */ j = 0; j < bigIntNumber + 1; j++) {
                c[i][j] = i * j;
                d[i][j] = i * j;
                e[i][j][i][j][i][j][i] = i * j;
            }
        }
        for (int i = 0; i < intNumber + 1; i++) {
            for (int j = 0; j < bigIntNumber + 1; j++) {
                c[i][j] = i * j;
                d[i][j] = i * j;
                e[i][j][i][j][i][j][i] = i * j;
            }
        }
    }
}
