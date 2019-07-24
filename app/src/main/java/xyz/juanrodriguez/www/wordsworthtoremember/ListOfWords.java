package xyz.juanrodriguez.www.wordsworthtoremember;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Map;

import static xyz.juanrodriguez.www.wordsworthtoremember.MainActivity.words;

public class ListOfWords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);

        for (Map.Entry<String, String> entry : words.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
