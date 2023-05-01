package com.springrest.linkcut.Controller;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.springrest.linkcut.HelperClass.CustomResponseEntity;
import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.UserLink;
import com.springrest.linkcut.models.repository.UserLinkRepository;
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
    private UserLinkRepository userLinkRepository;
    @Autowired
    private LinkService linkService;

    @GetMapping("/all")
    public CustomResponseEntity<?> allUsers() {
        List<UserLink> allUsers = userLinkRepository.findAll();
        LOGGER.info("Received GET request '/all'");
        return new CustomResponseEntity<>(allUsers, HttpStatus.FOUND);
    }

    @GetMapping("/user/{id}")
    @Cacheable(cacheNames = "userById")
    public Optional<UserLink> getUserById(@PathVariable(name = "id") Long id) {
        LOGGER.info("Received GET request '/user-{}'",id);
        Optional<UserLink> user = userLinkRepository.findById(id);
       return user;
    }

    @PostMapping("/create-short-link")
    @Cacheable(cacheNames = "shortLinks")
    public String getShortLink(@RequestBody UserLink user) {
        String shortLink = linkService.createCutLink(user.getLongLink());
        user.setShortLink(String.valueOf(shortLink));
        userLinkRepository.save(user);
        LOGGER.info("Received POST request '/create-short-link'");
        return shortLink;
    }
    @GetMapping("/{shortLink}")
    public CustomResponseEntity<?> redirect(@PathVariable("shortLink") String shortLink) {
        var url = linkService.getOriginalLink(shortLink);
        LOGGER.info("Received GET request "+ "/" +shortLink);
        return (CustomResponseEntity<?>) ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
