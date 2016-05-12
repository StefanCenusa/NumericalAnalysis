package com.company;

public class Main {

    private static double computePolinom(double A[], double x) {
        double Px = 0.;
        double xn = 1;
        int n = A.length;
        for (int i = n - 1; i >= 0; i--) {
            Px += A[i] * xn;
            xn *= x;
        }
        return Px;
    }

    private static double Muller(double A[], double x[], int k, double eps) {
        double deltaX = 0., h0, h1, sigma0, sigma1, a, b, c;
        double deltaXMax = Math.pow(10, 8);
        double kMax = 1000;
        int count = 0;

        do {
            h0 = x[k - 1] - x[k - 2];
            h1 = x[k] - x[k - 1];
            sigma0 = (computePolinom(A, x[k - 1]) - computePolinom(A, x[k - 2])) / h0;
            sigma1 = (computePolinom(A, x[k]) - computePolinom(A, x[k - 1])) / h1;
            a = (sigma1 - sigma0) / (h1 + h0);
            b = a * h1 + sigma1;
            c = computePolinom(A, x[k]);

            if (b * b - 4 * a * c < 0) {
                break;
            }

            if (Math.abs(Math.max(b + Math.sqrt(b * b - 4 * a * c), b - Math.sqrt(b * b - 4 * a * c))) < eps) {
                break;
            }

            deltaX = (2 * c) / (Math.max(b + Math.sqrt(b * b - 4 * a * c), b - Math.sqrt(b * b - 4 * a * c)));

            x[3] = x[2] - deltaX;
            count++;
            x[0] = x[1];
            x[1] = x[2];
            x[2] = x[3];
        }
        while (Math.abs(deltaX) >= eps && count <= kMax && Math.abs(deltaX) <= deltaXMax);
        if (Math.abs(deltaX) < eps) {
            return x[k];
        } else {
            System.out.println("Divergenta; Incercati alti x0, x1, x2");
            return -1;
        }
    }

    private static double g(double[] A, double x) {
        double h = Math.pow(10, -5);
        return (3 * computePolinom(A, x) - 4 * computePolinom(A, x - h) + computePolinom(A, x - 2 * h)) / (2 * h);
    }

    private static double g2(double[] A, double x) {
        double h = Math.pow(10, -5);
        return (-computePolinom(A, x + 2 * h) + 8 * computePolinom(A, x + h) - 8 * computePolinom(A, x - h) + computePolinom(A, x - 2 * h)) / (12 * h);
    }

    private static double metodaSecantei(double[] A, double[] x, int k, double eps) {
        double deltaX = 0.;
        double deltaXMax = Math.pow(10, 8);
        double kMax = 1000;
        int count = 0;
        do {
            double num = g(A, x[k - 1]) - g(A, x[k - 2]);
            if (Math.abs(num) < eps) {
                deltaX = Math.pow(10, -5);
            } else {
                deltaX = ((x[k - 1] - x[k - 2]) * g(A, x[k - 1])) / num;
            }
            x[k] = x[k - 1] - deltaX;
            x[k - 2] = x[k - 1];
            x[k - 1] = x[k];
            count++;
        }
        while (Math.abs(deltaX) >= eps && count <= kMax && Math.abs(deltaX) <= deltaXMax);
        if (Math.abs(deltaX) < eps) {
            return x[k];
        } else {
            System.out.println("Divergenta; Incercati alti x0, x1");
            return -1;
        }
    }

    private static double metodaSecantei2(double[] A, double[] x, int k, double eps) {
        double deltaX = 0.;
        double deltaXMax = Math.pow(10, 8);
        double kMax = 1000;
        int count = 0;
        do {
            double num = g2(A, x[k - 1]) - g2(A, x[k - 2]);
            if (Math.abs(num) < eps) {
                deltaX = Math.pow(10, -5);
            } else {
                deltaX = ((x[k - 1] - x[k - 2]) * g2(A, x[k - 1])) / num;
            }
            x[k] = x[k - 1] - deltaX;
            x[k - 2] = x[k - 1];
            x[k - 1] = x[k];
            count++;
        }
        while (Math.abs(deltaX) >= eps && count <= kMax && Math.abs(deltaX) <= deltaXMax);
        if (Math.abs(deltaX) < eps) {
            return x[k];
        } else {
            System.out.println("Divergenta; Incercati alti x0, x1");
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println("Homework 5");
        System.out.println("---------------------------");
        System.out.println("1. ");
        System.out.println("---------------------------");
        int p = 8;
        double eps = Math.pow(10, -p);
        int n = 3;
        double A[] = new double[n];
        A[0] = 1;
        A[1] = -4;
        A[2] = 3;
        double x[] = new double[n + 1];
        x[0] = 1.;
        x[1] = 1.5;
        x[2] = 2.;
        System.out.println(Muller(A, x, n - 1, eps));
        System.out.println("---------------------------");
        System.out.println("2. ");
        System.out.println("---------------------------");
        double x2[] = new double[n + 1];
        x2[0] = 1.;
        x2[1] = 1.5;
        System.out.println(metodaSecantei(A, x2, 2, eps));
        System.out.println(metodaSecantei2(A, x2, 2, eps));
        System.out.println("---------------------------");
        System.out.println("end");
    }
}
