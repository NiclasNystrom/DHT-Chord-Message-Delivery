package rest.resources;

import org.restlet.resource.ServerResource;
import rest.resources.interfaces.iDHTResource;
import rest.server.ServerData;

public class DHTResource extends ServerResource implements iDHTResource {

	@Override
	public String get(String k) {
		return ServerData.Instance.getNode(k);
	}

	@Override
	public void put(String node) {
		ServerData.Instance.putNode(node);
	}
	@Override
	public void remove(String k) {
		ServerData.Instance.dht.nodes.clear();
		//ServerData.Instance.removeNode(k);
	}
}
