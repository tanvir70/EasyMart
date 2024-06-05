package com.tanvir.easymart.exceptions;

public class OptimisticLockingFailureException extends RuntimeException {
    public OptimisticLockingFailureException(String s) {
        super(s);
    }
}
