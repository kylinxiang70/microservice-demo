package org.kylin.userservice.mq;

import org.kylin.userservice.entity.AuthDto;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(AuthDto dto) {
        this.rabbitTemplate.convertAndSend(queue.getName(), dto);
    }
}
