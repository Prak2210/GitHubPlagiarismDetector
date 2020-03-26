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
