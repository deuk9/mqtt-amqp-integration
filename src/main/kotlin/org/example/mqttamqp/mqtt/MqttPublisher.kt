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
class MqttPublisher(
    private val mqttProperties: MqttProperties,
) {

    private var client: MqttClient? = null

    companion object {
        private val log: Logger = LoggerFactory.getLogger(MqttPublisher::class.java)
    }

    @PostConstruct
    fun initialize() {
        val options = MqttConnectOptions().apply {
            userName = mqttProperties.username
            password = mqttProperties.password.toCharArray()
        }

        client = MqttClient(mqttProperties.brokerUrl, mqttProperties.publisher.clientId)
        client?.connect(options)

        log.info("MQTT publisher initialized with client ID=${mqttProperties.publisher.clientId} ")
    }

    fun publish(message: String) {
        client?.publish(mqttProperties.publisher.topic, message.toByteArray(), 0, false)
            ?: throw IllegalStateException("MQTT client is not initialized or connected")

        log.info("Published message: $message to topic: ${mqttProperties.publisher.topic}")
    }

    @PreDestroy
    fun destroy() {
        client?.disconnect()
        client?.close()
        log.info("MQTT publisher disconnected")
    }
}
