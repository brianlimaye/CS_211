import java.util.*;

public class Token {

	private static List<Character> validOperators = setOperators();
	private Character operator;
	private Double operand;
	private boolean isOperator;

	public Token(Character operator) {

		this.operator = operator;
		this.operand = 0.0;
		this.isOperator = true;
	}

	public Token(Double operand) {

		this.operator = ' ';
		this.operand = operand;
		this.isOperator = false;
	}

	private static List<Character> setOperators() {

		List<Character> validOperators = new ArrayList<Character>();
		validOperators.add('+');
		validOperators.add('-');
		validOperators.add('*');
		validOperators.add('/');

		return validOperators;
	}

	public Double applyOperator(Double value1, Double value2) {

		Double result = 0.0;

		switch(operator) {

			case '+':
				result = value1 + value2;
				break;
			case '-':
				result = value1 - value2;
				break;
			case '*':
				result = value1 * value2;
				break;
			case '/':
				result = value1 / value2;
				break;
		}

		return result;
	}

	public Double getIdentity() {

		Double identity = 0.0;

		switch(operator) {

			case '*':
			case '/':
				identity = 1.0;
				break;
		}

		return identity;
	}

	public boolean takesNoOperands() {

		return ((operator.charValue() == '+') || (operator.charValue() == '*'));
	}

	public boolean isOperator() {

		return validOperators.contains(operator);
	}

	public Double getValue() {

		return operand;
	}

	public Character getOperator() {

		return operator;
	}

	public String toString() {

		if(isOperator) {

			return "" + operator;
		}
		
		return "" + getValue();
	}
}