package xyz.juanrodriguez.www.wordsworthtoremember;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static xyz.juanrodriguez.www.wordsworthtoremember.MainActivity.createID;
import static xyz.juanrodriguez.www.wordsworthtoremember.MainActivity.getRandomElement;
import static xyz.juanrodriguez.www.wordsworthtoremember.MainActivity.words;

public class ReminderService extends Service {

    Timer timer;

    @Override
    public void onCreate() {
        startTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopSelf();
        stopTimer();
    }

    private void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String randWord = getRandomElement();

                sendNotification(randWord, words.get(randWord), createID(), getApplicationContext());

            }
        };
        timer = new Timer(true);
        int delay = 1000 * 5; //5 seconds
        int interval = 60000 * 60; //60 minutes
        timer.schedule(task, delay, interval);
    }

    private void stopTimer(){
        if ((timer != null)){
            timer.cancel();
        }
    }

    private void sendNotification(String word, String def, int id, Context context) {
        long when = System.currentTimeMillis();
        String randWord = word;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, DefinitionActivity.class);
        notificationIntent.putExtra("ACTUAL_WORD_DEF", def);
        notificationIntent.putExtra("ACTUAL_WORD", word);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(randWord)
                .setContentText(def).setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent);
        notificationManager.notify(id, mNotifyBuilder.build());

    }
}