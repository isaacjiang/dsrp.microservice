package com.edp.system;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class Utilities {

    public static String GenerateId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * Encoding method from passwordEncoderFactory
     */
    public static String passwordEncode(String password) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(password);
    }
}
