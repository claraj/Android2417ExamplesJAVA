package com.clara.unittest_calculator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorTest {

    @Test
    public void additionMathIsCorrect()  {
        assertEquals(7, Math.add(3, 4));
        assertEquals(142, Math.add(42, 100));
        assertEquals(-43, Math.add(-3, -40));
        assertEquals(39, Math.add(-3, 42));
    }


    @Test
    public void integersConvertedToString() {
        // Valid integers, as String
        assertEquals(new Integer(45), Math.verifyInputIsNumber("45"));
        assertEquals(new Integer(123456), Math.verifyInputIsNumber("123456"));
        assertEquals(new Integer(-23423445), Math.verifyInputIsNumber("-23423445"));
    }


    @Test
    public void invalidIntegersReturnNull() {
        assertNull(Math.verifyInputIsNumber("45.6"));      // decimal place
        assertNull(Math.verifyInputIsNumber("4sdfsdf56"));   // characters
        assertNull(Math.verifyInputIsNumber("4.5.6"));      // malformed number
        assertNull(Math.verifyInputIsNumber("pizza"));      // letters
        assertNull(Math.verifyInputIsNumber("2345675432567543567"));  // too long for an int
    }

}