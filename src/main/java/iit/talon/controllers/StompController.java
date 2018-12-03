package iit.talon.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {
   @MessageMapping("/hello")
   @SendTo("/topic/greetings")
   public String greeting(String message) throws Exception {
      return "Hi " + message;
   }
}