package es.ucm.fdi.apiremota;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.content.Context;

import android.os.Bundle;

abstract class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<String> {

    private Context _context;

    public BookLoaderCallbacks(Context context){
        _context = context;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args){
        return new BookLoader(_context, "hola", "adios");
    }

}
