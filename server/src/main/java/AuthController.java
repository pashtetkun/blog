import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import org.jsoup.Jsoup;

import com.google.gson.Gson;

public class AuthController {
	
	static Gson gson = new Gson();
	
	public AuthController() { 
		
	}
	
	public static Object handleLogin(Request req, Response res) {
        return doLogin(req, res);
    }
	
	private static String doLogin(Request req, Response res) {
		
		//the username is sent here and the salt and verifier for that username is
        //is sent back to the client
        //then the auth() method is invoked
        HashMap<String, String> respMap = new HashMap<String, String>();
        
        res.type("application/json");
        
        String username = Jsoup.parse(req.queryParams("username")).text();
        String password = Jsoup.parse(req.queryParams("password")).text();
            
		if ("user".equals(username) && "123456".equals(password)) {		
			res.status(200);
			respMap.put("code", "200");
			respMap.put("status", "success");
			req.session().attribute("username", username);
			
		} else {
			res.status(401);
			respMap.put("status", "Invalid User Credentials");
			respMap.put("code", "401");
		}
		
		return gson.toJson(respMap);
	}
}
