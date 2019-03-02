package rest.client;

import org.restlet.resource.ClientResource;
import rest.resources.interfaces.*;

public class RestConnection {
	private ClientResource clientMessages;
	private ClientResource clientDht;
	private ClientResource clientPreds;
	private ClientResource clientSuccs;
	private ClientResource clientNode;
	private ClientResource clientJoin;


	public iMessageResource messages;
	public iDHTResource dhts;
	public iPredecessorResource preds;
	public iSucessorResource succs;
	public iNodeResource node;
	public iJoinResource join;

	public RestConnection(String host, String port) {
		connectTo(host, port);
	}

	public void connectTo(String host, String port) {
		if (clientMessages != null)
			clientMessages.delete();
		if (clientDht != null)
			clientDht.delete();
		if (clientPreds != null)
			clientPreds.delete();
		if (clientSuccs != null)
			clientSuccs.delete();
		if (clientNode != null)
			clientNode.delete();
		if (clientJoin != null)
			clientJoin.delete();



		String url = "http://" + host + ":"+port;

		clientMessages 	= new ClientResource(url + "/message/");
		messages = clientMessages.wrap(iMessageResource.class);

		clientDht 	= new ClientResource(url + "/dht/");
		dhts = clientDht.wrap(iDHTResource.class);

		clientPreds = new ClientResource(url + "/preds/");
		preds = clientPreds.wrap(iPredecessorResource.class);

		clientSuccs = new ClientResource(url + "/succs/");
		succs = clientSuccs.wrap(iSucessorResource.class);

		clientNode = new ClientResource(url + "/node/");
		node = clientNode.wrap(iNodeResource.class);

		clientJoin = new ClientResource(url + "/join/");
		join = clientJoin.wrap(iJoinResource.class);
	}
}
