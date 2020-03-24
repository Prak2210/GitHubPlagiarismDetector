import dto.Assignment;
import dto.Repository;
import dto.Results;
import dto.Student;

import java.io.File;

public class PlagiarismChecker {

    /* class keeps the track of timer variable which will invoke "Git pull" after a particular time frame
    * */

    public void run(int assignmentID, File studentFile) {
        /*
        *
        *  Reads from an excel sheet about student details and initializes student and assignment data.
        *  returns List<Student>
        *
        * */
        Assignment assignment = new Assignment(assignmentID, null);
        Student student = new Student();
        Repository repository = new Repository();
        // repository needs to be looped on inititalize ... for now no need of student class

        student.initialize(studentFile);
        repository.initialize(student);

        Results results = Compare.compare(repository);

        results.displayResults();

    }

    public static void main(String a[]) { // will be replaced by Spring Boot framework
        int assignmentID = 10; // will be replaced by API call
        File students = new File("StudentDetails");  // csv
        run(assignmentID, students);
    }
}
