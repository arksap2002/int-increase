import java.util.Scanner;

public class CodeforcesProblem929B {
    public static void main(String[] args) {
        //https://codeforces.com/contest/929/submission/66805371
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int /* BigInteger */ k = scanner.nextInt();
        char[][] a = new char[n][12];
        int /* BigInteger */ number = 0;
        for (int i = 0; i < n + 1; i++) {
            String s = scanner.nextLine();
            char[] arr = s.toCharArray();
            if (i != 0) {
                System.arraycopy(arr, 0, a[i - 1], 0, 12);
            }
        }
        int[][] /* BigInteger */ zer = new int[10 * n][2];
        int[][] /* BigInteger */ one = new int[10 * n][2];
        int[][] /* BigInteger */ two = new int[10 * n][2];
        for (int i = 0; i < 10 * n; i++) {
            for (int j = 0; j < 2; j++) {
                zer[i][j] = -1;
                one[i][j] = -1;
                two[i][j] = -1;
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
                            zer[numZer][0] = i;
                            zer[numZer][1] = j;
                            numZer++;
                        } else {
                            one[numOne][0] = i;
                            one[numOne][1] = j;
                            numOne++;
                        }
                    }
                    if (j == 11) {
                        if ((a[i][j - 1] == 'P') || (a[i][j - 1] == '.')) {
                            zer[numZer][0] = i;
                            zer[numZer][1] = j;
                            numZer++;
                        } else {
                            one[numOne][0] = i;
                            one[numOne][1] = j;
                            numOne++;
                        }
                    }
                    if ((j != 0) && (j != 11)) {
                        if (((a[i][j - 1] == '.') || (a[i][j - 1] == '-') || (a[i][j - 1] == 'P')) && ((a[i][j + 1] == '.') || (a[i][j + 1] == '-') || (a[i][j + 1] == 'P'))) {
                            zer[numZer][0] = i;
                            zer[numZer][1] = j;
                            numZer++;
                        } else {
                            if (((a[i][j - 1] == 'S') || (a[i][j + 1] == 'S')) && (a[i][j - 1] != a[i][j + 1])) {
                                one[numOne][0] = i;
                                one[numOne][1] = j;
                                numOne++;
                            } else {
                                two[numTwo][0] = i;
                                two[numTwo][1] = j;
                                numTwo++;
                            }
                        }
                    }
                }
                if (a[i][j] == 'S'){
                    if (j == 0){
                        if (a[i][j + 1] != '.'){
                            number++;
                        }
                    }
                    if (j == 11){
                        if (a[i][j - 1] != '.'){
                            number++;
                        }
                    }
                    if ((j != 0) && (j != 11)){
                        if ((a[i][j - 1] != '.') && (a[i][j - 1] != '-')){
                            number++;
                        }
                        if ((a[i][j + 1] != '.') && (a[i][j + 1] != '-')){
                            number++;
                        }
                    }
                }
            }
        }
        if (k <= numZer){
            for (int i = 0; i < k; i++){
                a[zer[i][0]][zer[i][1]] = 'x';
            }
        }
        if ((k > numZer) && (k <= numOne + numZer)){
            number += k - numZer;
            for (int i = 0; i < numZer; i++){
                a[zer[i][0]][zer[i][1]] = 'x';
            }
            for (int i = 0; i < k - numZer; i++){
                a[one[i][0]][one[i][1]] = 'x';
            }
        }
        if (k > numOne + numZer){
            number += numOne + (k - numZer - numOne) * 2;
            for (int i = 0; i < numZer; i++){
                a[zer[i][0]][zer[i][1]] = 'x';
            }
            for (int i = 0; i < numOne; i++){
                a[one[i][0]][one[i][1]] = 'x';
            }
            for (int i = 0; i < k - numZer - numOne; i++){
                a[two[i][0]][two[i][1]] = 'x';
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
