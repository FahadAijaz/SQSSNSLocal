package org.example.sqssnslocal.core


import org.springframework.stereotype.Component
import io.awspring.cloud.sqs.annotation.SqsListener

@Component
class SqsQueueListener {

    @SqsListener(QueueNames.MyQueue1)
    fun listenToQueue1(message: String) {
        println("Received message: $message in Queue 1")
    }

    @SqsListener(QueueNames.MyQueue2)
    fun listenToQueue2(message: String) {
        println("Received message: $message in Queue 2")
    }

    @SqsListener(QueueNames.MyQueue3)
    fun listenToQueue3(message: String) {
        println("Received message: $message in Queue 3")
    }
}