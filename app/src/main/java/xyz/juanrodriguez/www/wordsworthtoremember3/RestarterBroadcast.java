package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestarterBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Log.i("Broadcast Listened", "Service tried to stop");
        Log.i("Notification Service", "Service restarted ");

        context.startService(new Intent(context, NotificationService.class));
    }
}
