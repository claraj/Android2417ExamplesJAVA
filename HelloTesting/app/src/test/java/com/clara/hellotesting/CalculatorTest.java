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

		assertEquals(7, Math.add(3, 4));
		assertEquals(142, Math.add(42, 100));
		assertEquals(-43, Math.add(-3, -40));
		assertEquals(39, Math.add(-3, 42));

	}


	@Test
	public void verifyInteger_isCorrect() throws Exception {

		// Valid integers, as String
		assertEquals( 45, Math.verifyInputIsNumber("45").intValue());
		assertEquals( 123456, Math.verifyInputIsNumber("123456").intValue());
		assertEquals( -23423445, Math.verifyInputIsNumber("-23423445").intValue());

		// Invalid integers should return null
		assertNull(Math.verifyInputIsNumber("45.6"));      // decimal place
		assertNull(Math.verifyInputIsNumber("4sdfsdf56"));   // characters
		assertNull(Math.verifyInputIsNumber("4.5.6"));      // malformed number
		assertNull(Math.verifyInputIsNumber("pizza"));      // letters
		assertNull(Math.verifyInputIsNumber("2345675432567543567"));  // too long for an int
	}


}


