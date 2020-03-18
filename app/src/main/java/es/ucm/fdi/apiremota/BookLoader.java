package es.ucm.fdi.apiremota;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private String _queryString;
    private String _printType;

    public BookLoader(Context context, String queryString, String printType) {
        super(context);
        _queryString = queryString;
        _printType = printType;
    }

    public List<BookInfo> loadInBackground() {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            String uri = getBookInfoJson(_queryString, _printType);
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int response = connection.getResponseCode();
            Log.d("BookLoader", "The response is: " + response);
            inputStream = connection.getInputStream();
            String contentAsString = convertInputToString(inputStream, 500);
            return BookInfo.fromJsonResponse(contentAsString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String convertInputToString(InputStream stream, int len) {
        try {
            /*
            Reader reader = null;
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
            */
            // TODO
            // esto lo he visto en internet para poder tener toda la respuesta en un String, y no solo 500 caracteres
            BufferedReader r = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuilder total = new StringBuilder();
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            return total.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onStartLoading() {
        forceLoad();
    }

    public String getBookInfoJson(String queryString, String printType) {
        // Base URL for the Books API.
        final String BOOK_BASE_URL =
                "https://www.googleapis.com/books/v1/volumes?";

        // Parameter for the search string
        final String QUERY_PARAM = "q";
        // Parameter to limit search results.
        final String MAX_RESULTS = "maxResults";
        // Parameter to filter by print type
        final String PRINT_TYPE = "printType";

        // Build up the query URI, limiting results to 5 printed books.
        Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "40")
                .appendQueryParameter(PRINT_TYPE, printType)
                .build();

        return builtURI.toString();
    }


}
