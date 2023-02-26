package com.springrest.linkcut.Service;

import com.springrest.linkcut.models.User;

import java.io.UnsupportedEncodingException;

public interface LinkService {
    String createCutLink(Long longLink);
    String getOriginalLink(String shortLink);
}
