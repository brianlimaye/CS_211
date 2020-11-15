import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpressionEvaluator2 extends ExpressionEvaluator {

	public static List<String> scanForLiterals(String expr) {

		List<String> literals = new ArrayList<String>();

		StringBuilder currentLiteral = new StringBuilder();

		for(int i = 0; i< expr.length(); i++) {

			char currentChar = expr.charAt(i);

			if(isAlpha(currentChar)) {

				currentLiteral.append(currentChar);
			}
			else {

				if(currentLiteral.toString().length() > 0) {

					literals.add(currentLiteral.toString());
					currentLiteral.setLength(0);
				}
			}
		}

		return literals;
	}

	public static String readValueInput(List<String> literals, String expr) {

		Scanner sc = new Scanner(System.in);

		String newExpr = expr;

		for(String currentLiteral: literals) {

			System.out.println("What is the value of \'" + currentLiteral + "\'?");
			String input = sc.next();
			newExpr = newExpr.replaceFirst(currentLiteral, " " + input + " ");
			System.out.println("newExpr steply is: " + newExpr);
		}

		return newExpr;
	}

	public static Double evaluate(String expr) {

		List<String> inputLiterals = scanForLiterals(expr);
		String newExpr = readValueInput(inputLiterals, expr);

		System.out.println("newExpr is: " + newExpr);

		return ExpressionEvaluator.evaluate(newExpr);
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

		System.out.println(evaluate("((+ (- l) (* 3 3 4)  (* (/ 3 s d) (* k ll))))"));
	}

}