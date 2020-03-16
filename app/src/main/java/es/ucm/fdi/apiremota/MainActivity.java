package es.ucm.fdi.apiremota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int BOOK_LOADER_ID = 0;
    private EditText authors;
    private EditText title;
    private RadioGroup radioGroup;

    // TODO
    // no sabemos si tiene que implementar esos métodos
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this) {
        @Override
        public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {

        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {

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


        authors = (EditText) findViewById(R.id.bookAuthors);
        title = (EditText) findViewById(R.id.bookTitle);
        radioGroup = (RadioGroup) findViewById(R.id.type);

    }

    public void searchBooks(View view){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        String queryString = authors.toString() + " " + title.toString();
        String printType = findViewById(radioButtonId).toString();

        // TODO
        // puede que tenga que ser distinto
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this)
                .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }
}
