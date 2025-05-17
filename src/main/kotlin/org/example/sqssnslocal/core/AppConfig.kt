package org.example.sqssnslocal.core

import io.awspring.cloud.sns.core.SnsTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sqs.SqsClient
import java.net.URI

@Configuration
class AppConfig {

    @Bean
    fun snsClient(): SnsClient {
        return SnsClient.builder()
            .endpointOverride(URI.create("http://localhost:4566")) // LocalStack endpoint
            .build()
    }

    @Bean
    fun snsTemplate(snsClient: SnsClient): SnsTemplate {
        return SnsTemplate(snsClient)
    }
    @Bean
    fun sqsClient(): SqsClient {
        return SqsClient.builder()
            .endpointOverride(URI.create("http://localhost:4566"))
            .build()
    }
}