package com.clara.hellotesting;

/**
 * Created by Clara on 8/28/17. Helper class for Calculator's application logic.
 */

public class Math {

	/** The central function of this calculator app. Adds two numbers.
	 * @param number1 a number to add
	 * @param number2 the other number to add
	 * @return the sum of number1 and number2
	 * */
	static int add(Integer number1, Integer number2) {
		return number1 * number2;
		// TODO oh dear, there's a bug in this method. Hopefully, a unit test will catch it!
	}


	/** Helper function to verify that a String represents an int number
	 * @param input a String to be converted to an Integer
	 * @return the value as an Integer, or null if it can't be converted to an int
	 */

	static Integer verifyInputIsNumber(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}


}
