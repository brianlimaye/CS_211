/**
 *Represents an individual course and its details. Details about a particular student taking this course is included.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/

public class Course {
	
	private String courseName;
	private int credits;
	private float grade;
	
	/**
	 *Two argument constructor for initalizing a Course.
	 *@param courseName Represents the name of the given course.
	 *@param credits Represents the number of credits represented by the given course.
	 *@throws UniversityException Thrown if credits are not between 1-10.
	 */
	public Course(String courseName, int credits) throws UniversityException {

		this.courseName = courseName;
		
		if(!validateCredits(credits)) {

			throw new UniversityException("Invalid number of credits for " + courseName);
		}
		this.credits = credits;
	}

	private boolean validateCredits(int credits) {

		if((credits < 1) || (credits > 10)) {

			return false;
		}

		return true;
	}

	/**
	 *Gets the name of a given course
	 *@return Returns the full university name represented by a course.
	*/
	public String getCode() {

		return courseName;
	}

	/**
	 *Gets the number of credits of a given course
	 *@return Returns the credits that a course represents.
	*/
	public int getCredits() {

		return credits;
	}

	/**
	 *Gets the grade of this particular course from a student.
	 *@return Returns the grade received in this course by a student.
	*/
	public float getGrade() {

		return grade;
	}

	/**
	 *Sets the grade of this particular course of a student.
	 *@param newGrade The new grade to set for a student's course.
	*/
	public void setGrade(float newGrade) {

		if(Double.compare(newGrade, 0f) >= 0) {

			this.grade = newGrade;
		}
	}

	/**
	 *Gets the string representation of the course.
	 *@return Returns the string representation of the info/details of the course.
	*/
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("GMU");
		sb.append(" ");
		sb.append(courseName);
		sb.append(" ");
		sb.append("|");
		sb.append(" ");
		sb.append(credits);
		sb.append(" ");
		sb.append("credits");

		return sb.toString();
	}

	/**
	 *Checks equality between two Course instances.
	 *@param o An object that should represent a Course instance.
	 *@return Returns true if the two Courses share the same name and credits.
	*/
	@Override
	public boolean equals(Object o) {

		if(o instanceof Course) {

			return ((this.courseName.equals(((Course) o).courseName)) && ((this.credits == (((Course) o).credits))));
		}

		return false;
	}
}