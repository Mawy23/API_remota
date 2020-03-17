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

    public BookInfo(){
        try {
            title = "Not found";
            authors = "Not found";
            infoLink = new URL("");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static List<BookInfo> fromJsonResponse(String s){
        JSONObject data = null;
        List<BookInfo> results = new ArrayList<BookInfo>();
        try {
            data = new JSONObject(s);
            JSONArray itemsArray = data.getJSONArray("items");
            for(int i = 0; i < itemsArray.length(); i++) {
                results.add(new BookInfo());
                JSONObject volumeInfo = itemsArray.getJSONObject(i).getJSONObject("volumeInfo");
                if(volumeInfo.has("title")){
                    results.get(i).title = volumeInfo.getString("title");
                }
                if(volumeInfo.has("authors")){
                    results.get(i).authors = volumeInfo.getString("authors");
                }
                if(volumeInfo.has("infoLink")){
                    results.get(i).infoLink = new URL(volumeInfo.getString("infoLink"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }





}
