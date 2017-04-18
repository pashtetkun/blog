import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {

	public Properties getProperties() throws IOException{
		InputStream inputStream = PropertiesService.class.getResourceAsStream("app.properties");
		Properties props = new Properties();
		props.load(inputStream);
		inputStream.close();
		return props;
	}
}
