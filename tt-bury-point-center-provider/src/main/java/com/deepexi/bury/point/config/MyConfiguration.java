package com.deepexi.bury.point.config;

import io.leopard.javahost.JavaHost;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @author: LuoGuang
 * @Package com.deepexi.customer.analysis.local.config
 * @Description:
 * @date: 2019/8/26 13:49
 */
@Configuration
@ConditionalOnClass(MyConfiguration.class)
@EnableConfigurationProperties(ServerProperties.class)
@EnableKafka
public class MyConfiguration implements DisposableBean {

    @Resource
    ServerProperties serverProperties;

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(10);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverProperties.getServers());
        props.put(ProducerConfig.RETRIES_CONFIG, serverProperties.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, serverProperties.getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, serverProperties.getLinger());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, serverProperties.getBufferMemory());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, serverProperties.getAcks());
        return props;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        JavaHost.updateVirtualDns(serverProperties.getHosts());
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(producerFactory());
        return kafkaTemplate;
    }


    @Override
    public void destroy() throws Exception {

    }
}
