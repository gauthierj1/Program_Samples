/**							J + M + J
 * @author: Joseph Gauthier
 * 
 *			This class is where everything about the current state of the 
 * 			cryptogram game is held and manipulated. Reads in a file within
 * 			the project that has to be called "quotes.txt" in order to obtain
 * 			a quote from random to base the game around. It then generates a
 * 			random encryption key, stored in a HashMap, that is used to encrypted 
 * 			the quote, and keeps track of a user's attempts by storing the letters
 * 			involved in another HashMap.
 * 
 * 				   CryptogramModel(): Constructor used to implement the answer, 
 * 									  encryptKey and decryptKey via their respective
 * 									  helper methods. With the exception of decryptKey
 * 									  which is implemented with an empty HashMap.
 * 				   pullFromFile(): Opens the "quotes.txt" file and selects a random 
 * 								   line from it. Used to implement answer field.
 * 				   encrypt(): Generates an encryption key by mapping each letter
 * 							  of the alphabet to a randomly generated letter. Used
 * 							  to implement the encryptKey field.
 * 				   setReplacement(): Adds the letters passed into it to the
 * 									 decryptKey Map. These letters are a user's
 * 									 attempt at decrypting the quote. 
 * 				   getEncryptedString(): Using the encryptKey and quote from the
 * 										 answer field, generates and returns a String
 * 										 representation of the encrypted quote.
 * 				   getDecryptedString(): Generates a String representation of the 
 * 										 user's attempts at decrypting the quote.
 * 										 NOTE: If a letter from the decryption has
 * 											   not yet be replaced, it is replaced
 * 											   here with an empty space.
 * 				   getAnswer(): Returns the actual, non-encrypted quote.
 * 			
 */

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;


public class CryptogramModel {
	private Random randObj;
	private String answer;
	private ArrayMap<Character, Character> encryptKey;
	private ArrayMap<Character, Character> decryptKey;
	
	
	/**
	 * Constructor used to implement the answer, 
	 * encryptKey and decryptKey fields via their respective
	 * helper methods. With the exception of decryptKey
	 * which is implemented with an empty HashMap.
	 * @throws FileNotFoundException 
	 */
	public CryptogramModel() {
		this.randObj = new Random();
		
		try {
			this.answer = pullFromFile("quotes.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.encryptKey = encrypt();
		this.decryptKey = new ArrayMap<Character, Character>();
	}
	
	/**
	 * 
	 * @param decrypt
	 * @param rand
	 */
	public CryptogramModel(ArrayMap<Character, Character> decrypt, int rand) {
		this.randObj = new Random(rand);
		
		try {
			this.answer = pullFromFile("quotes.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.encryptKey = encrypt();
		this.decryptKey = decrypt;
	}
	
	/**
	 * 
	 * @param flag
	 * @param decrypt
	 * @param rand
	 */
	public CryptogramModel(String flag, ArrayMap<Character, Character> decrypt, int rand) {
		this.randObj = new Random(rand);
		this.answer = "THIS, IS. A TEST!";
		this.encryptKey = encrypt();
		this.decryptKey = decrypt;
	}
	
	
	/**
	 * Opens the "quotes.txt" file and selects a random 
	 * line from it. Used to implement the answer field.
	 * 
	 * @return result The String selected randomly from input file to be used as
	 * 		   the quote to encrypt.
	 */
	private String pullFromFile(String fileName) throws FileNotFoundException {
		String result = "";
		
		try {
		File file = new File(fileName);
		Scanner scan = new Scanner(file);
		String line = scan.nextLine();
		
		// This list allows for all elements in file to have possibility of
		//selection while keeping code as simple as possible.
		List<String> lines = new LinkedList<String>();
		
		while (scan.hasNext()) {
		     lines.add(line);
		     line = scan.nextLine();
		}
		
		lines.add(line);
		String randomLine = lines.get(randObj.nextInt(lines.size()));
		result = randomLine;		
		scan.close();
		
		}
		catch(Exception e) {  
			e.printStackTrace();  
		}
		return result.toUpperCase();
	}
	
	/**
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public void testPullFromFile(String fileName) throws FileNotFoundException{
		try {
			pullFromFile(fileName);	
		} 
		catch(Exception e) {  
			e.printStackTrace();  
		}
	}
		
	/**
	 * Generates an encryption key by mapping each letter
	 * of the alphabet to a randomly generated letter. Used
	 * to implement the encryptKey field.
	 * 
	 * @return result A new HashMap to be used as the encryption key.
	 */
	private ArrayMap<Character, Character> encrypt(){
		ArrayMap<Character, Character> result = new ArrayMap<Character, Character>();
		
		// Uses ASCII to place whole alphabet in a list to be shuffled
		// This allows for each letter to be randomly selected and mapped
		// to another letter once.
		List<Character> alpha = new ArrayList<Character>();
			for(int i = 0; i < 26; i++){
				alpha.add((char)(65 + i));
			}
		// Adds all mappings to result, using ASCII code.
		Collections.shuffle(alpha, this.randObj);
		for(int i = 0; i < 26; i++){
			result.put(alpha.get(i),(char)(65 + i));
		}
		// Removes any self mappings from encryption
		for (int i = 0; i < 24; i ++) {
			char currKey = alpha.get(i);
			char currVal = result.get(currKey);
			if (currKey == currVal) {
				char keySwapMate = alpha.get((i + 1) % 26);
				char valSwapMate = result.get(keySwapMate);
				result.put(currKey, valSwapMate);
				result.put(keySwapMate, currVal);
			}
		}
		
		return result;
	}

	/**
	 * Adds the letters passed into it to the
	 * decryptKey Map. These letters are a user's
	 * attempt at decrypting the quote.
	 * 
	 * @param encryptedChar The letter to replace.
	 * 
	 * @param replacementChar The letter to replace it with.
	 */
	public void setReplacement(char encryptedChar, char replacementChar) {
		decryptKey.put(encryptedChar, replacementChar);
	}
	
	/**
	 * Using the encryptKey mapping and String in the
	 * answer field, generates and returns a String
	 * representation of the encrypted quote.
	 * 
	 * @return String representation of the quote in answer field 
	 * 		   that is encrypted.
	 */
	public String getEncryptedString() {
		String result = "";
		
		for (int i = 0; i < this.answer.length(); i++) {
			char currLet = this.answer.charAt(i);
			
			
			if (this.encryptKey.containsKey(currLet)) {
				char encryptedcurrLet = this.encryptKey.get(currLet);
				result += encryptedcurrLet;
				
			} else {
				result += currLet;
			}
		}
		return result;
	}
	
	/**
	 * Generates a String representation of the 
	 * user's attempts at decrypting the quote.
	 * 	NOTE: If a letter from the decryption has
	 * 	not yet be replaced, it is replaced
	 * 	here with an empty space.
	 * 
	 * @return result The String representation of the user's guesses from the
	 * 				  decryptKey field.
	 */
	public String getDecryptedString() {
		String result = "";
		String crypticWord = getEncryptedString();
		
		for (int i = 0; i < crypticWord.length(); i++) {
			char currLett = crypticWord.charAt(i);
			
			if (this.decryptKey.containsKey(currLett)) {
				result += decryptKey.get(currLett);
			} else if (this.encryptKey.containsKey(currLett)) {
				result += " ";
			} else {
				result += currLett;
			}
		}
		return result;
	}
	
	/**
	 * Returns the actual, non-encrypted quote stored in the answer field
	 * as a String Object.
	 * 
	 * @return String representation of the unencrypted quote.
	 */
	public String getAnswer() {
		return this.answer;
	}
	
	
	/**
	 * Returns the Map used to store the user's guesses so far,
	 * stored in the decryptKey field as a Map of Character Objects 
	 * to Character Objects.
	 * 
	 * @return Map<Character, Character> stored in the decryptKey field.
	 */
	public Map<Character, Character> getDecryptionField() {
		return this.decryptKey;
	}
	
	/**
	 * Returns the Map used to store the encryption key,
	 * stored in the encryptKey field as a Map of Character Objects 
	 * to Character Objects.
	 * 
	 * @return Map<Character, Character> stored in the encryptKey field.
	 */
	public Map<Character, Character> getEncryptKey() {
		return this.encryptKey;
	}
	
	
}
