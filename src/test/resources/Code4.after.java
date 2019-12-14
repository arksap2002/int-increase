import java.util.Scanner;

public class Code4 {

    public static void main(String[] args) {
        // https://codeforces.com/contest/929/problem/B
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        java.math.BigInteger k = scanner.nextBigInteger();
        char[][] a = new char[n][12];
        java.math.BigInteger number = java.math.BigInteger.ZERO;
        for (int i = 0; i < n + 1; i++) {
            String s = scanner.nextLine();
            char[] arr = s.toCharArray();
            if (i != 0) {
                System.arraycopy(arr, 0, a[i - 1], 0, 12);
            }
        }
        java.math.BigInteger[][] zer = new java.math.BigInteger[10 * n][2];
        for (int zerFilling1 = 0; zerFilling1 < 10 * n; zerFilling1++) {
            for (int zerFilling2 = 0; zerFilling2 < 2; zerFilling2++) {
                zer[zerFilling1][zerFilling2] = java.math.BigInteger.ZERO;
            }
        }
        java.math.BigInteger[][] one = new java.math.BigInteger[10 * n][2];
        for (int oneFilling1 = 0; oneFilling1 < 10 * n; oneFilling1++) {
            for (int oneFilling2 = 0; oneFilling2 < 2; oneFilling2++) {
                one[oneFilling1][oneFilling2] = java.math.BigInteger.ZERO;
            }
        }
        java.math.BigInteger[][] two = new java.math.BigInteger[10 * n][2];
        for (int twoFilling1 = 0; twoFilling1 < 10 * n; twoFilling1++) {
            for (int twoFilling2 = 0; twoFilling2 < 2; twoFilling2++) {
                two[twoFilling1][twoFilling2] = java.math.BigInteger.ZERO;
            }
        }
        for (int i = 0; i < 10 * n; i++) {
            for (int j = 0; j < 2; j++) {
                zer[i][j] = java.math.BigInteger.ONE.negate();
                one[i][j] = java.math.BigInteger.ONE.negate();
                two[i][j] = java.math.BigInteger.ONE.negate();
            }
        }
        int numZer = 0;
        int numOne = 0;
        int numTwo = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 12; j++) {
                if (a[i][j] == '.') {
                    if (j == 0) {
                        if ((a[i][j + 1] == 'P') || (a[i][j + 1] == '.')) {
                            zer[numZer][0] = java.math.BigInteger.valueOf(i);
                            zer[numZer][1] = java.math.BigInteger.valueOf(j);
                            numZer++;
                        } else {
                            one[numOne][0] = java.math.BigInteger.valueOf(i);
                            one[numOne][1] = java.math.BigInteger.valueOf(j);
                            numOne++;
                        }
                    }
                    if (j == 11) {
                        if ((a[i][j - 1] == 'P') || (a[i][j - 1] == '.')) {
                            zer[numZer][0] = java.math.BigInteger.valueOf(i);
                            zer[numZer][1] = java.math.BigInteger.valueOf(j);
                            numZer++;
                        } else {
                            one[numOne][0] = java.math.BigInteger.valueOf(i);
                            one[numOne][1] = java.math.BigInteger.valueOf(j);
                            numOne++;
                        }
                    }
                    if ((j != 0) && (j != 11)) {
                        if (((a[i][j - 1] == '.') || (a[i][j - 1] == '-') || (a[i][j - 1] == 'P')) && ((a[i][j + 1] == '.') || (a[i][j + 1] == '-') || (a[i][j + 1] == 'P'))) {
                            zer[numZer][0] = java.math.BigInteger.valueOf(i);
                            zer[numZer][1] = java.math.BigInteger.valueOf(j);
                            numZer++;
                        } else {
                            if (((a[i][j - 1] == 'S') || (a[i][j + 1] == 'S')) && (a[i][j - 1] != a[i][j + 1])) {
                                one[numOne][0] = java.math.BigInteger.valueOf(i);
                                one[numOne][1] = java.math.BigInteger.valueOf(j);
                                numOne++;
                            } else {
                                two[numTwo][0] = java.math.BigInteger.valueOf(i);
                                two[numTwo][1] = java.math.BigInteger.valueOf(j);
                                numTwo++;
                            }
                        }
                    }
                }
                if (a[i][j] == 'S') {
                    if (j == 0) {
                        if (a[i][j + 1] != '.') {
                            number = number.add(java.math.BigInteger.ONE);
                        }
                    }
                    if (j == 11) {
                        if (a[i][j - 1] != '.') {
                            number = number.add(java.math.BigInteger.ONE);
                        }
                    }
                    if ((j != 0) && (j != 11)) {
                        if ((a[i][j - 1] != '.') && (a[i][j - 1] != '-')) {
                            number = number.add(java.math.BigInteger.ONE);
                        }
                        if ((a[i][j + 1] != '.') && (a[i][j + 1] != '-')) {
                            number = number.add(java.math.BigInteger.ONE);
                        }
                    }
                }
            }
        }
        if (k.compareTo(java.math.BigInteger.valueOf(numZer)) <= 0) {
            for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(k) < 0; i++) {
                a[zer[i][0].intValue()][zer[i][1].intValue()] = 'x';
            }
        }
        if ((k.compareTo(java.math.BigInteger.valueOf(numZer)) > 0) && (k.compareTo(java.math.BigInteger.valueOf(numOne).add(java.math.BigInteger.valueOf(numZer))) <= 0)) {
            number = number.add(k.subtract(java.math.BigInteger.valueOf(numZer)));
            for (int i = 0; i < numZer; i++) {
                a[zer[i][0].intValue()][zer[i][1].intValue()] = 'x';
            }
            for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(k.subtract(java.math.BigInteger.valueOf(numZer))) < 0; i++) {
                a[one[i][0].intValue()][one[i][1].intValue()] = 'x';
            }
        }
        if (k.compareTo(java.math.BigInteger.valueOf(numOne).add(java.math.BigInteger.valueOf(numZer))) > 0) {
            number = number.add(java.math.BigInteger.valueOf(numOne).add((k.subtract(java.math.BigInteger.valueOf(numZer)).subtract(java.math.BigInteger.valueOf(numOne))).multiply(java.math.BigInteger.TWO)));
            for (int i = 0; i < numZer; i++) {
                a[zer[i][0].intValue()][zer[i][1].intValue()] = 'x';
            }
            for (int i = 0; i < numOne; i++) {
                a[one[i][0].intValue()][one[i][1].intValue()] = 'x';
            }
            for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(k.subtract(java.math.BigInteger.valueOf(numZer)).subtract(java.math.BigInteger.valueOf(numOne))) < 0; i++) {
                a[two[i][0].intValue()][two[i][1].intValue()] = 'x';
            }
        }
        System.out.println(number);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 12; j++) {
                System.out.print(a[i][j]);
            }
            System.out.println();
        }
    }
}
