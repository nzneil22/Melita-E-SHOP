package com.melita_task.api.exceptions;

public class ClientInactiveException extends LogicalErrorException {

    public ClientInactiveException() {
        super("CLIENT_INACTIVE");
    }

}
