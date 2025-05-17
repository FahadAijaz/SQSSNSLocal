package org.example.sqssnslocal.hello

import Topics
import org.example.sqssnslocal.core.SnsMessageSender
import org.springframework.stereotype.Service

@Service
class HelloService(private val snsMessageSender: SnsMessageSender) {
    fun sendMessageToTopic1(): String {

        val message = "Hello, this is a test message 1!"
        val subject = "Test Subject"

        snsMessageSender.sendMessage(Topics.MyTopic1, message, subject)
        sendMessageToTopic2()
        sendMessageToTopic3()
        return "Message sent to topic: ${Topics.MyTopic1.topicName}"
    }

    fun sendMessageToTopic2(): String {
        val message = "Hello, this is a test message 2!"
        val subject = "Test Subject"

        snsMessageSender.sendMessage(Topics.MyTopic2, message, subject)
        return "Message sent to topic: ${Topics.MyTopic2.topicName}"
    }
    fun sendMessageToTopic3(): String {
        val message = "Hello, this is a test message 3!"
        val subject = "Test Subject"

        snsMessageSender.sendMessage(Topics.MyTopic3, message, subject)
        return "Message sent to topic: ${Topics.MyTopic3.topicName}"
    }
}