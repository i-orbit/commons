package com.inmaytide.orbit.commons.utils.producer;

import com.inmaytide.orbit.commons.constants.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass(RabbitTemplate.class)
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送延时消息
     */
    public void sendDelayMessage(Object message, Integer delayInSeconds, String routingKey) {
        rabbitTemplate.convertAndSend(Constants.RabbitMQ.DIRECT_DELAY_EXCHANGE, routingKey, message, m -> {
            m.getMessageProperties().setDelay(delayInSeconds * 1000);
            return m;
        });
    }

    /**
     * 发送实时消息
     */
    public void sendMessage(Object message, String routingKey) {
        rabbitTemplate.convertAndSend(Constants.RabbitMQ.DIRECT_EXCHANGE, routingKey, message);
    }


}
