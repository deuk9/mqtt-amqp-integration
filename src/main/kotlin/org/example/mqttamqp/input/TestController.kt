package org.example.mqttamqp.input

import org.example.mqttamqp.ampq.RabbitMQPublisher
import org.example.mqttamqp.mqtt.MqttPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val mqttPublisher: MqttPublisher,
    private val rabbitMQPublisher: RabbitMQPublisher,
) {

    @GetMapping("/api/mqtt-to-amqp")
    fun mqttToAmqp() {
        mqttPublisher.publish("mqtt -> amqp test")
    }

    @GetMapping("/api/amqp-to-mqtt")
    fun amqpToMqtt() {
        rabbitMQPublisher.publish("amqp -> mqtt test")
    }

}