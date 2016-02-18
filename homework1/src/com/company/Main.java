package com.company;

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
        x = x % Math.PI - Math.PI / 2.0;
//        if (x % (Math.PI / 2.0) == 0) {
//            if (x < 0) {
//                return -1;
//            } else if (x > 0) {
//                return 1;
//            }
//        }
        if (x > -Math.PI / 2.0 && x < Math.PI / 2.0) {
            return Lentz(x, e);
        } else {
            return -Lentz(-x, e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Tema 1");
        System.out.println("1.  " + addPrecision());
        System.out.println("2.  " + addAssociativity());
        System.out.println("2.  " + crossProductAssociativity());
        System.out.println("3.  " + tan(Math.PI/4, Math.pow(10, -20)));
    }
}
