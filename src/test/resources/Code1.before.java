
import java.util.*;

public class Code1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int /* BigInteger */ n, m, k, a, b, c;
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        int[] /* BigInteger */ x = new int[500000];
        int[] /* BigInteger */ y = new int[500000];
        int[] /* BigInteger */ ans = new int[500000];
        for (int i = 1; i <= k; i++) {
            a = scanner.nextInt();
            b = scanner.nextInt();
            ans[i] = scanner.nextInt();
            if (a == 1)
                x[b - 1] = i;
            else
                y[b - 1] = i;
        }
        for (int /* BigInteger */ i = 0; i < n; i++) {
            for (int /* BigInteger */ j = 0; j < m; j++) {
                System.out.print(ans[Math.max(x[i], y[j])] + " ");
            }
            System.out.println();
        }
    }
}
