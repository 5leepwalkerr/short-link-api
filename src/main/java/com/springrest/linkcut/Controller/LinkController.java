package com.springrest.linkcut.Controller;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.UserLink;
import com.springrest.linkcut.models.repository.UserLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private UserLinkRepository userRepository;
    @Autowired
    private LinkService linkService;

    @GetMapping("/all")
    public ResponseEntity<?>allUsers(){
        List<UserLink> allUsers = userRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
    }
    @GetMapping("/user-{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id")Long id){
        return new ResponseEntity<>(userRepository.findById(id),HttpStatus.FOUND);
    }
    @PostMapping("/create-short-link")
    public String getShortLink(@RequestBody UserLink user){
        userRepository.save(user);
        return linkService.createCutLink(user.getLongLink());
    }
     @GetMapping("/{shortLink}")
    public ResponseEntity<?>redirect(@PathVariable("shortLink")String shortLink){

    }
}
