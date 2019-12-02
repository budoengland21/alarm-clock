package com.example.alarmv5;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    RelativeLayout linear;
  //  FloatingActionButton flt;v
    TimePickerDialog pick;
    Calendar current;
    int hour,min;
    int id = 0;
    int tag = 1;
    int top = 350;


    PendingIntent make;
    Intent i;
    PendingIntent pi;
    AlarmManager boss;
    MediaPlayer save ;
    String soundPick; // string for intent to be picked

    static int sound_id = 9; // int to cancel dismiss sound, all same request code

    String channel_id = "00";
    static int notify_id=0; // int of each alarm notification based on first come first serve

    int alarm_id = 0;



    int hower; // saved
    int mino;
    int date;


   // String track= "";






    public void add(View view){
        FloatingActionButton flt = findViewById(R.id.flt);
        flt.show();
        pick = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.i("hr", "hr"+ hourOfDay);
                Log.i("min", "min"+ minute);
                 run(hourOfDay,minute);
                 System.out.println(hourOfDay);

                  startAlarmSys(hourOfDay, minute); // BEGIN ALARM SYSTEM WITH DEFAULT RINGTONE





            }
        },hour, min, false)
        ;
        pick.show();



    }
    public void startAlarmSys(int h, int m) {
        hower = h;
        mino = m;
        Calendar v = Calendar.getInstance();

        v.set(Calendar.HOUR_OF_DAY, h);
       // v.set(Calendar.DATE, date );
        v.set(Calendar.MINUTE, m);
        v.set(Calendar.SECOND, 0);
      //  Log.i("time", "alarm" + v);
        //v.add(Calendar.)
        if (v.before(Calendar.getInstance())){
            v.add(Calendar.DATE, 1); // next day


        }

        boss = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // createChannel();

        Log.i("pass", "boss");

        // try passing intent of alarmmanager
        i = new Intent(this, AlarmReceiver.class);

      i.putExtra("Sound", getsound()); // default sound




        Log.i("pass", "intent");
        alarm_id+=1;

        pi = PendingIntent.getBroadcast(this, alarm_id, i, 0); // make a new Pending action
        Log.i("pass", "pi");

        boss.setRepeating(AlarmManager.RTC_WAKEUP, v.getTimeInMillis(),24*60*60*1000, pi);
       // boss.setExact(AlarmManager.RTC_WAKEUP, v.getTimeInMillis(), pi); // pending intent invoked when timer reaches what u want
        // intent for sound





        Log.i("pass", "exact");

        Log.i("completo", "del");

    } // download virtual device +++++++++++++++++++++++++++????????????????????????????????????????????
//    public void power(){
//        setTurnScreenOn(true);
//    }
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



    public void Invokeintent() {
        // happens every time change sound
        Calendar v = Calendar.getInstance();
        if (v.before(Calendar.getInstance())){
            v.add(Calendar.DATE, 1); // next day
        }
        v.set(Calendar.HOUR_OF_DAY, hower);
        v.set(Calendar.MINUTE, mino);
        v.set(Calendar.SECOND, 0);
        //v.set(Calendar.DATE, date);
        i = new Intent(this, AlarmReceiver.class);
        i.putExtra("Sound", getsound());
        Log.i("Alarmid", "is"+alarm_id);
        pi=  PendingIntent.getBroadcast(this, alarm_id ,i, 0);
        boss.setRepeating(AlarmManager.RTC_WAKEUP, v.getTimeInMillis(),24*60*60*1000, pi);
    }

    public String getsound(){
      if (soundPick == null){
          getRing(0);

        }
      return soundPick;
    }



    // change pm am__________________________________________________________________________________
    public void  run(int hr, int mino) {
        String p ;

        if (hr == 0){
            hr = 12;
            p="am"; }
        else if (hr == 12){
            p = "pm";
        }
        else if (hr > 12) {
            hr = hr-12;

            p = "pm";

        } else {
            p = "am";
        }
        start(p,hr, mino);

    }
    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            longClick();
            return true;
        }
    };
    // INVOKED WHEN A BUTTON IS CLICKED)___________________________________________________________
    View.OnClickListener onClickListener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (tag == 1){tag = 0;
                v.setBackground(getDrawable(R.drawable.offbtn));
                v.setTag(tag);
                int store = v.getId();  // since id and alarm_id are same
                cancel_alarm(store);

            }
            else{tag = 1;

                Log.i("Pressed", "ON");
                v.setBackground(getDrawable(R.drawable.onbtn));
                v.setTag(tag);
                int savi = v.getId();
                Button touch = (Button) v;

               String val = (String) touch.getText();
                String set;
                String hr ;
                String zone;
                val = val.replace(":", "");

                if (val.length() == 6){ hr = val.substring(0, 2);
                    set = val.substring(2,4);
                    zone = val.substring(4,6);}

                else{hr = val.substring(0,1);
                    set = val.substring(1,3);
                    zone = val.substring(3,5);}
                // check if pm/a,
                int get_hr  = Integer.parseInt(hr);
                int get_min = Integer.parseInt(set);
                if (zone == "pm") {
                    if (get_hr != 12) {
                        get_hr += 12;// ;
                    }
                }
                else if (zone == "am" && get_hr == 12){
                    get_hr-=12;





                }


               re_enable_alarm(get_hr, get_min, savi);




            }
    }
    };

    // cancel selected alarm
    public void cancel_alarm(int store){
        // id and alarm id are same so yeah btn clicked cancels alarm
        AlarmManager cancelAlarm =(AlarmManager)  getSystemService(Context.ALARM_SERVICE);
        Intent im = new Intent(this, AlarmReceiver.class);
        PendingIntent pin = PendingIntent.getBroadcast(this, store, im, 0);
        pin.cancel();
        cancelAlarm.cancel(pin);

    }
    // activate alarm
    public void re_enable_alarm(int h, int m, int store){

        Calendar re = Calendar.getInstance();
        if (re.before(Calendar.getInstance())){
            re.add(Calendar.DATE, 1); // next day
        }
        re.set(Calendar.HOUR_OF_DAY, h);
        re.set(Calendar.MINUTE, m);
        re.set(Calendar.SECOND, 0);
        //re.set(Calendar.DATE, date);

        AlarmManager enableAlarm =(AlarmManager)  getSystemService(Context.ALARM_SERVICE);
        Intent enable = new Intent(this, AlarmReceiver.class);
        PendingIntent p2 = PendingIntent.getBroadcast(this, store, enable, 0);
        enable.putExtra("Sound", getsound());
        enableAlarm.setRepeating(AlarmManager.RTC_WAKEUP, re.getTimeInMillis(),24*60*60*1000, p2);


    }


    //__________________________________________________________________________________________________
    // FOR RINGTONE ADD TO DIFFERENT CLASS
    public void getRing(int choice) {

        if (choice == 0) {
            soundPick = "cartoon";


        }else if (choice == 1){

            soundPick = "door";

        }else if (choice == 2){
            soundPick = "fire";

        }else if (choice == 3){
            soundPick = "horn";
        }else if (choice == 4){
            soundPick = "morse";
        }else if (choice == 5){

            soundPick = "bell";
        }else  {

            soundPick="whistle";

        }

    }
//___________________________________________________________________________________________________
    // when user clicks for long, initiates a dialog box

    public void longClick(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);



        build.setTitle("CHOOSE RINGTONE");
        String[] x = {"Cartoon ","Loud click", "bell", "fire truck", "Horn", "Morse code", "Bell 2", "Loud Whistle"};
        int checked = 0;
        build.setSingleChoiceItems(x, checked, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        getRing(which);

                        pi.cancel();
                        boss.cancel(pi);// cancels since new sound
                        Invokeintent();
                    }

                });
        build.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // creates a dialog box and builder added to it
       /* Intent sd = new Intent(this, MakeNoise.class);
        sd.putExtra("Sound", getsound());
         make = PendingIntent.getBroadcast(this, alarm_id, sd, 0);
        try {
            make.send();

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        AlertDialog dialog = build.create();
        dialog.show();



    }

// layout for buttons_____________________________________________________________________________
    public void start(String s, int hr, int min){
        id+=1;
        //tag+=1;
        String mino;
        if (min < 10){
            mino = "0"+min;
        }
        else{mino = Integer.toString(min);}

        Button b = new Button(this);
        b.setOnClickListener(onClickListener);
        b.setOnLongClickListener(onLongClickListener);


         b.setGravity(Gravity.CENTER);
        b.setText(Integer.toString(hr)+":"+mino+s);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(980,
                300);
        if (id > 1){

            params.setMargins(0,top,0,0);
            top+=350;
            params.addRule(RelativeLayout.ALIGN_TOP);
        }


        b.setLayoutParams(params);
        b.setTextSize(32f);
        b.setTag(tag);
        b.setId(id);





        b.setBackground(getDrawable(R.drawable.onbtn));

        linear.addView(b);
//
    }
//_______________________________________________________________________________________________





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear = findViewById(R.id.linear);
        current = Calendar.getInstance();
        hour = current.get(Calendar.HOUR_OF_DAY);
        min = current.get(Calendar.MINUTE);

      //  MediaPlayer save = null;




       // FloatingActionButton flt = findViewById(R.id.flt);



    }
}
