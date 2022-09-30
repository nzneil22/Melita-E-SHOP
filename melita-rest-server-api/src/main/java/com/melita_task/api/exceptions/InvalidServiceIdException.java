package com.melita_task.api.exceptions;

public class InvalidServiceIdException extends LogicalErrorException  {
    public InvalidServiceIdException() {
        super("INVALID_SERVICE_ID");
    }
}
