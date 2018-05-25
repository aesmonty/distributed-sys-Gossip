# Gossip protocol Simulator

This project consists on an asynchronous distributed systems simulator, focused on the study of gossip algorithms. In particular, the PUSH protocol is implemented for typical graph topologies.

## Getting Started

The design of the project consists of these classes:

* **MyNode:** Represents each node or process.
* **Network:** Represents the Network in the Cluster. Adds delay to the message passing between nodes and may drop the messages. 
* **Message:** The Message passed in the gossip protocol. 
* **Cluster:** It encompasses the network and the nodes. Keeps track of which of the versions (q1,q2,q3) is run, elapsed times on the protocol,... 
* **Parser:** Parses the adjacency list representing the graph (txt file). 
* **GraphGenerator:** Graph generator for q2. 
* **Plot:** Plot generator for q3. 
* **q1:** Main class for q1. 
* **q2:** Main class for q2. 
* **q3:** Main class for q3.

### Prerequisites

JVM v1.8

### Up and running

To compile the Java project:

```
./run.sh compile
```
To run the different versions of the simulator (q1, q2 or q3):
```
./run.sh q (where X can be 1, 2 or 3)
```
An example taking as input a Barbell graph and setting node 2 as the starting node:

```
./run.sh q3 graphs/barbell_graph 2
```

## Built With

* [GraphStream](http://graphstream-project.org/) - Graph visualization.
* [Maven](https://maven.apache.org/) - Dependency Management


