package com.graphtype;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

class JasyptConfigAESTest {
//    @Value("${jasypt.encryptor.password}")
    private String PASSWORD = System.getProperty("jasypt.encryptor.password");;

    @Test
    void stringEncryptor() {
//        String url = "db_url";
        String accessKey = "AKIAXOIVJXCVI2YGMXND";
        String secretKey = "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";

//        System.out.println(jasyptEncoding(url));
        String eAccessKey = jasyptEncrypt(accessKey);
        System.out.println(eAccessKey);

        String eSecretKey = jasyptEncrypt(secretKey);
        System.out.println(eSecretKey);

        Assertions.assertThat(accessKey).isEqualTo(jasyptDecryt(eAccessKey));
        Assertions.assertThat(secretKey).isEqualTo(jasyptDecryt(eSecretKey));
    }

    private String jasyptEncrypt(String input) {
        String key = PASSWORD;
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecryt(String input){
        String key = PASSWORD;
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }
}