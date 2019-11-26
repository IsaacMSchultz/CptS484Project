package com.example.eyeballinapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechParser {


    String mCommand;
    String mDestination;

    public SpeechParser() {
        mCommand = "";
    }

    //get the sentence and look for keywords: yes, no, take me, room, stop, setup, commands
    /*Pattern p = Pattern.compile("^.*(\\byes\\b|\\bno\\b).*$");
        Matcher m = p.matcher("yes no");
        boolean b = m.matches(); --> true*/
    public String getSpeechCommand(String sentence) {
        Pattern p = Pattern.compile("^.*(\\btake me\\b|\\broom\\b|\\bgo to\\b).*$");
        Matcher m = p.matcher(sentence);
        if(m.matches()) {
            Pattern p2 = getPattern("room");
            mDestination = "451";
            return "repeat";
        }
        else {
            Pattern p2 = Pattern.compile("^.*(\\byes\\b|\\bno\\b|\\bset up\\b|\\bcommands\\b|\\bstop\\b).*$");
            Matcher m2 = p2.matcher(sentence);
            if(m2.matches())
                return m2.group(1);
            else {
                return "ask again";
            }
            //get occurrence of whatever word
        }
        //if command is navigate, use set the return value of getDestination to the room number or something.
    }

    //we could use the global variable here....
    public String getDestination() {
        return mDestination;
    }

    private Pattern getPattern(String word) {
        return Pattern.compile("^.*(\\b" + word + "\\b).*$");
    }
}
