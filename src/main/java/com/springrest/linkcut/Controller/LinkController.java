package com.springrest.linkcut.Controller;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.Link;
import com.springrest.linkcut.models.User;
import com.springrest.linkcut.models.repository.LinkRepository;
import com.springrest.linkcut.models.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private LinkService linkService;

    @GetMapping("/all")
    public ResponseEntity<?>allUsers(){
        List<User> allUsers = userRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
    }
    @GetMapping("/user-{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id")Long id){
        return new ResponseEntity<>(userRepository.findById(id),HttpStatus.FOUND);
    }
    @PostMapping("/create-short-link")
    public ResponseEntity<?>getShortLink(@RequestBody User user){

    }
    @GetMapping("/{shortLink}")
    public ResponseEntity<?>redirect(@PathVariable("shortLink")String shortLink){

    }
}
