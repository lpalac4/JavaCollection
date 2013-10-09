/**
 * Test program for a two dimensional tree implementation.  Currently support inserts, delete, exists among other rudimentary 
 * tree operations.
 * 
 * @author Luis Palacios
 *
 */
public class TDMain {
	
	public static void main(String[] args){
		Point[] pts = new Point[10];
		TDTree test = new TDTree();
		for(int i = 0 ; i < 10; i++){
			double a = Math.random();
			double b = Math.random();
			Point x = new Point(a, b);
			test.insert(x);
			pts[i] = x;
			
			
		}
		
		test.preOrder();
		
	}
}
