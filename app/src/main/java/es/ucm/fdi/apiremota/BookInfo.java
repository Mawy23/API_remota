package es.ucm.fdi.apiremota;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setInfoLink(URL infoLink) {
        this.infoLink = infoLink;
    }

    public static List<BookInfo> fromJsonResponse(String s){
        JSONObject data = null;
        List<BookInfo> results = new ArrayList<BookInfo>();
        try {
            data = new JSONObject(s);
            JSONArray itemsArray = new JSONArray();
            if(data.has("items")) {
                itemsArray = data.getJSONArray("items");
            }
            for(int i = 0; i < itemsArray.length(); i++) {
                JSONObject volumeInfo = itemsArray.getJSONObject(i).getJSONObject("volumeInfo");

                String title = "Not found";
                String authors = "Not found";
                URL infoLink = new URL("http://www.example.com/docs/resource1.html");

                if(volumeInfo.has("title")){
                    title = volumeInfo.getString("title");
                }
                if(volumeInfo.has("authors")){
                    authors = volumeInfo.getString("authors");
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
