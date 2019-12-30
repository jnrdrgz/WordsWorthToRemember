package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ViewWordsActivity extends AppCompatActivity implements WordAdapter.ItemClickListener {

    //DB
    private DBController dbcon;
    private RecyclerView rv;
    private ArrayList<String> data;
    private WordAdapter adapter;
    private RecyclerTouchListener recyclerTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_words);

        //db
        dbcon = new DBController(this);

        data = new ArrayList<>();

        List<Word> allWords = dbcon.getAllWords();
        for(int i = 0; i < allWords.size(); i++){
            Word w = allWords.get(i);
            data.add(w.get_word());
            System.out.println(w.get_word());
        }

        //ui
        rv = findViewById(R.id.recyclerView_words);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordAdapter(this, allWords);
        adapter.setClickListener(this);

        recyclerTouchListener = new RecyclerTouchListener(this, rv)
                .setSwipeOptionViews(R.id.delete, R.id.edit)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        if (viewID == R.id.delete) {
                            Word w = adapter.getItem(position);
                            dbcon.deleteWord(w);
                            finish();
                            startActivity(getIntent());
                            Toast.makeText(ViewWordsActivity.this, "Word Deleted", Toast.LENGTH_SHORT).show();

                        } else if (viewID == R.id.edit) {
                            Word w = adapter.getItem(position);
                            Intent editWordIntent = new Intent(ViewWordsActivity.this, EditWordActivity.class);
                            System.out.println(w.get_id());
                            editWordIntent.putExtra("WORD_ID", Integer.toString(w.get_id()));
                            startActivity(editWordIntent);
                        }
                    }
                });

        rv.addOnItemTouchListener(recyclerTouchListener);

        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onTouchWord(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position).get_word() + " on row number " + position, Toast.LENGTH_SHORT).show();
        if (view.findViewById(R.id.tvDefinition).getVisibility() == View.VISIBLE) {
            view.findViewById(R.id.tvDefinition).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.tvDefinition).setVisibility(View.VISIBLE);
        }
    }

}