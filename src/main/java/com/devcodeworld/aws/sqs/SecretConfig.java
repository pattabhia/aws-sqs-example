package com.devcodeworld.aws.sqs;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.amazonaws.regions.Regions.US_EAST_1;


@Component
public class SecretConfig {

    private Gson gson;

    public SecretConfig() {
        gson = new Gson();
    }

    public AwsSecrets getSecrets() {

        final String secretName = "apiadmin-creds";

        AWSSecretsManager client = AWSSecretsManagerClientBuilder
                .standard()
                .withRegion(US_EAST_1)
                .build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
        GetSecretValueResult getSecretValueResult;
        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }
        if (getSecretValueResult.getSecretString() != null) {
            String secret = getSecretValueResult.getSecretString();
            return gson.fromJson(secret, AwsSecrets.class);
        }
        return null;
    }
}
