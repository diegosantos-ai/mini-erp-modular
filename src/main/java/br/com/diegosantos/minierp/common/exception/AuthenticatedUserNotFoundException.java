package br.com.diegosantos.minierp.common.exception;

public class AuthenticatedUserNotFoundException extends RuntimeException {

    public AuthenticatedUserNotFoundException(String message) {
        super(message);
    }
}
