package xyz.juanrodriguez.www.wordsworthtoremember;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
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
    Button autoButton;

    TextView wordTextV;
    TextView defTextV;
    int MID = 1;
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

        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
//        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 30000, pendingIntent);
        am.set(AlarmManager.RTC_WAKEUP, 30000, pendingIntent);

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

        autoButton = findViewById(R.id.button_autodef);
        autoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Feature not available yet", Toast.LENGTH_SHORT);
                toast.show();
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

    public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                    context).setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Alarm Fired")
                    .setContentText("Events to be Performed").setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationManager.notify(MID, mNotifyBuilder.build());
            MID++;

            System.out.println("notifier created");
        }

    }
}
