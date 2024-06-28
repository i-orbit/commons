package com.inmaytide.orbit.commons.configuration;

import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.log.OperationLogMessageProducer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author inmaytide
 * @since 2023/5/16
 */
@Configuration
public class RabbitMQConfiguration {

    @Bean
    public MessageConverter jsonToMapMessageConverter() {
        DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
        defaultClassMapper.setTrustedPackages("*"); // trusted packages
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
        return jackson2JsonMessageConverter;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(Constants.RabbitMQ.DIRECT_EXCHANGE, true, false);
    }

    @Bean
    public CustomExchange delayedExchange() {
        return new CustomExchange(Constants.RabbitMQ.DIRECT_DELAY_EXCHANGE, "x-delayed-message", true, false, Map.of("x-delayed-type", "direct"));
    }

    @Bean(name = "operationLogQueue")
    public Queue queue() {
        return new Queue(OperationLogMessageProducer.ROUTE_KEY_OPERATION_LOG, true);
    }

    @Bean
    public Binding bindingNotify(@Qualifier("operationLogQueue") Queue queue,
                                 DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(OperationLogMessageProducer.ROUTE_KEY_OPERATION_LOG);
    }

}
