package rest.resources;


import dht.Node;
import org.restlet.resource.ServerResource;
import rest.client.RestConnection;
import rest.resources.interfaces.iMessageResource;
import rest.resources.interfaces.iPredecessorResource;
import rest.server.ServerData;

public class PredecessorResource extends ServerResource implements iPredecessorResource {


	@Override
	public String getPredecessor() {
		return ServerData.Instance._node.predecessor_key;
	}

	@Override
	public void setPredecessor(String key) {
		Node n = Node.fromJson(key);
		ServerData.Instance._node.predecessor_key = n.predecessor_key;
		ServerData.Instance._node.predecessor_host = n.predecessor_host;
		ServerData.Instance._node.predecessor_port = n.predecessor_port;
	}
}
