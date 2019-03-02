package rest.server;

import dht.DHT;
import dht.Key;
import dht.Node;
import rest.message.Message;
import rest.message.MessageJson;

import java.util.ArrayList;
import java.util.List;

public class ServerData {

	public static ServerData Instance;

	public List<Message> messages;

	public DHT dht;

	public Node _node;

	public ServerData(Node node) {

		if (Instance == null) {
			Instance = this;
			_node = node;
		} else {
			return;
		}
		messages = new ArrayList<Message>();
		dht = new DHT();
	}


	public boolean removeNode(String k) {
		if (dht.containsNode(k)) {
			dht.remove(k);
			return true;
		}
		System.err.println("Warning Remove: Node doesnt exist in DHT (Server).");
		return false;
	}


	public String getNode(String k) {
		if (dht.containsNode(k)) {
			return Node.toJson(dht.get(k));
		}
		System.err.println("Warning: Node doest not exist in DHT (Server).");
		return "";
	}

	public void putNode(String n) {

		Node _n = Node.fromJson(n);
		if (_n != null) {
			if (!_n.key.equals(_node.key))
			dht.put(_n.key, _n);
		}

	}




	public void AddMessage(String json) {
		Message m = MessageJson.fromJson(json);

		Boolean newMessage = true;
		for (Message _m : messages) {
			if (_m.getID().equals(m.getID())) {
				newMessage = false;
				break;
			}
		}

		if (newMessage) {
			System.out.println("------ New Message ------");
			System.out.println("ID: " + m.getID());
			System.out.println("Timestamp: " + m.getTimestamp());
			System.out.println("Recipient: " + m.getRecipient());
			System.out.println("Sender: " + m.getSender());
			System.out.println("Topic: " + m.getTopic());
			System.out.println("Content: " + m.getContent());
			System.out.println("------------------------------");
			messages.add(m);
		}

	}


	public void setNode(Node n) {
		_node = n;
		putNode(Node.toJson(n));
	}
	public String getNode() {
		return Node.toJson(_node);
	}


	public boolean join(String node) {

		Node new_node = Node.fromJson(node);
		Node myNode = _node;
		Boolean isKeyBetweenKeys = Key.inBetweenKeys(myNode.key, new_node.predecessor_key, new_node.key);
		if (isKeyBetweenKeys)
			putNode(Node.toJson(new_node));
		//dht.put(new_node.key, new_node);
		return isKeyBetweenKeys;
	}


}
