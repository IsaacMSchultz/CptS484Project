package com.example.eyeballinapp.MapStuff;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlPullParserException;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.app.Activity;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;

import android.content.res.Resources;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import org.w3c.dom.Element;
import java.io.File;

//import android.content.res.Resources;

public class XmlParser {

    public MapGraph graph;
    private Context context;

    public XmlParser(Context current){
        this.graph = new MapGraph();
        this.context = current;

    }

    public XmlParser(MapGraph existingGraph){
        this.graph = existingGraph;
    }

    public MapGraph tempParse() throws  IOException{
        File fXmlFile;
        InputStream ins;
        try{
            // not working at the moment
            //fXmlFile = new File("/app/src/main/assets/halfschool.xml");
            //boolean test = fXmlFile.canRead();
            //System.out.print(test);

            ins = context.getResources().openRawResource(
                    context.getResources().getIdentifier("halfschool","raw", context.getPackageName()));

            ins.read();

        } catch (Exception e){
            e.printStackTrace();
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        //dbFactory.setSchema();
//        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//        Document doc = dBuilder.parse(fXmlFile);
//        doc.getDocumentElement().normalize();
          return null;
    }
}
