
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.graphstream.graph.Graph;

/**
 * Cluster which encompasses the network and the nodes deployed in it. 
 * 
 * @author Andres Monteoliva
 * @since 19-03-2018
 *
 */
public class Cluster {

	private LinkedHashMap<Integer, List<Integer>> nodeGraph;
	private Network network;
	private List<MyNode> nodeList = new ArrayList<>();
	private long startTime;
	private long timeElapsed;

	/**
	 * Constructor for the cluster class.
	 * @param nodeGraph The map representing the topology of the graph of nodes.
	 * @param network The network thread in this cluster.
	 */
	public Cluster(LinkedHashMap<Integer, List<Integer>> nodeGraph, Network network) {

		this.nodeGraph = nodeGraph;
		this.network = network;
	}

	/**
	 * Deploy the nodes on the cluster
	 */
	public void deployNodes() {
		
		//Iterate through the map and add nodes to the list of nodes. It also starts running each node.
		for (HashMap.Entry<Integer, List<Integer>> entry : nodeGraph.entrySet()) {
			MyNode node = new MyNode(entry.getKey(), entry.getValue(), network);

			nodeList.add(entry.getKey(), node);
			Thread t = new Thread(node);
			t.start();
		}
		
		network.setNetwork(nodeList);
	}

	/**
	 * Deploy the network of the cluster.
	 */
	public void deployNetwork() {

		Thread t = new Thread(network);
		t.start();
	}

	/**
	 * Return the state of the nodeID. 1 meaning he knows the secret already and 0
	 * he does not know it.
	 * 
	 * @param nodeID id of the node
	 * @return state  state of the node
	 */
	public boolean checkState(MyNode node) {

		return nodeList.get(node.getId()).getState();
	}

	/**
	 * Start the push protocol for the q1 conditions. It will create and save a txt file logging the times in which each of the nodes became
	 * aware of the message. The nodes will be printed in ascending order (e.g. the last one aware will be printed last).
	 * 
	 * @param originNode Node in which the protocol will be started.
	 */
	public void startPushA(int originNode) {

		Message message = new Message();
		startTime = System.nanoTime();
		nodeList.get(originNode).receiveMessage(message);

		while (!allNodesAware()) {

			try {

				Thread.sleep(500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		killAllNodes();
		killNetwork();
		
		
		//Create a txt file and write the time log results to it.
		try {
			printTimesFile();
		} catch (UnsupportedEncodingException e) {
			System.err.println("Error producing file. Invalid encoding");
			
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error producing file. IO exception");

			e.printStackTrace();
		}

	}

	/**
	 * Start push protocol for the q2 conditions. Create a graph which shows in real time which nodes are becoming aware of the message.
	 * 
	 * 
	 * @param originNode Node in which the protocol is started.
	 */

	public void startPushB(int originNode) {

		Message message = new Message();
		GraphGenerator graphGen = new GraphGenerator(nodeGraph); 

		Graph graph = graphGen.getGraph();
		startTime = System.nanoTime();
		nodeList.get(originNode).receiveMessage(message);
		//Set graph attribute
		graphGen.getNodes().get(originNode).setAttribute("ui.class", "green");

		graph.display();

		while (!allNodesAware()) {

			// Check which nodes know the secret to turn them in green accordingly
			for (MyNode node : nodeList) {

				if (checkState(node)) {

					graphGen.getNodes().get(node.getId()).setAttribute("ui.class", "green");
				}
			}
			try {

				Thread.sleep(500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		// Turn green last node
		for (MyNode node : nodeList) {

			graphGen.getNodes().get(node.getId()).setAttribute("ui.class", "green");

		}

		killAllNodes();
		killNetwork();
		

	}
	
	/**
	 * Start push protocol for q3, recording the complete time the protocol has elapsed.
	 * @param originNode
	 */
	public void startPushC(int originNode) {

		Message message = new Message();
		startTime = System.nanoTime();
		nodeList.get(originNode).receiveMessage(message);


		while (!allNodesAware()) {

			try {

				Thread.sleep(500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		//Record elapsed time and kill nodes
		timeElapsed = System.nanoTime() - startTime;
		
		
		killAllNodes();
		killNetwork();
		

	}
	
	/**
	 * Getter method for the time elapsed in milliseconds running the algorithm.
	 * @return
	 */
	public Double getElapsedTime() {
		return (double) timeElapsed/1000000.0;
	}

	/**
	 * Print in stdout the times when each node received the message.
	 */
	private void printTimes() {

		sortTimesList();

		for (MyNode node : nodeList) {
			long elapsedTime = node.getReceivedTime() - startTime;
			double mseconds = (double) elapsedTime / 1000000.0;
			DecimalFormat df = new DecimalFormat("#.##");
			System.out.println("Node ID: " + node.getId() + "  Reception Time (ms): " + df.format(mseconds));
		}
	}
	
	/**
	 * Prints in a text file when each node received the message.
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private void printTimesFile() throws IOException, UnsupportedEncodingException {
		
		sortTimesList();
		PrintWriter writer = new PrintWriter("./log/q1.txt", "UTF-8");
		
		
		for (MyNode node : nodeList) {
			long elapsedTime = node.getReceivedTime() - startTime;
			double mseconds = (double) elapsedTime / 1000000.0;
			DecimalFormat df = new DecimalFormat("#.##");
			writer.println("Node ID: " + node.getId() + "  Reception Time (ms): " + df.format(mseconds));
		}
		
		writer.close();
	}

	/**
	 * Sort the list of nodes by the time they received the message
	 */
	private void sortTimesList() {

		nodeList.sort(new Comparator<MyNode>() {

			@Override
			public int compare(MyNode node1, MyNode node2) {

				return Long.compare(node1.getReceivedTime(), node2.getReceivedTime());
			}
		});
	}

	/**
	 * Kill the nodes and stop push protocol
	 */
	private void killAllNodes() {

		for (MyNode node : nodeList) {

			node.stop();

		}

	}
	
	/**
	 * Kill the network thread
	 */
	private void killNetwork() {
		network.stop();
	}

	/**
	 * Check if all the nodes know already the message
	 * 
	 * @return Boolean True if all nodes are aware of the message. False else.
	 */
	public boolean allNodesAware() {

		int nodesAware = 0;
		for (MyNode node : nodeList) {
			if (checkState(node)) {
				nodesAware++;
			}
		}
		
		if (nodesAware < nodeList.size()) { 
			return false;
		} else {
			return true;
		}

	}

}
