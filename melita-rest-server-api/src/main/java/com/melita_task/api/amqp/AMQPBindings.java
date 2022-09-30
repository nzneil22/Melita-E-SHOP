
package com.melita_task.api.amqp;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Service;

@Service
public interface AMQPBindings {

    @Output("crmQueue")
    MessageChannel crmQueueSend();

    String LISTEN = "crmQueue";

    @Input(LISTEN)
    SubscribableChannel amqpMicroServiceListen();

}