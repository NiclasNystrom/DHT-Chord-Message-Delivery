package rest.resources;


import org.restlet.resource.ServerResource;
import rest.resources.interfaces.iNodeResource;
import rest.server.ServerData;

public class NodeResource extends ServerResource implements iNodeResource {

	@Override
	public String getNode() {
		return ServerData.Instance.getNode();
	}

	@Override
	public void postNode(String node) {
		ServerData.Instance.putNode(node);
	}
}
