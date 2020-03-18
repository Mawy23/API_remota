package es.ucm.fdi.apiremota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    private List<BookInfo> mBooksData = new ArrayList<>();

    // TODO
    // ¿Hay que pasarle this?
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this);


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
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new BooksResultListAdapter(this, new ArrayList<BookInfo>());

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnItemClickListener(new BooksResultListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Uri webPage = Uri.parse(mBooksData.get(position).getInfoLink().toString());

                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);

                String title = getResources().getString(R.string.chooser_title);
                Intent chooser = Intent.createChooser(intent, title);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });
    }

    public void updateBooksResultList(List<BookInfo> bookInfos) {
        mBooksData = bookInfos;

        if (bookInfos.size() == 0){
            results_Title.setText(R.string.no_results_found_title);
        }
        else {
            results_Title.setText(R.string.results_found_title);
        }

        mAdapter.setBooksData(bookInfos);
        mAdapter.notifyDataSetChanged();
    }

    public void searchBooks(View view){
        // The user didn't type anything
        if (authors.getText().toString().equals("") && title.getText().toString().equals("")) {
            results_Title.setText(R.string.no_data_results_title);
            results_Title.setGravity(17);
        }
        else {
            int radioButtonId = radioGroup.getCheckedRadioButtonId();
            String queryString = "";
            if (authors.getText().toString().equals("") && !title.getText().toString().equals("")) {
                queryString = title.getText().toString();
            }
            else if (!authors.getText().toString().equals("") && title.getText().toString().equals("")) {
                queryString = authors.getText().toString();
            }
            else if (!authors.getText().toString().equals("") && !title.getText().toString().equals("")) {
                queryString = authors.getText().toString() + " " + title.getText().toString();
            }

            RadioButton typeRadioButton = findViewById(radioButtonId);
            String printType = typeRadioButton.getText().toString();

            Bundle queryBundle = new Bundle();
            queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
            queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
            LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);

            results_Title.setText(R.string.results_loading_title);
        }
    }
}
