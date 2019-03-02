package dht;

import rest.server.ServerData;
import java.util.HashMap;
import java.util.Map;


/**
 * Class for the distributed hash table.
 *
 * @Author c14nnm
 */
public class DHT {

	// key, node
	public HashMap<String, Node> nodes;


	public DHT() {
		nodes = new HashMap<>();
	}


	public Node get(String key) {
		for (Map.Entry<String, Node> entry : nodes.entrySet()) {
			if (entry.getKey().equals(key)) {
				return nodes.get(key);
			}
		}
		return null;
	}

	public boolean put(String key, Node node) {

		if (!containsNode(key)) {
			nodes.put(key, node);
			return true;
		}
		return false;
	}
	public void remove(String key) {
		for (Map.Entry<String, Node> entry : nodes.entrySet()) {
			if (entry.getKey().equals(key)) {
				System.out.println("Removing node with  key <"+key+"> from DHT.");
				nodes.remove(key);
				break;
			}
		}
	}
	public boolean containsNode(String key) {
		for (Map.Entry<String, Node> entry : nodes.entrySet()) {
			if (entry.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}



	public void printDHT() {
		System.out.println("------------------------------");
		System.out.println("Printing content in DHT.");
		int i = 1;
		for (Node n: ServerData.Instance.dht.nodes.values()) {
			System.out.println("\tNode " + i + " -> Key: " + n.key + " Port: " + n.port);
			i += 1;
		}
		System.out.println("------------------------------");
	}

}
