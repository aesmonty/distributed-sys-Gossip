/**
 * Main class for question 2 of Part A. Runs a simulator and creates a live
 * graph which shows in real time how the protocol is evolving and which nodes
 * are becoming aware of the message.
 * 
 * @author Andres Monteoliva
 * @since 19-03-2018
 *
 */

public class q2 {

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

		// Parse graph.
		Parser parser = new Parser(args[0]);

		// Check validity of the origin node.
		if ((Integer.parseInt(args[1]) < 0) || (Integer.parseInt(args[1]) > parser.parseGraph().size())) {
			System.err.println("Error: Please submit a valid origin node");
			System.exit(0);
		}

		// Deploy cluster (network and nodes)
		// Network network = new Network(); //This is the OPTIONAL part where the user
		// can choose the delay of the network.
		Network network = new Network(0.0);

		Cluster cluster = new Cluster(parser.parseGraph(), network);

		// Deploy nodes and network on the cluster and star algorithm
		cluster.deployNetwork();
		cluster.deployNodes();

		cluster.startPushB(Integer.parseInt(args[1]));

	}

}
