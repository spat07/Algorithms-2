import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private final WordNet wnet;
	public Outcast(WordNet wordnet)         // constructor takes a WordNet object
	{
		if(wordnet == null)
		{
			throw new IllegalArgumentException("Invalid input");
		}
		wnet = wordnet;
	}
	public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
	{
		int maxDist = 0;
		String ocn = null;
		for(String fn : nouns)
		{
			int distance = 0;
			for(String sn : nouns)
			{
				distance += wnet.distance(fn, sn);
			}
			
			if(distance > maxDist)
			{
				maxDist = distance;
				ocn = fn;
			}
		}
		
		return ocn;
	}
	public static void main(String[] args)  // see test client below
	{
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}
	