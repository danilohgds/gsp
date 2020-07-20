package com.example.producingwebservice;

import org.springframework.security.core.AuthenticationException;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.SERVER,
faultStringOrReason = "An Error")
public class CustomException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }
}
