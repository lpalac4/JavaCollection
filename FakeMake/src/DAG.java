import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/*
 * The directed acyclic graph class or DAG will hold the data structure needed to try replicating the 
 * nature of a make file.
 * Uses a HashMap, and Queue to perform needed operations.
 */
public class DAG {  
		   
	HashMap<String, Vertex> vertex_map;
	Queue<Vertex> file_names;
	
	/*
	 * Constructor
	 */
	public DAG(Queue<Vertex> files, HashMap<String, Vertex> targets){
		vertex_map = targets;
		file_names = files;
	}
	
	/*
	 * Void method that checks for cycles using an ArrayList and Queue to perform evaluation.
	 */
	public void is_it_acyclical(){

		ArrayList<Vertex> L = new ArrayList<Vertex>();
		Queue<Vertex> S = file_names;
		ArrayList<Vertex> visited = new ArrayList<Vertex>();
		
		for(Vertex s: S){
			Vertex x = visit(s, visited);
			if(x != null)
				L.add(x);
		}
		
		System.out.printf("No Cycles detected...proceeding\n");
	}
	
	/*
	 * Helper method to check if the given graph is acyclical by tracking visited Vertices
	 */
	public Vertex visit(Vertex s, ArrayList<Vertex> v){
		if(!v.contains(s)){
			v.add(s);
			if(vertex_map.get(s) != null){
				Vertex[] forw_edges = vertex_map.get(s).edges;
					for(Vertex i: forw_edges){
						visit(i, v);
					}
			}
			return s;
		}
		else{
			System.out.printf("Error, cycle detected quitting program");
			System.exit(0);
		}
		return null;
	}
	
	/*
	 * Getter method
	 */
	public Vertex get_vertex(String argv) {
		if(vertex_map.get(argv) != null)
			return vertex_map.get(argv);
		
		return null;
	}
}