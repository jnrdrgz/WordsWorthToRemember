package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // URL Consts
    private String URL = "http://158.69.206.233:81/";
    private String LANG = "es";
    private int SELECTED_ITEM = 0;

    // UI
    private Button add_button;
    private Button viewwordsButton;
    private Button settingsButton;

    //DB
    private DBController dbcon;

    //Notification Service
    Intent mServiceIntennt;
    private NotificationService mNotificationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ui
        viewwordsButton = findViewById(R.id.button_viewwords);
        add_button = findViewById(R.id.button_add);
        settingsButton = findViewById(R.id.button_settings);
        //db
        dbcon = new DBController(this);

        mNotificationService = new NotificationService();
        mServiceIntennt = new Intent(this, mNotificationService.getClass());
        if (!isMyServiceRunning(mNotificationService.getClass())) {
            startService(mServiceIntennt);
            Log.i("NOTIFSERVICE", "SERVICE STARTED");
        }

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewWordActivity.class));
            }
        });


        viewwordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewWordsActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    @Override
    protected void onDestroy(){
        stopService(mServiceIntennt);
        super.onDestroy();
    }
}
