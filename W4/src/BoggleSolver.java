import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private trieST<Integer> dictTrieST;
    int R, C;
    boolean[][] dfsState;

    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dictTrieST = new trieST<Integer>();
        for (int i = 0; i < dictionary.length; i++) {
            dictTrieST.put(dictionary[i], i);
        }
    }

    private void dfsBoard(BoggleBoard b, ArrayList<String> validWords, int r, int c, int d, StringBuffer word) {

        if(dfsState[r][c])//visited 
            return;
        else
            dfsState[r][c] = true;

        word.append(b.getLetter(r, c));
        // check validity of word for length 3 or more
        if (d >= 2) {
            if (dictTrieST.get(word.toString()) != null) {
                if(!validWords.contains(word.toString()))
                    validWords.add(word.toString());
            }
        }

        if ((r - 1) >= 0) {
            // word.append(b.getLetter(r, c));
            dfsBoard(b, validWords, r - 1, c, d + 1, word);
            // remove added achar
            // word.deleteCharAt(d);
        }
        if ((r + 1) < R) {
            // word.append(b.getLetter(r, c));
            dfsBoard(b, validWords, r + 1, c, d + 1, word);
            // remove added achar
            // word.deleteCharAt(d);
        }
        if ((c - 1) >= 0) {
            // word.append(b.getLetter(r, c));
            dfsBoard(b, validWords, r, c - 1, d + 1, word);
            // remove added achar
            // word.deleteCharAt(d);
        }
        if ((c + 1) < C) {
            // word.append(b.getLetter(r, c));
            dfsBoard(b, validWords, r, c + 1, d + 1, word);
            // remove added achar
            // word.deleteCharAt(d);
        }
        if ((c + 1) < C && (r + 1) < R) {
            // word.append(b.getLetter(r, c));
            dfsBoard(b, validWords, r + 1, c + 1, d + 1, word);
            // remove added achar
            // word.deleteCharAt(d);
        }
        if ((c + 1) < C && (r - 1) >= 0) {
            // word.append(b.getLetter(r, c));
            dfsBoard(b, validWords, r - 1, c + 1, d + 1, word);
            // remove added achar
            // word.deleteCharAt(d);
        }
        if ((c - 1) >= 0 && (r + 1) < R) {
            // word.append(b.getLetter(r, c));
            dfsBoard(b, validWords, r + 1, c - 1, d + 1, word);
            // remove added achar
            // word.deleteCharAt(d);
        }
        if ((c - 1) >= 0 && (r - 1) >= 0) {
            // word.append(b.getLetter(r, c));
            dfsBoard(b, validWords, r - 1, c - 1, d + 1, word);
            // remove added achar
            // word.deleteCharAt(d);
        }
        word.deleteCharAt(d);
        dfsState[r][c] = false;     
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        ArrayList<String> validWords = new ArrayList<String>();
        R = board.rows();
        C = board.cols();

        // DFS the give board
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                StringBuffer sb = new StringBuffer("");
                dfsState = new boolean[R][C];
                dfsBoard(board, validWords, r, c, 0, sb);
            }
        }

        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        return 0;
    }

    // test main
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}