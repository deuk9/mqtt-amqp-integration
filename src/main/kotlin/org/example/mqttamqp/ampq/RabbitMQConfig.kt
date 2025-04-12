package org.example.mqttamqp.ampq

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun queue(): Queue {
        return QueueBuilder
            .nonDurable("amqp.queue")
            .build()
    }

    /**
     * topic exchange만 사용가능.
     */
    @Bean
    fun exchange(): Exchange {
        return ExchangeBuilder
            .topicExchange("amq.topic")
            .build()
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder
            .bind(queue())
            .to(exchange())
            .with("protocols.amqp.car")
            .noargs()
    }
}