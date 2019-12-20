import java.util.Arrays;
import java.util.Scanner;

public class CodeforcesProblem1011A {

    public static void main(String[] args) {
        // https://codeforces.com/contest/1011/problem/A
        Scanner scanner = new Scanner(System.in);
        java.math.BigInteger n = scanner.nextBigInteger();
        java.math.BigInteger k = scanner.nextBigInteger();
        scanner.nextLine();
        String string = scanner.nextLine();
        char[] ch = string.toCharArray();
        java.math.BigInteger ans = java.math.BigInteger.ZERO;
        java.math.BigInteger number = java.math.BigInteger.ZERO;
        Arrays.sort(ch);
        java.math.BigInteger prev = java.math.BigInteger.ZERO;
        for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(n) < 0; i++) {
            if (i == 0) {
                ans = ans.add(java.math.BigInteger.valueOf(ch[i]).subtract(java.math.BigInteger.valueOf('a')).add(java.math.BigInteger.ONE));
                prev = java.math.BigInteger.valueOf(i);
                number = number.add(java.math.BigInteger.ONE);
            } else {
                if (java.math.BigInteger.valueOf(ch[i]).subtract(java.math.BigInteger.valueOf(ch[prev.intValue()])).compareTo(java.math.BigInteger.ONE) > 0) {
                    ans = ans.add(java.math.BigInteger.valueOf(ch[i]).subtract(java.math.BigInteger.valueOf('a')).add(java.math.BigInteger.ONE));
                    prev = java.math.BigInteger.valueOf(i);
                    number = number.add(java.math.BigInteger.ONE);
                }
            }
            if (number.equals(k)) {
                break;
            }
        }
        if (number.compareTo(k) < 0) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }
}
