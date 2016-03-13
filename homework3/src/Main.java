public class Main {

    public static double matrixDeterminant(double[][] A, int n) {
        double sum = 0.0;
        double[][] c = new double[n][n];
        if (n == 2) {
            return (A[0][0] * A[1][1] - A[0][1] * A[1][0]);
        } else {
            int line = 0, col = 0;
            for (int p = 0; p < n; p++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (j == p)
                            continue;
                        c[line][col] = A[i][j];
                        col++;
                        if (col == n - 1) {
                            line++;
                            col = 0;
                        }
                    }
                }
                sum = sum + A[0][p] * Math.pow(-1, p) * matrixDeterminant(c, n - 1);
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
                    matrix[i][j] += A[i][x] * A[x][i];
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
        for (int j = 0; j < n; j++) {
            aux = A[i0][j];
            A[i0][j] = A[l][j];
            A[l][j] = aux;
        }
    }

    public static void reverseMatrixComputation() {

    }

    public static void GaussElimination(double[][] A, double eps) {
        int n = A.length;
        double[][] Acopy = new double[n][n];
        for (int i = 0; i < A.length; i++)
            Acopy[i] = A[i].clone();

        int l = 0;
        partialPivoting(l, A);
        while ((l < n) && (Math.abs(Acopy[l][l]) > eps)) {
            for (int i = l + 1; i < n; i++) {
                double f = -(Acopy[i][l]) / (Acopy[l][l]);
                for (int j = l + 1; j < 2 * n; j++) {
                    Acopy[i][j] = Acopy[i][j] + f * Acopy[i][j];
                }
                Acopy[i][l] = 0;
            }
            l++;
            partialPivoting(l, A);
        }
        if (Math.abs(Acopy[l][l]) <= eps) {
            System.out.println("Singular matrix!");
            return;
        }
        reverseMatrixComputation();
        double norm = normComputation(A, Acopy);
        System.out.println("||A*(A^-1) - In||1 = " + norm);
    }

    public static void main(String[] args) {
        System.out.println("Homework 3");
        System.out.println("---------------------------");

        int m = 10;
        double eps = Math.pow(10, -m);
        double[][] A = {{1., 0., 2.}, {0., 1., 0.}, {1., 1., 1.}};
        System.out.println("Gauss elimination algorithm");
        System.out.println("---------------------------");
        GaussElimination(A, eps);

        System.out.println("Matrix determinant");
        System.out.println("---------------------------");
        matrixDeterminant(A, A.length);
    }
}
