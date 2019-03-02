package rest.message;
import java.util.*;

public class Message implements iMessage {
	public String id; // Max 32 chars
	public String sender; // 1-255 chars
	public String recipient; // 1-255 chars
	public String topic; // 1-255 chars
	public long timestamp; // 64 bits
	public String content; // 0-65535 chars




	public final int MAX_CHARS = 255;
	public final int MAX_ID_CHARS = 32;
	public final int MAX_CONTENT_CHARS = 65535;
	public final int MAX_ATTACHMENT_BYTES = 4194303;
	public final String EMPTY = "None";

	public Message() {
		sender = topic = content = EMPTY;
		timestamp = 0;
		setID(UUID.randomUUID().toString());
	}



	@Override
	public String getSender() {
		return this.sender;
	}


	@Override
	public void setSender(String sender) {
		if (this.sender.length() > 0)
			this.sender = sender;
		if (this.sender.length() > MAX_CHARS)
			this.sender = this.sender.substring(0, MAX_CHARS);
	}


	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public void setID(String id) {
		this.id = id;
		if (this.id.length() > MAX_ID_CHARS)
			this.id = this.id.substring(0, MAX_ID_CHARS);
	}


	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}



	@Override
	public String getTopic() {
		return this.topic;
	}

	@Override
	public void setTopic(String topic) {
		if (this.topic.length() > 0)
			this.topic = topic;
		if (this.topic.length() > MAX_CHARS)
			this.topic = this.topic.substring(0, MAX_CHARS);
	}


	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	@Override
	public void setTimestamp() {
		this.timestamp = new Date().getTime();
	}



	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
		if (this.content.length() > MAX_CONTENT_CHARS)
			this.content = this.content.substring(0, MAX_CONTENT_CHARS);
	}


	public static class Builder {
		public String sender;
		public String recipient;
		public String topic;
		public String content;



		public Builder() {
			sender = topic = content = "";
		}
		public Builder sender(final String sender) {
			this.sender = sender;
			return this;
		}
		public Builder recipient(final String recipient) {
			this.recipient = recipient;
			return this;
		}
		public Builder topic(final String topic) {
			this.topic = topic;
			return this;
		}
		public Builder content(String content) {
			this.content = content;
			return this;
		}
		public Message build() {
			Message m = new Message();
			m.setSender(sender);
			m.setRecipient(recipient);
			m.setTopic(topic);
			m.setContent(content);
			return m;
		}
	}



}
