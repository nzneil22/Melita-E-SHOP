package com.melita_task.api.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(AMQPBindings.class)
@AllArgsConstructor
public class MessageProducer {

	@Autowired
	private AMQPBindings cs;

	public void sendMessage(MessagePayload mp){
		log.info("AMQP Send - " + mp.getAlteration());
		cs.crmQueueSend().send(MessageBuilder.withPayload(mp).build());
	}

}