package com.example.eyeballinapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.eyeballinapp.MapStuff.Graph.Route;
import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Navigation.EyeBallinMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StepTest {
    Context instrumentationContext;
    CustomLocation zeroZero = new CustomLocation(1, 0, 0);

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void directionTesting() {
        String[] expectedSteps = {"Outside Hallway 1", "Outside 151, 152", "151"};

        CustomLocation userLocation = new CustomLocation(-1, 0, 1);
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("151");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("forward");
        assertEquals("forward", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("left", direction);

        direction = steps.getStep(0).setUserDirection("down");
        assertEquals("backwards", direction);

    }
}