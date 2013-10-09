/**
 * Two-dimensional tree implementation.  Supports insert, delete, exists, pre-order traversal and constant time getters for basic tree data.
 * Implements StdDraw class to draw points on a canvas for a visual aid.
 * TODO: Balancing, closest points 
 * @author Luis Palacios
 * 
 */
import java.util.*;


public class TDTree {

  //TDTree data members
  TDTree leftChild;
  TDTree rightChild;
  Point tpoint;
  TDTree parent;
  boolean direction;   //true = x coordinate, false = y
  static int SIZE = 1;
  static int HEIGHT = 0;
  static double maxX = -1.0, maxY = -1.0;
  static double minX = 2.0, minY = 2.0;

  /**
  *   Given trivial implementation just calls insert
  *      for each point, but does not control balance
  *      of tree.
  */
  public TDTree(Point [] pts){
    this();
    for(Point p : pts)
	insert(p);
  }

  /**
  *   default constructor creates an empty tree.
  */
  public TDTree() {
	  leftChild = null;
	  rightChild = null;
	  parent = null;
	  tpoint = null;
	  direction = true;
  }
/**
 * Additional constructor used for my inserts and deletes
 * @param pt = new point that needs to be inserted
 * @param dir = the axis on which the new node will cut the plane 
 * @param papa = parent directory for the new node
 */

  public TDTree(Point pt, boolean dir, TDTree papa){
	  leftChild = null;
	  rightChild = null;
	  parent = papa;
	  tpoint = pt;
	  direction = dir;
  }
  /**
  * Size is tracked trough a static parameter within the class
  * runtime:  O(1)
  */
  public int size() {
    return SIZE;
  }

  /**
  * Some recursive height methods along with some helper methods that will assist in size balancing my tree
  * runtime:  O(1) 
  */
  public int numNodes(TDTree t){
	  if (t == null) return 0;
	  return (1 + numNodes(t.leftChild) + numNodes(t.rightChild));
  }
  
  public int height(TDTree t){
	  if (t == null) return 0;
	  int hLeft = height(t.leftChild);
	  int hRight = height(t.rightChild);

	  if( hLeft > hRight ) return hLeft + 1;
	  else
	  return hRight +1;
  }
  public int height(){
	  return HEIGHT;
  }
  
  /**
  * Static parameter for max and min coordinates will allow for constant time look ups.
  */
  public Double minX(){
    return minX;
  }
  /**
  * Static parameter for max and min coordinates will allow for constant time look ups.
  */
  public Double minY(){
    return minY;
  }
  /**
  * Static parameter for max and min coordinates will allow for constant time look ups.
  */
  public Double maxX(){
    return maxX;
  }
  /**
  * Static parameter for max and min coordinates will allow for constant time look ups.
  */
  public Double maxY(){
    return maxY;
  }

  /**
  * Insert a point(pt) into the tree and returns true if successful
  * returns false if point pt already in tree
  * @param pt = point to be inserted.
  */
  public boolean insert(Point pt) {
	  //if(this == null) 
	  if(this.tpoint == null){
		  this.tpoint = pt;
		  
	  }
	  else{
	  if(this.direction){
		  
		  if(pt.x() > this.tpoint.x()){
			  if(this.rightChild != null)
				  return this.rightChild.insert(pt);
			  else{
				  maxX = pt.x();
				  if(minY > pt.y()) minY = pt.y();
				  if(maxY < pt.y()) maxY = pt.y();
				  TDTree runner;
				  this.rightChild = runner = new TDTree(pt, !direction, this);
				  SIZE++;
				  this.rightChild.parent = this;
				  int count = 0;
				  while(runner.parent != null){
					  System.out.println(runner.tpoint.x());
					  if(runner.parent != null)runner = runner.parent;
					  count++;
				  }
				  if(count > HEIGHT) HEIGHT = count;
				  return true;
			  }
		
		  }
		  else if(pt.x() < this.tpoint.x()){
			  if(this.leftChild != null)
				  return this.leftChild.insert(pt);
			  else{
				  minX = pt.x();
				  if(minY > pt.y()) minY = pt.y();
				  if(maxY < pt.y()) maxY = pt.y();
				  this.leftChild = new TDTree(pt, !direction, this);
				  SIZE++;
				  this.leftChild.parent = this;
				  TDTree runner = this.leftChild;
				  int count = 0;
				  while(runner.parent != null){
					  if(runner.parent != null)runner = runner.parent;
					  count++;
				  }
				  if(count > HEIGHT) HEIGHT = count;
				  return true;
			  } 
		  }
		  else{
			  if (pt.equals(this.tpoint)) return false;
			  else if(this.rightChild.tpoint.y()>pt.y()) return this.leftChild.insert(pt);
			  else return this.rightChild.insert(pt);
		  }
	  }
	  else if(!this.direction){
		  if(pt.y() > this.tpoint.y()){
			  if(this.rightChild != null)
				  return this.rightChild.insert(pt);
			  else{
				  maxY = pt.y();
				  if(minX > pt.x()) minX = pt.x();
				  if(maxX < pt.x()) maxX = pt.x();
				  this.rightChild = new TDTree(pt, !direction, this);
				  SIZE++;
				  this.rightChild.parent = this;
				  TDTree runner = this.rightChild;
				  int count = 0;
				  while(runner.parent != null){
					  runner = runner.parent;
					  count++;
				  }
				  if(count > HEIGHT) HEIGHT = count;
				  return true;
			  }
		
		  }
		  else if(pt.y() < this.tpoint.y()){
			  if(this.leftChild != null)
				  return this.leftChild.insert(pt);
			  else{
				  minY = pt.y();
				  if(minX > pt.x()) minX = pt.x();
				  if(maxX < pt.x()) maxX = pt.x();
				  this.leftChild = new TDTree(pt, !direction, this);
				  SIZE++;
				  this.leftChild.parent = this;
				  TDTree runner = this.leftChild;
				  int count = 0;
				  while(runner.parent != null){
					  if(runner.parent != null)runner = runner.parent;
					  count++;
				  }
				  if(count > HEIGHT) HEIGHT = count;
				  return true;
			  }
		  }
		  else{
			  if(pt.equals(this.tpoint)) return false;
			  if(this.rightChild != null){
				  if(this.rightChild.tpoint.x() > pt.x()) return this.leftChild.insert(pt);
				  else return this.rightChild.insert(pt);
			  }
		  }
	  }
	  
	  }
	  return false;
	  
  }
  public boolean insert(double x, double y) {
    return insert(new Point(x,y));
  }

  /**
  * Returns true if Point p is already in the tree by checking the coordinates within Point and follows 
  * the same tie-breaking mechanic that insert does.
  * @param pt = point to be checked if it exists in the tree already.
  */
  public boolean contains(Point p){
	  if(this.direction){
		  if(p.x() < this.tpoint.x()){
			  return this.leftChild.contains(p);
		  }
		  else if(p.x() > this.tpoint.x()){
			  return this.rightChild.contains(p);
		  }else{
			  if(p.equals(this.tpoint)) return true;
			  if(this.rightChild.tpoint.y()>p.y()) return(this.leftChild.tpoint.equals(p));
			  else return this.rightChild.tpoint.equals(p);
		  }
	  }
	  else{
		  if(p.y() < this.tpoint.y()){
			  return this.leftChild.contains(p);
		  }
		  else if(p.y() > this.tpoint.y()){
			  return this.rightChild.contains(p);
		  }else{
			  if(this.tpoint.equals(p)) return true;
			  if(this.rightChild.tpoint.x()>p.x()) return(this.leftChild.tpoint.equals(p));
			  else return this.rightChild.tpoint.equals(p);
		  }
	  }
	  
  }
  public boolean contains(double x, double y) {
    return contains(new Point(x,y));
  }

  /**
   * Some helpful comparator methods to will assists in inserts and deletes.
   * @return true if a is less than b
   */
  public boolean compareX(Point a, Point b){
	  if(a.x() > b.x()) return true;
	  else return false;
  }
  public boolean compareY(Point a, Point b){
	  if(a.y()>b.y()) return true;
	  else return false;
  }


  /**
   * Equals method that will check if the point within a node is the same as TDTree x's.
   * @param x is the node being compared with the calling object
   * @return true if successful
   */
  public boolean equals(TDTree x){
	  if(this == null || x == null) return false; 
	  if(x.tpoint.x() == this.tpoint.x() && x.tpoint.y() == this.tpoint.y())
		  return true;
	  else return false;
  }
  
  
  /**
   * Delete method that will take into account how many children the node to be deleted has.
  * @return true if successful
  */
  
  public boolean delete(Point p) {
	if(this == null) return false;
	else{
    if(this.direction){
		  if(p.x() < this.tpoint.x()){
			  return this.leftChild.delete(p);
		  }
		  else if(p.x() > this.tpoint.x()){
			  return this.rightChild.delete(p);
		  }else{
			  if(this.tpoint.equals(p)){
				  if((this.rightChild == null)&&(this.leftChild == null)){
					  if(this.parent.leftChild != null && this.parent.leftChild.equals(this)) this.parent.leftChild = null;  //Zero Children
					  else this.parent.rightChild = null;
				  }
				  else if((this.rightChild == null)&&(this.leftChild != null)){  //One Child
					  if(this.parent.leftChild != null && this.parent.leftChild.equals(this)){
						  this.parent.leftChild = this.leftChild;
						  this.leftChild.parent = this.parent;
					  }
					  else{
						  this.parent.rightChild = this.leftChild;
						  this.leftChild.parent = this.parent;
					  }
				  }
				  else if((this.rightChild != null)&&(this.leftChild == null)){  
					  if(this.parent.leftChild == this){
						  this.parent.leftChild = this.rightChild;
						  this.rightChild.parent = this.parent;
					  }
					  else{
						  this.parent.rightChild = this.rightChild;
						  this.rightChild.parent = this.parent;
					  }
				  }
				  else{										
					  TDTree runner = new TDTree();//Two Children
					  runner = this;
					  TDTree min = new TDTree();
					  min = this;
					  while((runner.rightChild != null) && (runner.leftChild != null)){
						  if(this.tpoint.x() < min.tpoint.x()) min = this;
						  if(runner.leftChild.tpoint.x() < runner.rightChild.tpoint.x()) runner = this.leftChild;
						  else runner = this.rightChild;
					  }
					  if(min.parent.rightChild == min) min.parent.rightChild = null;
					  else min.parent.rightChild = null;
					  if(this.parent.rightChild == this) this.parent.rightChild = min;
					  else this.parent.leftChild = min;
					  min.rightChild = this.rightChild;
					  min.leftChild = this.leftChild;
					  return true;
				  }
				  }
			  else{ 
				  if(this.rightChild.tpoint.y() < this.leftChild.tpoint.y()) return this.leftChild.delete(p);
				  else return this.rightChild.delete(p);
			  }
		  }
    }
    else{
		  if(p.y() < this.tpoint.y()){
			  return this.leftChild.delete(p);
		  }
		  else if(p.y() > this.tpoint.y()){
			  return this. rightChild.delete(p);
		  }else{
			  if(this.tpoint.equals(p)){
				  if((this.rightChild == null)&&(this.leftChild == null)){
					  if(this.parent.leftChild.equals(this)) this.parent.leftChild = null;  //Zero Children
					  else this.parent.rightChild = null;
					  
				  }
				  else if((this.rightChild == null)&&(this.leftChild != null)){  //One Child
					  if(this.parent.leftChild != null && this.parent.leftChild == this){
						  this.parent.leftChild = this.leftChild;
						  this.leftChild.parent = this.parent;
					  }
					  else{
						  this.parent.rightChild = this.leftChild;
						  this.leftChild.parent = this.parent;
					  }
				  }
				  else if((this.rightChild != null)&&(this.leftChild == null)){  
					  if(this.parent.leftChild !=null && this.parent.leftChild == this){
						  this.parent.leftChild = this.rightChild;
						  this.rightChild.parent = this.parent;
					  }
					  else{
						  this.parent.rightChild = this.rightChild;
						  this.rightChild.parent = this.parent;
					  }
				  }
				  else{							 			
					  TDTree runner = new TDTree();//Two Children
					  runner = this;
					  TDTree min = new TDTree();
					  min = this;
					  while((runner.rightChild != null) && (runner.leftChild != null)){
						  if(this.tpoint.y() < min.tpoint.y()) min = this;
						  if(runner.leftChild.tpoint.y() < runner.rightChild.tpoint.y()) runner = this.leftChild;
						  else runner = this.rightChild;
					  }
					  if(min.parent.rightChild.equals(min)) min.parent.rightChild = null;
					  else min.parent.rightChild = null;
					  if(this.parent.rightChild == this) this.parent.rightChild = min;
					  else this.parent.leftChild = min;
					  min.rightChild = this.rightChild;
					  min.leftChild = this.leftChild;
					  return true;
				  }
			  }
			  else{ 
				  if(this.rightChild.tpoint.x() < this.leftChild.tpoint.x()) return this.leftChild.delete(p);
				  else return this.rightChild.delete(p);
				  }
		  }
    }
	
	}
	return false;
  }
  /**
  * for convenience
  */
  public boolean delete(double x, double y) {
    return delete(new Point(x,y));
  }

  /**
   * PreOder method will take the current node and its tree and push them onto a stack.  The method will automatically pop all elements until isEmpty(),
  * when it will print to console the tree in pre-order-root-left - right.
  */
  
  public void preOrder() {
	     preOrder(this);
	 }
  public void preOrder(TDTree root) {
      Stack<TDTree> nodes = new Stack<TDTree>();
      nodes.push(root);

      TDTree currentNode = new TDTree();
      while (!nodes.isEmpty()) {
              currentNode = nodes.pop();
            	  
              TDTree right = currentNode.rightChild;
              if (right != null) {
                      nodes.push(right);
              }
              TDTree left = currentNode.leftChild;
              if (left != null) {
                      nodes.push(left);      
              }
             
              if(currentNode.tpoint != null){
            	  for(int i = HEIGHT; i >= 0; i--){		//GO LEVEL BY LEVEL FROM HEIGHT DOWN TO 0 AND PRINT EACH LEVEL WITH GREATER.X THAN ROOT FIRST THEN 
            		  								// ROOT ITSELF THEN L ESS THAN.X ROOT LAST
            		  
            	  }
              draw(currentNode);
              }
      }
} 
  /**
   * Using the the StdDraw class the draw() method will draw each point into the canvas and cut along the axis upon which the point splits its children
   * by.
   */

  public void draw(TDTree node){
	  StdDraw.circle(node.tpoint.x(),node.tpoint.y(),0.005);
      if(node.direction == true){
    	  StdDraw.setPenColor(StdDraw.BLACK);
    	  StdDraw.line(0, node.tpoint.y(), 1, node.tpoint.y());
      }
      else{
    	 StdDraw.line(node.tpoint.x(), 0, node.tpoint.x(), 1);
      }
  }
  
  /**
  * TODO: Not Implemented
  *  returns point in tree closest to point p (by Euclidean distance).
  *  if tree empty, null is returned.
  */
  public Point nearest(Point p) {
     return null;
  }

  /**
  * TODO: Not Implemented
  * for convenience
  */
  public Point nearest(double x, double y) {
     return nearest(new Point(x,y));
  }

  /**
  * TODO: Not Implemented
  *
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   populates Collection<Point> result with points in the
  *     rectangle.
  */
  public void rangeQuery(Point sw, Point ne,
                            Collection<Point> result) {
    	System.out.println("unimplemented method:  rangeQuery()");
  }

  /**
  * TODO: Not Implemented
  *  for convenience.  Just calls above method
  */
  public void rangeQuery(double minX, double maxX, 
                            double minY, double maxY, 
                            Collection<Point> result) {

	Point sw = new Point(minX, minY);
	Point ne = new Point(maxX, maxY);
	

	rangeQuery(sw, ne, result);
  }


  /**
  * TODO Not Implemented
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   returns number of points within the rectangle (but NOT
  *     the points themselves).
  *   
  */
  public int rangeSize(Point se, Point ne) {
    	System.out.println("unimplemented method:  rangeSize()");
	return 0;

  }
  public int rangeSize(double minX, double maxX, 
                            double minY, double maxY){

	Point sw = new Point(minX, minY);
	Point ne = new Point(maxX, maxY);

	return rangeSize(sw, ne);
  }

}
