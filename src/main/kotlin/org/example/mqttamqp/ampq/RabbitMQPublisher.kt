package org.example.mqttamqp.ampq

import org.example.mqttamqp.mqtt.properties.MqttProperties
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RabbitMQPublisher(
    private val rabbitTemplate: RabbitTemplate,
    private val mqttProperties: MqttProperties
) {

    companion object {
        private val log = LoggerFactory.getLogger(RabbitMQPublisher::class.java)
    }

    fun publish(message: String) {
        rabbitTemplate.convertAndSend(
            "amq.topic",
            mqttTopicToAmqpTopic(mqttProperties.subscriber.topic),
            message
        )
        log.info("Published message: $message to topic: ${mqttProperties.subscriber.topic}")
    }

    private fun mqttTopicToAmqpTopic(mqttTopic: String): String {
        return mqttTopic.replace("/", ".")
    }
}