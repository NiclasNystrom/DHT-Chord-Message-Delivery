package rest.resources;


import org.restlet.resource.ServerResource;
import rest.resources.interfaces.iJoinResource;
import rest.resources.interfaces.iPredecessorResource;
import rest.server.ServerData;

public class JoinResource extends ServerResource implements iJoinResource {

	@Override
	public String join(String node) {
		//ServerData.Instance.putNode(node);
		Boolean b = ServerData.Instance.join(node);
		return b.toString();
	}

	@Override
	public void routeNodes() {
		//ServerData.Instance.updateRouting();
	}
}
