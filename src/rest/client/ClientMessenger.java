package rest.client;
import dht.Key;
import dht.Node;
import org.apache.commons.codec.binary.Base64;
import rest.ClientServerMain;
import rest.message.Message;
import rest.message.MessageJson;
import rest.server.ServerData;

import java.util.*;

public class ClientMessenger {


	RestConnection connection;
	public int otherPort = 8322;
	public Node node;
	public boolean isStartNode;

	public HashMap<String, Node> nodes;
	public Node start;//, prev, next;

	public Boolean workingConnection;

	public ClientMessenger(int otherPort, Node n, boolean startNode) {

		this.isStartNode = startNode;
		this.otherPort = otherPort;
		workingConnection = false;

		String url = "http://localhost:" + otherPort;
		node = n;

		connection = new RestConnection("localhost", String.valueOf(otherPort));
		start = Node.fromJson(connection.node.getNode());
		System.out.println("Start Node Key: " + start.key);

		if (!start.key.equals(node.key))
			Join();
		else {
			workingConnection = true;
		}

		//TracePrintRouting();

		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (workingConnection)
					ClientServerMain.Instance.messenger.checkConnection();
			}
		}, 0, 20000);


	}



	public void Leave(Boolean terminate) {
		// A <-> B <-> C
		// ... B Leaves
		// A <-> C
		// A -> C, A <- C


		Node _B = node;

		try {

			RestConnection connA = new RestConnection(_B.predecessor_host, _B.predecessor_port);
			Node _A = Node.fromJson(connA.node.getNode());


			RestConnection connC = new RestConnection(_B.successor_host, _B.successor_port);
			Node _C = Node.fromJson(connC.node.getNode());

			setNodeSuccessor(_A, _C.key, _C.host, _C.port);
			connA.succs.setSuccessor(Node.toJson(_A));

			setNodePredecessor(_C, _A.key, _A.host, _A.port);
			connC.preds.setPredecessor(Node.toJson(_C));
		}catch (Exception e) {}



		System.out.println("Left network");

		if (terminate)
			System.exit(0);

	}


	public void Join() {

		node.Reset();

		Node nextNode = Node.fromJson(connection.node.getNode());
		RestConnection _conn = new RestConnection(nextNode.host, nextNode.port);

		ArrayList<String> keys = new ArrayList<>();
		ArrayList<Node> keynodes = new ArrayList<>();


		boolean hasJoined = false;
		while(!hasJoined) {

			boolean newKeyNotBetweenOtherKeys = false;
			if (keys.contains(nextNode.key)) {
				newKeyNotBetweenOtherKeys = true;
			} else {
				keys.add(nextNode.key);
				keynodes.add(nextNode);
			}

			hasJoined = _conn.join.join(Node.toJson(node)).equals("true") ? true : false;

			// Only start node in dht.
			Boolean sameKeys = (nextNode.predecessor_key.equals(nextNode.key) && nextNode.successor_key.equals(nextNode.key));

			if (newKeyNotBetweenOtherKeys) {

				String closestsKey = Key.FindClosestsKey(node.key, keys);
				Node closetsNode = null;
				for (Node _cn : keynodes) {
					if (_cn.key.equals(closestsKey)) {
						closetsNode = _cn;
						break;
					}
				}
				// HasJoined
				// CS -> CS+1
				// CS -> New_Node -> CS+1
				//
				// CS -> New_Node, CS <- New_Node
				// New_Node -> CS+1, New_Node <- CS+1
				//
				Node _CS = closetsNode;

				RestConnection succConn = new RestConnection(_CS.successor_host, _CS.successor_port);
				RestConnection conn = new RestConnection(_CS.host, _CS.port);

				Node _CS1 = Node.fromJson(succConn.node.getNode());

				setNodeSuccessor(_CS, node.key, node.host, node.port);
				conn.succs.setSuccessor(Node.toJson(_CS));
				conn.dhts.put(Node.toJson(node));

				setNodePredecessor(node, _CS.key, _CS.host, _CS.port);
				setNodeSuccessor(node, _CS1.key, _CS1.host, _CS1.port);
				ServerData.Instance.putNode(Node.toJson(_CS));
				ServerData.Instance.putNode(Node.toJson(_CS1));

				setNodePredecessor(_CS1, node.key, node.host, node.port);
				succConn.preds.setPredecessor(Node.toJson(_CS1));
				succConn.dhts.put(Node.toJson(node));
				break;

			} else if (hasJoined || sameKeys) {
				if (hasJoined && !sameKeys) {
					// A -> C
					// A -> B -> C
					//
					// A -> B, A <- B
					// B -> C, B <- C
					Node _C = nextNode;
					Node _B = node;
					RestConnection prevConn1 = new RestConnection(_C.predecessor_host, _C.predecessor_port);
					Node _A = Node.fromJson(prevConn1.node.getNode());

					setNodeSuccessor(_A, _B.key, _B.host, _B.port);
					prevConn1.succs.setSuccessor(Node.toJson(_A));
					prevConn1.dhts.put(Node.toJson(_B));

					setNodePredecessor(_B, _A.key, _A.host, _A.port);
					setNodeSuccessor(_B, _C.key, _C.host, _C.port);
					ServerData.Instance.putNode(Node.toJson(_A));
					ServerData.Instance.putNode(Node.toJson(_C));

					RestConnection nextConn = new RestConnection(_C.host, _C.port);
					setNodePredecessor(_C, _B.key, _B.host, _B.port);
					nextConn.preds.setPredecessor(Node.toJson(_C));
					nextConn.dhts.put(Node.toJson(_B));
				} else if (sameKeys) {

					// SameKeys (startnode)
					// A
					// A -> B
					//
					// A -> B, A <- B
					// B -> A
					Node _A = nextNode;
					Node _B = node;
					RestConnection conn = new RestConnection(_A.host, _A.port);

					setNodeSuccessor(_A, _B.key, _B.host, _B.port);
					conn.succs.setSuccessor(Node.toJson(_A));
					conn.dhts.put(Node.toJson(_A));

					setNodePredecessor(_B, _A.key, _A.host, _A.port);
					setNodeSuccessor(_B, _A.key, _A.host, _A.port);
					ServerData.Instance.putNode(Node.toJson(_A));
					ServerData.Instance.putNode(Node.toJson(_B));

					setNodePredecessor(_A, _B.key, _B.host, _B.port);
					conn.preds.setPredecessor(Node.toJson(_A));
					conn.dhts.put(Node.toJson(_B));
				}

				break;
			} else {
				_conn = new RestConnection(nextNode.successor_host, nextNode.successor_port);
				nextNode = Node.fromJson(_conn.node.getNode());
			}
		}
		System.out.println("Predecessor: " + node.predecessor_port + " Successor: " + node.successor_port);
		updateFingers();
		workingConnection = true;
		System.out.println("Joined DHT-network.");
	}

	public void setNodeSuccessor(Node node, String key, String host, String port) {
		node.successor_key = key;
		node.successor_host = host;
		node.successor_port = port;
	}
	public void setNodePredecessor(Node node, String key, String host, String port) {
		node.predecessor_key = key;
		node.predecessor_host = host;
		node.predecessor_port= port;
	}
	public void sendMessage(Message m) throws Exception{

		String json = MessageJson.toJson(m);

		ArrayList<Node> nodes = FindAllNodesInNetwork();
		String[] _keys = Key.sortKeysByDistance(node.key, nodes);
		ArrayList<String> keys = filterKeys(_keys);

		for (Node n : nodes) {
			if (!keys.contains(n.key))
				continue;
			RestConnection conn = new RestConnection(n.host, n.port);
			conn.messages.postMessage(json);
		}


	}


	public void updateFingers() {

		ArrayList<Node> nodes = FindAllNodesInNetwork();
		String[] _keys = Key.sortKeysByDistance(node.key, nodes);
		ArrayList<String> keys = filterKeys(_keys);
		//System.out.println("Nodes: " + nodes.size() + " Keys: " + keys.size());


		for (int i = 0; i < nodes.size(); i++) {
			Node _n = nodes.get(i);
			RestConnection conn = new RestConnection(_n.host, _n.port);
			conn.dhts.remove("");

		}

		for (int i = 0; i < nodes.size(); i++) {
			Node _n = nodes.get(i);
			String json = Node.toJson(_n);
			if (!keys.contains(_n.key))
				continue;

			for (int j = 0; j < nodes.size(); j++) {
				if (i == j)
					continue;
				Node _n2 = nodes.get(j);
				RestConnection conn = new RestConnection(_n2.host, _n2.port);
				conn.node.postNode(json);
			}
		}
	}

	public ArrayList<Node> FindAllNodesInNetwork() {

		Node n = node;
		ArrayList<Node> nodes = new ArrayList<>();

		int i = 0;
		do {
			nodes.add(n);
			RestConnection conn = new RestConnection(n.successor_host, n.successor_port);
			n = Node.fromJson(conn.node.getNode());
			i += 1;
		} while (!node.key.equals(n.key) && i < 32);
		return nodes;
	}



	public void TracePrintRouting() {

		Node n = node;
		int i = 0;

		ArrayList<String> traces = new ArrayList<>();
		do {
			RestConnection conn = new RestConnection(n.successor_host, n.successor_port);
			n = Node.fromJson(conn.node.getNode());

			String s = "Node " + (i+1) + ":  Key " + n.key + " on port " + n.port;
			traces.add(s);

			i += 1;

		} while(!n.key.equals(node.key) && i < 32);
		System.out.println("-----------------------");
		System.out.println("Trace printing Node path from " + node.key + " and around.");
		for (String s : traces)
			System.out.println("\t" + s);
		System.out.println("Finish trace on node key: " + n.key);
		System.out.println("-----------------------");
	}

	public void checkConnection() {
		System.out.println("Connection Check!");
		try {
			if (workingConnection) {
				RestConnection conn_succ = new RestConnection(node.successor_host, node.successor_port);
				conn_succ.node.getNode();
			}
		} catch (Exception e) {
			workingConnection = false;
			System.out.println("Node failure: Key: " + node.successor_key + ", Host: " + node.successor_host +
								", Port: " + node.successor_port);
			fixConnection();
		}
	}

	public void fixConnection() {
		ArrayList<Node> allHosts = new ArrayList<>(ServerData.Instance.dht.nodes.values());
		ServerData.Instance.dht.nodes.clear();
		String faulty_key = node.successor_key;

		// Traverse back in dht to find the other faulty node.
		// A (this) -> B (faulty) -> C
		// A <-> C
		// A -> C, A <- C
		Node _A = node;
		Node _C = null;
		for (Node n : allHosts) {
			if (n.key.equals(node.key) || n.key.equals(faulty_key)) {
				continue;
			}

			if (n.predecessor_key.equals(faulty_key)) {
				_C = n;
				break;
			}
		}
		System.out.println("Pairing " + _A.key + " with " + _C.key + " as Predecessor <-> Successor.");

		setNodeSuccessor(_A, _C.key, _C.host, _C.port);
		setNodePredecessor(_C, _A.key, _A.host, _A.port);
		ServerData.Instance.putNode(Node.toJson(_C));

		RestConnection conn = new RestConnection(_C.host, _C.port);
		conn.preds.setPredecessor(Node.toJson(_C));
		conn.dhts.put(Node.toJson(_A));




		updateFingers();
		workingConnection = true;
	}


	public ArrayList<String> filterKeys(String[] keys) {
		ArrayList<String> _keys = new ArrayList<>();
		for (int i = 0; i < keys.length; i++) {
			int m = (int)Math.pow(2f, i);
			if (m > keys.length) {
				break;
			}
			_keys.add(keys[i]);
		}
		return _keys;
	}


	public void printDHT() {
		updateFingers();
		ServerData.Instance.dht.printDHT();
	}
}
