enum class Topics(val topicName: String) {
    MyTopic1("MyTopic1"),
    MyTopic2("MyTopic2"),
}

object QueueNames {
    const val MyQueue1 = "MyQueue1"
    const val MyQueue2 = "MyQueue2"
    const val MyQueue3 = "MyQueue3"
}

data class TopicConfig(
    val queueName: String,
    val isFifo: Boolean,
)
data class TopicMap(
    val topicConfigs: Map<Topics, List<TopicConfig>>
)


val topicMapObj = TopicMap(
    topicConfigs = mapOf(
        Topics.MyTopic1 to listOf(
            TopicConfig(
                queueName = QueueNames.MyQueue1,
                isFifo = false,
            ),
            TopicConfig(
                queueName = QueueNames.MyQueue2,
                isFifo = false,
            )
        ),
        Topics.MyTopic2 to listOf(
            TopicConfig(
                queueName = QueueNames.MyQueue3,
                isFifo = false,
            )
        )
    )
)