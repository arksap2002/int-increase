import java.util.Arrays;
import java.util.Scanner;

public class Code6 {
    public static void main(String[] args) {
        //https://codeforces.com/contest/1011/problem/A
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        scanner.nextLine();
        String string = scanner.nextLine();
        char ch[] = string.toCharArray();
        int ans = 0;
        int number = 0;
        Arrays.sort(ch);
        int prev = 0;
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
