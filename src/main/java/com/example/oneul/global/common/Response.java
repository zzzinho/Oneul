package com.example.oneul.global.common;

import javax.validation.constraints.NotBlank;

public class Response {
    @NotBlank
    private Code code;
    @NotBlank
    private String message;

    public Code getCode(){
        return this.code;
    }

    public void setCode(Code code){
        this.code = code;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public Response() {}

    public Response(Code code, String message){
        this.code = code;
        this.message = message;
    }
}
