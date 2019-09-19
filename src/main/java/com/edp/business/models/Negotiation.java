package com.edp.business.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "negotiation")
public class Negotiation {
    @Id
    private String id;
    private String status;
    private String category;


    public Negotiation() {
//        setId(Utilities.GenerateId());
    }


}
