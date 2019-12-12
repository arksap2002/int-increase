import java.util.*;

public class Code8 {
    public static void main(String[] args) {
//        https://codeforces.com/problemset/status/4/problem/B
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] ar = new int[n][2];
        int min = 0, max = 0;

        for (int i = 0; i < n; i++) {
            min += ar[i][0] = in.nextInt();
            max += ar[i][1] = in.nextInt();
        }

        if (min > m || max < m)
            System.out.println("NO");
        else {
            System.out.println("YES");
            m -= min;
            for (int i = 0; i < n; i++) {
                int a = Math.min(m, ar[i][1] - ar[i][0]);
                m -= a;
                System.out.printf("%d ", ar[i][0] + a);
            }
        }
        in.close();
    }
}