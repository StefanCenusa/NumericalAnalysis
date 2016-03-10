package com.company;

import java.util.Arrays;

import Jama.*;

public class Main {

    public static double[][] generateMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (Math.random() * 100) % 1000;
            }
        }
        return matrix;
    }

    public static double[][] generateInMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
            matrix[i][i] = 1;
        }
        return matrix;
    }

    public static double[] generateArray(int n) {
        double[] array = new double[n];
        for (int i = 0; i < n; i++)
            array[i] = (Math.random() * 100) % 1000;
        return array;
    }

    public static double[] arraySubtract(double[] a, double[] b) {
        int n = a.length;
        double[] array = new double[n];
        for (int i = 0; i < n; i++)
            array[i] = a[i] - b[i];
        return array;
    }

    public static double[][] getSingleColumnMatrix(double[] a) {
        int n = a.length;
        double[][] b = new double[n][1];
        for (int i = 0; i < n; i++) {
            b[i][0] = a[i];
        }
        return b;
    }

    public static double[] getColumn(double[][] a) {
        int n = a.length;
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = a[i][i];
        }
        return b;
    }

    public static void printArray(double a[]) {
        System.out.println(Arrays.toString(a));
    }

    public static void printMatrix(double matrix[][]) {
        for (int i = 0; i < matrix.length; i++) {
            printArray(matrix[i]);
        }
    }

    public static double[] arrayMatrixProduct(double[][] A, double[] s) {
        int n = s.length;
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = 0;
            for (int j = 0; j < n; j++) {
                b[i] += s[j] * A[i][j];
            }
        }
        return b;
    }

    public static double euclideanNorm(double z[]) {
        double norm = 0.0;
        int n = z.length;
        for (int i = 0; i < n; i++) {
            norm += z[i] * z[i];
        }
        return Math.sqrt(norm);
    }

    public static double[][] Householder(double[][] A, double[] s, double eps) {
        int n = A.length;
        double[][] Acopy = new double[n][n];
        for (int i = 0; i < A.length; i++)
            Acopy[i] = A[i].clone();
        double[][] QR = generateInMatrix(n);
        double[] b = arrayMatrixProduct(Acopy, s);
        double[] u = new double[n];

        for (int r = 0; r < n; r++) {
            double sigma, beta, k, gamma;
            sigma = 0.0;

            //constructia matricii Pr - constanta beta si vectorul u
            for (int i = r; i < n; i++) {
                sigma += Acopy[i][r] * Acopy[i][r];
            }

            if (sigma <= eps) {
                break;
            }

            k = Math.sqrt(sigma);

            if (Acopy[r][r] > 0) {
                k = -k;
            }

            beta = sigma - k * Acopy[r][r];
            u[r] = Acopy[r][r] - k;
            for (int i = r + 1; i < n; i++) {
                u[i] = Acopy[i][r];
            }

            //transformarea coloanelor j = r+1,...,n
            for (int j = r + 1; j < n; j++) {
                gamma = 0.0;
                for (int i = r; i < n; i++) {
                    gamma += u[i] * Acopy[i][j];
                }
                gamma /= beta;
                for (int i = r; i < n; i++) {
                    Acopy[i][j] -= gamma * u[i];
                }
            }

            //transformarea coloanei r a matricii A
            Acopy[r][r] = k;
            for (int i = r + 1; i < n; i++) {
                Acopy[i][r] = 0;
            }

            gamma = 0.0;
            for (int i = r; i < n; i++) {
                gamma += u[i] * b[i];
            }
            gamma /= beta;
            for (int i = r; i < n; i++) {
                b[i] -= gamma * u[i];
            }

            for (int j = 0; j < n; j++) {
                gamma = 0.0;
                for (int i = r; i < n; i++) {
                    gamma += u[i] * QR[i][j];
                }
                gamma /= beta;
                for (int i = r; i < n; i++) {
                    QR[i][j] -= gamma * u[i];
                }
            }
        }

        return QR;
    }

    public static void main(String[] args) {
        System.out.println("Homework 2");
        System.out.println("---------------------------");

        int n = 3, m = 10;
        double eps = Math.pow(10, -m);
        double[][] A = generateMatrix(n);
        double[] s = generateArray(n);
        long startTime, stopTime, elapsedTime;

        System.out.println("1. Array matrix product: ");
        double[] b = arrayMatrixProduct(A, s);
        printArray(b);
        System.out.println("---------------------------");

        System.out.println("2. QR Decomposition of Matrix A");
        printMatrix(Householder(A, s, eps));
        System.out.println("---------------------------");

        System.out.println("3. QR Decomposition of Matrix A time measurement");

        n = 270;
        startTime = System.currentTimeMillis();
        double[][] An250 = generateMatrix(n);
        double[] sn250 = generateArray(n);
        double[] bn250 = arrayMatrixProduct(An250, sn250);
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Generating matrix and array of n: " + n + " took: " + elapsedTime + "ms");

        startTime = System.currentTimeMillis();
        double[][] XHouseholder = Householder(An250, sn250, eps);
        double[] XHouseholderArray = getColumn(XHouseholder);
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("a. Using Householder algorithm: " + elapsedTime + "ms");

        Matrix Am = new Matrix(An250);
        double[][] bm = getSingleColumnMatrix(bn250);
        startTime = System.currentTimeMillis();
        Matrix x = Am.solve(new Matrix(bm));
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("b. Using Java lib: " + elapsedTime + "ms");
        System.out.println("---------------------------");


        System.out.println("4. Errors of QR Decomposition of Matrix A: ");
        System.out.println("||Ainit * Xhouseholder - binit||2   ->  " + euclideanNorm(arraySubtract(arrayMatrixProduct(An250, XHouseholderArray), bn250)));
        System.out.println("||Ainit * XQR - binit||2    ->  " + euclideanNorm(arraySubtract(arrayMatrixProduct(An250, x.getColumnPackedCopy()), bn250)));
        System.out.println("||Xhouseholder - s||2 / ||s||2  ->  " + euclideanNorm(arraySubtract(XHouseholderArray, sn250)) / euclideanNorm(sn250));
        System.out.println("||XQR - s||2 / ||s||2   ->  " + euclideanNorm(arraySubtract(x.getColumnPackedCopy(), sn250)) / euclideanNorm(sn250));
        System.out.println("---------------------------");
    }
}
