import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CloudService {
	
	private String repoOwner;
	private String repoName;
	private String folder;
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	private final String ADMIN_EXTENSION = "markdown";
	
	public CloudService(Properties properties){
		this.repoOwner = properties.getProperty("account", "testAccount");
		this.repoName = properties.getProperty("project", "testProject");
		this.folder = properties.getProperty("folder", "testFolder");
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
	
	private List getResponseList (String path) throws URISyntaxException, IOException{
		String url = getRequestUrl(path);
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
		    Gson gson = new Gson();
		    JsonParser parser = new JsonParser();
		    JsonElement rootObject = parser.parse(jsonStr);
		    if (rootObject.isJsonArray())
		    	jsonList = gson.fromJson(rootObject, ArrayList.class);
		}
		
		return jsonList;
	}
	
	private String getResponseArticle (String path) throws URISyntaxException, IOException{
		String url = getRequestUrl(path);
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		
		HttpEntity entity = response.getEntity();
		String jsonStr = "";
		Map jsonMap = new HashMap();
		if (entity != null) {
		    BufferedReader br = new BufferedReader(
                    new InputStreamReader(entity.getContent()));
		    jsonStr = br.readLine();
		    Gson gson = new Gson();
		    JsonParser parser = new JsonParser();
		    JsonElement rootObject = parser.parse(jsonStr);
		    if (rootObject.isJsonObject()) {
		    	jsonMap = gson.fromJson(rootObject, Map.class);
		    	String articleUrl = jsonMap.get("download_url").toString();
		    	HttpGet request2 = new HttpGet(articleUrl);
				request.addHeader("User-Agent", USER_AGENT);
				HttpClient client2 = HttpClientBuilder.create().build();
				HttpResponse response2 = client2.execute(request2);
				
				HttpEntity entity2 = response2.getEntity();
				InputStream is = entity2.getContent();
				jsonStr = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		    }
		}
		
		return jsonStr;
	}
	
	public ArrayList<String> getNameFolders(String path, boolean forAdmin) throws URISyntaxException, IOException{
		ArrayList<String> directories = new ArrayList<>();
		List<Map> json = getResponseList(path);
		for (Map m : json){
			String name = m.get("name").toString();
			String ext = FilenameUtils.getExtension(name);
			if (ADMIN_EXTENSION.equals(ext) && !forAdmin)
				continue;
			directories.add(m.get("name").toString());
		}
		
		return directories;
	}
	
	public String getArticle(String path) throws URISyntaxException, IOException {
		String result = "";
		result = getResponseArticle(path);
		return result;
		
	}

}
