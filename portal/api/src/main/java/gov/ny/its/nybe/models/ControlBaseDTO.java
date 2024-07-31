package gov.ny.its.nybe.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "controlType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LabelControlDTO.class, name = "LabelControl"),
    @JsonSubTypes.Type(value = ContainerControlDTO.class, name = "ContainerControl"),
    @JsonSubTypes.Type(value = TextControlDTO.class, name = "TextControl"),
    @JsonSubTypes.Type(value = BooleanControlDTO.class, name = "BooleanControl")
})
public abstract class ControlBaseDTO {
    private String id;
    private String caption;
    // Add other common properties here

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}