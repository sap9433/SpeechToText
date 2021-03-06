package iit.talon.configuration; 

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {
   @Bean
   public ProducerFactory<String, String> producerFactoryString() {
      Map<String, Object> configProps = new HashMap<>();
      configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      configProps.put("message.max.bytes", "41943040");
      configProps.put("max.request.size", "41943040");
      configProps.put("replica.fetch.max.bytes", "41943040");
      configProps.put("fetch.message.max.bytes", "41943040");
      return new DefaultKafkaProducerFactory<>(configProps);
   }
   @Bean
   public KafkaTemplate<String, String> kafkaTemplate() {
      return new KafkaTemplate<String, String>(producerFactoryString());
   }

   @Bean
   public ProducerFactory<String, byte[]> blobProducerFactory() {
      Map<String, Object> configProps = new HashMap<>();
      configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
      configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
      configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
      return new DefaultKafkaProducerFactory<>(configProps);
   }
   @Bean
   public KafkaTemplate<String, byte[]> kafkaBlobTemplate() {
      return new KafkaTemplate<String,byte[]>(blobProducerFactory());
   }
}