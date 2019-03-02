package rest.resources.interfaces;


import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface iDHTResource {

	@Get
	public String get(String k);

	@Post
	public void put(String node);

	@Delete
	public void remove(String k);

}
