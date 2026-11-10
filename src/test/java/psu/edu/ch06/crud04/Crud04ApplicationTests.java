package psu.edu.ch06.crud04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Crud04ApplicationTests {

	@Autowired
	Environment env;

	private final HttpClient http = HttpClient.newHttpClient();

	private HttpResponse<String> get(String path, String user, String password) throws Exception {
		HttpRequest.Builder b = HttpRequest.newBuilder(
				URI.create("http://localhost:" + env.getProperty("local.server.port") + path));
		if (user != null) {
			String token = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
			b.header("Authorization", "Basic " + token);
		}
		return http.send(b.GET().build(), HttpResponse.BodyHandlers.ofString());
	}

	@Test
	void requestWithoutCredentialsIsRejected() throws Exception {
		assertEquals(401, get("/api/v1/products", null, null).statusCode());
	}

	@Test
	void wrongPasswordIsRejected() throws Exception {
		assertEquals(401, get("/api/v1/products", "usera", "wrong").statusCode());
		assertEquals(401, get("/api/v1/products", "nobody", "pwd123").statusCode());
	}

	@Test
	void userFromDatabaseCanAccessApi() throws Exception {
		HttpResponse<String> res = get("/api/v1/products", "usera", "pwd123");
		assertEquals(200, res.statusCode());
		assertTrue(res.body().contains("3d printer"));
		assertEquals(200, get("/api/v1/products/1", "usera", "pwd123").statusCode());
	}
}
