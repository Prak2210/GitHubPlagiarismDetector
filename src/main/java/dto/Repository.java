package dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Repository {
    public static List<Repository> repositories = new ArrayList<>();
	private int repoIndex; // TODO: change to repoIndex and its getters and setters
	private string repoIndex;
	private string repoLink;
	private string localLink;
	private List<String> team; // each string in this list will be an unity id.
	//for student(s) working on this repo


    //gettter and setter
    List<Student> team = new ArrayList<>();

    public getrepoID(){}
    public setrepoID(){}

    public getrepoURL(){}
    public setrepoURL(){}

    public mostRecentSourceFile(){}
    public mostRecentSourceFile(){}

    public Repository initialize(List<Student> teams, Assignment assignment) {

        for(Student team: teams) {
            Repository repo = new Repository(team);
            repositories.add(repo);
        }

    }

    /*
    if it is a team project, the repo is owned by more than one students, 
    so students list will not be empty

    Hence, isTeamProject will return true if students is not empty
    otherwise false
    */
    public isTeamProject(){}

}
