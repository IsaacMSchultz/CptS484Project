package com.example.eyeballinprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eyeballinprototype.MapStuff.MapGraph;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button mSpeakBtn;
    TextToSpeechClass mTts;
    TextView mStatusText;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    SpeechParser parser = new SpeechParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTts = new TextToSpeechClass(getApplicationContext(), getString(R.string.opening_message));

//        mSpeakBtn = findViewById(R.id.speak_button);
//        mStatusText = findViewById(R.id.status_text);
//
//        mSpeakBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Intent myIntent = new Intent(MainActivity.this, TextToSpeechClass.class);
        startActivity (myIntent);
    }


//    private void SpeechToText() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
//        try {
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException a) {
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == RESULT_OK && null != data) {
//                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    mStatusText.setText(result.get(0));
//                    startApplication(result.get(0));
//                    //Toast.makeText(MainActivity.this, "activity result ran", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            }
//        }
//    }
//
//    private void startApplication(String command) {
//        //parse command while it isnt the command were looking for keep on asking
//        switch(parser.getSpeechCommand(command)) {
//            case "yes": navigate(parser.getDestination());
//                break;
//            case "no": speak("You said no");
//                break;
//            case "repeat": repeat(command);
//                SpeechToText();
//                break;
//            case "stop": speak("Stop requested, goodbye");
//                break;
//            case "setup": speak("Going to setup");
//                break;
//            case "commands": speak("Commands are: Go to, setup, or stop");
//                SpeechToText();
//                break;
//            default: speak("Sorry I did not hear that, please repeat");
//                SpeechToText();
//                break;
//        }
//
//    }
//
//    private void navigate(String roomNumber) {
//        mTts.speak("Going to room " + roomNumber);
//        while(mTts.isSpeaking()) {}
//    }
//
//    private void repeat(String command) {
//        mTts.speak(command + "...is that correct?");
//        while(mTts.isSpeaking()) {}
//    }
//
//    private void speak(String sentence) {
//        mTts.speak(sentence);
//        while(mTts.isSpeaking()) {}
//    }
}
