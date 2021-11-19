package com.online.www.exception;

import lombok.Data;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
public class AuthException extends RuntimeException{
    private String msgDes;

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
        msgDes = message;
    }

    public String getMsgDes() {
        return msgDes;
    }
}
