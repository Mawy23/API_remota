package es.ucm.fdi.apiremota;
import android.content.AsyncTaskLoader;
import android.content.Context;

public class BookLoader extends AsyncTaskLoader<String> {

    private String _queryString;
    private String _printType;

    public BookLoader(Context context, String queryString, String printType){
        super(context);
        _queryString = queryString;
        _printType = printType;
    }

    public String loadInBackground(){
        // Devolver resultado de operacion volume.list del API de GoogleBooks con los atributos
        return null;
    }

    protected void onStartLoading(){
        forceLoad();
    }

    public String getBookInfoJson(String queryString, String printType){
        // Hacer la peticion al servicio usando HttpURLConnection
        return null;
    }



}
