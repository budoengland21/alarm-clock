/*
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


//import android.support.v4.app.NotificationCompat;


public class AlertReciever extends BroadcastReceiver {
     String channel_id = "00";
     int notify_id = 0;



    @Override
    public void onReceive(Context context, Intent intent) {
        Notification.Builder builder = new Notification.Builder(this, channel_id);
        builder.setContentTitle("SimpliAlarm");
        //builder.setPriority; // depreciated code
        NotificationManagerCompat see = NotificationManagerCompat.from(this);

        see.notify(notify_id, builder.build());




    }


}
// setUp channel since not api's are above 26+ (f android updates )


*/
