package org.example.sqssnslocal.core


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "topics")
class SnsSqsConfig {
    var topics: List<TopicConfig> = listOf()

    class TopicConfig {
        lateinit var name: String
        var queues: List<String> = listOf()
    }
}