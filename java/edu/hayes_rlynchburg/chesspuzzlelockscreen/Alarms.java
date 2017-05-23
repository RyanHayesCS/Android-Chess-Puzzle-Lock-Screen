package edu.hayes_rlynchburg.chesspuzzlelockscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by William on 11/10/2015.
 * This class runs in the background whenever the lockscreen is active.
 * When the alarm clock goes off and the Lockscreen is active, the lockscreen is closed (finished)
 *      and the user should see the alarm screen so he/she can stop the alarm.
 * This lockscreenActivity is still active when the alarm goes off.  The alarm screen draws over this activty
 *      so the user will never see this.
 * When user stops the alarm, this activty come into the foreground and at that point, the Lockscreen
 *      is started back up (onRestart).
 * This lockscreenActivity is ended when the lockscreen is unlocked and starts when the lockscreen starts.
 */
public class Alarms extends Activity {

    private static boolean running = true;

    private static  final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this allows the activty to be ended outside of the class
        LS_Service.alarm_activity_ = this;

        Log.d(TAG, "Alarms create");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Alarms destroy");
    }

    //this is called when the lockscreenActivity comes back into foreground
    @Override
    protected void onRestart() {
        super.onRestart();

        //if there was an alarm going off
        if(LS_Service.alarmAlert_)
        {
            LS_Service.alarmAlert_ = false;

            //Start up the lockscreen
            Intent intent1 = new Intent(this, NewLockscreen.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            this.startActivity(intent1);
        }

        Log.d(TAG, "Alarms Restart");
    }

    @Override
    protected void onResume() {
        //if(hasEnded) {
            super.onResume();
            Log.d(TAG, "Alarms resume");
        //}
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Alarms pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Alarms stop");
    }
}
