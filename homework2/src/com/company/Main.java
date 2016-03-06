package com.company;

import java.util.Arrays;

public class Main {

    public static double[][] generateMatrix(int n, int m) {
        double[][] matrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Math.random();
            }
        }
        return matrix;
    }

    public static double[] generateArray(int n) {
        double[] array = new double[n];
        for (int i = 0; i < n; i++)
            array[i] = Math.random();
        return array;
    }

    public static void printArray(double a[]) {
        System.out.println(Arrays.toString(a));
    }

    public static double[] arrayMatrixProduct(double[][] A, double[] s) {
        int n = s.length;
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i] = s[j] * A[i][j];
            }
        }
        return b;
    }

//    public static double Householder(double[][] A) {
//
//    }

    public static void main(String[] args) {
        System.out.println("Homework 2");
        System.out.println("---------------------------");

        int n = 3;
        double[][] A = generateMatrix(n, n);
        double[] s = generateArray(n);
        double[] b = arrayMatrixProduct(A, s);
        System.out.println("1. Array matrix product: ");
        printArray(b);
        System.out.println("---------------------------");
//
//        System.out.println("2. Array matrix product: " + b.toString());
//        System.out.println("---------------------------");
    }
}
