package com.inmaytide.orbit.commons.utils.producer;

import com.inmaytide.orbit.commons.consts.RabbitMQ;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送延时消息
     */
    public void sendDelayMessage(Object message, Integer delayInSeconds, String routingKey) {
        rabbitTemplate.convertAndSend(RabbitMQ.DIRECT_DELAY_EXCHANGE, routingKey, message, m -> {
            m.getMessageProperties().setDelay(delayInSeconds * 1000);
            return m;
        });
    }

    /**
     * 发送实时消息
     */
    public void sendRealMessage(Object message, String routingKey) {
        rabbitTemplate.convertAndSend(RabbitMQ.DIRECT_EXCHANGE, routingKey, message);
    }


}
