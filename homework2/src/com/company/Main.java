package com.company;

import java.util.Arrays;
import Jama.*;

public class Main {

    public static double[][] generateMatrix(int n) {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Math.random();
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
            array[i] = Math.random();
        return array;
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
            for (int j = 0; j < n; j++) {
                b[i] = s[j] * A[i][j];
            }
        }
        return b;
    }

    public static double euclideanNorm(double z[]) {
        double norm = 0.0;
        int n = z.length;
        for (int i = 0; i < n; i++) {
            norm+= z[i] * z[i];
        }
        return Math.sqrt(norm);
    }

    public static double[][] Householder(double[][] A, double eps) {
        int n = A.length;
        double[][] QR = generateInMatrix(n);

        for (int r = 0; r < n; r++) {
            double sigma, beta, k, s;
            sigma = 0.0;
            double[] u = new double[n], b;

            //constructia matricii Pr - constanta beta si vectorul u
            for (int i = r; i < n; i++) {
                sigma += A[i][r] * A[i][r];
            }

            if (sigma <= eps) {
                break;
            }

            k = Math.sqrt(sigma);

            if (A[r][r] > 0) {
                k = -k;
            }

            beta = sigma - k * A[r][r];
            u[r] = A[r][r] - k;
            for (int i = r + 1; i < n; i++) {
                u[i] = A[i][r];
            }

            //transformarea coloanelor j = r+1,...,n
            for (int j = r + 1; j < n; j++) {
                s = 0.0;
                for (int i = r; i < n; i++) {
                    s += u[i] * A[i][j] / beta;
                }
                for (int i = r; i < n; i++) {
                    A[i][j] -= s * u[i];
                }
            }

            //transformarea coloanei r a matricii A
            A[r][r] = k;
            for (int i = r + 1; i < n; i++) {
                A[i][r] = 0;
            }

            b = arrayMatrixProduct(A, u);
            s = 0.0;
            for (int i = r; i < n; i++) {
                s += u[i] * b[i] / beta;
            }

            for (int i = r; i < n; i++) {
                b[i] -= s * u[i];
            }

            for (int j = 1; j < n; j++) {
                s = 0.0;
                for (int i = r; i < n; i++) {
                    s += u[i] * QR[i][j] / beta;
                }
                for (int i = r; i < n; i++) {
                    QR[i][j] -= s * u[i];
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
        double[] b = arrayMatrixProduct(A, s);
        System.out.println("1. Array matrix product: ");
        printArray(b);
        System.out.println("---------------------------");

        System.out.println("2. QR Decomposition of Matrix A: ");
        printMatrix(Householder(A, eps));
        System.out.println("---------------------------");

        double[][] An250 = generateMatrix(270);
        long startTime, stopTime, elapsedTime;

        startTime = System.currentTimeMillis();
        double[][] XHouseholder = Householder(An250, eps);
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("3. QR Decomposition of Matrix A time measurement");
        System.out.println("a. Using Householder algorithm: " + elapsedTime);

        Matrix Am = new Matrix(An250);
        startTime = System.currentTimeMillis();
        QRDecomposition QR = new QRDecomposition(Am);
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("b. Using Java lib: " + elapsedTime);
        System.out.println("---------------------------");

        System.out.println("4. Errors of QR Decomposition of Matrix A: ");
        System.out.println(euclideanNorm(arrayMatrixProduct(A, XHouseholder) - b));
        System.out.println(euclideanNorm(arrayMatrixProduct(A, QR) - b));
        System.out.println(euclideanNorm(XHouseholder - s)/ euclideanNorm(s));
        System.out.println(euclideanNorm(QR - s)/ euclideanNorm(s));
        System.out.println("---------------------------");
    }
}
