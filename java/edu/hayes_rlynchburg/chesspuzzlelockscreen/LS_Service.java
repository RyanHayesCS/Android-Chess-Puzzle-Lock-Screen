package edu.hayes_rlynchburg.chesspuzzlelockscreen;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Ryan Hayes on 10/9/2016.
 */
public class LS_Service extends Service {

    //receiver to handle system events
    private BroadcastReceiver receiver_;

    //used to determine if an alarm is going off
    public static boolean alarmAlert_;

    //an instance of the lockscreen activity. having it means I close it outside of its class
    public static Activity lockscreenActivity_;

    //used for logging
    private static final String TAG = "TAG";

    //contains a array of notifications to be shown
    public static Notification[] notifications_;

    //allows access to the alarms activity outside of its class
    public static Activity alarm_activity_;

    //show notifications on the screen
    public static boolean showNotifications_;

    public static String puzzlesFileName_ = "appPuzzles";

    public static String password_;

    public static List<Puzzle> puzzleList;
    public static String initialLayout_;
    public static String finalLayout_;
    public static boolean hasPuzzles_ = false;
    public static Queue<String> puzzleQueue = new LinkedList<String>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        Log.d(TAG, "LS_Service onCreate Start");

        //load files (password/settings, and all challenge codes)
        loadFiles();
        String uristring = "http://161.115.86.186:8080/PuzzleSender/rest/PuzzleService/puzzles";

        if(!getBaseContext().getFileStreamPath("appPuzzles").exists()) {
            requestData(uristring);
        }else{
            Log.d(TAG, "Reading puzzles from file");
            //readPuzzles();
        }

        alarmAlert_ = false;

        //Register our Receiver to handle screen on and off events as well as notifications
        IntentFilter filt = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filt.addAction(Intent.ACTION_SCREEN_OFF);
        filt.addAction("testBoot");
        filt.addAction("NotificationReceived");
        receiver_ = new LS_Reciever();
        registerReceiver(receiver_, filt);

        super.onCreate();
        Log.d(TAG, "LS_Service onCreate end");
    }

    //load the password and settings
    private void loadFiles() {
        String filename = "appData";
        File file = new File(this.getFilesDir(), filename);
        try {
            FileInputStream fis = new FileInputStream(file);


            try {
                //make a buffer to hold the bytes in the file
                byte[] buffer = new byte[(int)file.length()];

                try {
                    //key used to encrypt data
                    String key = "448AFBF228EC9AZX";

                    //Cipher used to decrypt data
                    Cipher cipher = Cipher.getInstance("AES");

                    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF8"), "AES");

                    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

                    //get the data from the file
                    fis.read(buffer);

                    //decrypt the data
                    buffer = cipher.doFinal(buffer);

                    fis.close();

                    //get the password from the buffer
                    LS_Service.password_ = new String(buffer, 0, buffer.length-1);

                    //get whether or not to show notifications
                    byte showNots = buffer[buffer.length - 1];


                    if ((int) showNots == 1)
                        showNotifications_ = false;
                    else
                        showNotifications_ = true;
                    Log.d(TAG, "SHOWNOTS " + showNotifications_);
                    //password = (new String(str));
                    Log.d(TAG, password_ + " good");

                    //load saved challenges
//                    loadSavedChallenges();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                Log.d(TAG, file.getPath());
                Log.d(TAG, ((Integer) (int)file.length()).toString());
                e.printStackTrace();
                Log.d(TAG, "file not found76487");
            }
        }
        catch (Exception e)
        {
            //if the password file is not found then the there won't be challenge codes
            Log.d(TAG,"file not found");
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "LS_Service onDestroy start");
        unregisterReceiver(receiver_);
        super.onDestroy();
        Log.d(TAG, "LS_Service onDestroy end");
    }

    public static String getData(String uri){
        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line +"\n");
            }

            return sb.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    private void requestData(String uristring) {
        MyTask task = new MyTask();
        task.execute(uristring, "Param2", "Param3");
    }


    private class MyTask extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting Task");
        }

        @Override
        protected String doInBackground(String... params){
            String content = getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result){

            puzzleList = PuzzleXMLParser.parseFeed(result);
            //initialLayout = puzzleList.get(0).getInitialLayout();
            //finalLayout_ = puzzleList.get(0).getFinalLayout();
            Log.d("STATE", "Puzzles received");

            writePuzzles();
            //readPuzzles();

            hasPuzzles_ = true;
        }
    }

    public void writePuzzles() {
        //File file = new File(this.getFilesDir(), puzzlesFileName_);
        //String newLine = "\n\r";
        String newLine = "\n";
        String initialIndex = "2";
        try {
            FileOutputStream outputStream = openFileOutput(puzzlesFileName_, Context.MODE_PRIVATE);

            //write index of first puzzle and amount of puzzles to file
            outputStream.write(initialIndex.getBytes());
            outputStream.write(newLine.getBytes());
            outputStream.write(Integer.toString(puzzleList.size()).getBytes());
            outputStream.write(newLine.getBytes());

            for(Puzzle puzzle: puzzleList) { //write every puzzle to file, each layout on it's own line
                outputStream.write(puzzle.getInitialLayout().getBytes());
                outputStream.write(newLine.getBytes());
                outputStream.write(puzzle.getFinalLayout().getBytes());
                outputStream.write(newLine.getBytes());
                puzzleQueue.add(puzzle.getInitialLayout());
                puzzleQueue.add(puzzle.getFinalLayout());
            }

            outputStream.close();
            Log.d(TAG, "puzzles written");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*public void readPuzzles() {
        try {
            File file = new File(this.getFilesDir(), puzzlesFileName_);
            //file.length();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            // do reading, usually loop until end of file reading
            String mLine = reader.readLine();

            int currentIndex = Integer.parseInt(mLine);
            boolean hasRead = false;
            List<String> fileLines = new ArrayList<>();
            fileLines.add(mLine);
            mLine = reader.readLine();
            fileLines.add(mLine); //add amount of puzzles to file lines

            int counter = 2;

            while (mLine != null) { //while we havent read through the file
                if (counter == currentIndex && !hasRead) //and we havent read in init and final layout
                {
                    mLine = reader.readLine();
                    fileLines.add(mLine);
                    initialLayout_ = mLine; // process// line
                    mLine = reader.readLine();
                    fileLines.add(mLine);
                    finalLayout_ = mLine;
                    currentIndex += 2;
                    hasRead = true;
                }
                counter++;
                mLine = reader.readLine();
                fileLines.add(mLine);
            }

            reader.close();

            if(hasRead)
            {
                String newLine = "\n";
                fileLines.set(0, Integer.toString(currentIndex));

                FileOutputStream fos = new FileOutputStream(new File(this.getFilesDir(), puzzlesFileName_));

                for(String line : fileLines) {
                    Log.d(TAG, "readPuzzles: " + line);
                    if(line != null) {
                        fos.write(line.getBytes());
                        fos.write(newLine.getBytes());
                    }
                }
                fos.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

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

