package iit.talon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iit.talon.service.GetText;



@RestController
public class SoundController {
    @Autowired
    GetText cloudService;
    @RequestMapping("/api/gettext")
    public String getText() {
        return " Transcript is " + cloudService.transcript();
    }
}