import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.client.indirect.IndirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;

public class SecurityConfig implements ConfigFactory {

	@Override
    public Config build() {
		final FormClient formClient = new FormClient("http://localhost:8080/login", new SimpleTestUsernamePasswordAuthenticator());
		final IndirectBasicAuthClient indirectBasicAuthClient = new IndirectBasicAuthClient(new SimpleTestUsernamePasswordAuthenticator());
		
		final Clients clients = new Clients("http://localhost:8080/callback", formClient, indirectBasicAuthClient);
		final Config config = new Config(clients);
		return config;
	}
}
