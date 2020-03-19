package es.ucm.fdi.apiremota;

import androidx.annotation.NonNull;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.content.Context;

import android.os.Bundle;

import java.util.List;

class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    private Context _context;
    // TODO
    // ¿Está bien esto?
    private MainActivity _mainActivity;

    public static String EXTRA_QUERY = "queryString";
    public static String EXTRA_PRINT_TYPE = "print_type";

    public BookLoaderCallbacks(Context context){
        _context = context;
        _mainActivity = (MainActivity) context;
    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args){
        return new BookLoader(_context, args.getString(EXTRA_QUERY), args.getString(EXTRA_PRINT_TYPE));
    }

    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        // TODO
        // ¿Está bien esto?
        _mainActivity.updateBooksResultList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {

    }

}
