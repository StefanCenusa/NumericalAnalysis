package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static double addPrecision() {
        double u = 1.0;
        while (1.0 + u != 1.0) {
            u /= 10.0;
        }
        return u * 10.0;
    }

    public static boolean addAssociativity() {
        double u = addPrecision();
        double a = 1.0;
        double b = u / 10.0;
        double c = u / 10.0;
        return ((a + b) + c) == (a + (b + c));
    }

    public static boolean crossProductAssociativity() {
        double a = 0.01;
        double b = 4.7;
        double c = 789.0;
        return ((a * b) * c) == (a * (b * c));
    }

    public static double Lentz(double x, double e) {
        double b0 = 0;
        double f0 = b0, mic = Math.pow(10, -20);
        if (f0 == 0) {
            f0 = mic;
        }
        double C0 = f0;
        double D0 = 0.0;
        double C = C0, D = D0, delta, f = f0, b = b0, a = x, j = 1;
        do {
            if (j >= 2.0) {
                a = -(x * x);
            }
            b = 2.0 * j - 1.0;
            D = b + a * D;
            if (D == 0.0) {
                D = mic;
            }
            C = b + a / C;
            if (C == 0.0) {
                C = mic;
            }

            D = 1.0 / D;
            delta = C * D;
            f = delta * f;
            j++;
        }
        while (Math.abs(delta - 1.0) >= e);
        return f;
    }

    public static double tan(double x, double e) {
        if (x % (Math.PI / 2.0) == 0) {
            return x < 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        }
        x = x % Math.PI;
        if (x > -Math.PI / 2.0 && x < Math.PI / 2.0) {
            return Lentz(x, e);
        } else {
            return -Lentz(-x, e);
        }
    }

    public static double[] readArrayFromConsole() {
        Scanner s = new Scanner(System.in);

        int count = s.nextInt();
        s.nextLine();

        double[] numbers = new double[count];
        Scanner numScanner = new Scanner(s.nextLine());
        for (int i = 0; i < count; i++) {
            if (numScanner.hasNextDouble()) {
                numbers[i] = numScanner.nextDouble();
            } else {
                System.out.println("You didn't provide enough numbers");
                break;
            }
        }

        return numbers;
    }

    public static double[] readArrayFromFile() {
        Scanner s = null;
        try {
            s = new Scanner(new File("resources/arrayInput.txt"));
        } catch (IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }

        int count = s.nextInt();
        s.nextLine();

        double[] numbers = new double[count];
        Scanner numScanner = new Scanner(s.nextLine());
        for (int i = 0; i < count; i++) {
            if (numScanner.hasNextDouble()) {
                numbers[i] = numScanner.nextDouble();
            } else {
                System.out.println("You didn't provide enough numbers");
                break;
            }
        }

        return numbers;
    }

    public static double[] generateArray(int n) {
        double[] array = new double[n];
        for (int i = 0; i < n; i++)
            array[i] = Math.random();
        return array;
    }

    public static double[][] readMatrixFromConsole() {
        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        int m = s.nextInt();
        s.nextLine();

        double[][] numbers = new double[n][m];
        Scanner numScanner = new Scanner(s.nextLine());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (numScanner.hasNextDouble()) {
                    numbers[i][j] = numScanner.nextDouble();
                } else {
                    System.out.println("You didn't provide enough numbers");
                    break;
                }
            }
            if (i < n - 1) {
                numScanner = new Scanner(s.nextLine());
            }
        }

        return numbers;
    }

    public static double[][] readMatrixFromFile() {
        Scanner s = null;
        try {
            s = new Scanner(new File("resources/matrixInput.txt"));
        } catch (IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }

        int n = s.nextInt();
        int m = s.nextInt();
        s.nextLine();

        double[][] numbers = new double[n][m];
        Scanner numScanner = new Scanner(s.nextLine());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (numScanner.hasNextDouble()) {
                    numbers[i][j] = numScanner.nextDouble();
                } else {
                    System.out.println("You didn't provide enough numbers");
                    break;
                }
            }
            if (i < n - 1) {
                numScanner = new Scanner(s.nextLine());
            }
        }

        return numbers;
    }

    public static double[][] generateMatrix(int n, int m) {
        double[][] matrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Math.random();
            }
        }
        return matrix;
    }

    public static void printArray(double a[]) {
        System.out.println(Arrays.toString(a));
    }

    public static void printMatrix(double matrix[][]) {
        for (int i = 0; i < matrix.length; i++) {
            printArray(matrix[i]);
        }
    }

    public static void main(String[] args) {
        System.out.println("Homework 1");
        System.out.println("---------------------------");

        System.out.println("1.  Add precision: " + addPrecision());
        System.out.println("---------------------------");

        System.out.println("2.  Add associativity: " + addAssociativity());
        System.out.println("2.  crossProduct associativity: " + crossProductAssociativity());
        System.out.println("---------------------------");

        double x = Math.PI / 2 + 1;
        System.out.println("3.  Tan using Lentz formula: " + tan(x, Math.pow(10, -20)));
        System.out.println("3.  Math tan function: " + Math.tan(x));
        System.out.println("---------------------------");

        System.out.println("4.  ");
//        readArrayFromConsole();
        printArray(readArrayFromFile());
        printArray(generateArray(3));
        printMatrix(generateMatrix(3, 3));
        printMatrix(readMatrixFromConsole());
        printMatrix(readMatrixFromFile());
    }
}
