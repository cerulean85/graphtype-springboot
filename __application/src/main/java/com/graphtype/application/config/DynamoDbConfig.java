package com.graphtype.application.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
//@EnableJpaRepositories(basePackages = "com.graphtype.application.repository")
//@ComponentScan(basePackages = {"com.graphtype.application.repository"})
@ComponentScan(basePackages = "com.graphtype.application.*")
@EntityScan("com.graphtype.application.*")
@EnableJpaRepositories(basePackages = "com.graphtype.application.repository")
public class DynamoDbConfig {

    @Value("${aws.access.key}")
    private String awsAccessKey;

    @Value("${aws.access.secret-key}")
    private String awsSecretKey;

    @Value("${aws.dynamodb.endpoint}")
    private String awsDynamoDBEndPoint;

    @Value("${aws.region:}")
    private String awsRegion;

    @Bean
    public AWSCredentials amazonAWSCredentials(){

        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    public AWSCredentialsProvider amazonAWSCredentialsProvider(){
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }

    public AmazonDynamoDB amazonDynamoDB(){
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndPoint, awsRegion))
                .withCredentials(amazonAWSCredentialsProvider())
                .build();
    }

    @Bean
    public DynamoDBMapper mapper(){
        return new DynamoDBMapper(amazonDynamoDB());
    }
}
