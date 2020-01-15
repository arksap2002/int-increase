import java.io.*;
import java.util.*;

public class CodeforcesProblem521A extends PrintWriter {
    CodeforcesProblem521A() { super(System.out, true); }
    Scanner sc = new Scanner(System.in);
    public static void main(String[] $) {
        CodeforcesProblem521A o = new CodeforcesProblem521A(); o.main(); o.flush();
    }

    static final int MD = 1000000007, A = 26;
    void main() {
        int /* BigInteger */ n = sc.nextInt();
        byte[] cc = sc.next().getBytes();
        int[] /* BigInteger */ kk = new int[A];
        for (int i = 0; i < n; i++)
            kk[cc[i] - 'A']++;
        int  /* BigInteger */ k = 0;
        for (int a = 0; a < A; a++)
            k = Math.max(k, kk[a]);
        int  /* BigInteger */ cnt = 0;
        for (int a = 0; a < A; a++)
            if (k == kk[a])
                cnt++;
        long ans = 1;
        for (int i = 0; i < n; i++)
            ans = ans * cnt % MD;
        println(ans);
    }
}