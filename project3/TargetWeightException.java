/**
 *This class represents a RuntimeException that is thrown when a target weight is greater than the actual weight.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class TargetWeightException extends RuntimeException {
	
	/**
	 *Default constructor that instantiates a new TargetWeightException
	 *@version 1.0
	*/
	public TargetWeightException() {

		super(); //Calls RuntimeException's constructor
	}

	/**
	 *One-arg constructor that instantiates a new TargetWeightException with a message.
	 *@param message The error message to be display when thrown.
	 *@version 1.0
	*/
	public TargetWeightException(String message) {

		super(message); //Calls RuntimeException's overloaded constructor.
	}
}