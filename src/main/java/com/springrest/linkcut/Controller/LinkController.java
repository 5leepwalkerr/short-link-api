package com.springrest.linkcut.Controller;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.UserLink;
import com.springrest.linkcut.models.repository.UserLinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private UserLinkRepository userLinkRepository;
    @Autowired
    private LinkService linkService;

    @GetMapping("/all")
    public ResponseEntity<?> allUsers() {
        List<UserLink> allUsers = userLinkRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
    }

    @GetMapping("/user-{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(userLinkRepository.findById(id), HttpStatus.FOUND);
    }

    @PostMapping("/create-short-link")
    public String getShortLink(@RequestBody UserLink user) {
        String shortLink = linkService.createCutLink(user.getLongLink());
        user.setShortLink(shortLink);
        userLinkRepository.save(user);
        return shortLink;
    }
    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirect(@PathVariable("shortLink") String shortLink) {
        var url = linkService.getOriginalLink(shortLink);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }



}
