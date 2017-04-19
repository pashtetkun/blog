import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CloudService {
	
	private final String repoUrl = "https://api.github.com/repos";
	private String repoOwner;
	private String repoName;
	private String folder;
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	public CloudService(Properties properties){
		this.repoOwner = properties.getProperty("account", "testAccount");
		this.repoName = properties.getProperty("project", "testProject");
		this.folder = properties.getProperty("folder", "testFolder");
	}
	
	public String getReadme() throws ClientProtocolException, IOException{
		HttpGet request = new HttpGet(String.format("%s/%s/%s/readme", repoUrl, repoOwner, repoName));
		request.addHeader("User-Agent", USER_AGENT);
		HttpClient client = HttpClientBuilder.create().build();
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
		/*String path = String.format("/repos/%s/%s/contents/server/src/main/resources/repo/articles/preview/%s", 
				repoOwner, repoName, article);
		
		URI uri = new URI(
			    "https", 
			    "api.github.com", 
			    path,
			    null);*/
		String url = getRequestUrl("/articles/preview/"+article);
		
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);
		HttpClient client = HttpClientBuilder.create().build();
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
	
	private String getRequestUrl(String path) throws URISyntaxException{
		String repoFolder = String.format("/repos/%s/%s/contents/%s", repoOwner, repoName, folder);
		URI uri = new URI(
			    "https", 
			    "api.github.com", 
			    repoFolder+path,
			    null);
		return uri.toString();
	}
	
	private synchronized List getResponseList (String path) throws URISyntaxException, IOException{
		String url = getRequestUrl(path);
		System.out.println(url);
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		
		HttpEntity entity = response.getEntity();
		List jsonList = new ArrayList();
		if (entity != null) {
		    BufferedReader br = new BufferedReader(
                    new InputStreamReader(entity.getContent()));
		    String jsonStr = br.readLine();
		    System.out.println(jsonStr);
		    Gson gson = new Gson();
		    JsonParser parser = new JsonParser();
		    JsonElement rootObject = parser.parse(jsonStr);
		    if (rootObject.isJsonArray())
		    	jsonList = gson.fromJson(rootObject, ArrayList.class);
		    
		    //jsonList = new Gson().fromJson(jsonStr, ArrayList.class);
		    //String articleUrl = jsonList.get("download_url").toString();
		    //System.out.println(articleUrl);
		}

		System.out.println("Response Code : "
		                + response.getStatusLine().getStatusCode());
		//client.getConnectionManager().shutdown();
		return jsonList;
	}
	
	public ArrayList<String> getNameFolders(String path) throws URISyntaxException, IOException{
		ArrayList<String> directories = new ArrayList<>();
		List<Map> json = getResponseList(path);
		for (Map m : json){
			directories.add(m.get("name").toString());
		}
		
		return directories;
	}

}
