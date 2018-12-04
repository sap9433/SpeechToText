package iit.talon.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import iit.talon.service.GetText;

@RestController
public class SoundController {
    @Autowired
    GetText cloudService;
    @Autowired
       private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
       private KafkaTemplate<String, byte[]> kafkaBlobTemplate;
    @RequestMapping("/api/gettext")
    public String getText() {
        //return " Transcript is " + cloudService.transcript();
        return "Need to change cloudService transcript";
    }

    @RequestMapping("/api/savesound")
    public void sendMessage(@RequestParam MultipartFile  audio_data) throws IOException{
        byte[] bytes = audio_data.getBytes();
        kafkaBlobTemplate.send("recieved_sound", bytes);
    }
    
    @RequestMapping("/api/savetext")
    public void sendTextMessage() {
        kafkaTemplate.send("test", "Some random data");
    }  
}