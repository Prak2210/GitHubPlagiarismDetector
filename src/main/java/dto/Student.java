package dto;

import java.io.File;

public class Student {
	private int studentID;
	public static List<Student> authors = new ArrayList<>();
	//private
	private string studentRepoURL;

	//this may or may not be empty

	public getStudentID(){}
	public setStudentID(){}
	public getStudentRepoURL(){}
	public setStudentRepoURL(){}
	public getStudentLocalFiles(){}
	public setStudentLocalFiles(){}

	public Student initialize(File studentDetails) {
//		initialize student details.
//		keep details of all the students, handles initialization, and repository url
		authors = read(studentDetails);
	}
	

}
