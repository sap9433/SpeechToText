package iit.talon.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import iit.talon.configuration.TranscriptRepository;
import iit.talon.service.GetText;
import iit.talon.model.Transcript;;

@Component
public class KafKaConsumer {
    @Autowired
    GetText cloudService;

    @Autowired
    private SimpMessagingTemplate webSocket;
    @Autowired
    private TranscriptRepository repository;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafKaConsumer.class);

   @KafkaListener(topics = "transcriptToClient", groupId = "group-id")
    public void listen(String message) throws Exception{
        logger.info("Received Messasge: " + message);
        //Saves transcript in mongo
        repository.save(new Transcript(""+message.hashCode(), message));
        webSocket.convertAndSend("/topic/backToClient", message);
    }

    @KafkaListener(topics = "recieved_sound")
    public void processSound(byte[] sounddata) {
        String transcript = cloudService.transcript(sounddata);
        logger.info(transcript);
        kafkaTemplate.send("transcriptToClient", transcript);
    }
}