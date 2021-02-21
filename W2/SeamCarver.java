import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
	
	private Picture myImage;
	private double [][] imgEnrg;
	private int H, W;

   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture) {
	   
	   if (picture == null)
		   throw new IllegalArgumentException("Invalid Input");
	   
	   myImage = new Picture(picture);
	   
	   H = picture.height();
	   W = picture.width();
	   
	   StdOut.printf("height = %d, width = %d\n", H, W);
	   
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
	   
	   if(r == 0 || r == (H - 1) || c == 0 || c == (W - 1)) {
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

   // sequence of indices for horizontal seam
   public int[] findHorizontalSeam() {
	   // start from r = 1 to H-1
	   // for all vertices in height
	   // 	find the imgEnrg[r+1, (c-1, c, c+1)] with minimizing energySum
	   //   add it to the list
	   
	   int [] vList = new int[W];
	   int [] tempvList = new int[W];
	   double enrgSum = Double.MAX_VALUE;
	   
	   for(int r = 1 ; r < H - 1; r++) {

		   int r_ = r;
		   double tempEnrgSum = 0;
		   
		   int c = 0;
		   tempvList[c] = r_;
		   tempEnrgSum += imgEnrg[r_][c];
		   
		   for(c = 1; c < W - 1; c++) {
//			   StdOut.printf("ie[%d][%d]=%f, ie[%d][%d]=%f, ie[%d][%d]=%f, ", 
//					   r_ - 1, c, imgEnrg[r_ - 1][c],
//					   r_, c, imgEnrg[r_][c],
//					   r_ + 1, c, imgEnrg[r_ + 1][c]);
			   
			   if (imgEnrg[r_ - 1][c] < imgEnrg[r_][c]) { // a < b
				   
				   if(imgEnrg[r_ - 1][c] < imgEnrg[r_ + 1][c]) { // a < c
					   r_ = r_ - 1;	// a is least
				   } else {
					   r_ = r_ + 1; // c is least (a < b and a > c => c < b as well)
				   }
			   } else { // a > b
				   if (imgEnrg[r_][c] < imgEnrg[r_ + 1][c]) { // b < c
					   // no change - b is least
				   } else { // c < b
					   r_ = r_ + 1; // c is least (a > b and b > c)
				   }
			   }
			   
			   
			   tempvList[c] = r_;
			   tempEnrgSum += imgEnrg[r_][c];
//			   StdOut.printf("tempvList[%d]=%d, tempEnrgSum=%f, enrgSum = %f\n", c, tempvList[c], tempEnrgSum, enrgSum);
		   }
		   
		   tempvList[c] = r_ - 1;
		   tempEnrgSum += imgEnrg[r_][c];
		   
//		   StdOut.printf("\ntempEnrgSum=%f, enrgSum=%f, c=%d, tempvList=", tempEnrgSum, enrgSum, c);
//		   for(int i : tempvList) {
//			   StdOut.printf("%d,", i);
//		   }
		   if(tempEnrgSum < enrgSum) {
			   System.arraycopy(tempvList, 0, vList, 0, W);
			   enrgSum = tempEnrgSum;
		   }
	   }
	   
	   return vList;

   }

   // sequence of indices for vertical seam
   public int[] findVerticalSeam() {
	   
	   // start from c = 0 to W-1
	   // for all vertices in height
	   // 	find the imgEnrg[r+1, (c-1, c, c+1)] with minimizing energySum
	   //   add it to the list
	   
	   int [] vList = new int[H];
	   int [] tempvList = new int[H];
	   double enrgSum = Double.MAX_VALUE;
	   
	   for(int c = 1 ; c < W - 1; c++) {

		   int c_ = c;
		   double tempEnrgSum = 0;
		   
		   int r = 0;
		   tempvList[r] = c_;
		   tempEnrgSum += imgEnrg[r][c_];
		   
		   for(r = 1; r < H - 1; r++) {
//			   StdOut.printf("ie[%d][%d]=%f, ie[%d][%d]=%f, ie[%d][%d]=%f, ", 
//					   r, c_ -1, imgEnrg[r][c_ - 1],
//					   r, c_, imgEnrg[r][c_],
//					   r, c_ + 1, imgEnrg[r][c_ + 1]);
			   
			   if (imgEnrg[r][c_ - 1] < imgEnrg[r][c_]) { // a < b
				   
				   if(imgEnrg[r][c_ - 1] < imgEnrg[r][c_ + 1]) { // a < c
					   c_ = c_ - 1;	// a is least
				   } else {
					   c_ = c_ + 1; // c is least (a < b and a > c => c < b as well)
				   }
			   } else { // a > b
				   if (imgEnrg[r][c_] < imgEnrg[r][c_ + 1]) { // b < c
					   // no change - b is least
				   } else { // c < b
					   c_ = c_ + 1; // c is least (a > b and b > c)
				   }
			   }
			   
			   tempvList[r] = c_;
			   tempEnrgSum += imgEnrg[r][c_];
//			   StdOut.printf("tempvList[%d] = %d, tempEnrgSum = %f, enrgSum = %f\n", r, tempvList[r], tempEnrgSum, enrgSum);
		   }
		   
		   tempvList[r] = c_ - 1;
		   tempEnrgSum += imgEnrg[r][c_];
		   
//		   StdOut.printf("\ntempEnrgSum=%f, enrgSum=%f, c=%d, tempvList=", tempEnrgSum, enrgSum, c);
//		   for(int i : tempvList) {
//			   StdOut.printf("%d,", i);
//		   }
		   if(tempEnrgSum < enrgSum) {
			   System.arraycopy(tempvList, 0, vList, 0, H);
			   enrgSum = tempEnrgSum;
		   }
	   }
	   
	   return vList;
   }

   // remove horizontal seam from current picture
   public void removeHorizontalSeam(int[] seam) {
	   
	   if (seam == null || seam.length != W)
		   throw new IllegalArgumentException("Invalid Input");

	   for(int r = 1; r < seam.length; r++) {
		   if(Math.abs(seam[r - 1 ] - seam[r]) > 1) {
			   throw new IllegalArgumentException("Invalid seam values!");
		   }
	   }
	   
	   H = myImage.height() - 1;
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
	   
	   if (seam == null || seam.length != H)
		   throw new IllegalArgumentException("Invalid Input");
	   
	   for(int c = 1; c < seam.length; c++) {
		   if(Math.abs(seam[c - 1 ] - seam[c]) > 1) {
			   throw new IllegalArgumentException("Invalid seam values!");
		   }
	   }
	   
	   W = myImage.width() - 1;
	   Picture modPic = new Picture(W, H); // remove one horizontal pixel line
	   
	   int rIn, cIn, rOut, cOut;
	   
	   for(rIn = 0, rOut = 0; rIn < modPic.height(); rIn++, rOut++) {
		   for(cIn = 0, cOut = 0; cIn < seam[rIn] && cIn < modPic.width(); cIn++, cOut++) {
			   modPic.setRGB(cOut, rOut, myImage.getRGB(cIn, rIn));
		   }
		   cIn++; //skip seam
		   for(;cIn < modPic.width();cIn++, cOut++) {
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

   //  unit testing (optional)
   public static void main(String[] args) {
	   
//	   Picture testImg = new Picture(args[0]);
//	   SeamCarver sc1 = new SeamCarver(testImg);
//	   Picture pe = sc1.pictureEnergies();
//	   pe.save(args[1]);
	   
	   
   }

}