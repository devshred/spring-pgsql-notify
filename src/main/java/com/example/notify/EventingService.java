package com.example.notify;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.jdbc.channel.PostgresChannelMessageTableSubscriber;
import org.springframework.integration.jdbc.channel.PostgresSubscribableChannel;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@DependsOn("flywayInitializer")
@AllArgsConstructor
public class EventingService {
    private static final String GROUP_ID = "someGroup";
    final PostgresChannelMessageTableSubscriber subscriber;

    final JdbcChannelMessageStore messageStore;

    @PostConstruct
    private void init() {
        subscriber.start();

        final PostgresSubscribableChannel channel = new PostgresSubscribableChannel(messageStore, GROUP_ID, subscriber);
        channel.subscribe(message -> processCallback((String) message.getPayload()));
        log.info("init completed");
    }

    @PreDestroy
    private void destroy() {
        subscriber.stop();
        log.info("destroy completed");
    }

    public void send(String someString) {
        messageStore.addMessageToGroup(GROUP_ID, new GenericMessage<>(someString));
    }

    private void processCallback(String payload) {
        log.info("received: {}", payload);
    }
}
