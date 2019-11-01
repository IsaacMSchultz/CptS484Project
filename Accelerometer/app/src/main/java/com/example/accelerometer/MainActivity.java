package com.example.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager snsmgr;
    Sensor sns;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snsmgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sns = snsmgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        snsmgr.registerListener(MainActivity.this, sns, SensorManager.SENSOR_DELAY_NORMAL);
        txt = findViewById(R.id.textV);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        txt.setText(String.valueOf(sensorEvent.values[0]) + '\n' + String.valueOf(sensorEvent.values[1]) + '\n' + String.valueOf(sensorEvent.values[2]) + '\n');
        if(sensorEvent.values[0] > 10) {
            Toast.makeText(MainActivity.this, "Detected graph spike", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
