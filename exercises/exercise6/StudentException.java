/**
 *This class represents a StudentException, which is thrown when a student is graduating when he/she took no courses, or is trying to add a course already taken.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/

public class StudentException extends RuntimeException {
	
	/**
	 *Default constructor for initializing a StudentException.
	*/
	public StudentException() {

		super();
	}

	/**
	 *One argument constructor for initializing a StudentException.
	 *@param message The error message to be included when throwing this exception.
	*/

	public StudentException(String message) {

		super(message);		
	}
}