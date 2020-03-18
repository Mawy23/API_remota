package es.ucm.fdi.apiremota;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    private String title;
    private String authors;
    private URL infoLink;

    public BookInfo(String title, String authors, URL infoLink) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public URL getInfoLink() {
        return infoLink;
    }

    public static List<BookInfo> fromJsonResponse(String s){
        JSONObject data;
        List<BookInfo> results = new ArrayList<>();
        try {
            data = new JSONObject(s);
            JSONArray itemsArray = new JSONArray();
            JSONArray authorsArray;
            if(data.has("items")) {
                itemsArray = data.getJSONArray("items");
            }
            for(int i = 0; i < itemsArray.length(); i++) {
                JSONObject volumeInfo = itemsArray.getJSONObject(i).getJSONObject("volumeInfo");

                String title = "Unknown";
                String authors = "Unknown";
                URL infoLink = new URL("http://www.example.com/docs/resource1.html");

                if(volumeInfo.has("title")){
                    title = volumeInfo.getString("title");
                }
                if(volumeInfo.has("authors")){
                    authors = "";
                    authorsArray = volumeInfo.getJSONArray("authors");
                    for(int j = 0; j < authorsArray.length(); j++) {
                        authors += authorsArray.get(j).toString();
                        if (j == authorsArray.length() - 2) {
                            authors += " y ";
                        }
                        else if (j < authorsArray.length() - 2) {
                            authors += ", ";
                        }
                    }

                }
                if(volumeInfo.has("infoLink")){
                    infoLink = new URL(volumeInfo.getString("infoLink"));
                }

                results.add(new BookInfo(title, authors, infoLink));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
