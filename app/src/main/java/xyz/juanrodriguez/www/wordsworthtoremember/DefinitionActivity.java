package xyz.juanrodriguez.www.wordsworthtoremember;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DefinitionActivity extends AppCompatActivity {

    TextView textVDef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        String definition = getIntent().getStringExtra("ACTUAL_WORD_DEF");

        textVDef = findViewById(R.id.textView_definition);
        textVDef.setText(definition);

    }
}
