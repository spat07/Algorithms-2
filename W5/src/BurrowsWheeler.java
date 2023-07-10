// import edu.princeton.cs.algs4.BinaryStdIn;
// import edu.princeton.cs.algs4.BinaryStdOut;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        // String s = BinaryStdIn.readString();
        String s = StdIn.readString();

        StdOut.printf("Input string : %s\n", s);

        CircularSuffixArray csa = new CircularSuffixArray(s);
        int n = s.length();
        String[] suffixes = csa.sortedSuffixes();

        //create t[] array
        StringBuilder sb = new StringBuilder();
        int matchIdx = -1;
        for(int i = 0; i < n; i++) {
            sb.append(suffixes[i].charAt(n-1));
            if(csa.index(i) == 0) {
                matchIdx = i;
            }
        }
        if (matchIdx != -1) {
            // BinaryStdOut.write(matchIdx);
            StdOut.printf("idx:%d\n", matchIdx);
        } else {
            StdOut.println("Error! invalid matchIdx\n");
        }

        // BinaryStdOut.write(sb.toString());
        StdOut.printf("t[] : %s\n", sb.toString());
    }

    private static int binarySearch(Character[] a, int start, int end, char key) {
        int hi = end, lo = start;
        int mid = (start + end) / 2;
        while (lo < hi) {
            mid = (hi + lo) / 2;
            if (a[mid] == key) {
                break;
            } else if (a[mid] > key) {
                hi = mid;
            } else if (a[mid] < key) {
                lo = mid;
            }
        }

        // check untill start if same key repeating
        // if so send the first occurance

        while(mid >= start) {
            if(a[mid] != key) {
                mid++;
                break;
            }
            mid--;
        }

        return mid;
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {

        // read original string position in sorted order
        // int k = BinaryStdIn.readInt();
        int k = StdIn.readInt();

        ArrayList<Character> in = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        // read last character sequece or sorted suffix array
        while (!StdIn.isEmpty()) {
            // while (!BinaryStdIn.isEmpty()) {
            // in.add(BinaryStdIn.readChar());
            char c = StdIn.readChar();
            // System.out.printf("c=%d\n", (int)c);
            if (((int) c >= 65 && (int) c <= 90) || ((int)c == 33)) {
                in.add(c);
                temp.append(c);
            }
        }
        int n = in.size();
        StdOut.printf("input:%s\n",temp.toString());

        // generate sorted order sequence for fist char of suffix array
        Character[] t = new Character[n];
        t = in.toArray(t);
        
        Character[] f  = new Character[n];
        f = in.toArray(f);
        Arrays.sort(f);

        // StdOut.printf("t = %s\n",Arrays.toString(t));
        // StdOut.printf("f = %s\n",Arrays.toString(f));


        int next[] = new int[n];
        boolean serched[] = new boolean[n];

        // next[0] = k;
        // serched[0] = true;
    
        for(int i = 0; i < n; i++) {
            int idx = binarySearch(f, 0, n, t[i]);
            while(serched[idx]) {
                idx = binarySearch(f, idx + 1, n, t[i]);
            }
            System.out.printf("Search:i %d, key %c, idx %d, searched %b\n", i, t[i], idx, serched[i]);
            next[idx] = i;
            serched[idx] = true;
        }

        
        StringBuilder sb = new StringBuilder();
        for(int i = 0, j = 0; i < n; i++) {
            sb.append(f[next[j]]);
            j = next[j];
        }
        System.out.printf("decoded output: %s", sb.toString());
               
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {

        if (args[0].charAt(0) == '-') {
            transform();
        } else if (args[0].charAt(0) == '+') {
            inverseTransform();
        }
    }

}
