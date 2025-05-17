package org.example.sqssnslocal.core


import io.awspring.cloud.sns.core.SnsTemplate
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sns.SnsClient

@Service
class SnsMessageSender(private val snsTemplate: SnsTemplate, private val snsClient: SnsClient) {

    fun sendMessage(topicArn: String, message: String, subject: String? = null) {
        snsTemplate.sendNotification(topicArn, message, subject)
    }
}