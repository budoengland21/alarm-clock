package com.example.alarmv5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

public class MakeNoise extends BroadcastReceiver {

    static MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        String extra = intent.getStringExtra("Sound");
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


    }
}
