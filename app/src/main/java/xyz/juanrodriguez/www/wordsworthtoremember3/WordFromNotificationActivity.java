package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WordFromNotificationActivity extends AppCompatActivity {
    TextView wordTextView;
    TextView defTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_from_notification);

        String word = getIntent().getStringExtra("WORD");
        String definition = getIntent().getStringExtra("WORD_DEF");

        wordTextView = findViewById(R.id.textView_word);
        defTextView = findViewById(R.id.textView_definition);
        wordTextView.setText(word);
        defTextView.setText(definition);
    }
}
