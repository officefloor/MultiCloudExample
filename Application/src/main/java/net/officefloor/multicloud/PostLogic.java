package net.officefloor.multicloud;

import java.io.IOException;

import net.officefloor.server.http.ServerHttpConnection;
import net.officefloor.web.HttpPathParameter;
import net.officefloor.web.ObjectResponse;

public class PostLogic {

	public void retrieve(@HttpPathParameter("key") String key, Repository repository,
			ObjectResponse<PostDocument> response) {
		PostDocument document = repository.retrievePostByKey(key);
		response.send(document);
	}

	public void store(PostDocument document, Repository repository, ServerHttpConnection connection)
			throws IOException {
		repository.store(document);
		String key = document.getKey();
		connection.getResponse().getEntityWriter().write(key);
	}
}
