for queue_url in $(aws --endpoint-url=http://localhost:4566 sqs list-queues --query "QueueUrls[]" --output text); do
    echo "Purging queue: $queue_url"
    aws --endpoint-url=http://localhost:4566 sqs purge-queue --queue-url "$queue_url"
done
echo "All queues purged successfully."
