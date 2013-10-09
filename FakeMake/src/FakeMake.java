/**
 *  Fake makefile project.  The goal is to make a small program that acts like a makefile only in the sense that it tracks timestamps for given files
 *  and creates a directional acyclic graph of files and their dependencies.  It will parse a file with file names, and file dependencies using those 
 *  dependencies as the edge connecting two files or nodes.  It does check for cirular dependencies while parsing the file  As a file is update the 
 *  makefile will insure that any dependencies are also updated by traversing the graph.
 *  @author Luis Palacios
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/*
 * Class that pretends to have characteristics of a makefile.  Using HashMaps, Lists, Scanners within this and the DAG class.
 */
public class FakeMake {
	
	
	
	public static void main(String[] argsv) throws FileNotFoundException{
		
		HashMap<String,Vertex> vertex_map = new HashMap<String,Vertex>();
		int clock = 1;
		
		File input = new File(argsv[0]);
		Scanner parser = new Scanner(input);
		Queue<Vertex> file_names = new LinkedList<Vertex>();
		ArrayList<String> target_headers = new ArrayList<String>();
		String current_line = new String(); 
		
		/*
		 * Scanner while loop that will detect file format based on given parameters and do a little
		 * string manipulation to get data into correct data structures.
		 */
		while(parser.hasNextLine()){
			current_line = parser.nextLine();
			System.out.printf("%s\n",current_line);
			
			
			if(current_line.contains(":")){
					
				String target_and_files[] = current_line.split(":");
				target_and_files[0] = target_and_files[0].trim();
				target_and_files[1] = target_and_files[1].trim();
				
				String all_dependencies[] = target_and_files[1].split(" ");
				
				if(target_headers.contains(target_and_files[0])){
					System.out.printf("Repeat vertices in our Graph, Logging you out");
					System.exit(0);
				}
				
				Vertex[] v_all_dependencies = Vertex.string_to_vertex_array(all_dependencies, clock, vertex_map);
				Vertex v_target_and_files = new Vertex(target_and_files[0], v_all_dependencies, clock);
				clock++;;
					
				target_headers.add(target_and_files[0]);
				
				vertex_map.put(target_and_files[0], v_target_and_files);  
					
			}
			else{
				if(!file_names.contains(current_line)){
					vertex_map.put(current_line, new Vertex(current_line));
					file_names.add(new Vertex(current_line, clock));
					clock++;
				}
				else{
					System.out.printf("Repeat vertices in our Graph, logging you out");
					System.exit(0);
				}
			}
				
		}

		System.out.printf("%d \n", file_names.size());
		DAG makeGraph = new DAG(file_names, vertex_map);
		makeGraph.is_it_acyclical();
		//static int[] visited_nodes = new int[makeGraph.size()];
		
		/*
		 * Small interface for the user with very limited capabilities.
		 * The interface kills the program if it detects missing arguments. This is not very user friendly.
		 */
		boolean run = true;
		while(run){
			
			System.out.printf("Please select a task: \n " +
								"time \n " +
								"touch <file> \n " +
								"timestamp <file> \n " +
								"make <target> \n" +
								"quit \n");
			try {
				BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in));
				String selection = user_input.readLine();
				String argument = null;
				
				if(selection.contains(" ")){
					String[] broken_string = selection.split(" ");
					selection = broken_string[0];
					argument = broken_string[1];
				}
				if(selection.equals("time"))
					System.out.printf("%d \n", clock);
				
				else if(selection.equals("touch")){
					if(argument == null){
						System.out.printf("Error, need a filename \n");
						break;
					}
					Vertex pointer = makeGraph.get_vertex(argument);
					pointer.bring_up_to_date(clock);
					clock++;
					System.out.printf("File %s has been updated \n", argument);
					
				}
				else if(selection.equals("timestamp")){
					if(argument == null){
						System.out.printf("Error, need a filename \n");
						break;
					}
					Vertex pointer = makeGraph.get_vertex(argument);
					System.out.printf("File '%s' has time: %d\n", argument, pointer.time);
					
				}
				else if(selection.equals("make")){
					if(argument == null){
						System.out.printf("Error, need a target file \n");
						break;
					}
					makefile(argument, makeGraph);
					
				}
				else if(selection.equals("quit")){
					System.out.printf("Adios!\n");
					run = false;
				}
				
				
				
			} catch (IOException e) {
				System.out.printf("Cannot read input, dying");
				e.printStackTrace();
			}
			
		}
		
	}

	
	/*
	 * Recursive function that checks if a files dependent has a higher timestamp.
	 * returns: Nothing
	 */
	public static void makefile(String argument, DAG makeGraph) {
		
		if(makeGraph.get_vertex(argument) == null || makeGraph.get_vertex(argument).edges == null)
			return;
		Vertex[] dependents_pointer = makeGraph.get_vertex(argument).edges;
		int root_time = makeGraph.get_vertex(argument).time;
		for(Vertex e: dependents_pointer){
			makefile(e.vName, makeGraph);
			if(e.time > root_time){
				makeGraph.get_vertex(argument).bring_up_to_date(e.time);
				System.out.printf("Making file %s ...\n", argument);
			
			}
		}
	
	}
	
	public boolean isVisited(int[] n, int i){
		if(n[i] == 1)
			return false;
		else if(n[i] == 0)
			return true;
		
		return false;
		
		
	}
}
