package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    // URL Consts
    private String URL = "http://158.69.206.233:81/";
    private String LANG = "es";
    private int SELECTED_ITEM = 0;

    // UI
    private TextView word_box;
    private TextView def_box;
    private Button b_auto;
    private Button add_button;
    private Button viewwordsButton;
    private Button clear_button;
    private Switch lang_switch;

    //DB
    private DBController dbcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        def_box = findViewById(R.id.box_definition);
        word_box = findViewById(R.id.box_word);
        b_auto = findViewById(R.id.button_auto);
        lang_switch = findViewById(R.id.lang_switch);
        add_button = findViewById(R.id.button_add);
        clear_button = findViewById(R.id.button_clear);

        //db
        dbcon = new DBController(this);

        b_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWord(word_box.getText().toString());
            }
        });

        lang_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LANG = "en";
                } else {
                    LANG = "es";
                }
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word_ = word_box.getText().toString();
                String def = def_box.getText().toString();
                Word word = new Word(word_,def);

                dbcon.addWord(word);
                Toast.makeText(MainActivity.this, "Word Added", Toast.LENGTH_SHORT).show();
            }
        });

        viewwordsButton = findViewById(R.id.button_viewwords);
        viewwordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewWordsActivity.class));
            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word_box.setText("Word");
                def_box.setText("Definition");

            }
        });
    }

    private void requestWord(String word){
        AsyncHttpClient client = new AsyncHttpClient();

        String url = URL + LANG + "/" + word;
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("Word", "Json: " + response.toString());
                try{
                    JSONArray definitions = response.getJSONArray("definitions");
                    if(definitions.length() != 1){
                        a(definitions);

                    } else {
                        def_box.setText(definitions.getString(0));
                    }
                } catch (Exception e){
                    Log.e("Word", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                Log.d("WORD", "request fail! status code:"+response);
                Log.d("WORD", "Fail response: " + response);
                Log.e("ERROR", e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void a(JSONArray defs){
        final String[] list = new String[defs.length()];
        final String[] list_complete_defs = new String[defs.length()];
        for (int i=0; i<defs.length(); i++) {
            try {
                String s = defs.getString(i);
                if(s.length() > 30){
                    s = s.substring(0, 29);
                }
                list[i] =  s;
                list_complete_defs[i] = defs.getString(i);
            }  catch (JSONException e) {
                System.out.println("e");
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What definition you want?");
        builder.setSingleChoiceItems(list, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SELECTED_ITEM = whichButton;
                    }
                });
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        System.out.println(whichButton);
                        def_box.setText(list_complete_defs[SELECTED_ITEM]);
                    }
                });
        builder.create().show();
    }
}
