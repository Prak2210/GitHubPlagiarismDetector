package dto;

import java.util.ArrayList;
import java.util.List;


/*
there will be multiple assignments
*/

public class Assignment {
    private final int ASSIGNMENT_ID; //input from the user
    List<Student> students = new ArrayList<>(); //authors suits more and handle teams first

    public Assignment(int assignment_id, List<Student> students) {
        ASSIGNMENT_ID = assignment_id;
        this.students = students;
    }
    /*
     * method to get all owners of 1 repository plus the link to the repository.
     *
     */

}
