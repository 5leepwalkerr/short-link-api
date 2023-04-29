package com.springrest.linkcut;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

public class TestPort {

    @Value("${spring.redis.port}")
    private Integer port;
    @Test
    public void testPort(){
        int x = Integer.parseInt(null);

    }
}
