package com.seamfix.bio.job.processors;

import com.seamfix.bio.util.FingersTools;
import com.seamfix.util.CompressorWriter;
import com.sf.bioregistra.entity.CapturedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.sf.bioregistra.entity.DataUnit;
import java.util.ArrayList;
import java.util.Base64;

public class CapturedDataProcessor implements ItemProcessor<CapturedData, CapturedData> {
    
    private static final Logger log = LoggerFactory.getLogger(CapturedDataProcessor.class);
    
    @Override
    public CapturedData process(CapturedData inData) throws Exception {
        log.info("migration in progress!");
        CapturedData outData = new CapturedData();
        outData = inData;
        ArrayList<DataUnit> pixDataUnits = inData.getPix();
        ArrayList<DataUnit> updatedPixDataUnits = new ArrayList<>();
        if (pixDataUnits != null) {
            for (DataUnit unit : pixDataUnits) {
                if (unit != null) {
                    if (unit.getValue() != null) {
                        byte[] df = null;
                        try {
                            df = Base64.getDecoder().decode(unit.getValue());
                        } catch (Exception ex) {
                            log.error("Error  " + ex.getMessage());
                        }
                        if (df != null) {
                            df = CompressorWriter.compress(df, 0.7f, "jpeg");
                        }
                        if (df != null) {
                            unit.setValue(Base64.getEncoder().encodeToString(df));
                            updatedPixDataUnits.add(unit);
                        } else {
                            updatedPixDataUnits.add(unit);
                        }
                        
                    }
                }
            }
        }
        if (!updatedPixDataUnits.isEmpty()) {
            outData.setPix(updatedPixDataUnits);
        }
        ArrayList<DataUnit> fileUploadUnits = inData.getFileUpload();
        ArrayList<DataUnit> updatedfileUploadUnits = new ArrayList<>();
        if (fileUploadUnits != null) {
            for (DataUnit unit : fileUploadUnits) {
                if (unit != null) {
                    if (unit.getValue() != null) {
                        if (unit.getExt() != null && (unit.getExt().equalsIgnoreCase("jpg") || unit.getExt().equalsIgnoreCase("jpeg"))) {
                            byte[] df = Base64.getDecoder().decode(unit.getValue());
                            df = CompressorWriter.compress(df, 0.7f, "jpeg");
                            if (df != null) {
                                unit.setValue(Base64.getEncoder().encodeToString(df));
                                updatedfileUploadUnits.add(unit);
                            } else {
                                updatedfileUploadUnits.add(unit);
                            }
                            
                        } else {
                            updatedfileUploadUnits.add(unit);
                        }
                        
                    }
                }
            }
        }
        if (!updatedfileUploadUnits.isEmpty()) {
            outData.setFileUpload(updatedfileUploadUnits);
        }
        
        ArrayList<DataUnit> fingerUnits = inData.getFingers();
        ArrayList<DataUnit> updatedfingerUnits = new ArrayList<>();
        if (fingerUnits != null) {
            for (DataUnit unit : fingerUnits) {
                if (unit != null) {
                    if (unit.getValue() != null) {
                        String base64WsqString = null;
                        
                        try {
                            base64WsqString = FingersTools.getInstance().getExtractor().toWsq(unit.getValue());
                        } catch (Exception ex) {
                            log.error("Error " + ex.getMessage());
                        }
                        if (base64WsqString != null) {
                            unit.setValue(base64WsqString);
                            updatedfileUploadUnits.add(unit);
                        } else {
                            updatedfileUploadUnits.add(unit);
                        }
                        
                    }
                }
            }
        }
        if (!updatedfingerUnits.isEmpty()) {
            outData.setFingers(updatedfingerUnits);
        }
        
        return outData;
    }
}
