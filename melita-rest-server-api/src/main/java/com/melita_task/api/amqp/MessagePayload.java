package com.melita_task.api.amqp;

import com.melita_task.contract.ClientDtoRabbit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagePayload {
    private String alteration;
    private ClientDtoRabbit client;
}
