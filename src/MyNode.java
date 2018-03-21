import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class represents a node in the cluster. Each node is an independent
 * thread in the simulation.
 * 
 * @author Andres Monteoliva
 * @since 19-03-2017
 *
 */

public class MyNode  implements Runnable {

	private int nodeID;
	private List<Integer> neighbors = new ArrayList<>();
	private boolean state;
	private boolean messageReceived = false;
	private Network network;
	private Random randomGenerator = new Random();
	private Timer timer;
	private long receivedTime;

	/**
	 * Constructor for the MyNode class.
	 * @param id ID of the node.
	 * @param neighbors Neighbors of the node
	 * @param network Network connected to the node.
	 */
	public MyNode(int id, List<Integer> neighbors, Network network) {
		
		
		nodeID = id;
		state = false;
		this.neighbors = neighbors;
		this.network = network;
		this.timer = new Timer();

	}

	/**
	 * Starts the MyNode thread.
	 */
	public void run() {
		
		//System.out.println("Running " + nodeID + " and my neighbors are " + neighbors);

	}

	/**
	 * Choose one of the neighbors of the node at random.
	 * @return int randomNeigbor chosen 
	 */
	public int chooseNeighbor() {

		int randomIndex = randomGenerator.nextInt(neighbors.size());
		int randomNeighbor = neighbors.get(randomIndex);

		return randomNeighbor;

	}

	/**
	 * Receive message from the network. When a message is received, the node starts subsequently to send
	 * messages to a random neighbor at a random rate within the delay interval of the network.
	 * 
	 * @param message Message to be received.
	 */
	public void receiveMessage(Message message) {

		if (!messageReceived) {

			messageReceived = message.getMessage();
			state = messageReceived;
			receivedTime = System.nanoTime();
			System.out.println("NodeID: " + nodeID + " has received the message");
			
			

			TimerTask repeatedTask = new TimerTask() {
				public void run() {
					//Start sending messages to neighbors
					network.sendMessage(chooseNeighbor(), message);

				}
			};
			//Send messages at random rate. The rate is within the network delay interval.
			Random r = new Random();
         	long period = r.nextInt(network.getMaxDelay() - network.getMinDelay()) + network.getMinDelay();
			timer.scheduleAtFixedRate(repeatedTask, 0, period);
			

		}
	}

	/**
	 * Getter of the state of the node
	 * @return Boolean State of the node (aware or not aware of the message).
	 */
	public boolean getState() {

		return state;
	}

	/**
	 * Getter of the time the node received the message.
	 * @return long The received time.
	 */
	public long getReceivedTime() {
		return receivedTime;
	}

	/**
	 * Getter of the ID of the node.
	 * @return int The id of the node.
	 */
	public int getId() {

		return nodeID;
	}

	/**
	 * Stop the timertask. Node stop sending messages.
	 */
	public void stop() {
		timer.cancel();
	}
}
