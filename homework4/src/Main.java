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

    private static boolean compareLists(List<Pair> A[], List<Pair> B[], double eps) {

        if (A.length != B.length) {
            return false;
        }
        int n = A.length;
        for (int i = 0; i < n; i++) {
            if (A[i].size() != B[i].size()) {
                return false;
            }
            if (A[i].size() == 0) {
                continue;
            }
            ListIterator<Pair> it = A[i].listIterator();
            ListIterator<Pair> it2 = B[i].listIterator();
            while (it.hasNext()) {
                Pair pair1 = it.next();
                Pair pair2 = it2.next();
                if (Math.abs(pair1.getVal() - pair2.getVal()) > eps || Math.abs(pair1.getCol() - pair2.getCol()) > eps) {
                    return false;
                }
            }
        }
        return true;
    }

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
    }

    private static List<Pair>[] matrixSum(int n, List<Pair> A[], List<Pair> B[]) {
        List<Pair> AplusB[] = new List[n];

        for (int i = 0; i < n; i++) {
            List<Pair> list = new ArrayList<Pair>();
            ListIterator<Pair> it = null;
            ListIterator<Pair> it2 = null;
            if (A[i] != null) {
                it = A[i].listIterator();
            }
            if (B[i] != null) {
                it2 = B[i].listIterator();
            }
            Pair<Double, Integer> a = Pair.createPair(0., 0), b = Pair.createPair(0., 0);
            boolean aChanged = false, bChanged = false;
            if (it != null && it.hasNext()) {
                a = it.next();
                aChanged = true;
            }
            if (it2 != null && it2.hasNext()) {
                b = it2.next();
                bChanged = true;
            }

            do {
                Pair<Double, Integer> pair = null;
                if (aChanged && a.getCol() < b.getCol()) {
                    pair = Pair.createPair(a.getVal(), a.getCol());
                    if (it != null && it.hasNext()) {
                        a = it.next();
                        aChanged = true;
                    } else {
                        aChanged = false;
                    }
                    if (pair != null) {
                        if (AplusB[i] == null) {
                            AplusB[i] = new ArrayList<Pair>();
                        }
                        AplusB[i].add(AplusB[i].size(), pair);
                    }
                } else if (bChanged && a.getCol() > b.getCol()) {
                    pair = Pair.createPair(b.getVal(), b.getCol());
                    if (it2 != null && it2.hasNext()) {
                        b = it2.next();
                        bChanged = true;
                    } else {
                        bChanged = false;
                    }
                    if (pair != null) {
                        if (AplusB[i] == null) {
                            AplusB[i] = new ArrayList<Pair>();
                        }
                        AplusB[i].add(AplusB[i].size(), pair);
                    }
                } else if (aChanged && bChanged) {
                    pair = Pair.createPair(a.getVal() + b.getVal(), a.getCol());
                    if (it != null && it.hasNext()) {
                        a = it.next();
                        aChanged = true;
                    } else {
                        aChanged = false;
                    }
                    if (it2 != null && it2.hasNext()) {
                        b = it2.next();
                        bChanged = true;
                    } else {
                        bChanged = false;
                    }
                    if (pair != null) {
                        if (AplusB[i] == null) {
                            AplusB[i] = new ArrayList<Pair>();
                        }
                        AplusB[i].add(AplusB[i].size(), pair);
                    }
                } else {
                    while (aChanged) {
                        pair = Pair.createPair(a.getVal(), a.getCol());
                        if (AplusB[i] == null) {
                            AplusB[i] = new ArrayList<Pair>();
                        }
                        AplusB[i].add(AplusB[i].size(), pair);
                        if (it != null && it.hasNext()) {
                            a = it.next();
                            aChanged = true;
                        } else {
                            aChanged = false;
                        }
                    }
                    while (bChanged) {
                        pair = Pair.createPair(b.getVal(), b.getCol());
                        if (it2 != null && it2.hasNext()) {
                            b = it2.next();
                            bChanged = true;
                        } else {
                            bChanged = false;
                        }
                        if (AplusB[i] == null) {
                            AplusB[i] = new ArrayList<Pair>();
                        }
                        AplusB[i].add(AplusB[i].size(), pair);
                    }
                }
            }
            while (aChanged || bChanged);
        }

        return AplusB;
    }

    private static List<Pair>[] transpusa(int n, double[] M) {
        List<Pair>[] aux = new List[n];

        for (int i = 0; i < n; i++) {
            Pair elem = Pair.createPair(M[i], 0);
            if (aux[i] == null) {
                aux[i] = new ArrayList<Pair>();
            }
            aux[i].add(aux[i].size(), elem);
        }

        return aux;
    }

    private static double[] matrixProduct2(int n, List<Pair> A[], double[] xb) {
        double AoriB[] = new double[n];
        for (int i = 0; i < n; i++) {
            ListIterator<Pair> it = A[i].listIterator();
            double sum = 0.;
            while (it.hasNext()) {
                Pair aij = it.next();
                int k = aij.getCol();
                sum += aij.getVal() * xb[k];
            }
            AoriB[i] = sum;
        }
        return AoriB;
    }

    private static List<Pair>[] matrixProduct2(int n, List<Pair> A[], List<Pair> B[]) {
        List<Pair> AoriB[] = new List[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ListIterator<Pair> it = A[i].listIterator();
                double sum = 0.;
                while (it.hasNext()) {
                    Pair aij = it.next();
                    int k = aij.getCol();
                    ListIterator<Pair> itb = B[k].listIterator();
                    while (itb.hasNext()) {
                        Pair xb = itb.next();
                        if (xb.getCol().equals(j)) {
                            sum += aij.getVal() * xb.getVal();
                        }
                    }
                }
                if (sum != 0) {
                    Pair pair = Pair.createPair(sum, j);
                    if (AoriB[i] == null) {
                        AoriB[i] = new ArrayList<Pair>();
                    }
                    AoriB[i].add(AoriB[i].size(), pair);
                }
            }
        }
        return AoriB;
    }

    public static void main(String[] args) {
        System.out.println("Homework 4");
        System.out.println("---------------------------");
        Scanner s = null;
        double eps = 0.0001;
        try {
            s = new Scanner(new File("resources/a.txt"));
        } catch (IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }

        int n = s.nextInt();
        s.nextLine();

        List<Pair> A[] = new List[n];
        double Ax[] = new double[n];
        readInputFromFile(s, n, A, Ax);

        //-----------------------------------------------------
        try {
            s = new Scanner(new File("resources/b.txt"));
        } catch (IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }

        n = s.nextInt();
        s.nextLine();

        List<Pair> B[] = new List[n];
        double Bx[] = new double[n];

        readInputFromFile(s, n, B, Bx);

        //----------------------------------------------------
        try {
            s = new Scanner(new File("resources/aplusb.txt"));
        } catch (IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }

        n = s.nextInt();
        s.nextLine();

        List<Pair> AplusB[] = new List[n];
        double AxplusBx[] = new double[n];

        readInputFromFile(s, n, AplusB, AxplusBx);

        //----------------------------------------------------
        try {
            s = new Scanner(new File("resources/aorib.txt"));
        } catch (IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }

        n = s.nextInt();
        s.nextLine();

        List<Pair> AoriB[] = new List[n];
        double AxoriBx[] = new double[n];

        readInputFromFile(s, n, AoriB, AxoriBx);

        //---------------------------------------------------

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = i + 1;
        }

        //----------------------------------------------------
        System.out.println(compareLists(matrixSum(n, A, B), AplusB, eps));
        System.out.println(compareLists(matrixProduct2(n, A, B), AoriB, eps));
        System.out.println(compareLists(matrixProduct2(n, A, transpusa(n, x)), transpusa(n, Ax), eps));
    }
}
