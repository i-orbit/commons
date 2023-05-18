package com.inmaytide.orbit.commons.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * @author inmaytide
 * @since 2020/11/09
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 输出到json时不包含值为Null的属性
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        // 反序列化json对象时，忽略未知属性不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        // Long类型输出到json时自动转换为字符串，防止long类型过长时丢失精度的问题
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // Instant 类型输出到json是字符串格式"yyyy-MM-ddTHH:mm:ssZ"
        module.addSerializer(Instant.class, new CustomizedInstantSerializer());
        module.addDeserializer(Instant.class, new CustomizedInstantDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public static class CustomizedInstantSerializer extends InstantSerializer {

        @Serial
        private static final long serialVersionUID = -4409276922152896844L;

        public CustomizedInstantSerializer() {
            super(InstantSerializer.INSTANCE, false, false, DateTimeFormatter.ISO_INSTANT);
        }

    }

    public static class CustomizedInstantDeserializer extends InstantDeserializer<Instant> {

        @Serial
        private static final long serialVersionUID = 2335817937281563294L;

        protected CustomizedInstantDeserializer() {
            super(InstantDeserializer.INSTANT, DateTimeFormatter.ISO_INSTANT);
        }
    }

}
