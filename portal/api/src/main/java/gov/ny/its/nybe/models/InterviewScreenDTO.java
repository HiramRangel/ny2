package gov.ny.its.nybe.models;
import java.util.List;

public class InterviewScreenDTO {
    private List<ControlBaseDTO> controls;

    // Add other properties as needed

    // Getters and setters

    public List<ControlBaseDTO> getControls() {
        return controls;
    }

    public void setControls(List<ControlBaseDTO> controls) {
        this.controls = controls;
    }
}