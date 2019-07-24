package xyz.juanrodriguez.www.wordsworthtoremember;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Map;

import static xyz.juanrodriguez.www.wordsworthtoremember.MainActivity.words;

public class ListOfWords extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);

        for (Map.Entry<String, String> entry : words.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());

            final String def = entry.getValue();
            final Button b = new Button(this);
            b.setText(entry.getKey());

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(def);
                    Intent intent = new Intent(ListOfWords.this, DefinitionActivity.class);
                    intent.putExtra("ACTUAL_WORD_DEF", def);
                    startActivity(intent);
                }});

            LinearLayout ll = (LinearLayout)findViewById(R.id.layout_buttons_words);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(b, lp);
        }
    }
}
