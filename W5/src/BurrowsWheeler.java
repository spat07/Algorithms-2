import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

public class BurrowsWheeler {
    // profiling data
    // private static long transformTime = 0;
    // private static long invTransformTime = 0;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        // profile
        // long start = 0, end = 0;

        StringBuilder in = new StringBuilder();
        // read last character sequece or sorted suffix array
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            in.append(c);
        }

        // start = System.currentTimeMillis();

        String input = in.toString();

        CircularSuffixArray csa = new CircularSuffixArray(input);
        int n = input.length();

        //create t[] array
        StringBuilder sb = new StringBuilder();
        int matchIdx = -1;

        // look up index() to get position of sorted suffix
        // read the char with that position
        for(int i = 0, idx = -1; i < n; i++) {
            idx = csa.index(i);
            if(idx == 0) {
                matchIdx = i;
                idx = n;
            }
            sb.append(input.charAt(idx - 1));
        }
        if (matchIdx != -1) {
            BinaryStdOut.write(matchIdx);
            // System.out.printf("Index:%d\n", matchIdx);
        } else {
            StdOut.println("Error! invalid matchIdx\n");
        }

        // end = System.currentTimeMillis();
        // transformTime = (end - start);

        BinaryStdOut.write(sb.toString());
        BinaryStdOut.flush();


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

        while(--mid >= start) {
            if(a[mid] != key) {
                break;
            }
        }

        return mid + 1;
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {

        // profile
        // long start = 0, end = 0;

        // read original string position in sorted order
        int first = BinaryStdIn.readInt();
        // System.out.printf("first=%d\n", first);

        ArrayList<Character> in = new ArrayList<>();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            in.add(c);
        }

        // start = System.currentTimeMillis();

        int n = in.size();
        // StdOut.printf("input:first =%d, %s, len %d\n",first, temp.toString(), temp.length());

        // generate sorted order sequence for fist char of suffix array
        Character[] t = new Character[n];
        t = in.toArray(t);
        
        Character[] f  = new Character[n];
        f = in.toArray(f);
        Arrays.sort(f);

        int next[] = new int[n];
        boolean serched[] = new boolean[n];

        for(int i = 0; i < n; i++) {
            int idx = binarySearch(f, 0, n, t[i]);
            while(serched[idx]) {
                idx++;
            }
            // System.out.printf("Search:i %d, key %c, idx %d, searched %b\n", i, t[i], idx, serched[i]);
            next[idx] = i;
            serched[idx] = true;
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0, j = first; i < n; i++) {
            sb.append(f[j]);
            j = next[j];
        }

        // end = System.currentTimeMillis();
        // invTransformTime= (end - start);

        BinaryStdOut.write(sb.toString());
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].charAt(0) == '-') {
            transform();
        } else if (args[0].charAt(0) == '+') {
            inverseTransform();
        }

        // System.out.printf("\n ################ transformTime: %d\n", transformTime);
        // System.out.printf("\n ################ invTransformTime: %d\n", invTransformTime);
    }

}
