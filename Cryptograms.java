/**							J + M + J
 * @author Joseph Gauthier
 * 			This main, Cryptograms class starts and continues a cryptogram game 
 * 			where the user is shown an encrypted quote that was pulled 
 * 			from a file filled with quotes for the program to randomly choose.
 * 			The user then has different options to choose from to help them
 * 			win the game as quickly  as possible. 
 * 			help: Lists off all command options available.
 * 			exit: Exits the game.
 * 			hint: Displays a pairing not yet guessed.
 * 			freq: Displays how often each letter of the alphabet appears in the encryption.
 * 			"replace X by Y": Replaces whatever X is with whatever Y is.
 * 			X = Y: A shortened version of the previous command.
 * 			EX) 
 * 				VYJ, KVILV!
 * 
 *  			Enter a command (type help to see commands): help
 *  				
 *  			VYJ, KVILV!
 *  			replace X by Y
 *				X = Y
 *				freq
 *				hint
 *				exit
 *				help
 *
 *				Enter a command (type help to see commands): hint
 *				
 *				VYJ, KVILV!
 *				V: A
 *
 *				Enter a command (type help to see commands): freq
 *
 *				VYJ, KVILV!
 *				A: 0  B: 0  C: 0  D: 0  E: 0  F: 0  G: 0  
 *				H: 0  I: 1  J: 1  K: 1  L: 1  M: 0  N: 0  
 *				O: 0  P: 0  Q: 0  R: 0  S: 0  T: 0  U: 0  
 *				V: 3  W: 0  X: 0  Y: 1  Z: 0
 *					
 *				Enter a command (type help to see commands): replace V by A
 *				
 *				A     A  A
 *				VYJ, KVILV!
 *
 *				Enter a command (type help to see commands): Y = V
 *
 *				AV    A  A
 *				VYJ, KVILV!
 *
 *				Enter a command (type help to see commands): exit
 *
 *				NOTE) Capitalization doesn't matter, but spacing is picky.
 *				EX)
 *					VYJ, KVILV!
 * 
 *  				Enter a command (type help to see commands): V=A
 *  				
 *  				Invalid input. Try again.
 *  				
 *  				Enter a command (type help to see commands): ...
 * 
 * 				This is done by calling two other Cryptogram classes and uses 
 * 				them in their respective ways, with one, the Model class, used only
 * 				to keep track of the state of the game as the player goes along and
 * 				the other, the Controller class, being used solely to change the 
 * 				state of the Model. This implementation, although repetitive, helps
 * 				keep the program to be as clean/readable as possible with the 
 * 				Inclusion of the controller class allowing for functions to have both
 * 				a user friendly name and a programmer friendly name. 
 * 				EX) 
 * 					All the Controller method, getUserProgress(), 
 * 					does is call the Model class' getDecryptedString().
 * 					This is useful as the Controller method uses a user-friendly
 * 					name while the Model classes uses a more accurate name.
 */
import java.util.Scanner;


public class Cryptograms {
	
	public static void main(String args[]) {
		
		ArrayMap<Integer, Integer> test = new ArrayMap<Integer, Integer>();
		test.put(1, 1);
		test.put(1, 2);
		test.put(2, 2);
		test.put(3, 3);
		test.put(4, 4);
		test.put(5, 5);
		test.put(6,6);
		test.put(7,7);
		test.put(8,8);
		test.put(9,9);
		test.put(10,10);
		test.put(11,11);
		test.put(12,12);
		System.out.println(test.size());
		
		
		
//		// Sets up model, controller, and user input.
//		CryptogramModel game = new CryptogramModel();
//		CryptogramController prog = new CryptogramController(game);
//		Scanner input = new Scanner(System.in);
//		System.out.print(prog.printBoard());
//		
//		// By putting user input through controller, changes state of model
//		// and gets more user input until game is won.
//		while (!(prog.isGameOver())) {
//			System.out.print("\nEnter a command (type help to see commands): ");
//			String[] userArray = input.nextLine().toUpperCase().split(" ");
//			System.out.println();
//			
//			if (userArray[0].equals("HELP")) {
//				System.out.println(prog.printBoard());
//				System.out.println("replace X by Y\nX = Y\n"
//						+ "freq\nhint\nexit\nhelp");
//			} 
//			else if (userArray[0].equals("EXIT")) {
//				System.exit(0);
//			} 
//			else if (userArray[0].equals("HINT")) {
//				System.out.println(prog.printHint());
//			}
//			else if (userArray[0].equals("FREQ")) {
//				System.out.println(prog.printFreqs());
//			}
//			else if (userArray.length == 4 && userArray[0].equals("REPLACE")) {
//				prog.makeReplacement(userArray[1].charAt(0), userArray[3].charAt(0));
//				System.out.print(prog.printBoard());
//			}
//			else if (userArray.length == 3 && userArray[1].equals("=")) {
//				prog.makeReplacement(userArray[0].charAt(0), userArray[2].charAt(0));
//				System.out.print(prog.printBoard());
//			} 
//			else {
//				System.out.print(prog.printBoard());
//				System.out.println("\n Invalid input. Try again.");
//			}
//		}
//		// Shows final answer to user and closes input before 
//		// program ends.
//		System.out.println(prog.printBoard());
//		System.out.println();
//		System.out.println("You got it!");
//		input.close();
	}	
}
