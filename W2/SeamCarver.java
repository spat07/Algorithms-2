import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
	
	private Picture myImage;
	private double [][] imgEnrg;
	private double [][] pathEnrg;
	private int [][] edgeTo;

   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture) {
	   
	   if (picture == null)
		   throw new IllegalArgumentException("Invalid Input");

	   int H, W;
	   
	   myImage = new Picture(picture);
	   
	   H = picture.height();
	   W = picture.width();
	   
//	   StdOut.printf("height = %d, width = %d\n", H, W);
	   
	   imgEnrg = new double[H][W];
	   
	   for(int r = 0 ; r < H; r++) {
		   for (int c = 0; c < W; c++) {
			   imgEnrg[r][c] = energy(c, r);
		   }
	   }
	   
   }

   // current picture
   public Picture picture() {
	   
	   //make a copy and return
	   Picture imgCopy = new Picture(myImage);
	   return imgCopy;
   }

   // width of current picture
   public int width() {
	   return myImage.width();
   }

   // height of current picture
   public int height() {
	   return myImage.height();
   }

   // energy of pixel at column x and row y
   public double energy(int c, int r) {
	   
	   if (c < 0 || r < 0 || c >= width() || r >= height() ) {
		   throw new IllegalArgumentException("Inputs out of range");
	   }
	   
	   if(r == 0 || r == (height() - 1) || c == 0 || c == (width()- 1)) {
		   return 1000.00;
	   }
	   
	   int rgbL = myImage.getRGB(c, r - 1);
	   int rgbR = myImage.getRGB(c, r + 1);
	  
	   int rDiff = ((rgbL >> 16) & 0xFF) - ((rgbR >> 16) & 0xFF);
	   int gDiff = ((rgbL >> 8) & 0xFF) - ((rgbR >> 8) & 0xFF);
	   int bDiff = (rgbL & 0xFF) - (rgbR & 0xFF);
	   
	   long xGrad = rDiff * rDiff + gDiff * gDiff + bDiff * bDiff;
	   
	   int rgbU = myImage.getRGB(c + 1, r);
	   int rgbD = myImage.getRGB(c - 1, r);
	   rDiff = ((rgbU >> 16) & 0xFF) - ((rgbD >> 16) & 0xFF);
	   gDiff = ((rgbU >> 8) & 0xFF) - ((rgbD >> 8) & 0xFF);
	   bDiff = (rgbU & 0xFF) - (rgbD & 0xFF);
	   
	   long yGrad = rDiff * rDiff + gDiff * gDiff + bDiff * bDiff;
	   
	   return (Math.sqrt(xGrad + yGrad));
   }

   private void relax (int vc, int vr, int wc, int wr, double[][] enrg) {
	   if(pathEnrg[wr][wc] > pathEnrg[vr][vc] + enrg[wr][wc]) {
		   pathEnrg[wr][wc] = pathEnrg[vr][vc] + enrg[wr][wc];
		   edgeTo[wr][wc] = vc;
	   }
   }
   
   private int[] findSeam(double[][] enrg, int H, int W) {
	   for(int r = 0; r < H - 1; r++) {
		   for(int c = 0; c < W; c++) {

			   // relax c - 1, r + 1 (if c > 0)
			   if(c > 0 ) {
				   relax(c, r, c - 1, r + 1, enrg);
			   }
			   
			   // relax c, r+1
			   relax(c, r, c , r + 1, enrg);


			   // relax c + 1, r + 1 (if c < W)
			   if(c < W - 1) {
				   relax(c, r, c + 1, r + 1, enrg); 
			   }

		   }
				   
	   }
	   
	   // find min energy index
	   double minEnrg = Double.POSITIVE_INFINITY;
	   int minIdx = 0;
	   for(int c = 0; c < W; c++) {
		   if(pathEnrg[H-1][c] < minEnrg) {
			   minEnrg = pathEnrg[H-1][c];
			   minIdx = c;
		   }
	   }
	   
	   // now find path walking back from minIdx in edge to
	   int seam[] = new int[H];
	   seam[H-1] = minIdx;
	   for(int r = H - 2; r >= 0; r--) {
		   seam[r] = edgeTo[r+1][seam[r+1]];
	   }
	   
//	   //test code
//	   StdOut.print("Path Energies:\n");
//	   for(int r = 0; r < H; r++) {
//		   for(int c = 0; c < W; c++) {
//			   StdOut.printf("%.2f,", pathEnrg[r][c]);
//		   }
//		   StdOut.print("\n");
//	   }
//	   
//	   StdOut.print("Path:\n");
//	   for(int r = 0; r < H; r++) {
//		   for(int c = 0; c < W; c++) {
//			   StdOut.printf("%d,", edgeTo[r][c]);
//		   }
//		   StdOut.print("\n");
//	   }
	   
//	   StdOut.print("Print seam:\n");
//	   for(int r = 0; r < H; r++) {
//		   StdOut.printf("%d, ", seam[r]);
//	   }
//	   StdOut.print("\n");
	   
	   return seam;
	   
   }

   private void setup(int H, int W) {
	   pathEnrg = new double[H][W];
	   edgeTo = new int[H][W];
	   
	   for(int r = 0 ; r < H; r++) {
		   for (int c = 0; c < W; c++) {
			   pathEnrg[r][c] = Double.POSITIVE_INFINITY;
			   edgeTo[r][c] = 0;
		   }

	   }
	   
	   for(int c = 0; c < W; c++) {
		   pathEnrg[0][c] = 0;
	   }

   }
   // sequence of indices for horizontal seam
   public int[] findVerticalSeam() {
	   setup(height(), width());
	   int seam[] = findSeam(imgEnrg, height(), width());
	   return seam;

   }

   private double[][] transposeImgEnrg() {
	   //reversed dims
	   int H = width();
	   int W = height();
	   
	   double [][] enrg = new double[H][W]; //reversed dims
	   
	   
	   for(int r = 0; r < H; r++) {
		   for(int c = 0; c < W; c++) {
			   enrg[r][c] = imgEnrg[c][r];
		   }
	   }
	   
	   return enrg;
   }
   // sequence of indices for vertical seam
   public int[] findHorizontalSeam() {
	   //transpose the imgEnrg and find vertical seam
	   double tpImgEngrg[][] = transposeImgEnrg();

	   //we exchanged dims as transpose
	   setup(width(), height());
	   int seam[] = findSeam(tpImgEngrg, width(), height());
	   return seam;
   }

   // remove horizontal seam from current picture
   public void removeHorizontalSeam(int[] seam) {
	   
	   if (seam == null || seam.length != width())
		   throw new IllegalArgumentException("Invalid Input");

 	   for(int v : seam) {
 		   if (v < 0) {
 			  throw new IllegalArgumentException("Invalid seam values!");
 		   }
 	   }
 	   
	   for(int r = 1; r < seam.length; r++) {
		   if(Math.abs(seam[r - 1 ] - seam[r]) > 1) {
			   throw new IllegalArgumentException("Invalid seam values!");
		   }
	   }
	   
	   int H = myImage.height() - 1;
	   int W = width();
	   
	   Picture modPic = new Picture(W , H); // remove one horizontal pixel line

	   
	   int rIn, cIn, rOut, cOut;
	   for(cIn = 0, cOut = 0; cIn < myImage.width(); cIn++, cOut++) {
		   for(rIn = 0, rOut = 0; rIn < seam[cIn] && rIn < modPic.height(); rIn++, rOut++) {
			   modPic.setRGB(cOut, rOut, myImage.getRGB(cIn, rIn));
		   }
		   rIn++; //skip seam
		   for(;rIn < myImage.height();rIn++, rOut++) {
			   modPic.setRGB(cOut, rOut, myImage.getRGB(cIn, rIn));
		   }
	   }
	   
	   myImage = modPic;
	   
	   double [][] modImgEnrg = new double[H][W];
	   for(int r = 0 ; r < H; r++) {
		   for (int c = 0; c < W; c++) {
			   modImgEnrg[r][c] = energy(c, r);
		   }
	   }
	   
	   imgEnrg = modImgEnrg;
   }

   // remove vertical seam from current picture
   public void removeVerticalSeam(int[] seam) {
	   
	   if (seam == null || seam.length != height())
		   throw new IllegalArgumentException("Invalid Input");
	   
	   for(int v : seam) {
		   if (v < 0) {
			   throw new IllegalArgumentException("Invalid Input");
		   }
	   }
	   
	   for(int c = 1; c < seam.length; c++) {
		   if(Math.abs(seam[c - 1 ] - seam[c]) > 1) {
			   throw new IllegalArgumentException("Invalid seam values!");
		   }
	   }
	   
	   int W = myImage.width() - 1;
	   int H = height();
	   
	   Picture modPic = new Picture(W, H); // remove one horizontal pixel line
	   
	   int rIn, cIn, rOut, cOut;
	   
	   for(rIn = 0, rOut = 0; rIn < modPic.height(); rIn++, rOut++) {
		   for(cIn = 0, cOut = 0; cIn < seam[rIn] && cIn < modPic.width(); cIn++, cOut++) {
			   modPic.setRGB(cOut, rOut, myImage.getRGB(cIn, rIn));
		   }
		   cIn++; //skip seam
		   for(;cIn < myImage.width();cIn++, cOut++) {
			   modPic.setRGB(cOut, rOut, myImage.getRGB(cIn, rIn));
		   }
	   }
	   
	   myImage = modPic;
	   
   
	   double [][] modImgEnrg = new double[H][W];
	   for(int r = 0 ; r < H; r++) {
		   for (int c = 0; c < W; c++) {
			   modImgEnrg[r][c] = energy(c, r);
		   }
	   }
	   
	   imgEnrg = modImgEnrg;
   }
   
//   public Picture pictureEnergies() {
//	   Picture newPic = new Picture(myImage.width(), myImage.height());
//	   for(int r = 0; r < myImage.height(); r++) {
//		   for(int c = 0; c < myImage.width(); c++) {
//			   newPic.setRGB(c, r, (int)(imgEnrg[r][c]));
//		   }
//	   }
//	   
//	   return newPic;
//   }
//   public void printValues() {
//	   // test code
//	   for(int r = 0; r < myImage.height(); r++) {
//		   for(int c = 0; c < myImage.width(); c++) {
//			   StdOut.printf("0x%x, ", myImage.getRGB(c, r));
//		   }
//		   StdOut.print("\n");
//	   }
//   }

   //  unit testing (optional)
   public static void main(String[] args) {
	   
	   Picture testImg = new Picture(args[0]);
	   SeamCarver sc1 = new SeamCarver(testImg);
	   
//	   StdOut.print("find vertical seam\n");
//	   int vSeam[] = sc1.findVerticalSeam();
//	   
//	   
//	   StdOut.print("Before vertical removing seam\n");
//	   sc1.printValues();
//	   sc1.removeVerticalSeam(vSeam);
//	   StdOut.print("After vertical removing seam\n");
//	   sc1.printValues();
//	   
//	   
//	   StdOut.print("find horizontal seam\n");
//	   int hSeam[] = sc1.findHorizontalSeam();
//	   StdOut.print("Before removing horizontal seam\n");
//	   sc1.printValues();
//	   sc1.removeHorizontalSeam(hSeam);
//	   StdOut.print("After horizontal removing seam\n");
//	   sc1.printValues();

//	   Picture pe = sc1.pictureEnergies();
//	   pe.save(args[1]);
	   
	   
   }

}