import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.StdOut;

public class testMst
{
	public static void main(String args[])
	{
		In in = new In(args[0]);
		EdgeWeightedGraph ewg1 = new EdgeWeightedGraph(in);
		
		KruskalMST m1 = new KruskalMST(ewg1);
		
		for(Edge e : m1.edges())
		{
			StdOut.println(e);
		}
	}
}