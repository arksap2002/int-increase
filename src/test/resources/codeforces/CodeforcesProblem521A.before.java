import java.util.Scanner;

public class Code10 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        https://codeforces.com/problemset/problem/521/A
        int /* BigInteger */ n = sc.nextInt();
        String s = sc.next();
        int[] /* BigInteger */ count = new int[128];
        for (int i = 0; i < n; i++) {
            count[s.charAt(i)]++;
        }
        int /* BigInteger */ max = 0;
        int /* BigInteger */ maxnum = 0;
        for (int i = 0; i < 128; i++) {
            if (count[i] > max) {
                max = count[i];
                maxnum = 1;
            } else if (count[i] == max) {
                maxnum++;
            }
        }
        int /* BigInteger */ ans = 1;
        for (int i = 0; i < n; i++) {
            ans = ans * maxnum % 1_000_000_007;
        }
        System.out.println(ans);
    }
}
