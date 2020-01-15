import java.util.Arrays;
import java.util.Scanner;

public class CodeforcesProblem1011A {
    public static void main(String[] args) {
        //https://codeforces.com/contest/1011/submission/66887069
        Scanner scanner = new Scanner(System.in);
        int /* BigInteger */ n = scanner.nextInt();
        int /* BigInteger */ k = scanner.nextInt();
        scanner.nextLine();
        String string = scanner.nextLine();
        char ch[] = string.toCharArray();
        int /* BigInteger */ ans = 0;
        int /* BigInteger */ number = 0;
        Arrays.sort(ch);
        int /* BigInteger */ prev = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                ans += ch[i] - 'a' + 1;
                prev = i;
                number++;
            } else {
                if (ch[i] - ch[prev] > 1) {
                    ans += ch[i] - 'a' + 1;
                    prev = i;
                    number++;
                }
            }
            if (number == k) {
                break;
            }
        }
        if (number < k) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }
}
