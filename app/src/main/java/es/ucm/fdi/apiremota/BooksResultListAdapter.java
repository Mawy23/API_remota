package es.ucm.fdi.apiremota;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder> {

    // TODO
    // private List<BookInfo> mBooksData = new ArrayList<>();
    private ArrayList<BookInfo> mBooksData = new ArrayList<BookInfo>();
    private LayoutInflater mInflater;

    public BooksResultListAdapter(Context context, ArrayList<BookInfo> bookInfoList){
        mInflater = LayoutInflater.from(context);
        mBooksData = bookInfoList;
    }


    public void setBooksData(List<BookInfo> data) {
        // TODO
        // mBooksData = data;

        mBooksData.clear();
        for(int i = 0; i < data.size(); i++) {
            BookInfo entry = new BookInfo(data.get(i).getTitle(), data.get(i).getAuthors(), data.get(i).getInfoLink());
            mBooksData.add(entry);
        }
    }


    @NonNull
    @Override
    public BooksResultListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksResultListAdapter.ViewHolder holder, int position) {

        // Retrieve the data for that position
        BookInfo mCurrent = mBooksData.get(position);
        // Add the data to the view
        holder.item_title_textView.setText(mCurrent.getTitle());
        holder.item_authors_textView.setText(mCurrent.getAuthors());
        holder.item_infoLink_textView.setText(mCurrent.getInfoLink().toString());
    }

    @Override
    public int getItemCount() {
        // Return the number of data items to display
        return mBooksData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final BooksResultListAdapter mAdapter;


        private TextView item_title_textView;
        private TextView item_authors_textView;
        private TextView item_infoLink_textView;


        public ViewHolder(@NonNull View itemView, BooksResultListAdapter adapter) {
            super(itemView);

            // Get the layout
            item_title_textView = itemView.findViewById(R.id.item_title);
            item_authors_textView = itemView.findViewById(R.id.item_authors);
            item_infoLink_textView = itemView.findViewById(R.id.item_infoLink);

            // Associate with this adapter
            this.mAdapter = adapter;
        }
    }
}