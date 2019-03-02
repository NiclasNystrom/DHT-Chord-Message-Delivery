package rest.resources.interfaces;


import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface iSucessorResource {

	@Get
	public String getSuccessor();

	@Post
	public void setSuccessor(String key);

}
