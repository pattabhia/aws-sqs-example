package com.devcodeworld.aws.sqs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsSecrets {

    private String accessName;
    private String accessPass;
}
