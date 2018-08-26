/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.util;

import com.neurotec.biometrics.client.NBiometricClient;
import com.neurotec.licensing.NLicense;
import com.sf.plugins.template.extractor.Extractor;
import nw.commons.logging.Loggable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Uchechukwu Onuoha
 */
public final class FingersTools extends Loggable {

    private static FingersTools instance;

    public static FingersTools getInstance() {
        synchronized (FingersTools.class) {
            if (instance == null) {
                instance = new FingersTools();
            }
            return instance;
        }
    }

    private final Map<String, Boolean> licenses;
    private final NBiometricClient client;
    private final Extractor extractor;

    private FingersTools() {
        licenses = new HashMap<String, Boolean>();
        client = new NBiometricClient();
        extractor = new Extractor();
    }

    private boolean isLicenseObtained(String license) {

        for (String l : licenses.keySet()) {
            logger.debug("==LICENSE FOUND: " + l + "; obtained?: " + licenses.get(l));
        }
        if (licenses.containsKey(license)) {
            return licenses.get(license);
        } else {
            return false;
        }
    }

    public boolean obtainLicenses(List<String> names, String host, int port) throws IOException {
        if (names == null) {
            return true;
        }
        boolean result = true;
        for (String license : names) {
            if (isLicenseObtained(license)) {
                logger.debug(license + ": " + " already obtained");
            } else {
                boolean state = NLicense.obtainComponents(host, port, license);
                logger.debug("==Retrieved state: " + host + port + license);
                licenses.put(license, state);
                if (state) {
                    logger.debug(license + ": obtained");
                } else {
                    result = false;
                    logger.debug(license + ": not obtained");
                }
            }
        }
        return result;
    }

    public void releaseLicenses() {
        Set<Entry<String, Boolean>> entries = licenses.entrySet();
        StringBuilder sb = new StringBuilder(256);
        for (Entry<String, Boolean> entry : entries) {
            if (entry.getValue()) {
                sb.append(entry.getKey()).append(',');
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            try {
                System.out.print("Releasing licenses... ");
                NLicense.releaseComponents(sb.toString());
                System.out.print("done.\n");
                licenses.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("Releasing licenses... Nothing to release.\n");
        }
    }

    public Map<String, Boolean> getLicenses() {
        return licenses;
    }

    public NBiometricClient getClient() {
        return client;
    }

    public Extractor getExtractor() {
        return extractor;
    }

}
