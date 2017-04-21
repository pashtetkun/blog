import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;

import spark.Session;
import spark.Spark;
import spark.utils.IOUtils;

/**
 * Created by 4 on 08.11.2016.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        port(8080);
        staticFiles.location("/public");
        Gson gson = new Gson();
        
        Properties properties = new PropertiesService().getProperties();
        CloudService cloudService = new CloudService(properties);
        
        post("/doLogin", (request, response) -> {
        	return AuthController.handleLogin(request, response, properties);
        });
        
        get("/logout", (request, response) -> {
        	return AuthController.handleLogout(request, response);
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
            //List<String> nameFolders = getNameFolders("src/main/resources/repo");
        	List<String> nameFolders = cloudService.getNameFolders("");
            return gson.toJson(nameFolders);
        });

        get("/getAllSubcategories/:category", (request, response) -> {
            String category = request.params("category");
            //List<String> nameFolders = getNameFolders("src/main/resources/repo/" + category);
            List<String> nameFolders = cloudService.getNameFolders("/"+category);
            return gson.toJson(nameFolders);
        });

        get("/getAllArticles/:category/:subcategory", (request, response) -> {
            String category = request.params("category");
            String subcategory = request.params("subcategory");
            //List<String> nameFolders = getNameFolders("src/main/resources/repo/" + category + "/" + subcategory);
            List<String> nameFolders = cloudService.getNameFolders("/"+category + "/" + subcategory);
            return gson.toJson(nameFolders);
        });

        get("/getArticle/:category/:subcategory/:article", (request, response) -> {
            String category = request.params("category");
            String subcategory = request.params("subcategory");
            String article = request.params("article");
            //String pathToArticle = "src/main/resources/repo/" + category + "/" + subcategory + "/" + article;
            String pathToArticle = "/" + category + "/" + subcategory + "/" + article;
            //String content = new String(Files.readAllBytes(Paths.get(pathToArticle)));
            String content = cloudService.getArticle(pathToArticle);
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

