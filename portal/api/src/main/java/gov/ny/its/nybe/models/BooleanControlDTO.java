package gov.ny.its.nybe.models;

public class BooleanControlDTO extends ControlBaseDTO {
    private String attributeId;
    private boolean currentValue;

    // Getters and setters

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public boolean isCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(boolean currentValue) {
        this.currentValue = currentValue;
    }
}