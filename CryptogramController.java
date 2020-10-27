/**						J + M + J
 * @author Joseph Gauthier
 * 			This class is used in the Cryptograms class to provide a more human-based
 * 			interaction between the end user in Cryptograms and the base mechanics 
 * 			of the program in the Model class.
 * 
 * 				   CryptogramController(): Class constructor that sets the "model"
 * 				   						   field with the Model object passed into 
 * 										   it, allowing for easy access to the model 
 * 										   throughout this class.
 * 				   isGameOver(): Checks if the user's progress equals the orignal quote
 * 								 that was encrypted.
 * 
 *  			   makeReplacement(): Calls the Model's setReplacment() method that places
 *  								  the passed in letters into a hashMap.
 *  
 *  			   getEncryptedQuote(): Calls Model's getEncryptedString() method 
 *  									and returns the encrypted quote stored in 
 *  									Model class.
 *  
 *  			   getUsersProgress(): Calls Models's getDecryptedString() and returns
 *  								   the String generated from the user's attempts
 *  								   stored in the HashMap mentioned in 
 *  								   makeReplacement()
 * 
 */
import java.util.HashMap;
import java.util.Map;

public class CryptogramController {
	private CryptogramModel model;

	/**
	 * Class constructor that sets the "model"
	 * field with the Model object passed into 
	 * it, allowing for easy access to the model 
	 * throughout this class.
	 * 
	 * @param mo The CryptogramModel instance used to instantiate this
	 * 			 CryptogramController instance.
	 */
	public CryptogramController(CryptogramModel mo) {
		this.model = mo;		
	}
	
	
	/**
	 * Checks if the user's progress equals the original quote
	 * that was encrypted and returns if it's true or not.
	 */
	public boolean isGameOver() {
		String answer = this.getAnswer();
		String guesses = this.getUsersProgress();
		return answer.equals(guesses);
	}
	
	/**
	 * Calls the Model's setReplacment() method that places
	 * the passed in letters into a HashMap stored in the
	 * Model class.
	 * 
	 * @param letterToReplace The letter to replace (NOTE: doesn't have to actually be 
	 * 						  in encrypted quote)
	 * 
	 * @param replacementLetter  The letter to replace the letter in letterToReplace 
	 * 							 param.
	 */
	public void makeReplacement(char letterToReplace, char replacementLetter) {
		this.model.setReplacement(letterToReplace, replacementLetter);
	}
	
	/**
	 * Calls Model's getEncryptedString() method 
	 * and returns the encrypted quote stored in 
	 * Model class.
	 * 
	 * @return: String representation of the encrypted quote created in Model class.
	 */
	public String getEncryptedQuote() {
		return this.model.getEncryptedString();
	}
	
	/**
	 * Calls Models's getDecryptedString() and returns
	 * the String generated from the user's attempts
	 * stored in the HashMap mentioned in makeReplacement()
	 * 
	 * @return: String representation of the users attempts created in Model class.
	 */
	public String getUsersProgress() {
		return this.model.getDecryptedString();
	}
	
	public String printBoard() {
		/**
		 * Using the controller instance passed in, returns a string representation of
		 * the current state of the game. Prints a max of 10 characters per line and 
		 * keeps correct the alignment between the encrypted quote and the user's guesses.
		 * 
		 * @param prog The controller instance used to access the game model.
		 * 
		 * @return The String representation of the current state of the game.
		 */		
		
		String result = "";
		String currGuessStr = getUsersProgress();
		String currCryptStr = getEncryptedQuote();		
		while (currCryptStr.length() >= 10) {
			result += currGuessStr.substring(0, 9) + "\n";
			result += currCryptStr.substring(0, 9) + "\n";
			result += "\n";
				
			currGuessStr = currGuessStr.substring(9);
			currCryptStr = currCryptStr.substring(9);
		}
			
		result += currGuessStr +"\n" + currCryptStr +"\n";
		return result;
	}

	/**
	 * Prints out a correct mapping that is in the encryption key,
	 * in the encrypted quote, and not in the users' guesses so far.
	 * 
	 * @param prog The controller instance used to access the game model.
	 */
	public String printHint() {
		String result = "";
		String cryptQuote = getEncryptedQuote();
		Map<Character, Character> guessMap = getDecryptKey();
		String answer = getAnswer();
		
		for (int i = 0; i < cryptQuote.length(); i++) {
			if (!(guessMap.containsKey(cryptQuote.charAt(i)))) {
				result += cryptQuote.charAt(i) + ": " + answer.charAt(i);
				break;
			}
		}
		return result;
	}
		
	/**
	 * Prints out a neat grid of every letter in the alphabet, and for each letter,
	 * prints out how many times that letter appears in the encryption.
	 * 
	 * @param prog The controller instance used to access the game model.
	 */
	public String printFreqs() {
		String result = "";
		Map<Character, Integer> holder = new HashMap<Character, Integer>();
		for(int i = 0; i < 26; i++){
			holder.put((char)(65 + i), 0);
		}
		
		for (int i = 0; i < getEncryptedQuote().length(); i ++) {
			char currLet = getEncryptedQuote().charAt(i);
			if (!(holder.get(currLet) == null)) {
				int currCount = holder.get(currLet) + 1;
				holder.put(currLet, currCount);
			}
		}
		
		for (int i = 1; i < holder.keySet().size() + 1; i ++) {
			char currLet = (char) holder.keySet().toArray()[i - 1];
			int letVal = holder.get(currLet);
			result += currLet + ": " + letVal + "  ";
			
			if (i % 7 == 0) {
				result += "\n";
			}
		}
		return(result);
	}
	
	/**
	 * Returns the Map used to store the user's guesses so far, as a 
	 * Map of Character Objects to Character Objects.
	 * 
	 * @return Map<Character, Character> retrieved from the model class.
	 */
	public Map<Character, Character> getDecryptKey() {
		return this.model.getDecryptionField();
	}	
	
	/**
	 * Returns the actual, non-encrypted quote from the Model class.
	 * 
	 * @return String representation of the unencrypted quote.
	 */
	public String getAnswer() {
		return model.getAnswer();
	}
	
}
