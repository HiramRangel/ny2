package gov.ny.its.nybe.models;
import java.util.List;

public class InvestigateRequestDTO {
    private String goalState;
    private InterviewScreenDTO screen;
    private VersionInfoTypeDTO versionInfo;

    // Getters and setters

    public String getGoalState() {
        return goalState;
    }

    public void setGoalState(String goalState) {
        this.goalState = goalState;
    }

    public InterviewScreenDTO getScreen() {
        return screen;
    }

    public void setScreen(InterviewScreenDTO screen) {
        this.screen = screen;
    }

    public VersionInfoTypeDTO getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfoTypeDTO versionInfo) {
        this.versionInfo = versionInfo;
    }
}