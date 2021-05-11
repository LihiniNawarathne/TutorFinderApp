package com.example.tutorfinder.MainUI;

import android.app.Activity;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.tutorfinder.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest{

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity=null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void ActivityLaunch(){
        View view = mainActivity.findViewById(R.id.imageView2);

        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity =null;
    }
}