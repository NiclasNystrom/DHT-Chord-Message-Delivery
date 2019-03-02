package rest.message;


public interface iMessage {

	String getID();
	void setID(String id);

	long getTimestamp();
	void setTimestamp();

	String getSender();
	void setSender(String sender);

	String getRecipient();
	void setRecipient(String recipient);

	String getTopic();
	void setTopic(String topic);

	String getContent();
	void setContent(String content);

}
