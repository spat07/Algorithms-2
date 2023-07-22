// import edu.princeton.cs.algs4.BinaryStdOut;
// import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

// import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private int n;
    private CircularString[] suffixes;
    // private int[] index;
    // private String ss;

    private class CircularString implements Comparable<CircularString>{
        private int start;
        private String orgStrRef;

        public CircularString(String s, int offset) {
            orgStrRef = s;
            start = offset;
        }

        private char CSCharAt(int offset) {
            // return orgStrRef.charAt(start + offset);
            return orgStrRef.charAt((start + offset) % n);
        }

        @Override
        public int compareTo(CircularString that) {
            for(int i = 0; i < n; i++) {
                if (this.CSCharAt(i) < that.CSCharAt(i))
                    return -1;
                if (this.CSCharAt(i) > that.CSCharAt(i))
                    return +1;
            }
            return 0;
        }

        public String CSToString() {
            // return (orgStrRef.substring(start, start + n));
            return (orgStrRef.substring(start, n) + orgStrRef.substring(0, start));
        }
    }


    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if(s == null) {
            throw new IllegalArgumentException("Input can not be null!");
        }

        n = s.length();
        // ss = new String(s + s);
        suffixes = new CircularString[n];

        for(int i = 0; i < n; i++) {
            suffixes[i] = new CircularString(s, i);
        }

        Arrays.sort(suffixes);

        // StdOut.println("sorted suffixes:");
        // for(int i = 0; i < n; i++) {
        //     StdOut.printf("%2d, %12s, %2d\n", i, suffixes[i].CSToString(), suffixes[i].start);
        // }
    }

    // length of s
    public int length() {
        return n;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        // throw is i is our of range
        if(i >= n || i < 0)
            throw new java.lang.IllegalArgumentException();
        return suffixes[i].start;
    }


    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("CircularSuffixArray class:");
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        // BinaryStdOut.write(csa.suffixes[0].CSToString(), csa.index(0));
        // BinaryStdOut.flush();
    }

}