package com.springrest.linkcut;

import com.springrest.linkcut.controller.LinkController;
import com.springrest.linkcut.service.impl.LinkServiceImpl;
import com.springrest.linkcut.models.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

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
    public void contextLoads(){
        assertThat(linkController).isNotNull();
    }

    @Test
    public void transactionTest(){
        String testShortLink = "http://localhost:8083/link/CzBsxqsyqE";
        String testLongLink = "https://spring.io/guides/topicals/spring-boot-docker/";
        Assert.isTrue(testLongLink.equals(repository.getLongLinkByShortLink(testShortLink).get()),"Links aren't similar!");
    }
}
