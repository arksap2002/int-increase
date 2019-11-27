import java.util.Scanner;

public class Cf4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        java.math.BigInteger number = new java.math.BigInteger(str.length());
        char[] listUp = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        char[] listDown = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        char[] list1 = { '1', 'l', 'I', 'L', 'i' };
        boolean flagi = true;
        boolean flagj = true;
        java.math.BigInteger n = scanner.nextBigInteger();
        for (int i = 0; new java.math.BigInteger(i).compareTo(n.add(java.math.BigInteger.ONE)) < 0; i++) {
            String str2 = scanner.nextLine();
            if (flagi) {
                if (new java.math.BigInteger(str2.length()).equals(number)) {
                    char[] arr = str.toCharArray();
                    char[] arr2 = str2.toCharArray();
                    flagj = true;
                    for (int j = 0; new java.math.BigInteger(j).compareTo(number) < 0; j++) {
                        if (flagj) {
                            if (!(((arr[j] == '0') && (arr2[j] == 'O')) || ((arr[j] == 'O') && (arr2[j] == '0')) || (arr[j] == arr2[j]) || ((arr[j] == '0') && (arr2[j] == 'o')) || ((arr[j] == 'o') && (arr2[j] == '0')))) {
                                boolean newFlag = false;
                                for (int a = 0; a < 5; a++) {
                                    for (int b = a; b < 5; b++) {
                                        if (((arr[j] == list1[a]) && (arr2[j] == list1[b])) || ((arr2[j] == list1[a]) && (arr[j] == list1[b]))) {
                                            newFlag = true;
                                        }
                                    }
                                }
                                if (!newFlag) {
                                    boolean flagAlpfa = true;
                                    for (int z = 0; z < 26; z++) {
                                        if (((arr[j] == listUp[z]) && (arr2[j] == listDown[z])) || ((arr2[j] == listUp[z]) && (arr[j] == listDown[z]))) {
                                            flagAlpfa = false;
                                        }
                                    }
                                    if (flagAlpfa) {
                                        flagj = false;
                                    }
                                }
                            }
                        }
                    }
                    if (flagj) {
                        flagi = false;
                    }
                }
            }
        }
        if (!flagi) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }
    }
}
