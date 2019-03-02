# DHT-Chord-Message-Delivery
Decentralized message system using custom Chord DHT algorithms and P2P protocols (Restlet).


# Requirements

Java 8 (1.8) since javax is removed from java versions >8.


# Install
In order to compile the files and create the executable .Jar-file an ant-script is provided. Furthermore an automation script is provided such that only the command ./build.sh is needed in order to compile all files into the jar-file (main.jar).
The system is executed by typing the following command: java -jar main.jar my_port known_node_port mode where my_port is the port that this host should use and known_node_port is a port of one of the already connected hosts on the network. Mode can either be 0 or 1 and 0 is used to construct the network itself i.e. the very first node in a network (in this case the known_node_port should be the same as my_port) and 1 is when a host wants to join a pre-existing network.



```
./build.sh

// Start the network with a start node on port 10500
java -jar main.jar 10500 10500 0

// Connect into the network above with a node that operates on port 10600.
java -jar main.jar 10600 10500 1

/*
Connect into the network above with a node that operates on port 10700. It doesn't matter which node that is known as long as it is connected to the network.
*/
java -jar main.jar 10700 10600 1

// The last node.
java -jar main.jar 10800 10500 1
```


# TODO
Migrate to Maven (Sovles Java 8 requirement)
