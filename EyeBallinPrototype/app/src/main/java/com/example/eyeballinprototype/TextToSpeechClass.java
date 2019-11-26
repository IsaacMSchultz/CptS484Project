package com.example.eyeballinprototype;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class TextToSpeechClass extends AppCompatActivity {

    private Context mContext;
    TextToSpeech mTts;
    private final String UTTER_ID = "speak_id";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    SpeechParser parser = new SpeechParser();

//    public TextToSpeechClass(Context context, final String sentence) {
//        mContext = context;
//        mTts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
//
//            @Override
//            public void onInit(int status) {
//                if(status != TextToSpeech.ERROR) {
//                    mTts.setLanguage(Locale.US);
//                    mTts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, UTTER_ID);
//                }
//            }
//        });
//        while(mTts.isSpeaking()) {}
//        SpeechToText();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak("Hello bitch", TextToSpeech.QUEUE_FLUSH, null, UTTER_ID);
                }
            }
        });
        while(mTts.isSpeaking()) {}
        //SpeechToText();
    }

    public void speak(String sentence) {
        //Toast.makeText(mContext, sentence,Toast.LENGTH_SHORT).show();
        mTts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, UTTER_ID);
    }


    public boolean isSpeaking() {
        return mTts.isSpeaking();
    }

    public void SpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //mStatusText.setText(result.get(0));
                    startApplication(result.get(0));
                    //Toast.makeText(MainActivity.this, "activity result ran", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

        private void startApplication(String command) {
            //parse command while it isnt the command were looking for keep on asking
            switch(parser.getSpeechCommand(command)) {
                case "yes": navigate(parser.getDestination());
                    break;
                case "no": speak("You said no");
                    break;
                case "repeat": repeat(command);
                    SpeechToText();
                    break;
                case "stop": speak("Stop requested, goodbye");
                    break;
                case "setup": speak("Going to setup");
                    break;
                case "commands": speak("Commands are: Go to, setup, or stop");
                    SpeechToText();
                    break;
                default: speak("Sorry I did not hear that, please repeat");
                    SpeechToText();
                    break;
            }

        }

    private void navigate(String roomNumber) {
        speak("Going to room " + roomNumber);
        while(mTts.isSpeaking()) {}
    }

    private void repeat(String command) {
        speak(command + "...is that correct?");
        while(mTts.isSpeaking()) {}
    }
}
