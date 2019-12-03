/*
* Description: The navigation will start here, basically all the map stuff and graph nodes whatever
*              will happen here.
* */
package com.example.eyeballinapp.SpeechStuff;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eyeballinapp.MapStuff.Navigation.Navigation;
import com.example.eyeballinapp.R;

import static com.example.eyeballinapp.MainActivity.REQUEST_LISTENER;

public class NavigationActivity extends AppCompatActivity implements SensorEventListener {


    private SensorManager snsmgr;
    private Sensor sns;
    long interval;

    private CountDownTimer countTimer;

    boolean notified = false;
    boolean sendText = true;

    private Button mLeftButton, mRightButton, mUpButton, mDownButton, mDirectionLeft, mDirectionRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_path_layout);

        Intent intent = getIntent();

        // FIXME: 12/3/2019 This doesnt work but it should
        String destination = intent.getStringExtra("DESTINATION");

        final Navigation nav = new Navigation(getApplicationContext(), destination);

        snsmgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sns = snsmgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        snsmgr.registerListener(NavigationActivity.this, sns, SensorManager.SENSOR_DELAY_NORMAL);

        mUpButton = findViewById(R.id.up_button);

        mUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate("up");
            }
        });
    }

    public void fallDetected() {

        interval = 11000;

        countTimer = new CountDownTimer(interval, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                interval = millisUntilFinished;
                //if (sendText)updateCountDownText();
            }

            @Override
            public void onFinish() {
                if (sendText) sendAlert();
            }
        }.start();

        startListener(getString(R.string.notify_fall));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float xSquared = sensorEvent.values[0] * sensorEvent.values[0];
        float ySquared = sensorEvent.values[1] * sensorEvent.values[1];
        float zSquared = sensorEvent.values[2] * sensorEvent.values[2];
        double xyzSRoot = Math.sqrt(xSquared + ySquared + zSquared);


        if(xyzSRoot > 10 && !notified) {
            notified = true;
            fallDetected();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startListener(String message) {
        Intent myIntent = new Intent(NavigationActivity.this, ListenActivity.class);
        SpeechResult.get(getApplicationContext()).setSpeechText(message);
        startActivityForResult(myIntent, REQUEST_LISTENER);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LISTENER: {
                String testMessage = SpeechResult.get(getApplicationContext()).getMessage();
                handleAlert(testMessage);
                break;
            }
        }
        //finish();
    }

    private void handleAlert(String message) {
        //probably parse message
        if(message.equals("cancel")) {
            stopText();
            SpeakActivity say = new SpeakActivity(getApplicationContext(), getString(R.string.notify_cancel));
        }
        else {
            sendAlert();
        }
    }

    private void sendAlert() {
        String phoneNumber = "4256266630";
        String smsMessage = getString(R.string.notify_text);

        SmsManager sms = SmsManager.getDefault();
        try {
            SpeakActivity say = new SpeakActivity(getApplicationContext(), getString(R.string.notify_contacts));
            sms.sendTextMessage(phoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "Text sent", Toast.LENGTH_SHORT).show();
            //say someone has been notified.
            sendText = false;
            return;
        } catch(Exception e) {
            //no Text Permission
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            //Log.println(1, "TEST", e.toString());
        }

    }

//    private void updateCountDownText(){
//        int seconds = (int) (interval /1000);
//        if(seconds == 5) {
//            //SpeakActivity say = new SpeakActivity(getApplicationContext(), "5 seconds left");
//        }
//    }

    public void stopText(){
        sendText = !sendText;
        countTimer.cancel();
        notified = false;
    }
}
