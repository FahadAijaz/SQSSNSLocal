# SQSSNSLocal

# Project README

## Project Overview

This project demonstrates a fanout pattern for event sourcing using AWS SNS and SQS. It allows multiple queues to
subscribe to a single topic, enabling event distribution to multiple consumers. Additionally, it supports simple
queue-based messaging.

## Features

- **Fanout for Event Sourcing**: Multiple SQS queues can subscribe to a single SNS topic, enabling event distribution to
  multiple consumers.
- **Simple Queue-Based Messaging**: Direct messaging to individual SQS queues is also supported.
- **Dynamic Resource Management**: Topics and queues are created dynamically if they do not already exist.

---

## Setup Instructions

### Prerequisites

1. **Docker**: Ensure Docker is installed on your system.
2. **AWS CLI**: Install and configure the AWS CLI.

### Step 1: Start LocalStack

This project uses LocalStack to emulate AWS services locally. Use the provided `docker-compose.yml` file to start
LocalStack.

1. Navigate to the project directory.
2. Run the following command to start LocalStack:
   ```bash
   docker-compose up -d
   ```

### Step 2: Configure AWS CLI

Configure the AWS CLI to interact with LocalStack.

1. Run the following command:
   ```bash
   aws configure
   ```
2. Use the following values:
    - **Access Key ID**: `test`
    - **Secret Access Key**: `test`
    - **Default region**: `us-east-1`
    - **Output format**: `json`

3. Set the endpoint for LocalStack:
   ```bash
   export AWS_ENDPOINT=http://localhost:4566
   ```

---

## How to Add New Topics and Queues

### Adding a New Topic

To add a new topic, update the `Topics.kt` file. For example:

```kotlin
enum class Topics(val topicName: String) {
    MyTopic1("MyTopic1"),
    MyTopic2("MyTopic2"),
    MyTopic3("MyTopic3"),
    MyNewTopic("MyNewTopic") // Add your new topic here
}

```

### Adding a New Queue

To add a new queue, update the `QueueNames.kt` file. For example:

```kotlin
object QueueNames {
    const val MyQueue1 = "MyQueue1"
    const val MyQueue2 = "MyQueue2"
    const val MyNewQueue = "MyNewQueue" // Add your new queue here
}
```

### Topic To Queue Mapping (Event Sourcing)

This is where you define the mapping between topics and queues. The `TopicMap` class is responsible for this mapping.
To map the new topic to the new queue, update the `TopicMap.kt` file. For example:

```kotlin
val topicMapObj = TopicMap(
    topicConfigs = mapOf(
        ...,
    Topics.MyNewTopic to listOf( // New topic association
        TopicConfig(
            queueName = "MyNewQueue1",
            isFifo = false,
        ),
        TopicConfig(
            queueName = "MyNewQueue2",
            isFifo = false,
        )
    )
)
```

---

## Dynamic Creation of Topics and Queues

The application automatically creates topics and queues if they do not already exist. This is handled in the
`SnsSqsSubscriptionManager` class:

- **Topics**: If a topic is not found, it is created using the `createTopic` method.
- **Queues**: If a queue is not found, it is created using the `createQueue` method.

---

## Testing the Application

1. Start the application by running:
   ```bash
   ./mvnw spring-boot:run
   ```
2. Invoke the `/hello` endpoint to test the fanout functionality:
   ```bash
   curl http://localhost:8080/hello
   ```
   This will:
    - Send a message to `MyTopic1`.
    - Distribute the message to all queues subscribed to `MyTopic1`.

3. Check the logs to verify that the message was received by the appropriate queues.

---

## Example Use Case

1. Add a new topic `MyNewTopic` in `Topics.kt`.
2. Add a new queue `MyNewQueue` in `QueueNames.kt`.
3. Start the application and invoke the `/hello` endpoint.
4. Verify that the new queue receives messages if subscribed to the new topic.