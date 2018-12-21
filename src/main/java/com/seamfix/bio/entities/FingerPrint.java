package com.seamfix.bio.entities;

import com.sf.bioregistra.entity.BaseEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 *
 * @author Uche
 *
 */
@Document(collection = "finger_print")
public class FingerPrint extends BaseEntity {

    /**
     * ID of the project data belongs to
     */
    private String pId;

    /**
     * ID of the project configuration
     */
    private String cId;

    /**
     * unique ID of the capture
     */
    @Indexed(unique = true)
    private String uniqueId;

    /**
     * list of fingerprints captured
     */
    @Embedded
    private ArrayList<DataUnit> fingers;

    /**
     * time data was captured in client
     */
    private Long captured;

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

}
