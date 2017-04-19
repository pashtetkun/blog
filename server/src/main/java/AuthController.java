import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.jsoup.Jsoup;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Session;

public class AuthController {
	
	static Gson gson = new Gson();
	
	public AuthController() { 
		
	}
	
	public static Object handleLogin(Request req, Response res, Properties properties) throws IOException {
        return doLogin(req, res, properties);
    }
	
	public static Object handleLogout(Request req, Response res) {
		return doLogout(req, res);
	}
	
	private static String doLogin(Request req, Response res, Properties properties) throws IOException {
		
		//the username is sent here and the salt and verifier for that username is
        //is sent back to the client
        //then the auth() method is invoked
        HashMap<String, String> respMap = new HashMap<String, String>();
        
        res.type("application/json");
        
        String username = Jsoup.parse(req.queryParams("username")).text();
        String password = Jsoup.parse(req.queryParams("password")).text();
        
        String user = properties.getProperty("username", "user");
        String pwd = properties.getProperty("password", "123456");
            
		if (user.equals(username) && pwd.equals(password)) {		
			res.status(200);
			respMap.put("code", "200");
			respMap.put("status", "success");
			req.session().attribute("username", username);
			
		} else {
			res.status(200);
			respMap.put("status", "Invalid User Credentials");
			respMap.put("code", "200");
		}
		
		return gson.toJson(respMap);
	}
	
	private static String doLogout(Request req, Response res){
		Session session = req.session(false);
        if(session != null) 
        	session.invalidate();
        //res.redirect("/");
        //res.redirect("/public/index.html");
        HashMap<String, String> respMap = new HashMap<String, String>();
        
        res.type("application/json");
        res.status(200);
		respMap.put("code", "200");
		respMap.put("status", "success");
        return gson.toJson(respMap);
	}
}
