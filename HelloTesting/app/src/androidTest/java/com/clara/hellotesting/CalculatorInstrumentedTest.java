package com.clara.hellotesting;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import static android.support.test.espresso.matcher.ViewMatchers.withText;



import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorInstrumentedTest {
	@Test
	public void useAppContext() throws Exception {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();
		assertEquals("com.clara.hellotesting", appContext.getPackageName());
	}

	// A rule is used to launch the Activity before any tests are run.
	// Rules run before each test.
	// In a test, can get a reference to the current activity, if needed, with
	// mCalculatorRule.getActivity();

	@Rule
	public ActivityTestRule<CalculatorActivity> mCalculatorRule
			= new ActivityTestRule<>(CalculatorActivity.class);


	@Test
	public void initialView_isCorrect() throws Exception {

		// Check that the correct placeholder text is displayed
		String placeholderText = mCalculatorRule.getActivity().getString(R.string.answer_placeholder);
		onView(withText(placeholderText)).check(matches(isDisplayed()));

	}

	@Test
	public void addition_validInput() throws Exception {

		String number1 = "6"; String number2 = "16";

		// Find a TextView, type, close keyboard
		onView(withId(R.id.number_1_input)).perform(typeText(number1), closeSoftKeyboard());
		onView(withId(R.id.number_2_input)).perform(typeText(number2), closeSoftKeyboard());

		onView(withId(R.id.add_button)).perform(click());

		// Check that the answer is the answer format string with 22
		String expectedOutput = mCalculatorRule.getActivity().getString(R.string.answer, 22);
		onView(withId(R.id.answer_text)).check(matches(withText(expectedOutput)));

	}


	@Test
	public void addition_invalidInput() throws Exception {

		enterInvalidNumbersVerifyErrorToast("pizza", "42");
		enterInvalidNumbersVerifyErrorToast("pizza", "tacos");
		enterInvalidNumbersVerifyErrorToast("42", "pizza");
		enterInvalidNumbersVerifyErrorToast("", "");
		enterInvalidNumbersVerifyErrorToast("42", "");
		enterInvalidNumbersVerifyErrorToast("", "42");

	}


	// Helper method for above test, to avoid re-typing all of this!
	private void enterInvalidNumbersVerifyErrorToast(String number1, String number2) {

		// Find the textview, and replace any current text with the given data
		onView(withId(R.id.number_1_input)).perform(replaceText(number1), closeSoftKeyboard());
		onView(withId(R.id.number_2_input)).perform(replaceText(number2), closeSoftKeyboard());

		// Click the add buttin
		onView(withId(R.id.add_button)).perform(click());

		// Check that an error toast is shown
		String toastErrorMessage = mCalculatorRule.getActivity().getString(R.string.error_enter_two_numbers);

		// This causes the test to fail if a Toast with the given message is not shown
		onView(withText(toastErrorMessage))
				.inRoot(withDecorView(not(is(mCalculatorRule.getActivity().getWindow().getDecorView()))))
				.check(matches(isDisplayed()));

		// Check that the initial text in the answer TextView is unchanged
		String placeholderText = mCalculatorRule.getActivity().getString(R.string.answer_placeholder);

		// This causes the test to fail if the text is not found
		onView(withText(placeholderText)).check(matches(isDisplayed()));

	}


}
