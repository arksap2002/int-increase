import java.util.Scanner;

public class Code3 {
    public static void main(String[] args) {
        //https://codeforces.com/contest/886/problem/B
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int /* BigInteger */ sum = 0;
        int[] /* BigInteger */ a = new int[n];
        int[] /* BigInteger */ b = new int[200002];
        for (int i = 0; i < 200002; i++) {
            b[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            b[a[i]] = 1;
        }
        for (int i = 0; i < 200002; i++) {
            if (b[i] == 1) {
                sum++;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (sum == 1) {
                break;
            }
            if (b[a[i]] == 1) {
                b[a[i]] = 0;
                sum--;
            }
        }
        for (int i = 0; i < 200001; i++) {
            if (b[i] == 1) {
                System.out.println(i);
                break;
            }
        }
    }
}
