import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
	
	private final Digraph myGraph;
	
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
	   if(G == null)
	   {
		   throw new IllegalArgumentException("Invalid input");
	   }
	   myGraph = G;
   }

   private int ancestorParams(int v, int w, boolean isLength)
   {
	   
	   if (v == w)
	   {
		   if (isLength)
			   return (0);
		   else
			   return (v);
	   }
	   // find reachability from v
	   BreadthFirstDirectedPaths p1 = new BreadthFirstDirectedPaths(myGraph, v);
	   
	   // find reachability from w
	   BreadthFirstDirectedPaths p2 = new BreadthFirstDirectedPaths(myGraph, w);

	   Integer minLen = Integer.MAX_VALUE;
	   int ca = 0;
	   for(int u =0 ; u < myGraph.V(); u++)
	   {
		   if (/*u != v && u != w && */p1.hasPathTo(u) && p2.hasPathTo(u))
		   {
//			   StdOut.printf("u = %d, dt.p1=%d, dt.p2=%d\n", u, p1.distTo(u), p2.distTo(u));
			   if ((p1.distTo(u) + p2.distTo(u)) < minLen)
			   {
				   minLen = p1.distTo(u) + p2.distTo(u);
				   ca = u;
			   }
		   }
	   }
	   if(isLength)
	   {
		   return (minLen < Integer.MAX_VALUE ? minLen : -1);
	   }
	   else
	   {
		   return (minLen < Integer.MAX_VALUE ? ca : -1);
	   }
   }
   
   private boolean notIn (Iterable<Integer> v, int u)
   {
	   for(int n : v)
	   {
		   if(n == u)
			   return false;
	   }
	   return true;
   }
   private int ancestorParam(Iterable<Integer> v, Iterable<Integer> w, boolean isLength)
   {
	   // first set
	   BreadthFirstDirectedPaths p1 = new BreadthFirstDirectedPaths(myGraph, v);
	   
	   
	   //second set
	   BreadthFirstDirectedPaths p2 = new BreadthFirstDirectedPaths(myGraph, w);
	   
	   //find a shortest path from any of (v) to any of (w)
	   Integer minLen = Integer.MAX_VALUE;
	   int ca = 0;
	   for(int u =0 ; u < myGraph.V(); u++)
	   {
//		   StdOut.printf("u = %d, dt.p1=%d, dt.p2=%d\n", u, p1.distTo(u), p2.distTo(u));
		   if (notIn(v, u) && notIn(w, u) && p1.hasPathTo(u) && p2.hasPathTo(u))
		   {
			   if ((p1.distTo(u) + p2.distTo(u)) < minLen)
			   {
				   minLen = p1.distTo(u) + p2.distTo(u);
				   ca = u;
			   }
		   }
	   }
	   if(isLength)
	   {
		   return (minLen < Integer.MAX_VALUE ? minLen : -1);
	   }
	   else
	   {
		   return (minLen < Integer.MAX_VALUE ? ca : -1);
	   }
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
	   return (ancestorParams(v, w, true));
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {

	   return (ancestorParams(v, w, false));
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w)
   {
	   if(v == null || w == null)
	   {
		   throw new IllegalArgumentException("Invalid inut");
	   }
	   
	   // check zero size for v
	   int count = 0;
	   for (int t : v)
	   {
		   count++;
	   }
	   if (count == 0)
	   {
		   throw new IllegalArgumentException("Invalid inut");
	   }
	   
	   // check zero size for w
	   count = 0;
	   for (int t : w)
	   {
		   count++;
	   }
	   if (count == 0)
	   {
		   throw new IllegalArgumentException("Invalid inut");
	   }

	   return ancestorParam(v, w, true);
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
   {
	   return ancestorParam(v, w, false);
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
	    In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }

   }
}