import java.util.HashMap;

/*
 * Vertex Class will act as nodes or vertices in the graph.  Encapsulates a String, Integer and Vertex[] to perform all necessary
 * functions. 
 */
public class Vertex {

	
	public String vName;
	public int time;
	public Vertex[] edges;
	
	/*
	 * Constructors for several different scenarios.
	 */
	public Vertex(String file_or_target){
		vName = file_or_target;
		time = 0;
		edges = null;
	}
	public Vertex(String file_or_target, int clock){
		vName = file_or_target;
		time = clock;
		edges = null;
	}
	
	public Vertex(String target, Vertex[] dependents, int clock){
		vName = target;
		time = clock;
		edges = dependents;
	}
	
	/*
	 * 'touch' helper method
	 */
	public void increment_time(){
		this.time++;
	}
	
	/*
	 * 'make' helper method
	 */
	public void bring_up_to_date(int clock){
		this.time = clock;
	}
	
	/*
	 * A helper method that turns a string array from our file scanner.
	 * returns Vertex array.
	 */
	public static Vertex[] string_to_vertex_array(String[] temp_strings, int clock, HashMap<String, Vertex> map){
		
		Vertex all_dependencies[] = new Vertex[temp_strings.length];
		int i = 0;
		for(String s: temp_strings){
			if(!map.containsKey(s))
				all_dependencies[i] = new Vertex(s,clock);
			else
				all_dependencies[i] = map.get(s);
			i++;
				
		}
		
		return all_dependencies;
	}
	
	/*
	 * Getter method
	 */
	public Vertex[] get_edges(){
		return edges;
	}
	

}
