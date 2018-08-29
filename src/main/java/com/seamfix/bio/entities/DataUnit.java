/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.entities;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Uchechukwu Onuoha
 */
public class DataUnit {

    /**
     * unique identifier of the field
     */
    @Field(value = "id")
    private String id;

    /**
     * label attached to the field at the time of capture
     */
    private String label;

    /**
     * data captured in the field
     */
    private String value;

    /**
     * category of data defined by DataCategory enum (TEXT | PICTURE |
     * FINGERPRINT | FILE)
     */
    private String type;

    /**
     * file extension (for Data Category FILE)
     */
    private String ext;

    /**
     * threshold used to capture record null for text fields
     */
    private String threshold;

    /**
     * name of the device used to capture portrait or fingerprint
     */
    private String deviceName;

    /**
     * resolution of the camera or fp reader used for the capture
     */
    private String deviceRes;

    /**
     * reason why capture was skipped. Skip reasons are defined by project
     * admins
     */
    private String skipReason;

    public DataUnit() {
    }

    /**
     * constructor for text fields
     *
     * @param id
     * @param label
     * @param value
     * @param type
     */
    public DataUnit(String id, String label, String value, String type) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.type = type;
    }

    /**
     * constructor for biometrics
     *
     * @param id
     * @param label
     * @param value
     * @param type
     * @param threshold
     */
    public DataUnit(String id, String label, String value, String type, String threshold) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.type = type;
        this.threshold = threshold;
    }

    /**
     * constructor for files
     *
     * @param id
     * @param label
     * @param value
     * @param type
     * @param ext
     */
    public DataUnit(String id, String label, String value, String type, String ext, String threshold) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.type = type;
        this.ext = ext;
        this.threshold = threshold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceRes() {
        return deviceRes;
    }

    public void setDeviceRes(String deviceRes) {
        this.deviceRes = deviceRes;
    }

    public String getSkipReason() {
        return skipReason;
    }

    public void setSkipReason(String skipReason) {
        this.skipReason = skipReason;
    }
}
