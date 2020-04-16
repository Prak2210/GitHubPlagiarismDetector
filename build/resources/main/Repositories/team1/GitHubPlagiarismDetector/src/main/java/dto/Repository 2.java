package dto;

import java.util.Arrays;
import java.util.List;

public class Repository {
    private int repoIndex;
    private String repoLink; //link to their github repository
    private String localLink; // local url to access their repository on our machine
    private List<String> team; //unityids
    private static int index = 1;

    public Repository(String[] teamData) {
        this.repoIndex = index++;
        this.team = Arrays.asList(teamData[1].split(","));
        this.repoLink = teamData[2];
        this.localLink = "/team" + this.repoIndex;
    }

    public boolean isTeamProject() {
        if (team.size() > 1) {
            return true;
        }
        return false;
    }

    public String show() {
        return "ID: " + repoIndex + " Team: " + team.toString() + " URL: " + repoLink + " Local-Path: " + localLink;
    }

    public String getTeam() {
        return team.toString();
    }

    public int getRepoIndex() {
        return this.repoIndex;
    }

    public String getLocalLink() {
        return this.localLink;
    }

    public String getRepoLink() {
        return this.repoLink;
    }


}
