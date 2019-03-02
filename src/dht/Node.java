package dht;

import org.json.*;


public class Node {

	public String predecessor_key, successor_key;
	public String predecessor_host, successor_host;
	public String predecessor_port, successor_port;


	public String host, port;
	public String key;


	public Node() {
		key = "NaN";
		predecessor_key = successor_key = key;
		host = port = "NaN";

		predecessor_host = successor_host = host;
		predecessor_port = successor_port = port;


	}
	public Node(String guid) {
		key = Key.create_guid(guid);
		predecessor_key = successor_key = key;
		host = "NaN";
	}

	public Node(String host, String port) {
		this("http://"+host+":"+port);
		this.host = host;
		this.port = port;

		Reset();

	}



	public static String toJson(Node n) {
		JSONObject obj = new JSONObject();

		obj.put("predecessor", n.predecessor_key);
		obj.put("predecessor_host", n.predecessor_host);
		obj.put("predecessor_port", n.predecessor_port);


		obj.put("successor", n.successor_key);
		obj.put("successor_host", n.successor_host);
		obj.put("successor_port", n.successor_port);

		obj.put("host", n.host);
		obj.put("otherPort", n.port);
		obj.put("key", n.key);
		return obj.toString();
	}

	public static Node fromJson(String s) {
		Node n = new Node();
		JSONObject o = new JSONObject(s);

		n.predecessor_key = (String) o.get("predecessor");
		n.predecessor_host = (String) o.get("predecessor_host");
		n.predecessor_port = (String) o.get("predecessor_port");

		n.successor_key = (String) o.get("successor");
		n.successor_host = (String) o.get("successor_host");
		n.successor_port = (String) o.get("successor_port");

		n.host = (String) o.get("host");
		n.port = (String) o.get("otherPort");
		n.key = (String) o.get("key");
		return n;
	}


	public static void main(String[] args) {

		Node n1 = new Node("localhost", "10500");
		Node n2 = new Node("localhost", "10600");
		Node n3 = new Node("localhost", "10700");

		System.out.println("Key 1: " + n1.key);
		System.out.println("Key 2: " + n2.key);
		System.out.println("Key 3: " + n3.key);

		String json = Node.toJson(n1);
		Node _n1 = Node.fromJson(json);

		System.out.println("Key _1: " + _n1.key);
		System.out.println(json);


	}

	public void Reset() {
		predecessor_host = successor_host = host;
		predecessor_port = successor_port = port;
	}

}
