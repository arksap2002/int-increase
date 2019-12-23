import java.util.*;

public class CodeforcesProblem1A {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        java.math.BigInteger n = sc.nextBigInteger();
        java.math.BigInteger m = sc.nextBigInteger();
        java.math.BigInteger a = sc.nextBigInteger();
        System.out.println((((n.subtract(java.math.BigInteger.ONE)).divide(a)).add(java.math.BigInteger.ONE)).multiply((((m.subtract(java.math.BigInteger.ONE)).divide(a)).add(java.math.BigInteger.ONE))));
    }
}
