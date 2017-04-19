import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

public class CloudService {
	
	private final String repoUrl = "https://api.github.com/repos";
	private String repoOwner;
	private String repoName;
	private String folder;
	
	private HttpClient client = HttpClientBuilder.create().build();
	private final String USER_AGENT = "Mozilla/5.0";
	
	public CloudService(Properties properties){
		this.repoOwner = properties.getProperty("account", "testAccount");
		this.repoName = properties.getProperty("project", "testProject");
		this.folder = properties.getProperty("account", "testFolder");
	}
	
	public String getReadme() throws ClientProtocolException, IOException{
		HttpGet request = new HttpGet(String.format("%s/%s/%s/readme", repoUrl, repoOwner, repoName));
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    BufferedReader br = new BufferedReader(
                    new InputStreamReader(entity.getContent()));
		    String output = br.readLine();
		    System.out.println(output);
		}

		System.out.println("Response Code : "
		                + response.getStatusLine().getStatusCode());
		return "";
	}
	
	public String getArticleEx() throws ClientProtocolException, IOException, URISyntaxException{
		String article = "Tekken 7.md";
		String path = String.format("/repos/%s/%s/contents/server/src/main/resources/repo/articles/preview/%s", 
				repoOwner, repoName, article);
		
		URI uri = new URI(
			    "https", 
			    "api.github.com", 
			    path,
			    null);
		
		HttpGet request = new HttpGet(uri.toString());
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    BufferedReader br = new BufferedReader(
                    new InputStreamReader(entity.getContent()));
		    String jsonStr = br.readLine();
		    System.out.println(jsonStr);
		    Map jsonMap = new Gson().fromJson(jsonStr, Map.class);
		    String articleUrl = jsonMap.get("download_url").toString();
		    System.out.println(articleUrl);
		}

		System.out.println("Response Code : "
		                + response.getStatusLine().getStatusCode());
		return "";
	}

}
