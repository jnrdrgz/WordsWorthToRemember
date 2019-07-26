package xyz.juanrodriguez.www.wordsworthtoremember;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static xyz.juanrodriguez.www.wordsworthtoremember.MainActivity.saveWordsInJson;
import static xyz.juanrodriguez.www.wordsworthtoremember.MainActivity.words;

public class DefinitionActivity extends AppCompatActivity {

    TextView textVDef;
    Button delButton;
    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        String definition = getIntent().getStringExtra("ACTUAL_WORD_DEF");
        final String word = getIntent().getStringExtra("ACTUAL_WORD");

        textVDef = findViewById(R.id.textView_definition);
        textVDef.setText(definition);

        delButton = findViewById(R.id.button_deleteDef);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                words.remove(word);

                saveWordsInJson(getApplicationContext().getFilesDir().getPath());
                Toast toast = Toast.makeText(getApplicationContext(), "Word Deleted", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(DefinitionActivity.this, MainActivity.class));
                // re init app
            }});

        editButton = findViewById(R.id.button_editDef);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "You can edit a word by re-uploading it!", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(DefinitionActivity.this, MainActivity.class));
            }});

    }
}
