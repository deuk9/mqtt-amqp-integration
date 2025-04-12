package org.example.mqttamqp.mqtt.properties

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "mqtt")
data class MqttProperties(
    val brokerUrl: String,
    val username: String,
    val password: String,
    val publisher: MqttPublisherProperties,
    val subscriber: MqttConsumerProperties,

) {

    data class MqttPublisherProperties(
        val clientId: String,
        val topic: String,
    )

    data class MqttConsumerProperties(
        val clientId: String,
        val topic: String,
    )
}
