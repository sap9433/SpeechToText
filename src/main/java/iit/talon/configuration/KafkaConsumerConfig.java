package iit.talon.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig{
   @Bean
   public ConsumerFactory<String, byte[]> consumerFactory() {
      Map<String, Object> props = new HashMap<>();
 
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
      return new DefaultKafkaConsumerFactory<>(props);
   }
   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, byte[]> 
      factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
   }
}   