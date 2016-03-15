import Jama.*;

public class Main {

    public static double matrixDeterminant(double[][] A, int n) {
        double sum = 0.0;
        double[][] c = new double[n - 1][n - 1];
        if (n == 2) {
            return (A[0][0] * A[1][1] - A[0][1] * A[1][0]);
        } else {
            for (int p = 0; p < n; p++) {
                int line = 0, col = 0;
                for (int i = 1; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (j == p)
                            continue;
                        c[line][col++] = A[i][j];
                        if (col == n - 1) {
                            line++;
                            col = 0;
                        }
                    }
                }
                sum += A[0][p] * Math.pow(-1, p) * matrixDeterminant(c, n - 1);
            }
            return sum;
        }
    }

    public static double normComputation(double[][] A, double[][] A1) {
        int n = A.length;
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i == j ? -1. : 0;
                for (int x = 0; x < n; x++) {
                    matrix[i][j] += A[i][x] * A1[x][j];
                }
            }
        }

        double max = 0.0;
        for (int i = 0; i < n; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += Math.abs(matrix[i][j]);
            }
            if (sum > max) {
                max = sum;
            }
        }

        return max;
    }

    public static void partialPivoting(int l, double[][] A) {
        int n = A.length;
        int i0 = l;
        double max = Math.abs(A[i0][l]);
        for (int i = l + 1; i < n; i++) {
            if (Math.abs(A[i][l]) > max) {
                i0 = i;
                max = Math.abs(A[i][l]);
            }
        }

        double aux;
        if (i0 == l) {
            return;
        }
        for (int j = 0; j < 2 * n; j++) {
            aux = A[i0][j];
            A[i0][j] = A[l][j];
            A[l][j] = aux;
        }
    }

    public static double[][] computeReverseMatrix(double[][] Acopy, int n) {
        double[][] r = new double[n][n];
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                r[i][j] = Acopy[i][j];
            }
        }
        Matrix R = new Matrix(r);
        for (int j = 0; j < n; j++) {
            double[] b = new double[n];
            for (int i = 0; i < n; i++) {
                b[i] = Acopy[i][j + n];
            }
            Matrix Aej = new Matrix(b, n);
            Matrix X = R.solve(Aej);
            double[] x = X.getColumnPackedCopy();
            for (int i = 0; i < n; i++) {
                Acopy[i][j + n] = x[i];
            }
        }
        double[][] A1 = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A1[i][j] = Acopy[i][n + j];
            }
        }
        return A1;
    }


    public static void GaussElimination(double[][] A, double eps) {
        int n = A.length;
        double[][] ACopy = new double[n][2 * n];
        int k = n;
        for (int i = 0; i < n; i++) {
            int j;
            for (j = 0; j < n; j++) {
                ACopy[i][j] = A[i][j];
            }
            for (; j < 2 * n; j++) {
                ACopy[i][j] = 0;
            }
            ACopy[i][k++] = 1;
        }

        int l = 0;
        partialPivoting(l, ACopy);
        while ((l < n - 1) && (Math.abs(ACopy[l][l]) > eps)) {
            for (int i = l + 1; i < n; i++) {
                double f = -((ACopy[i][l]) / (ACopy[l][l]));
                for (int j = l + 1; j < 2 * n; j++) {
                    ACopy[i][j] += f * ACopy[l][j];
                }
                ACopy[i][l] = 0;
            }
            l++;
            partialPivoting(l, ACopy);
        }
        if (Math.abs(ACopy[l][l]) <= eps) {
            System.out.println("Singular matrix!");
            return;
        }
        double[][] A1 = computeReverseMatrix(ACopy, A.length);
        double norm = normComputation(A, A1);
        System.out.println("||A*(A^-1) - In||1 = " + norm);
    }

    public static void main(String[] args) {
        System.out.println("Homework 3");
        System.out.println("---------------------------");

        int m = 10;
        double eps = Math.pow(10, -m);
        double[][] A = {{1., 0., 2.}, {0., 1., 0.}, {1., 1., 1.}};
//        double[][] A = {{3., 0., 1.}, {0., 1., 1.}, {6., 1., 4.}};
        System.out.println("Gauss elimination algorithm");
        GaussElimination(A, eps);
        System.out.println("---------------------------");

        System.out.println("Matrix determinant");
        System.out.println("det(A) = " + matrixDeterminant(A, A.length));
        System.out.println("---------------------------");
    }
}
