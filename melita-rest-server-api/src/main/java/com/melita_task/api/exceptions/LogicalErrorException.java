package com.melita_task.api.exceptions;

import lombok.Data;

@Data
public class LogicalErrorException extends IllegalStateException {
    final String code;
}
