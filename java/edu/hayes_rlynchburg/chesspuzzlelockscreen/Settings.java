package edu.hayes_rlynchburg.chesspuzzlelockscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;*/

/*
 * This class acts as allows the user enter in a password and determine if he/she wants
 * to see notifications.  These settings are encrypted and saved to file.
 * When a new password is selected, the Mastermind class will generate challenge categories.
 * Those challenge categories will then be saved to file from here.
 *
 */

public class Settings extends Activity {
    CheckBox checkBox;
    private static final String TAG = "TAG";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
  //  private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //save the checkBox
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        if (LS_Service.showNotifications_)
            checkBox.setChecked(true);
        Log.d(TAG, "Settings onCreate");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
     //   client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent lockscreenActivity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    //when the user changes the checkbox check
    public void onCheck(View view) {
        //if its checked
        if (checkBox.isChecked()) {

            LS_Service.showNotifications_ = true;
            //go to where the setting needed to do this is located
            //Note:  Currently, the app doesn't check to see if the user allowed the app to use the
            //notification listener service.  That needs to be added.
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            Log.d(TAG, "Notfications are on");
        } else {
            Log.d(TAG, "Notfications are off");
            LS_Service.showNotifications_ = false;

        }

        //update file
        updateFile();
    }

    //saves all settings to file
    private void updateFile() {
        File file = new File(this.getFilesDir(), "appData");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {

                //key used in encryption
                String key = "448AFBF228EC9AZX";

                byte[] pw = LS_Service.password_.getBytes("UTF8");
                byte[] buffer = new byte[pw.length + 1];

                //put the password bytes in the buffer
                for (int i = 0; i < pw.length; i++) {
                    buffer[i] = pw[i];
                }

                //put the show notifications byte at the end of the buffer
                if (LS_Service.showNotifications_)
                    buffer[buffer.length - 1] = (byte) 0;
                else
                    buffer[buffer.length - 1] = (byte) 1;

                try {
                    //get a cipher
                    Cipher cipher = Cipher.getInstance("AES");

                    //put the secret key into the this class
                    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF8"), "AES");

                    //initialize the cipher
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

                    //encrypt the data
                    buffer = cipher.doFinal(buffer);

                    //write it to file
                    fos.write(buffer);

                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "file not made");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.d(TAG, "file not made2352");
        }
    }

    //This is called when the user hits the set password button
    public void onSetPassword(View view) {
        //get the password from the text box
        EditText editText = (EditText) findViewById(R.id.editText);
        String pw = editText.getText().toString();
        //make sure it is acceptable
        if (pw.length() == 4) {
            //save the password
            LS_Service.password_ = pw;
            editText.setText("");

            //update the password file
            updateFile();

            //save the challenge codes
            Settings.this.finish();

            Log.d(TAG, LS_Service.password_);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      /*  client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Settings Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://edu.hayes_rlynchburg.lockscreen/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onStop() {
        super.onStop();

       /*/// ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Settings Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://edu.hayes_rlynchburg.lockscreen/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }
}