package xyz.juanrodriguez.www.wordsworthtoremember;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    static Map<String, String> words = new HashMap<>();

    Button addButton;
    Button viewButton;

    TextView wordTextV;
    TextView defTextV;

    public static void saveWordsInJson(String path){
        JSONObject words_js_obj = new JSONObject(words);

        try (FileWriter file = new FileWriter(path + "words.json")) {

            file.write(words_js_obj.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("inittt");
        System.out.println(getApplicationContext().getFilesDir().getPath());
        try (FileReader r = new FileReader(getApplicationContext().getFilesDir().getPath() + "words.json")){
            System.out.println("loaded json file");
            StringBuilder f = new StringBuilder();
            int i;
            while((i=r.read()) != -1)
                f.append((char)i);
            r.close();

            System.out.println(f);

            String w = f.toString();
            words = toMap(w);

        } catch (IOException e) {
            System.out.println("error loading json file");
            e.printStackTrace();
        }

        addListenerButton();

    }

    public void addListenerButton(){
        addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                wordTextV = findViewById(R.id.box_word);
                defTextV = findViewById(R.id.box_definition);
                System.out.println(wordTextV.getText().toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Do you want to add the word " + wordTextV.getText().toString() + "?");
                //builder.setIcon(R.drawable.ic_launcher);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        words.put(wordTextV.getText().toString(), defTextV.getText().toString());

                        saveWordsInJson(getApplicationContext().getFilesDir().getPath());

                        Toast toast = Toast.makeText(getApplicationContext(), "Word Added", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        viewButton = findViewById(R.id.button_view);
        viewButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListOfWords.class));

            }
        });
    }

    Map<String, String> toMap(String s){
        Map<String, String> m = new HashMap<>();
        String ms = s.replace("{","").replace("{", "").replace("\"", "").replace("}", "");
        System.out.println(ms);

        for(String w : ms.split(",",0)){
            System.out.println(w);

            String[] arr = w.split(":", 0);
            m.put(arr[0], arr[1]);
        }

        return m;
    }
}
