package com.example.aleksandra.koziel_swim_lab2;


import android.provider.Settings;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class changingUnitsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void changingUnitsTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.yourBmi), withText("00.00"),
                        childAtPosition(
                                allOf(withId(R.id.moodLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                1),
                        isDisplayed()));

        textView.check(matches(withText("00.00")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.massUnits), withText("KG"),
                        childAtPosition(
                                allOf(withId(R.id.dataLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                3),
                        isDisplayed()));
        textView2.check(matches(withText("KG")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.heightUnits), withText("M"),
                        childAtPosition(
                                allOf(withId(R.id.dataLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                6),
                        isDisplayed()));
        textView3.check(matches(withText("M")));

        ViewInteraction editText = onView(
                allOf(withId(R.id.yourMass),
                        childAtPosition(
                                allOf(withId(R.id.dataLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                2),
                        isDisplayed()));
        editText.check(matches(withText("")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.yourHeight),
                        childAtPosition(
                                allOf(withId(R.id.dataLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                5),
                        isDisplayed()));
        editText2.check(matches(withText("")));

        ViewInteraction button = onView(
                allOf(withId(R.id.units), withText("IMPERIAL UNITS"),
                        withParent(withId(R.id.dataLayout)),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.massUnits), withText("LB"),
                        childAtPosition(
                                allOf(withId(R.id.dataLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                3),
                        isDisplayed()));
        textView4.check(matches(withText("LB")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.heightUnits), withText("FT"),
                        childAtPosition(
                                allOf(withId(R.id.dataLayout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                6),
                        isDisplayed()));
        textView5.check(matches(withText("FT")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
