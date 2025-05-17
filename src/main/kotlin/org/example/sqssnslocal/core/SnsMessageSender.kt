package org.example.sqssnslocal.core


import Topics
import io.awspring.cloud.sns.core.SnsTemplate
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.ListTopicsRequest

@Service
class SnsMessageSender(private val snsTemplate: SnsTemplate, private val snsClient: SnsClient) {

    fun getTopicArn(topicName: String): String {
        val topics = snsClient.listTopics(ListTopicsRequest.builder().build()).topics()
        return topics.firstOrNull { it.topicArn().endsWith(":$topicName") }
            ?.topicArn() ?: throw IllegalArgumentException("Topic $topicName not found")
    }
    fun sendMessage(topicName: Topics, message: String, subject: String? = null) {

        val topicArn = getTopicArn(topicName.topicName)
        snsTemplate.sendNotification(topicArn, message, subject)
    }
}