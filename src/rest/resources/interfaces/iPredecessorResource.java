package rest.resources.interfaces;


import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface iPredecessorResource {

	@Get
	public String getPredecessor();


	@Post
	public void setPredecessor(String key);

}
