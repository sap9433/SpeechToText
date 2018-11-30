package iit.talon.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class SoundController {
    private static final Logger logger = LoggerFactory.getLogger(SoundController.class);
    @RequestMapping("/api/gettext")
    public String getText() {
        String finalresult = "";
        try (SpeechClient speechClient = SpeechClient.create()){

            // The path to the audio file to transcribe
            String fileName = "/Users/diesel/Documents/BigDataProj/src/main/resources/audio.flac";
      
            // Reads the audio file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);
      
            // Builds the sync recognize request    
            RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(AudioEncoding.FLAC)
                .setSampleRateHertz(16000)
                .setLanguageCode("en-US")
                .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                .setContent(audioBytes)
                .build();
      
            // Performs speech recognition on the audio file
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            
            for (SpeechRecognitionResult result : results) {
              // There can be several alternative transcripts for a given chunk of speech. Just use the
              // first (most likely) one here.
              SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
              finalresult += alternative.getTranscript();
              logger.info("Received Messasge in group - group-id: " + finalresult);
            }
          }catch(IOException e){
            finalresult =  e.getMessage();          
        }
        return finalresult;
    }
}