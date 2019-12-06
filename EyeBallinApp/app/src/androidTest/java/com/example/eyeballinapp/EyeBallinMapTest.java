package com.example.eyeballinapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.eyeballinapp.MapStuff.Graph.MapGraph;
import com.example.eyeballinapp.MapStuff.Graph.MapNode;
import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Graph.Step;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Navigation.EyeBallinMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EyeBallinMapTest {
    Context instrumentationContext;
    CustomLocation zeroZero = new CustomLocation(1, 0, 0);

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void CalculateRoute() {
        String[] expectedSteps = {"Outside Hallway 1", "Floor 1 hallway", "Outside Bathroom 1", "Outside 151, 152", "151"};

        CustomLocation userLocation = new CustomLocation(0, 0, 1);
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("151");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }
    }


    //This test mocks the user walking from the south doors to 151
    @Test
    public void navigateFrom() {
        String[] expectedSteps = {"Outside Hallway 1", "Floor 1 hallway", "Outside Bathroom 1", "Outside 151, 152", "151"};
        Route steps;
        CustomLocation userLocation;
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("151");

        userLocation = new CustomLocation(0, 0, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 25, 1);
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(0, 30, 1);
        expectedSteps = new String[]{"Floor 1 hallway", "Outside Bathroom 1", "Outside 151, 152", "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(9, 30, 1);
        expectedSteps = new String[]{ "Outside Bathroom 1", "Outside 151, 152", "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(18, 30, 1);
        expectedSteps = new String[]{ "Outside Bathroom 1", "Outside 151, 152", "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(42, 30, 1);
        expectedSteps = new String[]{"Outside 151, 152", "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 30, 1);
        expectedSteps = new String[]{"151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 24, 1);
        expectedSteps = new String[]{"151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }

        userLocation = new CustomLocation(56, 22, 1); //arrived
        expectedSteps = new String[]{ "151"};
        map.updateUser(userLocation);
        steps = map.calculateRoute();

        assertEquals(expectedSteps.length, steps.getStepList().size()); // are the expected and actual the same size?
        for (String s : expectedSteps) { // are they the same order?
            assertEquals(s, steps.takeStep().getNode().getName());
        }
    }
}