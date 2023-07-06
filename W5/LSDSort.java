// import java.util.Arrays;
// import java.util.HashMap;
// import edu.princeton.cs.algs4.StdOut;

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

        //for debug
        // ipCopy = Arrays.copyOf(a, a.length);
        // HashMap<String, Integer> strIdx = new HashMap<>(n);
        // for(int i = 0; i < n; i++) {
        //     strIdx.put(ipCopy[i], i);
        // }

        for(int i = 0; i < n; i++) {
            index[i] = i;
        }

        for (int d = w-1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R+1];
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
        }
        return index;
    }
    public static void main(String[] args) {
        
    }
}
