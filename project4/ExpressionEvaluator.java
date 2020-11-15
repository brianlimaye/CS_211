import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

	private static List<Integer> readInputValues(List<Character> inputChars) {

		Scanner sc = new Scanner(System.in);
		List<Integer> inputs = new ArrayList<Integer>();

		for(Character c: inputChars) {

			System.out.println("What is the value of \'" + c.charValue() + "\'?");
			inputs.add(sc.nextInt());
		}

		sc.close();
		return inputs;
	}

	private static String replaceChars(String expr, List<Character> inputChars, List<Integer> inputValues) {

		String newExpr = expr;

		for(int i = 0; i< inputChars.size(); i++) {

			int indexOfChar = newExpr.indexOf(inputChars.get(i).charValue());
			newExpr = newExpr.substring(0, indexOfChar) +  " " + inputValues.get(i).toString() + " " + newExpr.substring(indexOfChar + 1);
		}

		return newExpr;
	}

	public static Double evaluate(String expr) {

		if((!isBalanced(expr)) || (isEmpty(expr)) || (!isValidExpression(expr))) {

			throw new RuntimeException();
		} 

		List<Character> inputChars = scanForCharacters(expr);

		if(inputChars.size() > 0) {

			List<Integer> inputValues = readInputValues(inputChars);
			expr = replaceChars(expr, inputChars, inputValues);
		}
		
		expr = expr.replaceAll(Pattern.quote("(+)"), "(+ 0)").replaceAll(Pattern.quote("(*)"), "(+ 1)");

		boolean needsSummative = false;
		String currentNumber = "";
		Token mainOperator = findFirstOperator(expr);
		Token currentOperator = null;
		Token secondaryOperator = null;
		Token correspondingOperator = null;

		Stack<String> s = new Stack<String>();
		Stack<Double> mainResults = new Stack<Double>();
		Stack<Double> subResults = new Stack<Double>();

		List<Double> currentOperands = new ArrayList<Double>();
		List<String> expressionParts = new ArrayList<String>();
		
		parseExpression(expressionParts, expr);
		fillStack(s, expressionParts);

		for(int i = 0; i< expressionParts.size(); i++) {

			needsSummative = false;
			String currentExpression = s.pop();
			if(!isBalanced(currentExpression)) {

				needsSummative = true;
			}

			currentOperator = new Token(findFirstOperator(currentExpression).getOperator());

			int indexOfOperator = currentExpression.indexOf(currentOperator.getOperator());

			if(needsSummative) {

				secondaryOperator = new Token(findFirstOperator(currentExpression.substring(indexOfOperator + 1)).getOperator());
			}

			for(int j = currentExpression.length() - 1; j > 0; j--) {

				char currentChar = currentExpression.charAt(j);
				Token tmp = new Token(currentChar);

				if(isNumeric(currentChar)) {

					currentNumber += "" + currentChar;
					continue;
				}

				else if(currentNumber.length() > 0) {

					currentNumber = reverseNumber(currentNumber);
					currentOperands.add(Double.parseDouble(currentNumber));
					subResults.push(Double.parseDouble(currentNumber));
					currentNumber = "";
				}
			}

			correspondingOperator = (needsSummative) ? secondaryOperator : currentOperator;

			subResults.push(evaluateSubResult(currentOperands, subResults, correspondingOperator));
			mainResults.push(subResults.pop());
			subResults.clear();
			currentOperands.clear();

			if(needsSummative) {

				mainResults.push(performMajorOperation(mainResults, currentOperator));
			}
		}
		return performMajorOperation(mainResults, mainOperator);
	}

	private static List<Character> scanForCharacters(String input) {

		List<Character> inputValues = new ArrayList<Character>();

		for(int i = 0; i< input.length(); i++) {

			char currentChar = input.charAt(i);
			
			if(isAlpha(currentChar)) {

				inputValues.add(currentChar);
			}
		}

		return inputValues;
	}


	public static boolean isBalanced(String expr) {

		return numOfOccurrences("(", expr) == numOfOccurrences(")", expr);
	}

	private static Double evaluateSubResult(List<Double> currentOperands, Stack<Double> subResults, Token operator) {

		Character operatorType = operator.getOperator();
		Double subResult = 0.0;

		if((currentOperands.size() == 0) && (operator.takesNoOperands())){

			return operator.getIdentity();
		}

		if((currentOperands.size() == 1) && (operatorType.equals('-'))) {

			return -1 * currentOperands.get(0);
		}

		if((currentOperands.size() == 1) && (operatorType.equals('/'))) {

			return 1.0 / currentOperands.get(0);
		}

		if(currentOperands.size() == 1) {

			return currentOperands.get(0);
		}

		subResult = subResults.pop();
		for(int i = 0; i< subResults.size(); i++) {

			subResult = operator.applyOperator(subResult, subResults.get(i));
		}

		return subResult;
	}

	private static void parseExpression(List<String> expressionParts, String expression) {

		do {
			int openingParenthesisIndex = expression.indexOf('(');
			int closingParenthesisIndex = expression.indexOf(')');

			if((openingParenthesisIndex != -1) && (closingParenthesisIndex != -1)) {

				String sector = expression.substring(openingParenthesisIndex, closingParenthesisIndex + 1);
				expressionParts.add(sector);
				expression = expression.substring(closingParenthesisIndex + 1);
				continue;
			}
			break;
		}
		while(expression.length() > 0);
	} 

	private static boolean isEmpty(String expr) {

		return (expr.trim().length() == 0);
	}

	private static Token findFirstOperator(String prim) {

		for(int i = 0; i< prim.length(); i++) {

			Token tmp = new Token(prim.charAt(i));

			if(tmp.isOperator()) {

				return tmp;
			}
		}

		return null;
	}

	private static Double performMajorOperation(Stack<Double> values, Token operator) {

		Double total = 0.0;

		List<Double> operands = new ArrayList<Double>();

		while(values.size() != 0) {

			operands.add(values.pop());
		}

		if(operands.size() > 0) {

			total = operands.get(0);
		}

		for(int i = 1; i< operands.size(); i++) {

			total = operator.applyOperator(total, operands.get(i));
		}

		return total;
	}
	private static void fillStack(Stack<String> s, List<String> list) {

		Token currentOperator = null;

		for(int i = 0; i < list.size(); i++) {

			s.push(list.get(i));
		}
	}

	private static int numOfOccurrences(String substr, String expression) {

		int occurrences = 0;
		int currentIndex = 0;

		while((currentIndex = expression.indexOf(substr)) != -1) {

			occurrences++;
			expression = expression.substring(currentIndex + 1);
		}

		return occurrences;
	}

	private static boolean isValidExpression(String expr) { // Psedo-Test: (+ (- 6 7) (* 234 455 256) (/ (/ 3) (*) (-2 3 1)))

		List<String> parts = new ArrayList<String>();
		List<String> operandDigits = new ArrayList<String>();
		boolean foundOperator = false;
		Token lastOperator = null;

		parseExpression(parts, expr);

		for(String currentExpression: parts) {

			for(int i = 0; i< currentExpression.length(); i++) {

				char currentChar = currentExpression.charAt(i);
				Token tmp = new Token(currentChar);

				if(tmp.isOperator()) {

					lastOperator = tmp;

					if(foundOperator) {

						return false;
					}

					foundOperator = true;
				}

				else if((isNumeric(currentChar)) || (isAlpha(currentChar))) {

					operandDigits.add("" + currentChar);
				}

				else if(currentChar == '(') {

					foundOperator = false;
				}

				else if(currentChar == ')') {

					if((!lastOperator.takesNoOperands()) && (operandDigits.size() == 0)) {

						return false;
					}

					if(!foundOperator) {

						return false;
					}

					foundOperator = false;
					operandDigits.clear();
					lastOperator = new Token(' ');
				}
			}
		}

		return true;
	}

	private static String reverseNumber(String s) {

		String newStr = "";

		for(int i = s.length() - 1; i >= 0; i--) {

			newStr += "" + s.charAt(i);
		}

		return newStr;
	}

	private static boolean isNumeric(char c) {

		return ((c >= 48) && (c <= 57));
	}

	private static boolean isAlpha(char c) {

		return (((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122)));
	}

	private static boolean isParenthesis(char c) {

		return ((c == '(') || (c == ')'));
	}

	public static void main(String[] args) {

		//System.out.println(ExpressionEvaluator.evaluate("(* (+ 6 3 2) (* 4 34) (/ (+ 72) (/ 20) (+ 21 (- 3) (- 1))))"));
		//System.out.println(ExpressionEvaluator.evaluate("(+ (- 4) (* 3 3 4) (/ 3 6 10) (* 7 7))"));
		System.out.println(ExpressionEvaluator.evaluate("((+ (- v2v) (* 3 3 4) (/  w3l) (* (+) r)))"));

	}
}