package com.melita_task.api.exceptions;

public class ClientAlreadyDeActivatedException extends LogicalErrorException {

    public ClientAlreadyDeActivatedException() {
        super("CLIENT_ALREADY_DEACTIVATED");
    }

}
