/**
*This class stores income ranges as lower/upper bounds.
*@author Brian Limaye
*@version 1.0
*@since 1.0 
*/

public class IncomeBrackets {
	
	private float lowerBound;
	private float upperBound;

	/**
	*Instantiates an income bracket with lower/upper bounds.
	*@param lower lower bound of income range.
	*@param upper upper bound of income range.
	*@version 1.0
	*/

	public IncomeBrackets(float lower, float upper) {

		this.lowerBound = lower;
		this.upperBound = upper;
	}

	/**
	*Gets the lower bound of the given income range.
	*@return returns lower bound of range.
	*@version 1.0
	*/
	public float getLowerBound() {

		return lowerBound;
	}

	/**
	*Gets the upper bound of the given income range.
	*@return returns upper bound of range.
	*@version 1.0
	*/
	public float getUpperBound() {

		return upperBound;
	}
}