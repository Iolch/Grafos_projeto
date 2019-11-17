import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;


public class FileReaderController {
	private final static String COMMA_DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$*)";
	
	public FileReaderController() {
		// TODO Auto-generated constructor stub
	}
	
	public static Graph readFile(String filepath) throws FileNotFoundException, IOException 
	{
		Graph graph = new Graph();
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	
		    	if(line.charAt(0) == 'p') {
		    		String[] values = line.split("\t");
		    		for(int i = 1; i <= Integer.parseInt(values[1]); i++) graph.addNode(i);		//values[1] é a quantidade de nodes
		    	}
		    	if(line.charAt(0) == 'e') {
		    		String[] values = line.split(" ");
		    		int weight = (int)(Math.random() * ((300 - 1) + 1)) + 1;
		    		graph.addEdge(Integer.parseInt(values[1]), Integer.parseInt(values[2]), weight);
		    		//break;
		    	}
		    }
		    
		}
		return graph;
	}

}
