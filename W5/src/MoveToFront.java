import java.util.ArrayList;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;


public class MoveToFront {
    public static final int alphaLen = 256;
   
    public static int seq[] = new int[alphaLen];
    public static char seq2[] = new char[alphaLen];
    public static ArrayList<Integer> out = new ArrayList<>();

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        for(int i = 0; i < alphaLen; i++) {
            seq[i] = i;
        }

        while(!BinaryStdIn.isEmpty()) {
            Character ch = BinaryStdIn.readChar();
            //hack for windows crap
            // if ((int)ch >= 48 && (int)ch <= 122) {
                Integer chIdx = seq[((int) ch)];

                out.add(chIdx);

                for(int i = 0; i < alphaLen; i++) {
                    if(seq[i] < chIdx) {
                        seq[i]++;
                    }
                }
                seq[(int)ch] = 0;
            // }
        }
        // map back
        for(int i = 0; i < alphaLen; i++) {
            int idx = seq[i];
            seq2[idx] = (char)i;
        }

    }


    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        
        for(int i = 0; i < alphaLen; i++) {
            seq[i] = i;
        }

        while (!BinaryStdIn.isEmpty()) {
            Character ch = BinaryStdIn.readChar();
            // hack for windows crap
            Integer chIdx = (int) ch;
            for (int i = 0; i < alphaLen; i++) {
                if (seq[i] < chIdx) {
                    seq[i]++;
                } else if (seq[i] == chIdx) {
                    StdOut.print((char)i);
                    seq[i] = 0;
                }
            }

        }

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        // System.out.println("MoveToFront Encoding/Decoding class!");

        if(args[0].charAt(0) == '-') {
            encode();
            // for(int i = 0; i < alphaLen; i++) {
            //     BinaryStdOut.write(seq2[i]);
            // }
            for(int i = 0; i < out.size(); i++) {
                int val = out.get(i);
                BinaryStdOut.write((char) val);
            }
            BinaryStdOut.flush();
            // System.out.println("\nPrint output seqeunce\n");
            // for(int i = 0; i < out.size(); i++) {
            //     System.out.printf("%d\n", out.get(i));
            // }
            // System.out.flush();
        } else if(args[0].charAt(0) == '+') {
            decode();
        }
    }

}