package rest.client;
import rest.message.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ClientMain {


	public static void main(String[] args)  {

		int port = 8322;

		if (args.length > 0)
			port = Integer.parseInt(args[0]);

		ClientMessenger messenger = new ClientMessenger(port, null, false);
		BufferedReader br = null;
		String sender = null, topic = null, message = null, username = null, attachment = null, input = null, id = null;

		try {

			br = new BufferedReader(new InputStreamReader(System.in));

			while(true) {
				System.out.println("\nChoose between...\n1. Send message\n" + "2. List messages.\n");
				input = br.readLine();
				if (input.equals(""))
					continue;
				int inp = Integer.parseInt(input);

				switch(inp) {
					case 1:
						System.out.println("\nEnter sender: ");
						sender = br.readLine();
						System.out.println("\nEnter topic: ");
						topic = br.readLine();
						System.out.println("\nEnter message: ");
						message = br.readLine();

						Message m = createMessage(message, topic, sender,null);
						messenger.sendMessage(m);
						break;
					case 2:
						//messenger.listMessages();
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


	public static Message createMessage(String message, String topic, String sender, List<Byte> bytes) {
		Message m = new Message();
		m.setID(UUID.randomUUID().toString());
		m.setSender(sender);
		m.setTimestamp();
		m.setTopic(topic);
		m.setContent(message);
		m.setRecipient("Test-Recipient");
		return m;
	}


}
