This project simulates the evolution of the PUSH protocol in a cluster, where each Node is a separate Thread and the Network adds delay (and messages drops).

Folders of the project:
-src: Java source files. The compiled binaries are located here too, for simplicity.
-libs: Dependencies folder.
-log: The output txt file of q1 will be located here.
-graphs: Example input graphs

The design of the project consists of these classes: 

-MyNode: Represents each node or process.
-Network: Represents the Network in the Cluster. Adds delay to the message passing between nodes and may drop the messages.
-Message: The Message passed in the gossip protocol.
-Cluster: It encompasses the network and the nodes. Keeps track of which of the versions (q1,q2,q3) is run, elapsed times on the protocol,...
-Parser: Parses the adjacency list representing the graph (txt file).
-GraphGenerator: Graph generator for q2.
-Plot: Plot generator for q3.
-q1: Main class for q1.
-q2: Main class for q2.
-q3: Main class for q3.

Design rationale:
The project has tried to follow OOP best practices, representing each class a different entity. Examples like the Message class (very simple, just one field and getter) respond to this principle. The most important design choice was the creation of the Cluster class. It has been chosen as the best option to keep track of the evolution of the protocol (e.g. which nodes have received the message, which of the option is being run, the elapsed time of the protocol,...). Even though the creation of this class was not strictly neccessary, to solve problems such as the termination of the protocol when all nodes have received the message, it becomes an appropriate solution. The implemenation of the different classes has tried to follow best code quality standards, with a balance amount of comments, which enhances the code readability.

To address the possibility of very different input graphs, when the protocol is started, the user is asked about which is the desired interval range of the delay of the network (in milliseconds). The user must input the minimum and maximum delays through the STDIN.

Instruction of use:

1. To compile the Java project: ./run.sh compile
2. To run q<X>: ./run.sh q<X> <path-to-graph> <origin node>
(where X can be 1, 2 or 3)
Example: ./run.sh q3 graphs/barbell_graph 2
Note: The example will work provided that the graph directory and the given graph exist.


