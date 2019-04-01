package org.kylin.authcenter.mq;

import org.kylin.authcenter.dto.AuthDto;
import org.kylin.authcenter.entity.User;
import org.kylin.authcenter.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;


public class AuthReceiver {

    @Autowired
    private UserRepository repository;

    @RabbitListener(queues = "auth-queue")
    public void receive(AuthDto dto) {
        repository.save(User.builder().
                id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(new HashSet<>(Arrays.asList("ROLE_USER")))
                .build());
    }
}
