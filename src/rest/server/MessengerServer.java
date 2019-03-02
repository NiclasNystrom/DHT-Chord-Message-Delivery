package rest.server;

import dht.Node;
import org.restlet.Component;
import org.restlet.data.Protocol;
import rest.resources.*;

public class MessengerServer extends Component{


    public static void main(String[] args) throws Exception {
        int port = 8322;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);

        int crypt = 0;
        if (args.length > 1)
            crypt = Integer.parseInt(args[1]);

        new MessengerServer(port, crypt).start();
    }
    public MessengerServer(int port, int crypt) {
        getServers().add(Protocol.HTTP, port);
        getDefaultHost().attach("/message/", MessageResource.class);
        getDefaultHost().attach("/dht/", DHTResource.class);
        getDefaultHost().attach("/preds/", PredecessorResource.class);
        getDefaultHost().attach("/succs/", SucessorResource.class);
        getDefaultHost().attach("/node/", NodeResource.class);
        getDefaultHost().attach("/join/", JoinResource.class);
    }

    public MessengerServer(int port, int crypt, Node n) {
        getServers().add(Protocol.HTTP, port);
        getDefaultHost().attach("/message/", MessageResource.class);
        getDefaultHost().attach("/dht/", DHTResource.class);
        getDefaultHost().attach("/preds/", PredecessorResource.class);
        getDefaultHost().attach("/succs/", SucessorResource.class);
        getDefaultHost().attach("/node/", NodeResource.class);
        getDefaultHost().attach("/join/", JoinResource.class);

        //ServerData.Instance.dht.put(n.key, n);
        ServerData.Instance.putNode(Node.toJson(n));

    }

}
