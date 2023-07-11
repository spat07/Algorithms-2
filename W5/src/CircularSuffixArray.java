import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private int len;
    private String[] suffixes;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if(s == null) {
            throw new IllegalArgumentException("Input can not be null!");
        }
        len = s.length();
        suffixes = new String[len];

        suffixes[0] = s;
        for(int i = 1; i < len; i++) {
            suffixes[i] = s.substring(i, len) + s.substring(0, i);
        }

        index = LSDSort.sort(suffixes, len);
        
        // StdOut.println("sorted suffixes:");
        // for(int i = 0; i < len; i++) {
        //     StdOut.printf("%2d, %12s, %2d\n", i, suffixes[i], index[i]);
        // }
    }

    // length of s
    public int length() {
        return len;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        // throw is i is our of range
        return index[i];
    }


    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("CircularSuffixArray class:");
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        BinaryStdOut.write(csa.suffixes[0], csa.index(0));
        BinaryStdOut.flush();
    }
}
