package org.example.sqssnslocal

import org.example.sqssnslocal.core.SnsSqsConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SnsSqsConfig::class)
class SqssnsLocalApplication

fun main(args: Array<String>) {
    runApplication<SqssnsLocalApplication>(*args)
}
