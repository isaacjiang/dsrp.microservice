package com.edp.system;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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



    public static JSONObject JSONObjectFileReader(String fileName){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(new String(Files.readAllBytes(Paths.get(fileName))));
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray JSONArrayFileReader(String fileName){
        JSONArray jsonObject = null;

        try {
            jsonObject = new JSONArray(new String(Files.readAllBytes(Paths.get(fileName))));
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
