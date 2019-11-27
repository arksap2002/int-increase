//package com.example.parsing.problemTests.intCode;

import java.util.Scanner;

public class Cf1 {
    public static void main(String[] args) {
        //https://codeforces.com/contest/857/problem/A
        Scanner in = new Scanner(System.in);
        int /* BigInteger */ n = in.nextInt();
        if (n % 3 == 0) {
            System.out.println(n / 3 - 1 + " " + n / 3 + " " + (n / 3 + 1));
        } else {
            System.out.println(-1);
        }
    }
}
