package com.example.eyeballinapp.MapStuff;

import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import org.w3c.dom.Element;
import java.io.File;
import java.util.List;

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

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(ins);
            doc.getDocumentElement().normalize();

            Element element = doc.getDocumentElement();
            NodeList nodes = element.getChildNodes();

            Element node = (Element) nodes.item(1);
            NodeList graphs = node.getChildNodes();

            Node nodevals = graphs.item(1);
            Node edgevals = graphs.item(3);

            // only the odd children have value because they're wrapped in
            // openening and closing newline characters probably because of the
            // formatting/ built in parse function
            for (int i = 1; i < nodevals.getChildNodes().getLength(); i+= 2){

                // Create a new CustomLocation object to be inserted into graph
                CustomLocation newLocation = new CustomLocation(
                        Double.valueOf(nodevals.getChildNodes().item(i).getAttributes().item(0).getNodeValue()),
                        Double.valueOf(nodevals.getChildNodes().item(i).getAttributes().item(1).getNodeValue()),
                        getFloorNum(Integer.parseInt(nodevals.getChildNodes().item(i).getAttributes().item(2).getNodeValue()))
                );

                // Create a new MapNode with the values
                // Create a function to parse id's to get floor numbers


                MapNode newNode = new MapNode(nodevals.getChildNodes().item(i).getAttributes().item(3).getNodeValue(),
                        null,
                        newLocation,
                        Integer.parseInt(nodevals.getChildNodes().item(i).getAttributes().item(2).getNodeValue())
                        );

                // Insert into the graph to be returned
                this.graph.addNode(newNode);
            }

            ins.read();

        } catch (Exception e){
            e.printStackTrace();
        }

          return graph;
    }

    int getFloorNum (int nodeId){

        while (nodeId >= 10){
            nodeId /= 10;
        }
        return nodeId;
    }
}
