package com.melita_task.api.exceptions;

public class OrderSubmittedException  extends LogicalErrorException  {
    public OrderSubmittedException() {
        super("ORDER_ALREADY_SUBMITTED");
    }
}
