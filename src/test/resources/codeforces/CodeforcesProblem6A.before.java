import java.util.Scanner;

public class CodeforcesProblem6A {
    public static void main(String[] args) {
//        https://codeforces.com/contest/6/submission/66887362
        Scanner in = new Scanner(System.in);
        int[] /* BigInteger */ a = new int[4];
        for (int i = 0; i < 4; ++i) a[i] = in.nextInt();
        java.util.Arrays.sort(a);
        if (a[0] + a[1] > a[2] || a[1] + a[2] > a[3]) System.out.println("TRIANGLE");
        else if (a[0] + a[1] == a[2] || a[1] + a[2] == a[3]) System.out.println("SEGMENT");
        else System.out.println("IMPOSSIBLE");
    }
}
