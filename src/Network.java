import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the Network in the simulation. It runs in an independent thread. Acts as a message passer between nodes,
 * adding some delay to each message to emulate a real distributed system.
 * 
 * @author Andres Monteoliva
 * @since 19-03-2018
 */

public class Network implements Runnable {

	private int minDelay;
	private int maxDelay;
	private List<MyNode> nodeList;
	private Double dropProb;
	Timer timer = new Timer();

	/**
	 * Constructor for the network class where the user is asked to set the interval of delay of the network.
	 */
	public Network(int minDelay, int maxDelay) {

		this.minDelay = minDelay;
		this.maxDelay = maxDelay;
		dropProb = 0.0;
	}
	
	/**
	 * Constructor for the network class. Minimum and maximum delay of the network are set by default (900-1100 ms).
	 * @param dropProb The probability of dropping a message by the network.
	 */
	public Network(int minDelay, int maxDelay, Double dropProb) {
		this.minDelay = minDelay;
		this.maxDelay = maxDelay;
		this.dropProb = dropProb;
	}

	/**
	 * The thread is started.
	 */
	public void run() {
		
		//System.out.println("The network is up and running");

	}
	
	/**
	 * Getter for the minimum delay of the network
	 * @return  minDelay The minimum delay of the network.
	 */
	public int getMinDelay() {
		return minDelay;
	}
	
	/**
	 * Getter for the maximum delay of the network
	 * @return maxDelay The maximum delay of the network.
	 */
	public int getMaxDelay() {
		return maxDelay;
	}

	/**
	 * Send a message to a node, after having received the "order" from another. A random delay (inside the delay interval) is added.
	 * There is random element, in which the network can drop the message. This element is tied with the drop probability parameter. 
	 * @param targetID The node which is going to receive the message. 
	 * @param message Message sent.
	 */
	public void sendMessage(int targetID, Message message) {
		TimerTask task = new TimerTask() {
			public void run() {
					
				// Compute random between number
				Random r1 = new Random();
				int randomNo = r1.nextInt(100);
				double randomDrop = randomNo/(100.0);
				
				//Check if the message is going to be dropped or successfully sent.
				if(randomDrop >= dropProb) {
					nodeList.get(targetID).receiveMessage(message);
				}
			}
		};
		
		//Implement a random delay between the delay interval of the network.
		Random r2 = new Random();
		int delay = r2.nextInt(maxDelay - minDelay) + minDelay;
		timer.schedule(task, delay);
	}

	/**
	 * Set the list of nodes deployed in the network.
	 * @param nodeList
	 */
	public void setNetwork(List<MyNode> nodeList) {

		this.nodeList = nodeList;
	}

//	/**
//	 * Ask the user via StdIn to set a custom interval delay of the network.
//	 */
//	public void setDelayInterval() {
//
//		Scanner reader = new Scanner(System.in);
//
//		System.out.println("Set minimum delay of the network (in ms): ");
//		minDelay = reader.nextInt(); // Set minimum delay
//
//		System.out.println("Set maximum delay of the network (in ms): ");
//		maxDelay = reader.nextInt(); // Set maximum delay
//		
//		// close scanner
//		reader.close();
//		
//		//Checks on the user input
//		if(minDelay >= maxDelay) {
//			System.err.println("The minimum delay should be smaller than the maximum delay");
//			System.exit(0);
//		}
//		
//		if((minDelay <= 0) || (maxDelay <= 0)) {
//			System.err.println("The delay of the network should be a positive number");
//			System.exit(0);
//		}
//	}
	
	/**
	 * Stop the timer.
	 */
	public void stop() {
		timer.cancel();
	}
}
