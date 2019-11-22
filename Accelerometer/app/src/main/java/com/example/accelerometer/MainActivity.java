package com.example.accelerometer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    public String InputMSG = "Please Input an emergency contact Phone Number: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission(Manifest.permission.SEND_SMS)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        final FallDetection newTest = new FallDetection(getApplication());

        newTest.stopButton = findViewById(R.id.button_stop);
        newTest.timerCountDown = findViewById(R.id.text_view_countdown);
        newTest.fallMessage = findViewById(R.id.fall_message);
        newTest.contactMessage = findViewById(R.id.input_contact_message);
        newTest.phoneNumber = findViewById(R.id.inputNumber);
        newTest.enterButton = findViewById(R.id.phone_enter);

        newTest.contactMessage.setText(InputMSG);
        newTest.contactMessage.setVisibility(View.VISIBLE);
        newTest.enterButton.setText("ENTER");
        newTest.enterButton.setVisibility((View.VISIBLE));
        newTest.phoneNumber.setVisibility(View.VISIBLE);
        newTest.phoneNumber.setEnabled(true);

        newTest.enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newTest.phoneNumberText = newTest.phoneNumber.getText().toString();
                newTest.enterButton.setVisibility((View.INVISIBLE));
                newTest.phoneNumber.setVisibility(View.INVISIBLE);
                newTest.contactMessage.setVisibility(View.INVISIBLE);
                newTest.phoneNumber.setEnabled(false);
            }
        });

        setContentView(R.layout.walk_path_layout);
    }

    public boolean checkPermission(String permission){

        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}