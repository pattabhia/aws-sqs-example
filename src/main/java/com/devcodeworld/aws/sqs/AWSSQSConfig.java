package com.devcodeworld.aws.sqs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AWSSQSConfig {

    @Value("${cloud.aws.region.static}")
    private String region;
    private final String awsAccessKey;
    private final String awsSecretKey;


    public AWSSQSConfig() {
        final SecretConfig secretConfig = new SecretConfig();
        this.awsAccessKey = secretConfig.getSecrets().getAccessKey();
        this.awsSecretKey = secretConfig.getSecrets().getAccessPass();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard().withRegion(region)
                .withCredentials
                        (new AWSStaticCredentialsProvider
                                (new BasicAWSCredentials(awsAccessKey, awsSecretKey))).build();

    }
}
