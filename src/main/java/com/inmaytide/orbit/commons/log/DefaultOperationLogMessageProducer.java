package com.inmaytide.orbit.commons.log;

import com.inmaytide.orbit.commons.log.domain.OperationLog;
import com.inmaytide.orbit.commons.utils.producer.RabbitProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass(RabbitTemplate.class)
public class DefaultOperationLogMessageProducer implements OperationLogMessageProducer {

    private final RabbitProducer rabbitProducer;

    public DefaultOperationLogMessageProducer(RabbitProducer rabbitProducer) {
        this.rabbitProducer = rabbitProducer;
    }

    @Override
    public void produce(OperationLog log) {
        rabbitProducer.sendMessage(log, ROUTE_KEY_OPERATION_LOG);
    }

}
