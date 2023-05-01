package com.springrest.linkcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.Serializable;

@SpringBootApplication
public class LinkCutApplication implements Serializable {

    public static void main(String[] args) {
        SpringApplication.run(LinkCutApplication.class, args);
    }

}
