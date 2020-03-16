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

    public static List<BookInfo> fromJsonResponse(String s){
        JSONObject data = null;
        List<BookInfo> results = new ArrayList<BookInfo>();
        try {
            data = new JSONObject(s);
            JSONArray itemsArray = data.getJSONArray("items");
            for(int i = 0; i < itemsArray.length(); i++) {
                results.get(i).title = itemsArray.getJSONObject(i).getString("volumeInfo.title");
                // TODO
                // esto puede petar
                results.get(i).authors = itemsArray.getJSONObject(i).getString("volumeInfo.authors");
                results.get(i).infoLink = new URL(itemsArray.getJSONObject(i).getString("volumeInfo.infoLink"));
            }
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
        return results;
    }





}
