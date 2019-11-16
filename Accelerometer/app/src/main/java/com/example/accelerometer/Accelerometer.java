package com.example.accelerometer;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class Accelerometer implements SensorEventListener {

    SensorManager snsmgr;
    Sensor sns;
    TextView txt;
    Context testContext;

    public Accelerometer(Context mainClass) {
        testContext = mainClass;
        snsmgr = (SensorManager) testContext.getSystemService(Context.SENSOR_SERVICE);

        sns = snsmgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        snsmgr.registerListener(Accelerometer.this, sns, SensorManager.SENSOR_DELAY_NORMAL);

        // txt.setText("");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float xSquared = sensorEvent.values[0] * sensorEvent.values[0];
        float ySquared = sensorEvent.values[1] * sensorEvent.values[1];
        float zSquared = sensorEvent.values[2] * sensorEvent.values[2];
        double xyzSRoot = Math.sqrt(xSquared + ySquared + zSquared);

        if(xyzSRoot > 15) {
            //Toast.makeText(testContext, "Detected graph spike", Toast.LENGTH_SHORT).show();
            this.sendAlert();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private void sendAlert(){
        // develop text system for alerting message
        String phoneNumber = "4256266630";
        String smsMessage = "Fall Detected! Kenzo Bannang fell and is in need of emergency assistance!";
        SmsManager sms = null;
        try {
            sms = SmsManager.getDefault();
        } catch(Exception e) {
            Toast.makeText(testContext, "Error SmsManager.getDefault()", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            sms.sendTextMessage("4256266630", null, smsMessage, null, null);
            Toast.makeText(testContext, "Message Sent!", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(testContext, "Error with sendTextMessage", Toast.LENGTH_SHORT).show();
        }
    }

}
