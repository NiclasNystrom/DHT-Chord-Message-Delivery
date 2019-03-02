package rest;

import dht.Node;
import rest.client.ClientMain;
import rest.client.ClientMessenger;
import rest.message.Message;
import rest.server.MessengerServer;
import rest.server.ServerData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientServerMain {

	public static ClientServerMain Instance;
	public ClientMessenger messenger;

	public ClientServerMain(int port, int port_other, boolean startNode) throws Exception {

		if (Instance != null) {
			return;
		}
		Instance = this;

		Node node = new Node("localhost", String.valueOf(port));
		new ServerData(node);
		new MessengerServer(port, 0, node).start();
		messenger = new ClientMessenger(port_other, node, startNode);
	}


	public static void main(String[] args) throws Exception{
		int _StartPort = 8322;
		if (args.length < 3) {
			System.err.println("Usage: port startport mode");
		}

		int port = Integer.parseInt(args[0]);
		_StartPort = Integer.parseInt(args[1]);
		int mode = Integer.parseInt(args[2]);
		Boolean isStartNode = mode == 0 ? true : false;
		ClientServerMain cs = new ClientServerMain(port, _StartPort, isStartNode);


		BufferedReader br = null;
		String sender = null, topic = null, message = null, username = null, attachment = null, input = null, id = null;

		try {

			br = new BufferedReader(new InputStreamReader(System.in));
			boolean isRunning = true;
			while(isRunning) {
				System.out.println("\nChoose between...\n1. Print All nodes in network\n" + "2. Leave\n3. Send test message\n4. Print DHT content\n");
				input = br.readLine();
				if (input == null)
					continue;
				if (input.equals(""))
					continue;
				int inp = Integer.parseInt(input);

				switch(inp) {
					case 1:
						cs.messenger.TracePrintRouting();
						break;
					case 2:
						cs.messenger.Leave(true);
						isRunning = false;
						break;
					case 3:
						Message m = ClientMain.createMessage("Test-Message", "Test-Topic", "Test-Sender", null);
						cs.messenger.sendMessage(m);
						System.out.println("Sent message to all other clients.");
						break;
					case 4:
						cs.messenger.printDHT();
						//ServerData.Instance.dht.printDHT();
						break;
					default:
						break;
				}
				System.out.println("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
