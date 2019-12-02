package com.example.alarmv5;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    String channel = "00";


    static MediaPlayer mp;
   // static int noti_id = 0; // used main acivity notify id = 0, same shit will always use 0 since ill be displaying
    // one notifcation and motherfuckers cant make more until they dismiss it haaaaaaa














    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dismiss = new Intent(context, buttonMiss.class); // goes to dismiss button receiver and
        // do what you want when press and remove song/stop
        dismiss.putExtra("stop", "stop");



        String extra = intent.getStringExtra("Sound");

        Log.i("Sound", extra);
        if (extra.equals("cartoon")){
            mp = MediaPlayer.create(context, R.raw.cartoon);

        }else if (extra.equals("door")){
            mp = MediaPlayer.create(context, R.raw.door_bell);
        }else if (extra.equals("fire")){
            mp = MediaPlayer.create(context, R.raw.fire_truck);
        }else if(extra.equals("horn")){
            mp = MediaPlayer.create(context, R.raw.horn);
        }else if (extra.equals("morse")){
            mp = MediaPlayer.create(context, R.raw.morse);
        }else if (extra.equals("bell")){
            mp = MediaPlayer.create(context, R.raw.service_bell);
        }else if(extra.equals("whistle")){
            mp = MediaPlayer.create(context, R.raw.whistle);
        }




        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        Log.i("check", "screen is"+pm.isInteractive());


        if (!pm.isInteractive()) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "app:mine");
            wl.acquire(2000);// 300000 = 5minuten

         //   mainActiviy.power();



            //do what want
            // or when cancel alarm but if not then release



            //wl.release(); // when cancel alarm


        }

        //noti_id+=1;
        PendingIntent dismissIntent = PendingIntent.getBroadcast(context,0, dismiss, 0);


        // comes here when alarm set

        createChannel(context);

        Log.i("Reached", "here");
        NotificationCompat.Builder bob = new NotificationCompat.Builder(context,channel );
        bob.setSmallIcon(R.drawable.andi);
        bob.setContentTitle("Alarm");
        bob.setContentText("ALARM RINGING");

        //MainActivity.mp = MediaPlayer.create(context, R.raw.analog); // for sound

        bob.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        bob.addAction(R.drawable.andi, "DISMISS",
                dismissIntent);


        NotificationManagerCompat builder = NotificationManagerCompat.from(context);
        builder.notify(MainActivity.notify_id, bob.build());
        mp.start();
        mp.setLooping(true);
        










    }

    // get sound from other class



    public void createChannel(Context context){

        CharSequence cha = "Personal";  // category for your channel
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel canal = new NotificationChannel(channel, cha, importance);
        canal.setDescription("Alarm settings"); // cant be seen
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        //NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.createNotificationChannel(canal);


        // manager.createNotificationChannel(canal);



    }




}


