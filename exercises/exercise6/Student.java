import java.util.ArrayList;

/**
 *Represents a single Student and their GMU information.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class Student {
	
	private String name;
	private String year;
	private static ArrayList<Course> allCourses = new ArrayList<Course>();
	private ArrayList<Course> registeredCourses = new ArrayList<Course>();



	/**
	 *One argument constructor that creates a new Student.
	 *@param name Represents the Student's name, which must be non null/numeric/empty.
	*/
	public Student(String name) {

		this.year = "freshman";

		
		if(!validateName(name)) {

			throw new StudentException("Student name is invalid");
		}

		this.name = name;

	}

	/**
	 *Two argument constructor that creates a new Student.
	 *@param name Represents the Student's name, which must be non numeric/empty.
	 *@param year Represents the Student's year at GMU (freshman, sophomore, junior, senior)
	*/
	public Student(String name, String year) {

		
			if(!validateName(name)) {

				throw new StudentException("Student name is invalid");
			}
			this.name = name;

			if(!validateYear(year)) {

				throw new StudentException("Student must be one of freshman|sophomore|junior|senior");
			}
			this.year = year;
	}

	private boolean validateYear(String year) {

		if(year == null) {

			return false;
		}

		if((year.equals("freshman")) || (year.equals("sophomore")) || (year.equals("junior")) || (year.equals("senior"))) {

			return true;
		}

		return false;
	}

	private boolean validateName(String name) {

		if(name == null) {

			return false;
		}
		

		if(name.trim().length() == 0) {

			return false;
		}

		for(int i = 0; i< name.length(); i++) {

			char curr = name.charAt(i);
			
			if((curr >= 48) && (curr <= 57)){

				return false;
			}
		}

		return true;
	}

	/**
	 *Gets the name representation of the Student.
	 *@return Returns the name of the Student.
	*/
	public String getName() {

		return name;
	}

	/**
	 *Gets the year standing of a Student.
	 *@return Returns the year of a Student at GMU.
	*/
	public String getYear() {

		return year;
	}

	/**
	 *Gets the all courses added at GMU.
	 *@return Returns a list of the courses at GMU that have been not been added already.
	*/
	public static ArrayList<Course> getAllCourses() {

		return allCourses;
	}

	/**
	 *Adds a course to a student's gradebook if all conditions/criterion are met.
	 *@param c The course to be added to a student's gradebook
	 *@param grade The grade of that particular course to be attempted to add to the gradebook.
	 *@return Returns true if the course was added successfully, false if not.
	 *@throws UniversityException Thrown if the student does not meet the minimum grade for the course.
	 *@throws Exception Thrown if the course was not found in the registry.
	*/
	public boolean addCourse(Course c, float grade) throws UniversityException, Exception {

		boolean isAdded = false;

		if(registeredCourses.contains(c)) {

			throw new StudentException(this.name + " has already taken " + c.getCode());
		}

		if(Float.compare(grade, Registrar.getMinimumGrade(c)) >= 0) {

			c.setGrade(grade);
			registeredCourses.add(c);
			allCourses.add(c);
			isAdded = true;
		}
		else {

			throw new UniversityException(c.getCode() + " requires a grade greater than or equal to " + Registrar.getMinimumGrade(c));
		}

		return isAdded;
	}

	/**
	 *Performs graduation of a given student and determines if they are eligible based on a protocol. The student must have taken courses first off.
	 *@return Returns the final GPA of a student (assuming they are eligible for graduation).
	 *@throws UniversityException Thrown if the Student's GPA is lower than the minimum to graduate and/or the Student does not have enough credits.
	*/

	public float graduation() throws UniversityException {

		if(registeredCourses.size() == 0) {

			throw new StudentException(this.name + " hasn't taken any courses yet");
		}

		float totalGrades = 0f;
		float totalCredits = 0f;
		int numOfCourses = 0;
		float gpa = 0f;

		for(Course c: registeredCourses) {

			totalGrades += c.getGrade();
			totalCredits += c.getCredits();
			++numOfCourses;
		}

		
		if(Float.compare(numOfCourses, 0f) > 0) {

			gpa = totalGrades / numOfCourses;
		}
		

		if(Float.compare(gpa, Registrar.minGPA) < 0) {

			throw new UniversityException(this.name + "'s GPA is lower than the minimum required");
		}

		if(Float.compare(totalCredits, Registrar.minCredits) < 0) {

			throw new UniversityException(this.name + " doesn't have enough credits to graduate");
		}

		return gpa;
	}
}