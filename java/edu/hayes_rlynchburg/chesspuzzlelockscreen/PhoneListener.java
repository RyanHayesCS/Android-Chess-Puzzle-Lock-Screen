package edu.hayes_rlynchburg.chesspuzzlelockscreen;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Ryan Hayes on 10/9/2016.
 */
public class PhoneListener extends PhoneStateListener {
    private static final String TAG = "TAG";
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state)
        {
            //getting a phone call
            case TelephonyManager.CALL_STATE_RINGING:

                Log.d(TAG, "phone is ringing");
                if(LS_Service.lockscreenActivity_ != null)
                {
                    LS_Service.lockscreenActivity_.finish();
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:

                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "phone is off hook");
        }
    }
}