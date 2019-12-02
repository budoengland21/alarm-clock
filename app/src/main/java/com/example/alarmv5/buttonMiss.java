package com.example.alarmv5;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class buttonMiss extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
       //Log.i("camw", "lasr");
        NotificationManager check = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        check.cancel(MainActivity.notify_id);
        //MainActivity.mp.stop();
        // CANCEL ALARM THAT WAS MADE, REQUEST CODE SHOULD BE SAME
        // fix alarm_id, shouldn't be 5
        AlarmManager cancelAlarm =(AlarmManager)  context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        cancelAlarm.cancel(pi);
        AlarmReceiver.mp.stop();





    }
}


