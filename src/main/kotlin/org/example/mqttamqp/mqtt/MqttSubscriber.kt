package org.example.mqttamqp.mqtt

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.example.mqttamqp.mqtt.properties.MqttProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MqttSubscriber(
    private val mqttProperties: MqttProperties,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(MqttSubscriber::class.java)
    }

    private var client: MqttClient? = null

    @PostConstruct
    fun initialize() {
        val options = MqttConnectOptions().apply {
            userName = mqttProperties.username
            password = mqttProperties.password.toCharArray()
        }

        client = MqttClient(mqttProperties.brokerUrl, mqttProperties.subscriber.clientId)
        client?.connect(options)
        client?.subscribe(mqttProperties.subscriber.topic) { topic, message ->
            val payload = String(message.payload)
            log.info("Received message: $payload from topic: $topic")
        }

        log.info("MQTT subscriber initialized with client ID=${mqttProperties.subscriber.clientId}")
    }

    @PreDestroy
    fun destroy() {
        client?.disconnect()
        client?.close()
        log.info("MQTT subscriber disconnected")
    }
}
