package es.ucm.fdi.apiremota;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.content.Context;

import android.os.Bundle;

import java.util.List;

abstract class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    private Context _context;
    // TODO
    // nos lo hemos inventado
    public static String EXTRA_QUERY = "queryString";
    public static String EXTRA_PRINT_TYPE = "print_type";

    public BookLoaderCallbacks(Context context){
        _context = context;
    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args){
        return new BookLoader(_context, args.getString(EXTRA_QUERY), args.getString(EXTRA_PRINT_TYPE));
    }

}
