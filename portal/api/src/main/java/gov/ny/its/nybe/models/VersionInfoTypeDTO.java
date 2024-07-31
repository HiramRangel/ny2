package gov.ny.its.nybe.models;

import javax.xml.datatype.XMLGregorianCalendar;

public class VersionInfoTypeDTO {
    private XMLGregorianCalendar buildTime;
    private int deploymentVersion;
    private String versionUid;

    // Getters and setters

    public XMLGregorianCalendar getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(XMLGregorianCalendar buildTime) {
        this.buildTime = buildTime;
    }

    public int getDeploymentVersion() {
        return deploymentVersion;
    }

    public void setDeploymentVersion(int deploymentVersion) {
        this.deploymentVersion = deploymentVersion;
    }

    public String getVersionUid() {
        return versionUid;
    }

    public void setVersionUid(String versionUid) {
        this.versionUid = versionUid;
    }
}