package com.springrest.linkcut.Controller;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.UserLink;
import com.springrest.linkcut.models.repository.UserLinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/link")
public class LinkController{
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkController.class);
    @Autowired
    private UserLinkRepository userLinkRepository;
    @Autowired
    private LinkService linkService;

    @GetMapping("/all")
    public ResponseEntity<?> allUsers() {
        List<UserLink> allUsers = userLinkRepository.findAll();
        LOGGER.info("Received GET request '/all'");
        return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
    }

    @GetMapping("/user-{id}")
    @Cacheable(cacheNames = "links")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id) {
        LOGGER.info("Received GET request '/user-{}'",id);
        return new ResponseEntity<>(userLinkRepository.findById(id), HttpStatus.FOUND);
    }

    @PostMapping("/create-short-link")
    public ResponseEntity<?> getShortLink(@RequestBody UserLink user) {
        String shortLink = linkService.createCutLink(user.getLongLink());
        user.setShortLink(shortLink);
        userLinkRepository.save(user);
        LOGGER.info("Received POST request '/create-short-link'");
        return new ResponseEntity<>(shortLink,HttpStatus.CREATED);
    }
    @GetMapping("/{shortLink}")
    @Cacheable(cacheNames = "links")
    public ResponseEntity<?> redirect(@PathVariable("shortLink") String shortLink) {
        var url = linkService.getOriginalLink(shortLink);
        LOGGER.info("Received GET request "+ "/" +shortLink);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }



}
