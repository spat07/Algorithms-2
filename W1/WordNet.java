import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
	
	private final ArrayList<String> sset;
	private final HashMap<String, Integer> hashSset;
	private final Digraph hnG;
	private final SAP sap;

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
	   if(synsets == null || hypernyms == null)
	   {
		   throw new IllegalArgumentException("Invalid input");
	   }
	
	   //process synsets
	   sset = new ArrayList<String>();
	   hashSset = new HashMap<String, Integer>();
	   
	   In in1 = new In(synsets);
	   while(in1.hasNextLine())
	   {
		   String line = in1.readLine();

		   String[] fields = line.split(",");
		   
		   sset.add(fields[1]);
		   
		   //create hash map to index
		   String[] subFields = fields[1].split(" ");
		   
		   for(String n : subFields)
		   {
			   hashSset.put(n, Integer.parseInt(fields[0])); //map
		   }
	   }

	   //process hypernyms
	   In in2 = new In(hypernyms);
	   hnG = new Digraph(sset.size());
	   while(in2.hasNextLine())
	   {
		   String line = in2.readLine();
		   String[] fields = line.split(",");

		   Integer ssId = Integer.parseInt(fields[0]);
		   
		   for(int i = 1; i < fields.length; i++)
		   {
			   hnG.addEdge(ssId, Integer.parseInt(fields[i]));
		   }
	   }

	   //check if its rooted DAG
	   boolean rDag = false;
	   int rCnt = 0;
	   for(int v = 0 ; v < hnG.V(); v++)
	   {
		   if (hnG.outdegree(v) == 0 && hnG.indegree(v) != 0)
		   {
			   rDag = true;
			   rCnt++;
		   }
	   }
	   if(!(rDag == true && rCnt == 1))
	   {
		   throw new IllegalArgumentException("Input graph is not rooted DAG");
	   }
	   
//	   StdOut.printf("hnG V = %d, E = %d\n", hnG.V(), hnG.E());
	   
	   // create SAP
	   sap = new SAP(hnG);
   }
   

   // returns all WordNet nouns
   public Iterable<String> nouns()
   {
	   return sset;
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {

	   if(word == null)
	   {
		   throw new IllegalArgumentException("Input is null");
	   }
	   return (hashSset.containsKey(word));

   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)
   {
	   // crate reverse hash set to get the index
	   // get the index of the noun
	   // create SAP object and get the length
	   if(!(hashSset.containsKey(nounA) && hashSset.containsKey(nounB)))
	   {
		   throw new IllegalArgumentException("Invalid input Nouns");
	   }
	  
	   Integer a = hashSset.get(nounA);
	   Integer b = hashSset.get(nounB);
	   
	   return (sap.length(a, b));

   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
	   if(!(hashSset.containsKey(nounA) && hashSset.containsKey(nounB)))
	   {
		   throw new IllegalArgumentException("Invalid input Nouns");
	   }
	  
	   Integer a = hashSset.get(nounA);
	   Integer b = hashSset.get(nounB);
	   
	   Integer ca = sap.ancestor(a, b);
	   
	   return (ca != -1 ? sset.get(ca) : null);
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
	   WordNet wn1 = new WordNet(args[0], args[1]);
	    while (!StdIn.isEmpty()) {
	        String nA = StdIn.readString();
	        String nB = StdIn.readString();
	        int length   = wn1.distance(nA, nB);
	        String ancestor = wn1.sap(nA, nB);
	        StdOut.printf("distance = %d, ancestor = %s\n", length, ancestor);
	    }
   }
}