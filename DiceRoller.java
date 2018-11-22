
/**
 * First Input Take as How many set of Dise are Present  for Example 3 set(i.e 3D8 - 2D4 + 4D6)
 * Enter a valid Dise Set(i.e 3D8 or 2D4 or 4D6) This is the Format
 * Enter the arithimitic operation(+ or - only allowed)
 */


import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DiceRoller {

	public static int getFinalResult(final String operator, final int operand1, final int operand2) {

		if (operator.equals("+")) {
			return operand1 + operand2;
		} else if (operator.equals("-")) {
			return operand1 - operand2;
		} else {
			throw new CustomException("Arithmetic operator not valid (Only Plus and minus allowed)");
		}
	}

	@SuppressWarnings("resource")
	static void startRoller() {
		int finalResult = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("How many Dise Set to be operate: ");
		int diseSet = sc.nextInt();
		String arithOperator = "";
		for (int i = 0; i < diseSet; i++) {
			System.out.print("Please enter " + (i + 1) + " Dise : ");
			Scanner sc1 = new Scanner(System.in);
			String inputDise = sc1.nextLine();
			inputDise = inputDise.trim();

			DiceScore ds = new DiceScore();
			int inputDiseRes = ds.getDiceResult(inputDise);
			System.out.println((i + 1) + " Dise Rolling Result:" + inputDiseRes);
			if (!(inputDiseRes > 0)) {
				System.exit(0);
			}


			if (i > 0 && "" != arithOperator) {
				int res = getFinalResult(arithOperator, finalResult, inputDiseRes);
				finalResult = res;
			} else if (finalResult == 0) {
				finalResult = inputDiseRes;
			}
			

			if (i < (diseSet - 1)) {
				System.out.print("Please enter the arithmetic operation: ");
				arithOperator = sc1.nextLine();
			}

		}

		System.out.println("Final Dise Roll Result is: " + finalResult);

	}

	public static void main(String[] args) {
		startRoller();
	}

}

class DiceScore {
	static int rollTheDice(int numberOfTime, int numberOfSides) {
		Random r1 = new Random();
		int rollTotal = 0;
		for (int count = 0; count < numberOfTime; ++count) {
			int randomNo = r1.nextInt(numberOfSides);
			++randomNo;
			rollTotal = rollTotal + randomNo;
		}
		return rollTotal;

	}

	public int getDiceResult(String inputDice) {
		if (inputDice.contains(" ")) {
			throw new CustomException("Space Not allowed ");
		} else if (!(inputDice.matches("[a-zA-Z0-9]+"))) {
			System.out.println("");
			throw new CustomException("Not a Valid Dice ");
		} else {
			String regex = "((?<=[a-zA-Z])(?=[0-9]))|((?<=[0-9])(?=[a-zA-Z]))";
			java.util.List<String> strs = Arrays.asList(inputDice.split(regex));
			if (strs.size() == 3) {

				String inputMidCharacter = strs.get(1).toUpperCase();
				if (!inputMidCharacter.equals("D")) {
					throw new CustomException("Not a Valid Format, Format should be 2D12,1D6 etc");
				}

				try {
					int noOfRolls = Integer.parseInt(strs.get(0));
					int diceSides = Integer.parseInt(strs.get(2));

					if (diceSides == 4 || diceSides == 6 || diceSides == 8 || diceSides == 10 || diceSides == 12
							|| diceSides == 20) {
						int diceRollRes = DiceScore.rollTheDice(noOfRolls, diceSides);
						return diceRollRes;
					} else {
						throw new CustomException("Support the following Dice only: 4, 6, 8, 10, 12, and 20 Sided.");
					}
				} catch (NumberFormatException e) {
					throw new CustomException("Dice Number & Sides should be integer, i.e 2D12,1D6 etc");
				}

			} else {
				throw new CustomException("Dice should be Valid format, i.e 2D12, 3D20 etc");
			}

		}
	}
}

@SuppressWarnings("serial")
class CustomException extends RuntimeException {
	public CustomException(String s) {
		super(s);
	}
}
