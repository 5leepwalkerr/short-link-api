package com.springrest.linkcut;

import com.springrest.linkcut.Controller.LinkController;
import com.springrest.linkcut.Service.Impl.LinkServiceImpl;
import com.springrest.linkcut.models.repository.LinkRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Collections;

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
