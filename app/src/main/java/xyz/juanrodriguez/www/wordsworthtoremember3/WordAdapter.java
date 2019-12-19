package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private List<Word> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    WordAdapter(Context context, List<Word> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word word = mData.get(position);
        if(word != null) {
            holder.wordTextView.setText(word.get_word());
            holder.definitionTextView.setText(word.get_definition());
            holder.definitionTextView.setVisibility(View.GONE);
        }


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView wordTextView;
        TextView definitionTextView;
        Button editButton;
        Button deleteButton;
        LinearLayout wordsLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.tvWord);
            definitionTextView = itemView.findViewById(R.id.tvDefinition);
            editButton = itemView.findViewById(R.id.button_edit);
            deleteButton = itemView.findViewById(R.id.button_delete);
            wordsLayout = itemView.findViewById(R.id.layout_words);


            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            wordsLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) { //mClickListener.onItemClick(view, getAdapterPosition());
                switch (view.getId()) {
                    case R.id.button_edit:
                        mClickListener.onEdit(view, getAdapterPosition());
                        break;
                    case R.id.button_delete:
                        mClickListener.onDelete(view, getAdapterPosition());
                        break;
                    case R.id.layout_words:
                        mClickListener.onTouchWord(view, getAdapterPosition());
                        break;
                }
            }
        }
    }

    // convenience method for getting data at click position
    Word getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        //void onItemClick(View view, int position);

        void onTouchWord(View view, int p);
        void onEdit(View view,int p);
        void onDelete(View view, int p);
    }
}