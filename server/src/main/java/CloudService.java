import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class CloudService {
	
	private final String repoUrl = "https://api.github.com/repos";
	private final String repoOwner = "pashtetkun";
	private final String repoName = "blog";
	
	private HttpClient client = HttpClientBuilder.create().build();
	private final String USER_AGENT = "Mozilla/5.0";
	
	public CloudService(){
		
	}
	
	public String getReadme() throws ClientProtocolException, IOException{
		HttpGet request = new HttpGet(String.format("%s/%s/%s/readme", repoUrl, repoOwner, repoName));
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    long len = entity.getContentLength();
		    // write the file to whether you want it.
		    BufferedReader br = new BufferedReader(
                    new InputStreamReader(entity.getContent()));
		    String output = br.readLine();
		    System.out.println(output);
			//System.out.println("Output from Server .... \n");
			//while ((output = br.readLine()) != null) {
				//System.out.println(output);
			//}

			//client.getConnectionManager().shutdown();
		}

		System.out.println("Response Code : "
		                + response.getStatusLine().getStatusCode());
		return "";
	}
	
	public String getArticleEx() throws ClientProtocolException, IOException, URISyntaxException{
		String article = "Tekken 7.md";
		String path = String.format("/repos/%s/%s/contents/server/src/main/resources/repo/articles/preview/%s", 
				repoOwner, repoName, article);
		//url = URLEncoder.encode(url, "UTF-8");
		
		URI uri = new URI(
			    "https", 
			    "api.github.com", 
			    path,
			    null);
		//URL url = uri.toURL();
		
		HttpGet request = new HttpGet(uri.toString());
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    long len = entity.getContentLength();
		    // write the file to whether you want it.
		    BufferedReader br = new BufferedReader(
                    new InputStreamReader(entity.getContent()));
		    String output = br.readLine();
		    System.out.println(output);
			//System.out.println("Output from Server .... \n");
			//while ((output = br.readLine()) != null) {
				//System.out.println(output);
			//}

			//client.getConnectionManager().shutdown();
		}

		System.out.println("Response Code : "
		                + response.getStatusLine().getStatusCode());
		return "";
	}

}
