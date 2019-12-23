import java.io.*;
import java.util.*;

public class CodeforcesProblem521A extends PrintWriter {

    CodeforcesProblem521A() {
        super(System.out, true);
    }

    Scanner sc = new Scanner(System.in);

    public static void main(String[] $) {
        CodeforcesProblem521A o = new CodeforcesProblem521A();
        o.main();
        o.flush();
    }

    static final int MD = 1000000007, A = 26;

    void main() {
        java.math.BigInteger n = sc.nextBigInteger();
        byte[] cc = sc.next().getBytes();
        java.math.BigInteger[] kk = new java.math.BigInteger[A];
        for (int kkFilling1 = 0; kkFilling1 < A; kkFilling1++) {
            kk[kkFilling1] = java.math.BigInteger.ZERO;
        }
        for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(n) < 0; i++) kk[cc[i] - 'A'] = kk[cc[i] - 'A'].add(java.math.BigInteger.ONE);
        java.math.BigInteger k = java.math.BigInteger.ZERO;
        for (int a = 0; a < A; a++) k = k.max(kk[a]);
        java.math.BigInteger cnt = java.math.BigInteger.ZERO;
        for (int a = 0; a < A; a++) if (k.equals(kk[a]))
            cnt = cnt.add(java.math.BigInteger.ONE);
        long ans = 1;
        for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(n) < 0; i++) ans = java.math.BigInteger.valueOf(ans).multiply(cnt).remainder(java.math.BigInteger.valueOf(MD)).intValue();
        println(ans);
    }
}
