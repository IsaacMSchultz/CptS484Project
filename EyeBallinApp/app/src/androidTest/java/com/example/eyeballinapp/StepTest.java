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
    public void UpCardinalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(0, -1, 1);
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("up");
        assertEquals("forward", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("left", direction);

        direction = steps.getStep(0).setUserDirection("down");
        assertEquals("backward", direction);
    }

    @Test
    public void RightCardinalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(3, 0, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("up");
        assertEquals("left", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("forward", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("backward", direction);

        direction = steps.getStep(0).setUserDirection("down");
        assertEquals("right", direction);
    }

    @Test
    public void LeftCardinalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(-3, 0, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("up");
        assertEquals("slight_left", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("slight_right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("120degrees_left", direction);

        direction = steps.getStep(0).setUserDirection("down");
        assertEquals("120degrees_right", direction);
    }

    @Test
    public void DownCardinalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(3, 0, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("up");
        assertEquals("slight_left", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("slight_right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("120degrees_left", direction);

        direction = steps.getStep(0).setUserDirection("down");
        assertEquals("120degrees_right", direction);
    }

    @Test
    public void UpRightDiagonalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(-3, -3, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("up");
        assertEquals("slight_right", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("120degrees_right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("slight_left", direction);

        direction = steps.getStep(0).setUserDirection("down");
        assertEquals("120degrees_left", direction);
    }

    @Test
    public void UpLeftDiagonalDirectionTesting() {
        CustomLocation userLocation = new CustomLocation(3, -3, 1); // 45 degrees towards the first step
        EyeBallinMap map = new EyeBallinMap(instrumentationContext);
        map.setDestination("South Doors");
        map.updateUser(userLocation);
        Route steps = map.calculateRoute();
        String direction;

        direction = steps.getStep(0).setUserDirection("up");
        assertEquals("slight_left", direction);

        direction = steps.getStep(0).setUserDirection("left");
        assertEquals("slight_right", direction);

        direction = steps.getStep(0).setUserDirection("right");
        assertEquals("120degrees_left", direction);

        direction = steps.getStep(0).setUserDirection("down");
        assertEquals("120degrees_right", direction);
    }


}