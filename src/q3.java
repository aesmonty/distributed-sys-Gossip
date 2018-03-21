import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.WindowConstants;

/**
 * Main class for question 3 of Part A. Runs a simulator and performs the PUSH
 * protocol for an array of different drop probabilities on the network and
 * record the elapsed time. Outputs a scattered plot with the results.
 * 
 * @author Andres Monteoliva
 * @since 19-03-2018
 *
 */

public class q3 {

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

		Scanner reader = new Scanner(System.in);

		System.out.println("Set minimum delay of the network (in ms): ");
		int minDelay = reader.nextInt(); // Set minimum delay

		System.out.println("Set maximum delay of the network (in ms): ");
		int maxDelay = reader.nextInt(); // Set maximum delay
		
		// close scanner
		reader.close();
		
		//Checks on the user input
		if(minDelay >= maxDelay) {
			System.err.println("The minimum delay should be smaller than the maximum delay");
			System.exit(0);
		}
		
		if((minDelay <= 0) || (maxDelay <= 0)) {
			System.err.println("The delay of the network should be a positive number");
			System.exit(0);
		}
		
		
		
		// Sanitize user input
		if (args.length != 2) {
			System.err.println("Error: Please submit 2 command line arguments");
			System.exit(0);
		}

		// Parse graph and deploy nodes and network on the cluster class.

		List<Double> dropProbs = new ArrayList<>();
		List<Double> elapTimes = new ArrayList<>();

		Parser parser = new Parser(args[0]);

		// Check validity of origin node.
		if ((Integer.parseInt(args[1]) < 0) || (Integer.parseInt(args[1]) > parser.parseGraph().size())) {
			System.err.println("Error: Please submit a valid origin node");
			System.exit(0);
		}

		// Run the protocol for different drop probabilities.
		for (int i = 5; i < 100; i = i + 5) {

			
			double dropProb = i / 100.0;
			System.out.println("Drop probability of the network: " + dropProb);
			Network network = new Network(minDelay,maxDelay,dropProb);
			Cluster cluster = new Cluster(parser.parseGraph(), network);
			cluster.deployNetwork();
			cluster.deployNodes();

			cluster.startPushC(Integer.parseInt(args[1]));
			DecimalFormat df = new DecimalFormat("#.##");
			System.out.println(
					"Drop probability: " + dropProb + " Elapsed Time of the protocol (in ms): " + df.format(cluster.getElapsedTime()));
			dropProbs.add(dropProb);
			elapTimes.add(cluster.getElapsedTime());
		}

		// Prints plot
		Plot example = new Plot("Scatter Plot", dropProbs, elapTimes);
		example.setSize(900, 500);
		example.setLocationRelativeTo(null);
		example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		example.setVisible(true);

	}

}