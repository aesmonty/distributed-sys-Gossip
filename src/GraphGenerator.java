import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

/**
 * Class that generates a visual graph given the topology of the network.
 * @author Andres Monteoliva
 * @since 19-03-2018
 *
 */
public class GraphGenerator {

	private Graph graph;
	LinkedHashMap<Integer, List<Integer>> parsedGraph;
	private List<SingleNode> nodeList = new ArrayList<>();

	/**
	 * Constructor for the GraphGenerator class.
	 * @param parseGraph Map representing the topology of the graph
	 */
	public GraphGenerator(LinkedHashMap<Integer, List<Integer>> parseGraph) {

		this.parsedGraph = parseGraph;
		graph = new SingleGraph("graph");
		setAttributes();
		drawGraphNodes();
		drawGraphEdges();
	}

	/**
	 * Set custom attributes to the graph.
	 */
	public void setAttributes() {
		graph.addAttribute("ui.antialias");
		graph.addAttribute("stylesheet", "graph {padding : 50px;}" + "node {size: 20px; fill-mode: plain;}"
				+ "node.red {fill-color: red;}" + "node.green {fill-color: green;}");
	}

	/**
	 * Draw the parsed nodes on the graph.
	 */
	public void drawGraphNodes() {

		for (HashMap.Entry<Integer, List<Integer>> entry : parsedGraph.entrySet()) {

			SingleNode node = graph.addNode(String.valueOf(entry.getKey()));

			node.addAttribute("ui.class", "red");
			nodeList.add(node);

		}
	}

	/**
	 * Draw the parsed edges on the graph.
	 */
	public void drawGraphEdges() {

		for (HashMap.Entry<Integer, List<Integer>> entry : parsedGraph.entrySet()) {

			for (int neighbor : entry.getValue()) {

				String a = String.valueOf(entry.getKey());
				String b = String.valueOf(neighbor);

				if (!(graph.getEdge(b + a) != null)) {

					graph.addEdge(a + b, a, b);
				}
			}
		}
	}

	/**
	 * Turn the node of the graph to green color.
	 * 
	 * @param nodeID
	 */
	public void nodeGreen(int nodeID) {

		nodeList.get(nodeID).setAttribute("ui.class", "red");
	}

	/**
	 * Getter which retrieves the produced graph.
	 * 
	 * @return graph Graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * Getter of a list with the nodes in the graph
	 * 
	 * @return nodeList Nodes of list of the graph
	 */
	public List<SingleNode> getNodes() {

		return nodeList;

	}
}
