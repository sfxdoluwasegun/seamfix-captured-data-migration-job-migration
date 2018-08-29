/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import com.sf.bioregistra.entity.BaseEntity;
import java.util.ArrayList;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author Uchechukwu Onuoha 
 */
public class CapturedData extends BaseEntity {

    /**
     * ID of the project data belongs to
     */
    private String pId;

    /**
     * ID of the project configuration
     */
    private String cId;

    /**
     * Device tag
     */
    private String tag;

    /**
     * unique ID of the capture
     */
   @Indexed(unique=true)
    private String uniqueId;

    /**
     * list of textual data (demographics) captured
     */
    
    private ArrayList<DataUnit> text;

    /**
     * list of fingerprints captured
     */
    
    private ArrayList<DataUnit> fingers;

    /**
     * list of pictures captured
     */
    
    private ArrayList<DataUnit> pix; //using shorter field names to reduce data footprint

    /**
     * list of fileUpload data (files and signatures) captured
     */
    
    private ArrayList<DataUnit> fileUpload;

    /**
     * list of signature images captured
     */
    
    private ArrayList<DataUnit> signature;

    /**
     * time data was captured in client
     */
    private Long captured;

    /**
     * email address of the user that captured the data
     */
    private String createdBy;

    /**
     * for verifying identifiers offline
     */
    private Boolean verified;

    /**
     * the preidentifier for the capture
     */
    private String preId;

    /**
     * capture price at the time of capture
     */
    private double cost;

    /**
     * name & serial number of capture device
     */
    private String machineId;

    /**
     * [OS,Hard Disk,RAM,Total Disk,Free Disk] delimited by comma
     */
    private String machineSpecs;

    /**
     * longitude location of the capture device
     */
    private String longitude;

    /**
     * latitude location of the capture device
     */
    private String latitude;

    /**
     * location where data was captured
     */
    private String location;

    /**
     * custom generated id based on project settings
     */
    private String customId;

    /**
     * Flag to indicate if record is active, quarantined or has a status defined
     * by the custom flags
     */
    private String flag = "ACTIVE";

    /**
     * capture use case [ENROLLMENT | RE-ENROLLMENT]
     */
    private String useCase;

    /**
     * previous unique ID, for re-enrolment purposes
     */
    private String prevId;

    /**
     * field used to search for record in client for audit purposes in
     * re-enrolment
     */
    private String sField;

    /**
     * capture address of a record based computed based on lat and long
     */
    private String captureAddr;

    private Long modified;
    
    private Double appVersion;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getModified() {

        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ArrayList<DataUnit> getText() {
        return text;
    }

    public void setText(ArrayList<DataUnit> text) {
        this.text = text;
    }

    public ArrayList<DataUnit> getPix() {
        return pix;
    }

    public void setPix(ArrayList<DataUnit> pix) {
        this.pix = pix;
    }

    public ArrayList<DataUnit> getFingers() {
        return fingers;
    }

    public void setFingers(ArrayList<DataUnit> fingers) {
        this.fingers = fingers;
    }

    public Long getCaptured() {
        return captured;
    }

    public void setCaptured(Long captured) {
        this.captured = captured;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getPreId() {
        return preId;
    }

    public void setPreId(String preId) {
        this.preId = preId;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineSpecs() {
        return machineSpecs;
    }

    public void setMachineSpecs(String machineSpecs) {
        this.machineSpecs = machineSpecs;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUseCase() {
        return useCase;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    public String getPrevId() {
        return prevId;
    }

    public void setPrevId(String prevId) {
        this.prevId = prevId;
    }

    public String getSField() {
        return sField;
    }

    public void setSField(String sField) {
        this.sField = sField;
    }

    public String getCaptureAddr() {
        return captureAddr;
    }

    public void setCaptureAddr(String captureAddr) {
        this.captureAddr = captureAddr;
    }

    public ArrayList<DataUnit> getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(ArrayList<DataUnit> fileUpload) {
        this.fileUpload = fileUpload;
    }

	public Double getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Double appVersion) {
        this.appVersion = appVersion;
    }    public ArrayList<DataUnit> getSignature() {
        return signature;
    }

    public void setSignature(ArrayList<DataUnit> signature) {
        this.signature = signature;
    }

}

