import java.util.HashMap;

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
        HashMap<String, String> respMap;
        
        res.type("application/json");
        
        
        String username = Jsoup.parse(req.queryParams("username")).text();
        
        
		if (username != null && !username.isEmpty()) {

			/*server = new SRP6JavascriptServerSessionSHA256(CryptoParams.N_base10, CryptoParams.g_base10);

			gen = new ChallengeGen(email);

			respMap = gen.getChallenge(server);*/
			
			respMap = new HashMap<String, String>();

			if (respMap != null) {
				res.status(200);
				respMap.put("code", "200");
				respMap.put("status", "success");
				return gson.toJson(respMap);
			}
		}
		respMap = new HashMap<>();

		res.status(401);
		respMap.put("status", "Invalid User Credentials");
		respMap.put("code", "401");
		return gson.toJson(respMap);
	}
}
