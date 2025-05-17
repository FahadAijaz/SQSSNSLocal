package org.example.sqssnslocal.core

import io.awspring.cloud.sqs.operations.SqsTemplate
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sns.model.SubscribeRequest
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest
import software.amazon.awssdk.services.sqs.model.QueueAttributeName
import topicMapObj

@Configuration
class SnsSqsSubscriptionManager(
    private val snsClient: SnsClient,
    private val sqsTemplate: SqsTemplate,
    private val sqsClient: SqsClient,
    private val environment: Environment,
    private val snsSqsConfig: SnsSqsConfig,
) {
//    @PostConstruct
//    fun subscribeQueueToTopic() {
//        val topicArn = "arn:aws:sns:us-east-1:000000000000:MyTopic" // Replace with your SNS topic ARN
//        val queueName = "MyQueue" //
//        // Resolve the queue URL
//        val queueUrl = resolveQueueUrl(queueName)
//
//        // Get the queue ARN
//        val queueDetails = sqsClient.getQueueAttributes {
//            it.queueUrl(queueUrl)
//            it.attributeNames(QueueAttributeName.QUEUE_ARN)
//        }
//
//        // Subscribe the queue to the SNS topic
//        val subscribeRequest = SubscribeRequest.builder()
//            .topicArn(topicArn)
//            .protocol("sqs")
//            .endpoint(queueDetails.attributes().getValue(QueueAttributeName.QUEUE_ARN))
//            .build()
//
//        snsClient.subscribe(subscribeRequest)
//    }

    @PostConstruct
    fun subscribeQueuesToTopics() {
        topicMapObj.topicConfigs.forEach { (topic, configs) ->

            val topicArn = "arn:aws:sns:us-east-1:000000000000:${topic.topicName}" // Replace with dynamic ARN if needed
            configs.forEach { config ->
                val queueUrl = resolveQueueUrl(config.queueName)
                val queueDetails = sqsClient.getQueueAttributes {
                    it.queueUrl(queueUrl)
                    it.attributeNames(QueueAttributeName.QUEUE_ARN)
                }

                val subscribeRequest = SubscribeRequest.builder()
                    .topicArn(topicArn)
                    .protocol("sqs")
                    .endpoint(queueDetails.attributes().getValue(QueueAttributeName.QUEUE_ARN))
                    .build()

                snsClient.subscribe(subscribeRequest)
            }

        }
    }


    fun resolveQueueUrl(queueName: String): String {
        val request = GetQueueUrlRequest.builder()
            .queueName(queueName)
            .build()
        return sqsClient.getQueueUrl(request).queueUrl()
    }

    @PostConstruct
    fun configureQueueNameResolutionStrategy() {
        System.setProperty("spring.cloud.aws.sqs.queue-name-resolution-strategy", "ALWAYS")
    }

    @PostConstruct
    fun logConfig() {
        println("Loaded topics: ${snsSqsConfig.topics}")
    }
}