package rest.resources;


import org.restlet.resource.ServerResource;
import rest.resources.interfaces.iMessageResource;
import rest.server.ServerData;

public class MessageResource extends ServerResource implements iMessageResource {

	@Override
	public void postMessage(String byteString) {
		ServerData.Instance.AddMessage(byteString);
	}
}
