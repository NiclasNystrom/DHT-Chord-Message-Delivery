package rest.message;


import org.json.JSONObject;
import rest.client.ClientMain;

public class MessageJson {

	public static Message fromJson(String json) {
		Message m = new Message();
		JSONObject o = new JSONObject(json);

		m.setRecipient((String) o.get("recipient"));
		m.setTopic((String) o.get("topic"));
		m.setContent((String) o.get("content"));
		m.setSender((String) o.get("sender"));
		m.setID((String) o.get("id"));
		m.timestamp = Long.parseLong((String) o.get("timestamp"));
		return m;
	}

	public static String toJson(Message m) {
		JSONObject obj = new JSONObject();

		obj.put("recipient", m.getRecipient());
		obj.put("topic", m.getTopic());
		obj.put("content", m.getContent());
		obj.put("sender", m.getSender());
		obj.put("id", m.getID());
		obj.put("timestamp", String.valueOf(m.getTimestamp()));
		return obj.toString();
	}

	public static void main(String[] args) {
		Message m = ClientMain.createMessage("Test-Message", "Test-Topic", "Test-Sender", null);
		String json = MessageJson.toJson(m);
		System.out.println("->Json:\n" + json);
		Message m2 = MessageJson.fromJson(json);
		if (m2 != null) {
			System.out.println("->Message:\nID: " + m2.getID() + "\nTopic: " + m2.getTopic() + "\nContent: " + m2.getContent());
		} else {
			System.err.println("M2 null");
		}
	}

}
