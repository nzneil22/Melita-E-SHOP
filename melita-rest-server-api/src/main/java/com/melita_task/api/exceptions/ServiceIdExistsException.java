package com.melita_task.api.exceptions;

public class ServiceIdExistsException extends LogicalErrorException  {
    public ServiceIdExistsException() {
        super("SERVICE_ID_ALREADY_EXISTS");
    }
}
