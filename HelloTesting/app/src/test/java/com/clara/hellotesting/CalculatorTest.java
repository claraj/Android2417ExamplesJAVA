package com.clara.hellotesting;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorTest {
	@Test
	public void addition_isCorrect() throws Exception {

		CalculatorActivity ca = new CalculatorActivity();

		assertEquals(7, ca.add(3, 4));
		assertEquals(142, ca.add(42, 100));
		assertEquals(-43, ca.add(-3, -40));
		assertEquals(39, ca.add(-3, 42));

	}


	@Test
	public void verifyInteger_isCorrect() throws Exception {

		CalculatorActivity ca = new CalculatorActivity();

		// Valid integers, as String
		assertEquals( 45, ca.verifyInputIsNumber("45").intValue());
		assertEquals( 123456, ca.verifyInputIsNumber("123456").intValue());
		assertEquals( -23423445, ca.verifyInputIsNumber("-23423445").intValue());

		// Invalid integers should return null
		assertNull(ca.verifyInputIsNumber("45.6"));      // decimal place
		assertNull(ca.verifyInputIsNumber("4sdfsdf56"));   // characters
		assertNull(ca.verifyInputIsNumber("4.5.6"));      // malformed number
		assertNull(ca.verifyInputIsNumber("pizza"));      // letters
		assertNull(ca.verifyInputIsNumber("2345675432567543567"));  // too long for an int
	}


}


