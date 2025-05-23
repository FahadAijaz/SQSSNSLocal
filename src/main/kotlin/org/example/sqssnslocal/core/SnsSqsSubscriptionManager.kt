package org.example.sqssnslocal.core

import io.awspring.cloud.sqs.operations.SqsTemplate
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.ListTopicsRequest
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sns.model.SubscribeRequest
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest
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

    private fun getTopicArn(topicName: String): String {
        val topics = snsClient.listTopics(ListTopicsRequest.builder().build()).topics()
        return topics.firstOrNull { it.topicArn().endsWith(":$topicName") }
            ?.topicArn() ?: throw IllegalArgumentException("Topic $topicName not found")
    }

    @PostConstruct
    fun subscribeQueuesToTopics() {
        topicMapObj.topicConfigs.forEach { (topic, configs) ->
            val topicArn = getTopic(topic.topicName)
            configs.forEach { config ->
                val queueUrl = getQueueURL(config.queueName)
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

    private fun getQueueURL(queueName: String): String {
        return try {
            resolveQueueUrl(queueName)
        } catch (e: Exception) {
            sqsClient.createQueue(
                CreateQueueRequest.builder().queueName(queueName).build()
            ).queueUrl()
        }
    }

    private fun getTopic(topicName: String): String {
        return try {
            return getTopicArn(topicName)
        } catch (e: Exception) {
            snsClient.createTopic(
                CreateTopicRequest.builder().name(topicName).build()
            ).topicArn()
        }
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