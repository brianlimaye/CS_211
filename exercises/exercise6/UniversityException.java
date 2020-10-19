/**
 *This class is a representation of a UniversityException, occurring from invalid input, lower than minimum gpa, lower than required credits, etc.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
 */ 

public class UniversityException extends Exception {
	
	
	/**
	 *Default constructor for initializing a UniversityException.
	*/
	public UniversityException() {

		super();
	}

	/**
	 *One argument constructor for initializing a UniversityException.
	 *@param message The error message to be included when throwing this exception.
	*/
	public UniversityException(String message) {

		super(message);
	}
}