package com.example.eyeballinapp;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class FallDetection implements SensorEventListener {

    SensorManager snsmgr;
    Sensor sns;
    Context testContext;

    long interval;
    boolean notified = false;
    boolean sendText = true;
    String FALLMSG = "Fall Detected, Contacting Emergency Contact in ";

    public Button stopButton;
    public Button enterButton;
    public TextView timerCountDown;
    public TextView fallMessage;
    public TextView contactMessage;
    public EditText phoneNumber;

    public String phoneNumberText = "";

    private CountDownTimer countTimer;

    public FallDetection(Context mainClass) {
        testContext = mainClass;
        snsmgr = (SensorManager) testContext.getSystemService(Context.SENSOR_SERVICE);

        sns = snsmgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        snsmgr.registerListener(FallDetection.this, sns, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float xSquared = sensorEvent.values[0] * sensorEvent.values[0];
        float ySquared = sensorEvent.values[1] * sensorEvent.values[1];
        float zSquared = sensorEvent.values[2] * sensorEvent.values[2];
        double xyzSRoot = Math.sqrt(xSquared + ySquared + zSquared);

        interval = 11000;

        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stopText();
            }
        });

        if(xyzSRoot > 10 && !notified) {

            countTimer = new CountDownTimer(interval, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    interval = millisUntilFinished;
                    if (sendText)updateCountDownText();
                }

                @Override
                public void onFinish() {
                    if (sendText) sendAlert();
                }
            }.start();

            stopButton.setText("Cancel");
            stopButton.setVisibility(View.VISIBLE);
            timerCountDown.setVisibility(View.VISIBLE);
            fallMessage.setText(FALLMSG);
            fallMessage.setVisibility(View.VISIBLE);
            notified = true;

        }
    }

    private void updateCountDownText(){
        int seconds = (int) (interval /1000);
        String timeLeft = String.valueOf(seconds);
        timerCountDown.setText(timeLeft);
    }

    public void stopText(){
        sendText = !sendText;
        countTimer.cancel();
        stopButton.setVisibility(View.INVISIBLE);
        timerCountDown.setVisibility(View.INVISIBLE);
        fallMessage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void sendAlert(){

        //String phoneNumber = "4256266630";
        String smsMessage = "Fall Detected!";

        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage(phoneNumberText, null, smsMessage, null, null);
            stopButton.setVisibility(View.INVISIBLE);
            timerCountDown.setVisibility(View.INVISIBLE);
            fallMessage.setVisibility(View.INVISIBLE);
            return;
        } catch(Exception e) {
            Toast.makeText(testContext, "Error with sendTextMessage", Toast.LENGTH_SHORT).show();
        }
    }
}