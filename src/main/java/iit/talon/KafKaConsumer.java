package iit.talon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import iit.talon.service.GetText;

@Component
public class KafKaConsumer {
    @Autowired
    GetText cloudService;

    private static final Logger logger = LoggerFactory.getLogger(KafKaConsumer.class);
   @KafkaListener(topics = "test", groupId = "group-id")
    public void listen(String message) {
        logger.info("Received Messasge: " + message);
    }

    @KafkaListener(topics = "recievedsound1")
    public void processSound(String message) {
        //logger.info(cloudService.transcript());
        logger.info("### KYA HAI YEHHH ### ");
    }
}