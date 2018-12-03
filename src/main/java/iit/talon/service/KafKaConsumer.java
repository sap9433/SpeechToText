package iit.talon.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import iit.talon.service.GetText;

@Component
public class KafKaConsumer {
    @Autowired
    GetText cloudService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafKaConsumer.class);
   @KafkaListener(topics = "test", groupId = "group-id")
    public void listen(String message) {
        logger.info("Received Messasge: " + message);
    }

    @KafkaListener(topics = "recievedsound1")
    public void processSound(byte[] sounddata) {
        String transcript = cloudService.transcript(sounddata);
        logger.info(transcript);
        kafkaTemplate.send("test", transcript);
    }
}