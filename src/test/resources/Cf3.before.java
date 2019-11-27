//package com.example.parsing.problemTests.intCode;

import java.util.Scanner;

public class Cf3 {
    public static void main(String[] args){
        //https://codeforces.com/contest/908/problem/C
        Scanner scanner = new Scanner(System.in);
        int /* BigInteger */ n = scanner.nextInt();
        int /* BigInteger */ r = scanner.nextInt();
        int[] /* BigInteger */ x = new int[n];
        for (int i = 0; i < n; i++){
            x[i] = scanner.nextInt();
        }
        int /* BigInteger */ length = 0;
        int[] /* BigInteger */ radx = new int[n];
        double[] /* BigInteger */ rady = new double[n];
        radx[0] = x[0];
        rady[0] = r;
        double maxy;
        double anser;
        for (int i = 1; i < n; i++){
            maxy = r;
            anser = 0;
            for (int j = 0; j < length + i; j++){
                if (Math.abs(x[i] - radx[j]) <= 2*r){
                    anser = (2*rady[j] + Math.sqrt(4*rady[j]*rady[j] - 4*(rady[j]*rady[j] + (radx[j] - x[i])*(radx[j] - x[i]) - 4*r*r)))/2;
                    if (maxy < anser){
                        maxy = anser;
                    }
                }
            }
            radx[i] = x[i];
            rady[i] = maxy;
        }
        for (int i = 0; i < n; i++){
            System.out.print(rady[i] + " ");
        }
    }
}
