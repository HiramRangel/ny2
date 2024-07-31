package gov.ny.its.nybe.models;

import java.util.List;

public class ContainerControlDTO extends ControlBaseDTO {
    private List<ControlBaseDTO> controls;

    // Getters and setters

    public List<ControlBaseDTO> getControls() {
        return controls;
    }

    public void setControls(List<ControlBaseDTO> controls) {
        this.controls = controls;
    }
}