import java.util.ArrayList;

/**
 *Represents a collection of students and the courses that are available.
 *@author Brian Limaye
 *@version 1.0
 *@since 1.0
*/
public class Registrar {

	/**
	 *Represents the minimum GPA to graduate.
	*/
	public static float minGPA = 3.0f;
	/**
	 *Represents the minimum credits to graduate.
	*/
	public static int minCredits = 10;
	/**
	 *Represents a collection of literals that represent the minimum grades for courses at GMU.
	*/
	public static ArrayList<String> minGrades = new ArrayList<String>();


	/**
	 *Sets the minimum grade of a course.
	 *@param course The course that is to be manipulated (must be a valid course)
	 *@param grade The grade that corresponds to the minimum grade of that course.
	*/
	public static void setMinimumGrade(Course course, float grade) {

		
		if(Float.compare(grade, 0f) < 0) {

			return;
		}

		ArrayList<Course> courses = Student.getAllCourses();

		for(int i = 0; i< courses.size(); i++) {

			if(courses.get(i).toString().equals(course.toString())) {

				minGrades.set(i, course.toString() + ": " + grade);
			}
		}

		minGrades.add(course.toString() + ": " + grade);

	}

	/**
	 *Gets the minimum grade of a given course (assuming that is exists)
	 *@param course The course that is used to lookup the minimum grade.
	 *@return Returns the minimum grade for that given course
	 *@throws Exception Thrown if the course was not found in the registry.
	*/
	public static float getMinimumGrade(Course course) throws Exception {

		ArrayList<Course> courses = Student.getAllCourses();
		float minimumGrade = 0f;
		boolean isFound = false;

		for(String s: minGrades) {

			int index = s.indexOf(":");
			String info = s.substring(0, index);

			if(info.equals(course.toString())) {

				minimumGrade = Float.parseFloat(s.substring(index + 1).trim());
				isFound = true;
				break;
			}
		}

		if(!isFound) {

			throw new Exception(course.getCode() + " was not found in the registry");
		}

		return minimumGrade;
	}

	/**
	 *Performs graduation of a collection of students.
	 *@param s A collection/array of students that are evaluated for graduation.
	*/
	public static void class2020(Student[] s) {

		for(Student student: s) {

			float totalGPA = 0f;
			try {

				totalGPA = student.graduation();
				System.out.println("Congrats to " + student.getName() + " for graduating with GPA " + totalGPA);
			}
			catch(StudentException se) {

				System.out.println(se.getMessage());
			}
			catch(UniversityException ue) {

				System.err.println("Sorry," + ue.getMessage());
			}
		}
	}


}