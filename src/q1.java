import java.util.Scanner;

/**
 * Main class for question 1 of Part A. Runs a simulator and prints the times
 * the nodes become aware of the message to a text file.
 * 
 * @author Andres Monteoliva
 * @since 19-03-2018
 *
 */

public class q1 {

	/**
	 * Main method of the class.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		
		
		Parser parser = new Parser(args[0]);

		// Check validity of origin node input by the user.

		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Error: Invalid input format. Origin node must be an integer.");
			System.exit(0);
		}

		if ((Integer.parseInt(args[1]) < 0) || (Integer.parseInt(args[1]) >= parser.parseGraph().size())) {
			System.err.println("Error: Please submit a valid origin node");
			System.exit(0);
		}
		
		//Ask the user to set the network delay
		Scanner reader = new Scanner(System.in);

		System.out.println("Set minimum delay of the network (in ms): ");
		while (!reader.hasNextInt()) {
			
			System.out.println("The minimum delay has to be an integer (in ms)");
			reader.next();
		}
		int minDelay = reader.nextInt(); // Set minimum delay

		System.out.println("Set maximum delay of the network (in ms): ");

		while (!reader.hasNextInt()) {
			
			System.out.println("The maximum delay has to be an integer (in ms)");
			reader.next();
		}
		int maxDelay = reader.nextInt(); // Set maximum delay

		// close scanner
		reader.close();

		// Checks on the user input
		if (minDelay >= maxDelay) {
			System.err.println("Error: The minimum delay should be smaller than the maximum delay");
			System.exit(0);
		}

		if ((minDelay <= 0) || (maxDelay <= 0)) {
			System.err.println("Error: The delay of the network should be a positive number");
			System.exit(0);
		}

		// Sanitize user input
		if (args.length != 2) {
			System.err.println("Error: Please submit 2 command line arguments");
			System.exit(0);
		}

		// Parse graph and deploy nodes and network on the cluster class.

		Network network = new Network(minDelay, maxDelay);
	

		// Deploy cluster
		Cluster cluster = new Cluster(parser.parseGraph(), network);

		cluster.deployNetwork();
		cluster.deployNodes();
                 
                System.out.println("Nodes Msg Received IDs");
		// Start protocol
		cluster.startPushA(Integer.parseInt(args[1]));

	}

}

