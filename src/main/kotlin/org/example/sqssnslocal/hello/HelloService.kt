package org.example.sqssnslocal.hello

import Topics
import org.example.sqssnslocal.core.SnsMessageSender
import org.springframework.stereotype.Service

@Service
class HelloService(private val snsMessageSender: SnsMessageSender) {
    fun hello1(): String {

        val message = "Hello, this is a test message 1!"
        val subject = "Test Subject"

        snsMessageSender.sendMessage(Topics.MyTopic1, message, subject)
        hello2()
        return "Message sent to topic: ${Topics.MyTopic1.topicName}"
    }

    fun hello2(): String {
        val message = "Hello, this is a test message 2!"
        val subject = "Test Subject"

        snsMessageSender.sendMessage(Topics.MyTopic2, message, subject)
        return "Message sent to topic: ${Topics.MyTopic2.topicName}"
    }
}