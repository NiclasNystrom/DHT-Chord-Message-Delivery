package rest.resources.interfaces;



import org.restlet.resource.Get;
import org.restlet.resource.Post;


public interface iNodeResource {

	@Get
	public String getNode();

	@Post
	public void postNode(String node);


}
