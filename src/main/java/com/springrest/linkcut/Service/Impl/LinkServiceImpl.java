package com.springrest.linkcut.Service.Impl;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Service
public class LinkServiceImpl implements LinkService {
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final char[] allowedChars = allowedString.toCharArray();
    private  int base = allowedChars.length;
    @Override
    public String createCutLink(Long longLink){
        String binaryLongLink = Long.toBinaryString(longLink);
        return "";
    }

    @Override
    public String getOriginalLink(String shortLink) {
        return null;
    }
}
