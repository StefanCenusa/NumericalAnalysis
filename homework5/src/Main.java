import java.util.*;
import java.io.IOException;
import java.io.File;

class Pair<K, V> {

    private final K val;
    private final V col;

    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<K, V>(element0, element1);
    }

    public Pair(K element0, V element1) {
        this.val = element0;
        this.col = element1;
    }

    public Double getVal() {
        return (Double) val;
    }

    public Integer getCol() {
        return (Integer) col;
    }
}

public class Main {

    private static void readInputFromFile(Scanner s, int n, List<Pair> A[], double Ax[]) {

        Scanner numScanner = new Scanner(s.nextLine()).useLocale(Locale.US);
        for (int i = 0; i < n; ) {
            if (numScanner.hasNextDouble()) {
                Ax[i] = numScanner.nextDouble();
                i++;
            }
            numScanner = new Scanner(s.nextLine()).useLocale(Locale.US);
        }

        numScanner = new Scanner(s.nextLine()).useLocale(Locale.US).useDelimiter(", ");

        while (s.hasNextLine()) {
            Double x = numScanner.nextDouble();
            Integer lin = numScanner.nextInt();
            Integer col = numScanner.nextInt();
            Pair<Double, Integer> pair = Pair.createPair(x, col);
            if (A[lin] == null) {
                A[lin] = new ArrayList<Pair>();
            }
            A[lin].add(A[lin].size(), pair);
            numScanner = new Scanner(s.nextLine()).useLocale(Locale.US).useDelimiter(", ");
        }

        Double x = numScanner.nextDouble();
        Integer lin = numScanner.nextInt();
        Integer col = numScanner.nextInt();
        Pair<Double, Integer> pair = Pair.createPair(x, col);
        if (A[lin] == null) {
            A[lin] = new ArrayList<Pair>();
        }
        A[lin].add(A[lin].size(), pair);
    }

    public static boolean checkDiagonals(int n, List<Pair> A[]) {
        for (int i = 0; i < n; i++) {
            boolean notNull = false;
            ListIterator<Pair> it = A[i].listIterator();
            while (it.hasNext()) {
                Pair aij = it.next();
                if (aij.getCol() == i && aij.getVal() != 0) {
                    notNull = true;
                    break;
                }
            }
            if (!notNull) {
                return false;
            }
        }
        return true;
    }

    public static double[] arraySubtract(double[] a, double[] b) {
        int n = a.length;
        double[] array = new double[n];
        for (int i = 0; i < n; i++)
            array[i] = a[i] - b[i];
        return array;
    }

    public static double euclideanNorm(double z[]) {
        double norm = 0.0;
        int n = z.length;
        for (int i = 0; i < n; i++) {
            norm += z[i] * z[i];
        }
        return Math.sqrt(norm);
    }

    public static double[] xSOR(int n, List<Pair> A[], double[] b, double eps) {
        double xc[] = new double[n];
        double xp[] = new double[n];
        for (int i = 0; i < n; i++) {
            xp[i] = xc[i] = 0;
        }
        int k = 0, kmax = 10000;
        double deltaX = 0.;
        double deltaMax = Math.pow(10, 8);
        double Aii = 0., Aij = 0.;

        do {
            for (int i = 0; i < n; i++) {
                xp[i] = xc[i];
            }
            for (int i = 0; i < n; i++) {
                ListIterator<Pair> it = A[i].listIterator();
                while (it.hasNext()) {
                    Pair aij = it.next();
                    if (aij.getCol() == i) {
                        Aii = aij.getVal();
                        break;
                    }
                }
                double sum1 = 0., sum2 = 0.;
                for (int j = 0; j < i - 1; j++) {
                    Aij = 0.;
                    ListIterator<Pair> it2 = A[i].listIterator();
                    while (it2.hasNext()) {
                        Pair aij = it2.next();
                        if (aij.getCol() == j) {
                            Aij = aij.getVal();
                            break;
                        }
                    }
                    if (Aij != 0) {
                        sum1 += Aij * xc[j];
                    }
                }
                for (int j = i + 1; j < n; j++) {
                    Aij = 0.;
                    ListIterator<Pair> it2 = A[i].listIterator();
                    while (it2.hasNext()) {
                        Pair aij = it2.next();
                        if (aij.getCol() == j) {
                            Aij = aij.getVal();
                            break;
                        }
                    }
                    if (Aij != 0) {
                        sum2 += Aij * xp[j];
                    }
                }
                xc[i] = -0.2 * xp[i] + 1.2 * (b[i] - sum1 - sum2) / Aii;
            }
            k++;
            deltaX = euclideanNorm(arraySubtract(xc, xp));
        }
        while (deltaX >= eps && k < kmax && deltaX <= deltaMax);
        if (deltaX < eps) {
            System.out.println("Xc este aproximarea solutiei gasita in " + k + " iteratii");
            return xc;
        } else {
            double[] div = new double[1];
            System.out.println("Divergenta");
            return div;
        }
    }

    public static void main(String[] args) {
        System.out.println("Homework 5");
        System.out.println("---------------------------");
        Scanner s = null;
        int p = 5;
        double eps = Math.pow(10, -p);
        try {
            s = new Scanner(new File("resources/m_rar_2016_1.txt"));
        } catch (IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
            return;
        }

        int n = s.nextInt();
        s.nextLine();

        List<Pair> A[] = new List[n];
        double Ax[] = new double[n];
        readInputFromFile(s, n, A, Ax);
        System.out.println("1. ");
        System.out.println("---------------------------");
        System.out.println("Diagonals: " + checkDiagonals(n, A));

        double[] AxSor = xSOR(n, A, Ax, eps);
        System.out.println("||AxSOR - b|| = " + euclideanNorm(arraySubtract(AxSor, Ax)));

        System.out.println("end");
    }
}
