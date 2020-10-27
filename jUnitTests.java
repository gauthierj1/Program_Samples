/*							J + M + J
 * 
 */


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class jUnitTests {

	@Test
	void testMakeRandom() {
		CryptogramModel testModel = new CryptogramModel();
		CryptogramController testControl = new CryptogramController(testModel);
		Cryptograms test = new Cryptograms();
	}
	
	@Test
	void testSetReplace() {
		ArrayMap<Character, Character> guesses = new ArrayMap<Character, Character>();
		CryptogramModel testModel = new CryptogramModel(guesses, 5);
		CryptogramController testControl = new CryptogramController(testModel);
		
		testControl.makeReplacement('a','r');
		
		assert(testControl.getDecryptKey().containsKey('a'));
	}
	
	@Test
	void testAnswer() {
		ArrayMap<Character, Character> guesses = new ArrayMap<Character, Character>();
		CryptogramModel testModel = new CryptogramModel(guesses, 5);
		CryptogramController testControl = new CryptogramController(testModel);
		
		assertFalse(testControl.isGameOver());
	}

	@Test
	void testEncryption() {
		ArrayMap<Character, Character> guesses = new ArrayMap<Character, Character>();
		CryptogramModel testModel = new CryptogramModel(guesses, 5);
		CryptogramController testControl = new CryptogramController(testModel);
		
		String modelEncrypt = testModel.getEncryptedString();
		String controlEcrypt = testControl.getEncryptedQuote();
		
		assertEquals(modelEncrypt, controlEcrypt);
	}
	
	@Test
	void testBoardPrint() {
		ArrayMap<Character, Character> guesses = new ArrayMap<Character, Character>();
		CryptogramModel testModel = new CryptogramModel("TEST", guesses, 5);
		CryptogramController testControl = new CryptogramController(testModel);
		
		String board = testControl.printBoard();
		String expectedBoard = "    ,   .\nNSRI, RI.\n\n       !\n F NDIN!\n";
		
		assertEquals(board, expectedBoard);
	}
	
	@Test
	void testHintPrint()  {
		ArrayMap<Character, Character> guesses = new ArrayMap<Character, Character>();
		CryptogramModel testModel = new CryptogramModel("TEST", guesses, 5);
		CryptogramController testControl = new CryptogramController(testModel);
		
		String expectedHint = "N: T";
		String actualHint = testControl.printHint();
		
		assertEquals(expectedHint, actualHint);
	}
	
	@Test
	void testFreqsPrint()  {
		ArrayMap<Character, Character> guesses = new ArrayMap<Character, Character>();
		CryptogramModel testModel = new CryptogramModel("TEST", guesses, 5);
		CryptogramController testControl = new CryptogramController(testModel);
		
		String actualFreqs = testControl.printFreqs();
		String expectedFreqs = "A: 0  B: 0  C: 0  D: 1  E: 0  F: 1  G: 0  \n"
				+ "H: 0  I: 3  J: 0  K: 0  L: 0  M: 0  N: 3  \n"
				+ "O: 0  P: 0  Q: 0  R: 2  S: 1  T: 0  U: 0  \n"
				+ "V: 0  W: 0  X: 0  Y: 0  Z: 0  ";
		
		assertEquals(actualFreqs, expectedFreqs);
	}
	
	@Test
	void testPrintGUesses() {
		ArrayMap<Character, Character> guesses = new ArrayMap<Character, Character>();
		guesses.put('N', 'f');
		guesses.put('S', 'f');
		guesses.put('R', 'f');
		
		CryptogramModel testModel = new CryptogramModel("TEST", guesses, 5);
		CryptogramController testControl = new CryptogramController(testModel);
		String expectedGuesses = "fff , f .   f  f!";
		String actualGuesses = testControl.getUsersProgress();
		
		assertEquals(expectedGuesses, actualGuesses);
	}	
	
//	Unable to get this test to function properly.
//	@Test
//	void testFailedFileRead()  {
//		ArrayMap<Character, Character> guesses = new ArrayMap<Character, Character>();
//		CryptogramModel testModel = new CryptogramModel("TEST", guesses, 5);
//		assertThrows(FileNotFoundException.class, 
//				() -> testModel.testPullFromFile("FailThis"));
//	}
}
