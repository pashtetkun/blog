import com.google.gson.Gson;

import spark.Route;
import spark.Session;
import spark.Spark;
import spark.utils.IOUtils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.CallbackRoute;

import static spark.Spark.*;

/**
 * Created by 4 on 08.11.2016.
 */

public class Main {

    public static void main(String[] args) {
        port(8080);
        staticFiles.location("/public");
        Gson gson = new Gson();
        
        /*Config securityCfg = new SecurityConfig().build();
        Route authCallback = new CallbackRoute(securityCfg);
        get("/callback", authCallback);*/
        
        post("/doLogin", (request, response) -> {
        	return AuthController.handleLogin(request, response);
        });
        
        get("/logout", (request, response) -> {
        	return AuthController.handleLogout(request, response);
        });
        
        get("/readme", (request, response) -> {
        	return new CloudService().getReadme();
        });
        
        get("/articleEx", (request, response) -> {
        	return new CloudService().getArticleEx();
        });
        
        //ensure user is logged in to have access to protected routes
        before((request, response) -> {
        	String uri = request.uri();   	
        	boolean isLoginAction = "/doLogin".equals(uri);
        	
            Session session = request.session(true);
            boolean auth = session.attribute("username") != null;
            
            if(!auth && !isLoginAction) {
                halt(401);
            }
        });

        get("/getAllCategories", (request, response) -> {
            List<String> nameFolders = getNameFolders("src/main/resources/repo");
            return gson.toJson(nameFolders);
        });

        get("/getAllSubcategories/:category", (request, response) -> {
            String category = request.params("category");
            List<String> nameFolders = getNameFolders("src/main/resources/repo/" + category);
            return gson.toJson(nameFolders);
        });

        get("/getAllArticles/:category/:subcategory", (request, response) -> {
            String category = request.params("category");
            String subcategory = request.params("subcategory");
            List<String> nameFolders = getNameFolders("src/main/resources/repo/" + category + "/" + subcategory);
            return gson.toJson(nameFolders);
        });

        get("/getArticle/:category/:subcategory/:article", (request, response) -> {
            String category = request.params("category");
            String subcategory = request.params("subcategory");
            String article = request.params("article");
            String pathToArticle = "src/main/resources/repo/" + category + "/" + subcategory + "/" + article;
            String content = new String(Files.readAllBytes(Paths.get(pathToArticle)));
            return gson.toJson(content);
        });

        get("/*", (request, response) ->
                IOUtils.toString(Spark.class.getResourceAsStream("/public/index.html")));
    }

    private static ArrayList<String> getNameFolders(String path) {
        ArrayList<String> directories = new ArrayList<>();
        File[] listOfDir = new File(path).listFiles();
        for(File dir : listOfDir){
            if(!dir.getName().contains(".gitkeep")){
                directories.add(dir.getName());
            }
        }
        return directories;
    }
}

