package com.devcodeworld.aws.sqs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
@EnableSqs
public class AwsSqsExampleApplication {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point}")
    private String endpoint;

    public AwsSqsExampleApplication(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String message) {
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
    }

    @SqsListener(value = "demo-sqs-queue")
    public void getMessage(String message) {
        log.info("getting message = {} ", message);
    }

    public static void main(String[] args) {
        SpringApplication.run(AwsSqsExampleApplication.class, args);
    }
}
