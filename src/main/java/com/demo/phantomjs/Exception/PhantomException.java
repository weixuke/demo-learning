package com.demo.phantomjs.Exception;

public class PhantomException extends Exception {
    public PhantomException(String message) {
        super(message);
    }

    public PhantomException(String message, Throwable cause) {
        super(message, cause);
    }
}
