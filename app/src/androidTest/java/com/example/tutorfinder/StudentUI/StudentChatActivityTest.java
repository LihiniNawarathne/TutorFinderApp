package com.example.tutorfinder.StudentUI;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.tutorfinder.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class StudentChatActivityTest {

    @Rule
    public ActivityTestRule<StudentChatActivity> studentChatActivityActivityTestRule= new ActivityTestRule<StudentChatActivity>(StudentChatActivity.class);

    private String message = "Hey";



    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void inputScenario(){
        Espresso.onView(withId(R.id.etvSentMSG)).perform(typeText(message));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.imgSendMsg)).perform(click());

        Espresso.onView(withId(R.id.tvmessage)).check(matches(withText(message)));
    }

    @After
    public void tearDown() throws Exception {
    }
}