package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EditWordActivity extends AppCompatActivity {

    private TextView word_box;
    private TextView def_box;
    private Button save_button;

    //DB
    private DBController dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        //db
        dbcon = new DBController(this);

        def_box = findViewById(R.id.box_definition);
        word_box = findViewById(R.id.box_word);
        save_button = findViewById(R.id.button_save);

        List<Word> wordList = dbcon.getAllWords();

        for (int i = 0; i < wordList.size(); i++) {
            if( wordList.get(i).get_id() ==  Integer.parseInt(getIntent().getExtras().getString("WORD_ID"))) {
                Word word = wordList.get(i);
                word_box.setText(word.get_word());
                def_box.setText(word.get_definition());
            }
        }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Word> wordList = dbcon.getAllWords();

                for (int i = 0; i < wordList.size(); i++) {
                    if( wordList.get(i).get_id() ==  Integer.parseInt(getIntent().getExtras().getString("WORD_ID"))) {
                        Word word = wordList.get(i);
                        word.set_word(word_box.getText().toString());
                        word.set_definition(def_box.getText().toString());
                        dbcon.updateWord(word);
                    }
                }

                Toast.makeText(EditWordActivity.this, "Word Updated", Toast.LENGTH_SHORT).show();
                Intent viewWordsActivity = new Intent(EditWordActivity.this, ViewWordsActivity.class);
                startActivity(viewWordsActivity);
            }
        });
    }
}
