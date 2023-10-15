package net.officefloor.multicloud;

import net.officefloor.cabinet.Document;
import net.officefloor.cabinet.Key;
import net.officefloor.web.HttpObject;

@Document
@HttpObject
public class PostDocument {

	private @Key String key;

	private String title;

	private String message;

	public PostDocument() {
	}

	public PostDocument(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public String getKey() {
		return this.key;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

}
