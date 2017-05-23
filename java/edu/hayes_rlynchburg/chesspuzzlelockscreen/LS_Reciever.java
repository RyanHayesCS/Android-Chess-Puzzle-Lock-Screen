package edu.hayes_rlynchburg.chesspuzzlelockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;


public class LS_Reciever extends BroadcastReceiver {
    private static final String TAG = "TAGs";

    /*public static List<Puzzle> puzzleList;
    public static String initialLayout;
    public static String finalLayout_;*/

    //Called when we recieve something that the app is listening for
    @Override
    public void onReceive(Context ctxt, Intent intent) {
        Log.d(TAG, "LS_Reciever onReceive called");

        //Create a new PhoneListener for handling when you receive a phone call
        PhoneListener phoneListener = new PhoneListener();

        //used in determining if the phone is ringing or not
        TelephonyManager telephonyManager = (TelephonyManager)ctxt.getSystemService(Context.TELEPHONY_SERVICE);

        //register our phoneListener to listen for when the phone changes state
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        //if the screen turns off
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            Log.d(TAG, "LS_Reciever Screen Off");
            //start the alarm lockscreenActivity
            startAlarmAndLockscreen(ctxt);
        }

        //if the screen turns on
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            Log.d(TAG, "LS_Reciever Screen On");
        }

        //if the phone booted up
        else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) )
        {
            Log.d(TAG, "LS_Reciever Boot Complete");
            //start the lockscreen service and the notification service
            ctxt.startService(new Intent(ctxt, LS_Service.class));
            ctxt.startService(new Intent(ctxt, NotificationListener.class));
            startAlarmAndLockscreen(ctxt);
        }

        //when a notification is received.  (sent by this app)
        else if(intent.getAction().equals("NotificationReceived"))
        {
            //if the user wanted to see notifications
            if(LS_Service.showNotifications_) {
                Log.d(TAG, "Notification Received");

                //if the app is on screen
                if (LS_Service.lockscreenActivity_ != null)
                {
                    //clear out all notifications currently displayed
                    NewLockscreen.listOfNotifications_.removeAllViewsInLayout();

                    //convert the notifications to views to be displayed on the screen
                    for (int i = 0; i < NotificationListener.numberOfNotifications; ++i)
                    {
                        //convert a notification to a remote view
                        //RemoteViews (I think) are views that can displayed in another proccess
                        RemoteViews remoteView = LS_Service.notifications_[i].contentView;

                        //inflate the remote view and save it as a view to be displayed
                        View notificationView = remoteView.apply(LS_Service.lockscreenActivity_, NewLockscreen.scrollView_);
                        notificationView.setClickable(true);

                        //generate a random id for the view
                        notificationView.setId(View.generateViewId());

                        //handles when a notification is pressed
                        notificationView.setOnClickListener(NewLockscreen.notifClicker);

                        //add it to the listOfNotifications_ to be displayed
                        NewLockscreen.listOfNotifications_.addView(notificationView, i, NewLockscreen.layoutParamsOfLS_);
                    }
                }
            }
        }

        //if the alarm goes off (Samsung)
        //Phone manufactures have their own clock apps and they have individual alarm alert messages
        //unless there is a blanket way of doing it, this is how to do it
        //This is a link to listOfNotifications_ of manufacturer alarm alert messages for their clock apps.
        //http://stackoverflow.com/questions/4115649/listing-of-manufacturers-clock-alarm-package-and-class-name-please-add
        if (intent.getAction().equals("com.samsung.sec.android.clockpackage.alarm.ALARM_ALERT") ||
                intent.getAction().equals("com.android.alarmclock.ALARM_ALERT"))
        {
            //if the app is running
            if (LS_Service.lockscreenActivity_ != null)
            {
                //close the app to show the alarm
                //when the alarm is handled, the blank alarms class activity should start the
                //lockscreen back up
                LS_Service.lockscreenActivity_.finish();
                LS_Service.alarmAlert_ = true;
            }
            Log.d(TAG, "LS_Reciever alarm went off");


        }
        Log.d(TAG, "LS_Reciever onReceive end");
    }

    private Context ctxt;
    //this will start up the Alarm and Lockscreen activities
    private void startAlarmAndLockscreen(Context ctxt)
    {
        Intent alarmIntent = new Intent(ctxt, Alarms.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctxt.startActivity(alarmIntent);

        Intent lockscreenIntent = new Intent(ctxt, NewLockscreen.class);
        lockscreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctxt.startActivity(lockscreenIntent);
    }


   /*
   protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }else{
            return false;
        }
    }*/
}
