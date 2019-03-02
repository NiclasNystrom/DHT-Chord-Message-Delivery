package rest.resources.interfaces;


import org.restlet.resource.Post;
import org.restlet.resource.Put;

public interface iJoinResource {

	@Post
	public String join(String node);

	@Put
	public void routeNodes();


}
