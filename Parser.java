
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.IOException;

/**
 * This class parses the text input file to a graph representation.
 * 
 * @author Andres Monteoliva
 * @since 19-03-2018
 */

public class Parser {

	private String file;

	public Parser(String file) {

		this.file = file;

	}

	/**
	 * It parses the input file, line by line, given the format provided in the
	 * submission instructions.
	 * 
	 * @return graph A map representing the topology of the graph contained in the
	 *         adjacency list of the file.
	 */

	public LinkedHashMap<Integer, List<Integer>> parseGraph() {

		LinkedHashMap<Integer, List<Integer>> graph = new LinkedHashMap<Integer, List<Integer>>();
		int nodeID, noNeighbors;

		try {
			// create a Buffered Reader object instance with a FileReader, and read line by
			// line tile end
			BufferedReader br = new BufferedReader(new FileReader(this.file));
			String fileRead = br.readLine();

			while (fileRead != null) {

				String[] splits = fileRead.split(":"); // separate node and neighbors
				nodeID = Integer.parseInt(splits[0]);

				String[] splitNeighbors = splits[1].split(",");// separate different neighbors ids
				noNeighbors = splitNeighbors.length;

				List<Integer> neighbors = new ArrayList<>();

				for (int i = 0; i < noNeighbors; i++) {

					neighbors.add(Integer.parseInt(splitNeighbors[i]));

				}

				graph.put(nodeID, neighbors);

				// read next line before looping
				// if end of file reached
				fileRead = br.readLine();

			}

			// close file
			br.close();

		}
		// handle exceptions
		catch (FileNotFoundException notFound) {
			System.out.println("File not found");
			System.exit(0);
		}

		catch (IOException ioe) {
			System.out.println("IO erro");
			System.exit(0);
		}

		return graph;
	}

}
