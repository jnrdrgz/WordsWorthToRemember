package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    //DB
    private DBController dbcon;

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            //to do
            //startMyOwnForeground();
        } else {
            startForeground(1, new Notification());
            Log.i("NOTIFSERVICE", "STARTED FOREGROUND ");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        startTimer();
        Log.i("NOTIFSERVICE", "TIMER STARTED");
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopTimerTask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, RestarterBroadcast.class);
        this.sendBroadcast(broadcastIntent);
    }

    private Timer timer;
    private TimerTask timerTask;
    public void startTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                sendNotification(getApplicationContext());
                Log.i("NOTIFSERVICE", "TIMER TASK");
            }
        };
        Log.i("NOTIFSERVICE", "RUNNING ");

        int minutes = 60;
        timer.schedule(timerTask, 0,minutes*60*1000);

    }

    public void stopTimerTask(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendNotification(Context context) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        dbcon = new DBController(this);
        Random rand = new Random();
        int id = rand.nextInt();
        List<Word> allWords = dbcon.getAllWords();
        Word word = allWords.get(rand.nextInt(allWords.size()-1));

        //Intent notificationIntent = new Intent(context, DefinitionActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
        //  notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(word.get_word())
                .setContentText(word.get_definition()).setSound(alarmSound)
                .setAutoCancel(true).setWhen(when);
        //.setContentIntent(pendingIntent);
        notificationManager.notify(id, mNotifyBuilder.build());
        Log.i("NOTIFSERVICE", "NOTIFICATION SENDED");
    }
}
