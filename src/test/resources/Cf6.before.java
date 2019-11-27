//package com.example.parsing.problemTests.intCode;

import java.util.Scanner;

public class Cf6 {
    public static void main(String[] args) {
        //https://codeforces.com/contest/1011/problem/C
        Scanner scanner = new Scanner(System.in);
        int /* BigInteger */ n = scanner.nextInt();
        int /* BigInteger */ m = scanner.nextInt();
        int /* BigInteger */ a[] = new int[n];
        int /* BigInteger */ b[] = new int[n];
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            if (a[i] == 1){
                flag = true;
            }
        }
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
            if (b[i] == 1){
                flag = true;
            }
        }
        if (flag) {
            System.out.println(-1);
        } else {
            double ans = 0.0;
            double second = m / (b[0] - 1.0);
            double first = (m + second) / (a[n - 1] - 1.0);
            ans += (first + second);
            for (int i = n - 1; i > 0; i--) {
                second = (m + ans) / (b[i] - 1.0);
                first = (m + second + ans) / (a[i - 1] - 1.0);
                ans += (first + second);
            }
            System.out.println(ans);
        }
    }
}
