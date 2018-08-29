package com.seamfix.bio.job.processors;

import com.seamfix.bio.util.FingersTools;
import com.seamfix.util.CompressorWriter;
import com.seamfix.bio.entities.CapturedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.entities.DataUnit;
import com.seamfix.bio.mongodb.dao.CapturedDataMongoRepository;
import java.util.ArrayList;
import java.util.Base64;

public class CapturedDataProcessor implements ItemProcessor<CapturedData, CapturedData> {

    private static final Logger log = LoggerFactory.getLogger(CapturedDataProcessor.class);

    private final CapturedDataMongoRepository capturedDataMongoRepository;

    public CapturedDataProcessor(CapturedDataMongoRepository capturedDataMongoRepository) {
        this.capturedDataMongoRepository = capturedDataMongoRepository;

    }

    @Override
    public CapturedData process(CapturedData inData) throws Exception {
        log.info("migration in progress!");
        CapturedData outData = capturedDataMongoRepository.findByUniqueIdQuery(inData.getUniqueId()).get(0);
        /////////////////

        ArrayList<DataUnit> oldSignDataUnits = inData.getSignature();
        ArrayList<DataUnit> updateSignDataUnits = outData.getSignature();
        ArrayList<DataUnit> oks = new ArrayList<>();
        if (updateSignDataUnits != null) {
            for (DataUnit upDateUnit : updateSignDataUnits) {
                if (upDateUnit != null) {
                    for (DataUnit oldDataUnit : oldSignDataUnits) {
                        if (oldDataUnit != null) {
                            if (oldDataUnit.getLabel().equalsIgnoreCase(upDateUnit.getLabel())) {
                                upDateUnit.setId(oldDataUnit.getId());
                                oks.add(upDateUnit);
                                break;
                            }

                        }

                        //
                    }
                }
            }
        }
        if (!oks.isEmpty()) {
            outData.setText(oks);
        }

        ////////////////
        ArrayList<DataUnit> oldTextDataUnits = inData.getText();
        ArrayList<DataUnit> updateTextDataUnits = outData.getText();
        ArrayList<DataUnit> okt = new ArrayList<>();
        if (updateTextDataUnits != null) {
            for (DataUnit upDateUnit : updateTextDataUnits) {
                if (upDateUnit != null) {
                    for (DataUnit oldDataUnit : oldTextDataUnits) {
                        if (oldDataUnit != null) {
                            if (oldDataUnit.getLabel().equalsIgnoreCase(upDateUnit.getLabel())) {
                                upDateUnit.setId(oldDataUnit.getId());
                                okt.add(upDateUnit);
                                break;
                            }

                        }

                        //
                    }
                }
            }
        }
        if (!okt.isEmpty()) {
            outData.setText(okt);
        }

        /////////////////
        ArrayList<DataUnit> oldPixDataUnits = inData.getPix();
        ArrayList<DataUnit> updatePixDataUnits = outData.getPix();
        ArrayList<DataUnit> ok = new ArrayList<>();
        if (updatePixDataUnits != null) {
            for (DataUnit upDateUnit : updatePixDataUnits) {
                if (upDateUnit != null) {
                    for (DataUnit oldDataUnit : oldPixDataUnits) {
                        if (oldDataUnit != null) {
                            if (oldDataUnit.getLabel().equalsIgnoreCase(upDateUnit.getLabel())) {
                                upDateUnit.setId(oldDataUnit.getId());
                                ok.add(upDateUnit);
                                break;
                            }

                        }

                        //
                    }
                }
            }
        }
        if (!ok.isEmpty()) {
            outData.setPix(ok);
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
                            updatedfingerUnits.add(unit);
                        } else {
                            updatedfingerUnits.add(unit);
                        }

                    }
                }
            }
        }
        if (!updatedfingerUnits.isEmpty()) {
            outData.setFingers(updatedfingerUnits);
        }
        capturedDataMongoRepository.save(outData);
        return outData;
    }

}
