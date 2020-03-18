package es.ucm.fdi.apiremota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int BOOK_LOADER_ID = 0;
    private EditText authors;
    private EditText title;
    private TextView results_Title;
    private RadioGroup radioGroup;

    private RecyclerView mRecyclerView;
    private BooksResultListAdapter mAdapter;


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
        results_Title = (TextView) findViewById(R.id.resultsTitle);
        radioGroup = (RadioGroup) findViewById(R.id.type);

        initRecyclerView();

        List<BookInfo> listaProvisional = new ArrayList<BookInfo>();

        try {
            BookInfo entrada1 = new BookInfo("titulo1", "autores1", new URL("http://www.example.com/docs/resource1.html"));
            BookInfo entrada2 = new BookInfo("titulo2", "autores2", new URL("http://www.example.com/docs/resource1.html"));
            BookInfo entrada3 = new BookInfo("titulo3", "autores3", new URL("http://www.example.com/docs/resource1.html"));
            BookInfo entrada4 = new BookInfo("titulo4", "autores4", new URL("http://www.example.com/docs/resource1.html"));
            BookInfo entrada5 = new BookInfo("titulo5", "autores5", new URL("http://www.example.com/docs/resource1.html"));

            listaProvisional.add(entrada1);
            listaProvisional.add(entrada2);
            listaProvisional.add(entrada3);
            listaProvisional.add(entrada4);
            listaProvisional.add(entrada5);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        updateBooksResultList(listaProvisional);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new BooksResultListAdapter(this, new ArrayList<BookInfo>());

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void updateBooksResultList(List<BookInfo> bookInfos) {
        if (bookInfos.size() == 0){
            results_Title.setText("No Results Found");
        }
        else {
            results_Title.setText("Results");
        }

        mAdapter.setBooksData(bookInfos);
        mAdapter.notifyDataSetChanged();
    }

    public void searchBooks(View view){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        String queryString = authors.getText().toString() + " " + title.getText().toString();
        RadioButton typeRadioButton = findViewById(radioButtonId);
        String printType = typeRadioButton.getText().toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);

         results_Title.setText("Loading...");
    }
}
