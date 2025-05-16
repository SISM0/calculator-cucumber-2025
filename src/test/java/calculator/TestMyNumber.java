package calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMyNumber {

	@Test
	void testConstructorAndValue() {
		MyNumber n = new MyNumber(8.5);
		assertEquals(8.5, n.getValue(), 0.0, "MyNumber should store the correct double value");
	}

	@Test
	void testEqualsAndHashCode() {
		MyNumber a = new MyNumber(42.0);
		MyNumber b = new MyNumber(42.0);
		MyNumber c = new MyNumber(7.0);

		// equals
		assertEquals(a, b, "Two MyNumber with same value should be equal");
		assertNotEquals(a, c, "Different values should not be equal");
		assertNotEquals(a, null);
		assertNotEquals(a, "not a MyNumber");

		// hashCode consistent with equals
		assertEquals(a.hashCode(), b.hashCode(), "Equal MyNumbers must have same hashCode");
	}

	@Test
	void testToString() {
		MyNumber n = new MyNumber(3.14159);
		String s = n.toString();
		assertTrue(s.contains("3.14159"), "toString should include the numeric value");
	}
}
