package com.seamfix.bio.util;

import org.keyczar.Crypter;
import sfx.crypto.CryptoReader;

import javax.annotation.PostConstruct;
import nw.commons.logging.Loggable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*

@modfied Uche

 */
@Component
public class CryptManager extends Loggable {

    private Crypter crypter;

    @Value("${crypto-path}")
    private String mapPath;

    @PostConstruct
    public void init() {
        CryptoReader cr = new CryptoReader(mapPath);
        try {
            crypter = new Crypter(cr);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
    }

    public Crypter getCrypter() {
        return crypter;
    }
}
