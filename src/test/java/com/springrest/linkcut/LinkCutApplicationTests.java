package com.springrest.linkcut;

import com.springrest.linkcut.controller.LinkController;
import com.springrest.linkcut.service.impl.LinkServiceImpl;
import com.springrest.linkcut.models.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class LinkCutApplicationTests {

    @Autowired
    private LinkController linkController;
    @Autowired
    private LinkServiceImpl service;
    @Autowired
    private LinkRepository repository;

    @Test
    public void contextLoads() throws Exception{
        assertThat(linkController).isNotNull();
    }

    @Test
    public void serviceTest(){
        assertThat(service.createCutLink(repository.getLongLinkById(34L))).isNotNull();
    }
}
