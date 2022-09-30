package com.melita_task.api.exceptions;

public class ClientNotFoundException extends LogicalErrorException {

    public ClientNotFoundException() { super("CLIENT_NOT_FOUND"); }

}
