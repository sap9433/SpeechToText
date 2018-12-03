package iit.talon.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import iit.talon.service.GetText;

@Component
public class KafKaConsumer {
    @Autowired
    GetText cloudService;

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafKaConsumer.class);


   @KafkaListener(topics = "test", groupId = "group-id")
    public void listen(String message) throws Exception{
        logger.info("Received Messasge: " + message);
        webSocket.convertAndSend("/topic/greetings", message);
    }

    @KafkaListener(topics = "recievedsound1")
    public void processSound(byte[] sounddata) {
        String transcript = cloudService.transcript(sounddata);
        logger.info(transcript);
        kafkaTemplate.send("test", transcript);
    }
}