package net.officefloor.multicloud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.extension.ExtendWith;

import net.officefloor.cabinet.spi.CabinetManager;
import net.officefloor.cloud.test.CloudTest;
import net.officefloor.cloud.test.OfficeFloorCloudProviders;
import net.officefloor.plugin.clazz.Dependency;
import net.officefloor.server.http.HttpMethod;
import net.officefloor.server.http.HttpStatus;
import net.officefloor.server.http.mock.MockHttpResponse;
import net.officefloor.server.http.mock.MockHttpServer;
import net.officefloor.woof.mock.MockWoofServer;

@ExtendWith(OfficeFloorCloudProviders.class)
public class RunApplicationTest {

	private @Dependency MockWoofServer server;

	private @Dependency CabinetManager cabinetManager;

	private @Dependency Repository repository;

	@CloudTest
	public void ensureApplicationAvailable() throws Exception {
		MockHttpResponse response = this.server.send(MockHttpServer.mockRequest("/hi/UnitTest"));
		response.assertResponse(200, "{\"message\":\"Hello UnitTest\"}", "content-type", "application/json");
	}

	@CloudTest
	public void repository() throws Exception {

		// Store post entity
		PostDocument post = new PostDocument("Interesting Test", "Always fun testing");
		this.repository.store(post);
		this.cabinetManager.flush();
		assertNotNull(post.getKey(), "Should generate key");

		// Retrieve
		PostDocument document = this.repository.retrievePostByKey(post.getKey());
		assertNotNull(document, "Should have entity");
	}

	@CloudTest
	public void retrieve() throws Exception {

		// Store post entity
		PostDocument post = new PostDocument("Interesting Test", "Always fun testing");
		this.repository.store(post);
		this.cabinetManager.flush();

		// Retrieve the entity
		MockHttpResponse response = this.server.send(MockHttpServer.mockRequest("/post/" + post.getKey()));
		response.assertJson(200, post);
	}

	@CloudTest
	public void store() throws Exception {

		// Create the post
		PostDocument post = new PostDocument("Interesting Test", "Always fun testing");
		MockHttpResponse response = this.server.send(MockHttpServer.mockJsonRequest(HttpMethod.POST, "/posts", post));
		response.assertStatus(HttpStatus.OK);
		String key = response.getEntity(null);

		// Ensure stored entity
		PostDocument document = this.repository.retrievePostByKey(key);
		assertEquals(key, document.getKey(), "Incorrect key");
		assertEquals(post.getTitle(), document.getTitle(), "Incorrect title");
		assertEquals(post.getMessage(), document.getMessage(), "Incorrect message");
	}
}