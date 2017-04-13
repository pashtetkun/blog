import com.google.gson.Gson;
import spark.Spark;
import spark.utils.IOUtils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by 4 on 08.11.2016.
 */

public class Main {

    public static void main(String[] args) {
        port(8080);
        staticFiles.location("/public");
        Gson gson = new Gson();

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

