package gov.ny.its.nybe.models;

public class TextControlDTO extends ControlBaseDTO {
    private String attributeId;
    private String controlState;
    private String controlOrientation;

    // Getters and setters

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getControlState() {
        return controlState;
    }

    public void setControlState(String controlState) {
        this.controlState = controlState;
    }

    public String getControlOrientation() {
        return controlOrientation;
    }

    public void setControlOrientation(String controlOrientation) {
        this.controlOrientation = controlOrientation;
    }
}