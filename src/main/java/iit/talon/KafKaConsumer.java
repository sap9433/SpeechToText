package iit.talon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafKaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafKaConsumer.class);
   @KafkaListener(topics = "test", groupId = "group-id")
    public void listen(String message) {
        logger.info("Received Messasge in group - group-id: " + message);
    }
}