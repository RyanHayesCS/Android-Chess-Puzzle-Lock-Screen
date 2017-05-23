package edu.hayes_rlynchburg.chesspuzzlelockscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StartupLS extends Activity {

    private static final String TAG = "TAG";

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "LS Startup onCreate Start");
        super.onCreate(savedInstanceState);

        this.startService(new Intent(this, LS_Service.class));

        //start the notification service
        this.startService(new Intent(this, NotificationListener.class));

        //start the settings activity
        this.startActivity(new Intent(this, Settings.class));

        //close this activity
        this.finish();
        Log.d(TAG, "LS Startup onCreate End");
    }
}
