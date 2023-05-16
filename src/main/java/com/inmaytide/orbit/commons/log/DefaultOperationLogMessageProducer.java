package com.inmaytide.orbit.commons.log;

import com.inmaytide.orbit.commons.log.domain.OperationLog;
import com.inmaytide.orbit.commons.utils.producer.RabbitProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(OperationLogMessageProducer.class)
public class DefaultOperationLogMessageProducer implements OperationLogMessageProducer {

    private final RabbitProducer rabbitProducer;

    public DefaultOperationLogMessageProducer(RabbitProducer rabbitProducer) {
        this.rabbitProducer = rabbitProducer;
    }

    @Override
    public void produce(OperationLog log) {
        rabbitProducer.sendRealMessage(log, ROUTE_KEY_OPERATION_LOG);
    }

}
