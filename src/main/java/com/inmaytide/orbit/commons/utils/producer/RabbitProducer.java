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

    public void sendDelayMessage(Object msg, Integer delayTime, String exchange, String routing) {
        rabbitTemplate.convertAndSend(exchange, routing, msg, a -> {
            a.getMessageProperties().setDelay(delayTime * 1000);
            return a;
        });
    }

    /**
     * 发送延时消息
     *
     * @param message
     * @param delayTime
     * @param router
     */
    public void sendDelayMessage(Object message, Integer delayTime, String router) {
        sendDelayMessage(message, delayTime, RabbitMQ.DIRECT_DELAY_EXCHANGE, router);
    }

    /**
     * 发送实时消息
     */
    public void sendRealMessage(Object message, String route) {
        rabbitTemplate.convertAndSend(RabbitMQ.DIRECT_EXCHANGE, route, message);
    }


}
