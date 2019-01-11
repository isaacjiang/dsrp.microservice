package com.edp.system;

import java.util.UUID;

public class Tools {

    public static String GenerateId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
