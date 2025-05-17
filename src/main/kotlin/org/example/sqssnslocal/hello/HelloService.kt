package org.example.sqssnslocal.hello

import org.example.sqssnslocal.core.SnsMessageSender
import org.springframework.stereotype.Service

@Service
class HelloService(private val snsMessageSender: SnsMessageSender) {
    fun hello1(): String {
        val topicArn = "arn:aws:sns:us-east-1:000000000000:MyTopic1"
        val message = "Hello, this is a test message 1!"
        val subject = "Test Subject"

        snsMessageSender.sendMessage(topicArn, message, subject)
        hello2()
        return "Message sent to topic: $topicArn"
    }

    fun hello2(): String {
        val topicArn = "arn:aws:sns:us-east-1:000000000000:MyTopic2"
        val message = "Hello, this is a test message 2!"
        val subject = "Test Subject"

        snsMessageSender.sendMessage(topicArn, message, subject)
        return "Message sent to topic: $topicArn"
    }
}