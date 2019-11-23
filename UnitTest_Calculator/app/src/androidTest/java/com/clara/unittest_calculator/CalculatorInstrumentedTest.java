package com.clara.unittest_calculator;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorInstrumentedTest {


    // Create an ActivityTestRule, used to access UI components as the test runs

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void placeholderTextIsDisplayed() {
        // A basic test - is the correct text displayed in the answer placeholder TextView?
        String expectedPlaceholder = activityTestRule.getActivity().getString(R.string.answer_placeholder);
        onView(withText(expectedPlaceholder)).check(matches(isDisplayed()));
    }


    @Test
    public void addition_validInput() {

        String number1 = "6"; String number2 = "16";

        // Find the TextView, type, close keyboard
        onView(withId(R.id.number_1_input)).perform(typeText(number1), closeSoftKeyboard());
        onView(withId(R.id.number_2_input)).perform(typeText(number2), closeSoftKeyboard());

        // Xlick the add button
        onView(withId(R.id.add_button)).perform(click());

        // Check that the answer is the answer format string with 22
        String expectedOutput = activityTestRule.getActivity().getString(R.string.answer, 22);
        onView(withId(R.id.answer_text)).check(matches(withText(expectedOutput)));
    }


    @Test
    public void addition_invalidInput() {
        enterInvalidNumbersVerifyErrorToast("pizza", "42");
        enterInvalidNumbersVerifyErrorToast("pizza", "tacos");
        enterInvalidNumbersVerifyErrorToast("42", "pizza");
        enterInvalidNumbersVerifyErrorToast("", "");
        enterInvalidNumbersVerifyErrorToast("42", "");
        enterInvalidNumbersVerifyErrorToast("", "42");
    }


    // Helper method for above test
    private void enterInvalidNumbersVerifyErrorToast(String number1, String number2) {

        // Find the TextViews, and replace any current text with the given data
        onView(withId(R.id.number_1_input)).perform(replaceText(number1), closeSoftKeyboard());
        onView(withId(R.id.number_2_input)).perform(replaceText(number2), closeSoftKeyboard());

        // Click the add button
        onView(withId(R.id.add_button)).perform(click());

        // Check that an error toast is shown
        String toastErrorMessage = activityTestRule.getActivity().getString(R.string.error_enter_two_numbers);

        // Fail if a Toast with the given message is not shown
        onView(withText(toastErrorMessage))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        // Check that the initial text in the answer TextView is unchanged
        // Fail if the expected placeholder text is not found
        String placeholderText = activityTestRule.getActivity().getString(R.string.answer_placeholder);
        onView(withText(placeholderText)).check(matches(isDisplayed()));
    }


}
