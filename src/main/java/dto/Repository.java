package dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Repository {
    public static List<Repository> repositories = new ArrayList<>();
    private int repoIndex;
    private String repoLink;
    private String localLink;
    private List<String> team;

    public Repository(String[] teamData) {
        this.repoIndex = Integer.parseInt(teamData[0]);
        this.team = Arrays.asList(teamData[1].split(","));
        this.repoLink = teamData[2];
        this.localLink = this.repoIndex + "/";
    }

    public boolean isTeamProject() {
        if (team.size() > 1) {
            return true;
        }
        return false;
    }

    public String show() {
        return "ID: " + repoIndex + " Team: " + team.toString() + " URL: " + repoLink +" local: "+ localLink;
    }

    public void setRepoIndex(int repoIndex) {
        this.repoIndex = repoIndex;
    }
    public int getRepoIndex()
    {
        return this.repoIndex;
    }
    public void setLocalLink(String localLink)
    {
        this.localLink = localLink;
    }
    public String getLocalLink ()
    {
        return this.localLink;
    }
    public void setRepoLink(String RepoLink)
    {
        this.repoLink = RepoLink;
    }
    public String getRepoLink()
    {
        return this.repoLink;
    }


}
