package com.melita_task.api.exceptions;

public class ClientAlreadyActivatedException extends LogicalErrorException {

    public ClientAlreadyActivatedException() {
        super("CLIENT_ALREADY_ACTIVATED");
    }

}
