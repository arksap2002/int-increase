import java.util.Scanner;

public class Code9 {
    public static void main(String[] args) {
//        https://codeforces.com/problemset/status/6/problem/A
        Scanner in = new Scanner(System.in);
        int[] a = new int[4];
        for (int i = 0; i < 4; ++i) a[i] = in.nextInt();
        java.util.Arrays.sort(a);
        if (a[0] + a[1] > a[2] || a[1] + a[2] > a[3]) System.out.println("TRIANGLE");
        else if (a[0] + a[1] == a[2] || a[1] + a[2] == a[3]) System.out.println("SEGMENT");
        else System.out.println("IMPOSSIBLE");
    }
}
