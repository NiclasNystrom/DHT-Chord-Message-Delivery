package rest.resources.interfaces;


import org.restlet.resource.Post;


public interface iMessageResource {

	@Post
	public void postMessage(String byteString);

}
