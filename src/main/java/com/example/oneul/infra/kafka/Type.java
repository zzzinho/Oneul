package com.example.oneul.infra.kafka;

public enum Type {
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    final String type;
    
    Type(String type){
        this.type = type;
    }
}
