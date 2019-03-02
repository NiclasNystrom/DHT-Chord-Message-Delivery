package rest.resources;


import dht.Node;
import org.restlet.resource.ServerResource;
import rest.client.RestConnection;
import rest.resources.interfaces.iMessageResource;
import rest.resources.interfaces.iSucessorResource;
import rest.server.ServerData;

public class SucessorResource extends ServerResource implements iSucessorResource {

	@Override
	public String getSuccessor() {
		return ServerData.Instance._node.successor_key;
	}

	@Override
	public void setSuccessor(String key) {
		Node n = Node.fromJson(key);
		ServerData.Instance._node.successor_key = n.successor_key;
		ServerData.Instance._node.successor_host = n.successor_host;
		ServerData.Instance._node.successor_port = n.successor_port;
	}
}
