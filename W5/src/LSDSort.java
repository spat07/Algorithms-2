import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class LSDSort {
   static String[] ipCopy;

    LSDSort() {

    }


    public static int[] sort(String[] a, int w) {
        int n = a.length;
        int R = 256;   // extend ASCII alphabet size
        String[] aux = new String[n];
        int[] index = new int[n];
        int[] temp = new int[n];
        int[] temp2 = new int[n];
        int[] count = new int[R+1];        


        for(int i = 0; i < n; i++) {
            index[i] = i;
        }

        for (int d = w-1; d >= 0; d--) {
            // sort by key-indexed counting on dth character
            // initialize count
            for (int i = 0; i <= R; i++) {
                count[i] = 0;
            }

            // compute frequency counts
            for (int i = 0; i < n; i++)
                count[a[i].charAt(d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // move data
            for (int i = 0; i < n; i++) {
                temp[count[a[i].charAt(d)]] = i;
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            //
            // copy back
            for (int i = 0; i < n; i++) {
                a[i] = aux[i];
                //update index tracker
                temp2[i] = index[temp[i]];
            }

            for(int i = 0; i < n; i++) {
                index[i] = temp2[i];
            }

            // StdOut.printf("%5s, %12s, %5s, %5s, %5s\n","idx", "input", "org", "temp", "index");
            // for(int i = 0; i < n; i++) {
            //     StdOut.printf("%5d, %12s, %5d, %5d, %5d\n", i, a[i], strIdx.get(a[i]), temp[i], index[i]);
            // }
            // StdOut.println("========================================\n");



            // more optimized 
            // // LSD sort implementation
            // final int R = 256; // extend ASCII alphabet size
            // CircularString[] aux = new CircularString[n];

            // int[] temp = new int[n];
            // int[] temp2 = new int[n];
            // int[] count = new int[R+1];
            // int[] accumulates = new int[R+1];

            // for(int i = 0; i < n; i++) {
            // index[i] = i;
            // }

            // // StdOut.println("unsorted suffixes:");
            // // for(int i = 0; i < n; i++) {
            // // StdOut.printf("%2d, %12s\n", i, suffixes[i].CSToString());
            // // }

            // // compute cumulates only once

            // // compute frequency counts
            // for (int i = 0; i < n; i++)
            // accumulates[suffixes[i].CSCharAt(n-1) + 1]++;

            // // compute cumulates
            // for (int r = 0; r < R; r++)
            // accumulates[r + 1] += accumulates[r];

            // for (int d = n-1; d >= 0; d--) {
            // // sort by key-indexed counting on dth character
            // // initialize count

            // // copy original accumulates
            // for (int i = 0; i <= R; i++) {
            // count[i] = accumulates[i];
            // }

            // // move data
            // for (int i = 0; i < n; i++) {
            // temp[count[suffixes[i].CSCharAt(d)]] = i;
            // aux[count[suffixes[i].CSCharAt(d)]++] = suffixes[i];
            // }
            // //
            // // copy back
            // for (int i = 0; i < n; i++) {
            // suffixes[i] = aux[i];
            // //update index tracker
            // temp2[i] = index[temp[i]];
            // }

            // for(int i = 0; i < n; i++) {
            // index[i] = temp2[i];
            // }

            // // StdOut.printf("%5s, %12s, %5s, %5s, %5s\n","idx", "input", "org", "temp",
            // "index");
            // // for(int i = 0; i < n; i++) {
            // // StdOut.printf("%5d, %12s, %5d, %5d, %5d\n", i, a[i], strIdx.get(a[i]),
            // temp[i], index[i]);
            // // }
            // // StdOut.println("========================================\n");
            // }
        }
        return index;
    }

    
    public static void sort2(String s) {

        int n = s.length();
        int R = 256; // alphabet size

        // Geenrate the W-1 level, rotation by n
        String slast = s.substring(n - 1, n) + s.substring(0, n - 1);

        StdOut.printf("slast = %s\n", slast);

        int[] count = new int[R+1]; 

        // generate dth string
        for(int d = n - 1; d >= 0; d--) {

            String sr = s.substring(d, n) + s.substring(0, d);
            char[] a = sr.toCharArray();
            StdOut.printf("unsorted: %s\n", Arrays.toString(a));

            // initialize count
            for (int i = 0; i <= R; i++) {
                count[i] = 0;
            }
            // compute frequency counts
            for (int i = 0; i < n; i++) {
                count[a[i] + 1]++;
            }
            // compute cumulates
            for (int r = 0; r < R; r++) {
                count[r+1] += count[r];
            }
            // sort to new string
            char[] aux = new char[n];
            for(int i = 0; i < n; i++) {
                aux[count[a[i]]++] = a[i];
            }

            // copyback to a
            for (int i = 0; i < n; i++) {
                a[i] = aux[i];
            }
            StdOut.printf("sorted: %s\n", Arrays.toString(a));
        }

    }

    public static void main(String[] args) {

        String s = new String("ABRACADABRA!");
        sort2(s);
        
    }
}
