package net.officefloor.multicloud;

import net.officefloor.cabinet.Cabinet;

@Cabinet
public interface Repository {

	PostDocument retrievePostByKey(String key);

	void store(PostDocument document);
}
