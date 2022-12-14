package com.melita_task.amqp;

import com.melita_task.melita.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MessagePayload {
    final String alteration;
    final Customer customer;
}
