import java.util.Random;
import javax.swing.JOptionPane;

public class Bool_Hit_Game {
	
	private static final int HARD_GUESSES_AMOUNT = 7;
	private static final int MEDIUM_GUESSES_AMOUNT = 10;
	private static final int EASY_GUESSES_AMOUNT = 13;

	// This function requests number of DificultyLevel from user 
	public static int getValidDificultyLevel(){

		String massage = "Enter valid difculty level:\n"
				+ "1-Easy - 13 guesses\n"
				+ "2-Medium - 10 guesses\n"
				+ "3-Hard - 7 guesses\n";

		String strDificultyLevel = JOptionPane.showInputDialog(massage);
		int difficultyLevel = Integer.parseInt(strDificultyLevel);

		if(difficultyLevel < 0 || difficultyLevel > 3){		
			JOptionPane.showMessageDialog(null,"Enter valid difculty level 1-Easy, 2-Medium, 3-Hard");
			difficultyLevel = getValidDificultyLevel();
		}
		return difficultyLevel;
	}

	// This function receives difficulty level number and according difficulty level returns number of guesses 
	public static int getNumOfGuesses(int difficultyLevel) {

		if (difficultyLevel == 1){
			return EASY_GUESSES_AMOUNT;
		}
		else if(difficultyLevel == 2){
			return MEDIUM_GUESSES_AMOUNT;
		}
		return HARD_GUESSES_AMOUNT;
	}
	// This function generates computer game number in range between 1234 - 9876
	public static int generateComputerGameNumber() {

		Random rnd = new Random();
		int gameNumberToReturn = rnd.nextInt(8634)+1234;

		if(isThereDuplicationsDigitsInTheNum(gameNumberToReturn)){
			gameNumberToReturn = generateComputerGameNumber();
		}
		
		return gameNumberToReturn;
	}
	// This function receives integer number and checks it for duplicates
	public static boolean isThereDuplicationsDigitsInTheNum(int num){

		
		boolean haveDuplications = false;
		boolean [] duplicatesNums = new boolean[10];
		while(num != 0){			
			for(int i = 0; i < duplicatesNums.length; i ++ ){
				if (num % 10 == i){
					if(duplicatesNums[i]){
						return !haveDuplications;
					}
					else
						duplicatesNums[i] = true;
				}
			}
			num = num/10;
		}

		return haveDuplications;
	}
	// This function receives integer number and separates the number to digits returns array of digits of the number
	public static int[] generateArrayOfSeparetedNums(int generatedNumber) {

		int tempNum = generatedNumber;
		int counter = 0;
		while(tempNum != 0){		
			counter++;
			tempNum /= 10;
		}			
		int[] separatedNamberArray = new int [counter];		
		for (int i = counter-1; i >=0 ; i--) {
			separatedNamberArray[i] = generatedNumber % 10;
			generatedNumber /=  10;			
		}
		return separatedNamberArray;
	}

	// This receives function integer number represents difficulty level of the game and starts the game
	public static void playTheGame(){		
		
		int difficultyLevel = Bool_Hit_Game.getValidDificultyLevel();
		int generatedComputerNumber = generateComputerGameNumber();		
		int[] computerGameNumber = generateArrayOfSeparetedNums(generatedComputerNumber);
		boolean isGameContinue = true;
		int numOfGuesses = getNumOfGuesses(difficultyLevel);
		String gameOverMassage = "Game is over.\n"
				+ "Thank You! Buy!";

		while (numOfGuesses > 0 && isGameContinue){				

			String massage = numOfGuesses + " Guesses left.\n"
					+ "Enter your number:";

			String invalidNumberMassage = "Invalid number!\n"
					+ "Please enter number without duplicates\n"
					+ "in range between 1234 - 9876\n";																				

			String strGuess =JOptionPane.showInputDialog(massage);
			int guess = Integer.parseInt(strGuess);

			while(isThereDuplicationsDigitsInTheNum(guess) || guess > 9999 || guess < 999){
				JOptionPane.showMessageDialog(null, invalidNumberMassage);
				strGuess = JOptionPane.showInputDialog(massage);
				guess = Integer.parseInt(strGuess);
			}

			int[] playerGuessNumber = generateArrayOfSeparetedNums(guess);

			int countBool=0;
			int countHit = 0;

			for(int i = 0; i < computerGameNumber.length; i++){
				for(int j = 0; j < playerGuessNumber.length; j++){
					if (computerGameNumber[i]==playerGuessNumber[j]) {
						if (i==j){
							countBool++;
						} else {
							countHit++;
						}	
					}
				}
			}

			if(countBool==4){
				JOptionPane.showMessageDialog(null, "Evelina Wins!\n"
						+ "Number is " + generatedComputerNumber + ".");
				isGameContinue = false;
			}
			else{
				if(countBool >= 2 )
					countHit -= 1;
				JOptionPane.showMessageDialog(null, "Bools :  " + countBool + "\n"
						+ "Hits : " + countHit + "\n guess" + guess);
			}
			numOfGuesses--;
		}

		JOptionPane.showMessageDialog(null, gameOverMassage );
	}	

}
