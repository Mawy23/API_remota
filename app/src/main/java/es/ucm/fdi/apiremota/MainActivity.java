package es.ucm.fdi.apiremota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    final int BOOK_LOADER_ID = 0;

    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this) {
        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
           // mAdapter.setData(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }

    }
}
