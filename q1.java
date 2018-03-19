
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
	 * @throws IllegalArgumentException
	 *             Throws an illegal args exception
	 * @throws InterruptedException
	 *             Throws an Interrupted exception.
	 */
	public static void main(String[] args) throws IllegalArgumentException, InterruptedException {

		// Sanitize user input
		if (args.length != 2) {
			System.err.println("Error: Please submit 2 command line arguments");
			System.exit(0);
		}

		// Parse graph and deploy nodes and network on the cluster class.

		// Network network = new Network(); //This is the OPTIONAL part where the user
		// can choose the delay of the network.

		Network network = new Network(0.0);

		Parser parser = new Parser(args[0]);

		// Check validity of origin node input by the user.
		if ((Integer.parseInt(args[1]) < 0) || (Integer.parseInt(args[1]) > parser.parseGraph().size())) {
			System.err.println("Error: Please submit a valid origin node");
			System.exit(0);
		}

		// Deploy cluster
		Cluster cluster = new Cluster(parser.parseGraph(), network);

		cluster.deployNetwork();
		cluster.deployNodes();

		// Start protocol
		cluster.startPushA(Integer.parseInt(args[1]));

		System.exit(0);

	}

}
