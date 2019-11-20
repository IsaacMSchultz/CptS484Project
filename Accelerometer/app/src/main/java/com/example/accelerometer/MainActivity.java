package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager snsmgr;
    Sensor sns;
    TextView txt;
    Context testContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snsmgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sns = snsmgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        snsmgr.registerListener(MainActivity.this, sns, SensorManager.SENSOR_DELAY_NORMAL);
        txt = findViewById(R.id.textV);


        //FallDetection newTest = new FallDetection(getApplication());

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float xSquared = sensorEvent.values[0] * sensorEvent.values[0];
        float ySquared = sensorEvent.values[1] * sensorEvent.values[1];
        float zSquared = sensorEvent.values[2] * sensorEvent.values[2];
        double xyzSRoot = Math.sqrt(xSquared + ySquared + zSquared);

        if(xyzSRoot > 15) {
            //Toast.makeText(MainActivity.this, "Detected graph spike", Toast.LENGTH_SHORT).show();
            this.sendSMS();
            
            txt.setText(String.valueOf(sensorEvent.values[0]) + '\n' + String.valueOf(sensorEvent.values[1]) + '\n' + String.valueOf(sensorEvent.values[2]) + '\n');
            if(sensorEvent.values[0] > 10) {
                Toast.makeText(MainActivity.this, "Detected graph spike", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private void sendSMS(){
        // develop text system for alerting message

        if (!checkPermission(Manifest.permission.SEND_SMS)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        String phoneNumber = "4256266630";
        String smsMessage = "Fall Detected! Kenzo Bannang fell and is in need of emergency assistance!";

        if (checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager sms = SmsManager.getDefault();
            try {
                sms.sendTextMessage("4256266630", null, smsMessage, null, null);
                Toast.makeText(MainActivity.this, "Message Sent!", Toast.LENGTH_SHORT).show();
                return;
            } catch(Exception e) {
                Toast.makeText(MainActivity.this, "Error with sendTextMessage", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(MainActivity.this, "Error permission is null", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
