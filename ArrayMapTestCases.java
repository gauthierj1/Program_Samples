import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Map.Entry;
import java.lang.IllegalStateException;

class ArrayMapTestCases {

	@Test
	void testGetSize() {
		ArrayMap<Integer, String> test = new ArrayMap<Integer, String>();
		test.put(1, "A");
		assertEquals(test.size(), 1);
	}

	@Test
	void testGrowArray() {
		ArrayMap<Integer, Integer> test = new ArrayMap<Integer, Integer>();
		for (int i = 0; i < 20; i++) {
			test.put(i, i);
		}
		assertEquals(test.size(), 20);
		assertEquals(test.entrySet().size(), 20);
	}
	
	@Test
	void testReturnValAtPut() {
		ArrayMap<Integer, Character> test = new ArrayMap<Integer, Character>();
		test.put(1,'A');
		test.put(2, 'C');
		char removedVal = test.put(1, 'B');
		assertEquals(removedVal, 'A');
	}
	
	@Test
	void testEntrySet1() {
		ArrayMap<Integer, Character> test = new ArrayMap<Integer, Character>();
		test.put(1,'A');
		test.put(2, 'C');
		SimpleEntry<Integer, Character> tester = new SimpleEntry<Integer, Character>(1, 'A');
		assertTrue(test.entrySet().contains(tester));
	}
	
	@Test
	void testEntrySet2() {
		ArrayMap<Integer, Character> test = new ArrayMap<Integer, Character>();
		test.put(1,'A');
		test.put(2, 'C');
		SimpleEntry<Integer, Character> tester = new SimpleEntry<Integer, Character>(3, 'D');
		assertFalse(test.entrySet().contains(tester));
	}
	
	@Test
	void testEntrySet3() {
		ArrayMap<Integer, Character> test = new ArrayMap<Integer, Character>();
		test.put(1,'A');
		test.put(2, 'C');
		int tester = 4;
		assertFalse(test.entrySet().contains(tester));
	}
	
	@Test
	void testIterating() {
		ArrayMap<Integer, Integer> test = new ArrayMap<Integer, Integer>();
		test.put(1, 1);
		test.put(2, 2);
		test.put(3, 3);
		test.put(4, 4);
		Iterator itr = test.entrySet().iterator();
		SimpleEntry<Integer, Integer> currElem = (SimpleEntry<Integer, Integer>) itr.next();
		String result = "";
		String expected = "1, 1\n2, 2\n3, 3\n4, 4\n";
		while (itr.hasNext()) {
		result += currElem.getKey() + ", " + currElem.getValue() + "\n";
		currElem = (SimpleEntry<Integer, Integer>) itr.next();
		}
		assertEquals(result, expected);
	}
	
	@Test
	void itrThrow() {
		ArrayMap<Integer, Integer> test = new ArrayMap<Integer, Integer>();
		Iterator itr = test.entrySet().iterator();
		test.put(1, 1);
		test.put(2, 2);
		test.put(3, 3);
		test.put(4, 4);
		assertThrows(IllegalStateException.class, () -> itr.remove());
		itr.next();
		itr.next();
		itr.next();
		itr.remove();
		assertThrows(IllegalStateException.class, () -> itr.remove());
	}
}
