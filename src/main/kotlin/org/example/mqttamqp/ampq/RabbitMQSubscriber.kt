package org.example.mqttamqp.ampq

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMQSubscriber {

    companion object {
        private val log = LoggerFactory.getLogger(RabbitMQSubscriber::class.java)
    }

    @RabbitListener(queues = ["amqp.queue"])
    fun received(message: String?) {
        log.info("Received message: $message")
    }
}