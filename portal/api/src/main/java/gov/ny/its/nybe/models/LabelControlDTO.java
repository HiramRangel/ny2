package gov.ny.its.nybe.models;

import java.math.BigInteger;

public class LabelControlDTO extends ControlBaseDTO {
    private String style;
    private BigInteger width;

    // Getters and setters

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public BigInteger getWidth() {
        return width;
    }

    public void setWidth(BigInteger width) {
        this.width = width;
    }
}