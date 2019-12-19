package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewWordsActivity extends AppCompatActivity implements WordAdapter.ItemClickListener {

    //DB
    private DBController dbcon;
    private RecyclerView rv;
    private ArrayList<String> data;
    private WordAdapter adapter;

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

        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position).get_word() + " on row number " + position, Toast.LENGTH_SHORT).show();
        if (view.findViewById(R.id.tvDefinition).getVisibility() == View.VISIBLE) {
            view.findViewById(R.id.tvDefinition).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.tvDefinition).setVisibility(View.VISIBLE);
        }
    }

}
