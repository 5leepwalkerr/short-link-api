package com.springrest.linkcut.controller;

import com.springrest.linkcut.exception.InvalidRequestException;
import com.springrest.linkcut.service.LinkService;
import com.springrest.linkcut.models.Link;
import com.springrest.linkcut.models.repository.LinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/link")
public class LinkController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkController.class);

    @Serial
    private static final long serialVersionUID = -7917644949275952078L;

    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private LinkService linkService;


    @GetMapping("/all")
    public ResponseEntity<?> allUsers() {
        List<Link> allUsers = linkRepository.findAll();
        LOGGER.info("Received GET request '/all'");
        return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
    }

    @GetMapping("/user/{id}")
    @Cacheable(cacheNames = "userById")
    public Optional<Link> getUserById(@PathVariable(name = "id") Long id) {
        LOGGER.info("Received GET request '/user-{}'",id);
        Optional<Link> link = linkRepository.findById(id);
       return link;
    }

    @PostMapping("/create-short-link")
    @Cacheable(cacheNames = "shortLinks", key = "#user.longLink")
    public String getShortLink(@RequestBody Link user) {
        if(user.getLongLink()==""){
            throw new InvalidRequestException("LongLink must not be null!");
        }
        String shortLink = linkService.createCutLink(user.getLongLink());
        user.setShortLink(shortLink);
        linkRepository.save(user);
        LOGGER.info("Received POST request '/create-short-user'");
        return shortLink;
    }
    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirect(@PathVariable("shortLink") String shortLink) {
        var url = linkService.getOriginalLink(shortLink);
        LOGGER.info("Received GET request from /{shortLink}");
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
