package com.example.accelerometer;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class FallDetection implements SensorEventListener {

    SensorManager snsmgr;
    Sensor sns;
    TextView txt;
    Context testContext;

    int interval;
    Timer timer;
    boolean notified = false;

    String FALLMSG = "Fall Detected, Contacting Emergency Contact in ";

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

        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = 3;


        if(xyzSRoot > 10 && !notified) {

            Toast.makeText(testContext, FALLMSG, Toast.LENGTH_SHORT).show();

            while (interval > 0){
                Toast.makeText(testContext, String.valueOf(interval), Toast.LENGTH_SHORT).show();
               for (double i = 0; i < 1000000*1000000; i++){ }
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                interval = interval - 1;
            }

            this.sendAlert();
            notified = true;
            return;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void sendAlert(){

        String phoneNumber = "4256266630";
        String smsMessage = "Fall Detected!";

        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage("4256266630", null, smsMessage, null, null);
            Toast.makeText(testContext, "Message Sent!", Toast.LENGTH_SHORT).show();
            return;
        } catch(Exception e) {
            Toast.makeText(testContext, "Error with sendTextMessage", Toast.LENGTH_SHORT).show();
        }
    }
}


//            timer.scheduleAtFixedRate(new TimerTask() {
//                public void run() {
//                    Looper.prepare();
//                    Handler mHandler = new Handler() {
//                        public void handleMessage(Message msg) {
//                            Toast.makeText(testContext, Integer.toString(setInterval()), Toast.LENGTH_SHORT).show();
//                        }
//                    };
//                    Looper.loop();
//                }
//            }, delay, period);