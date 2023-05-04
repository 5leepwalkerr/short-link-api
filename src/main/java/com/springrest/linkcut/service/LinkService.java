package com.springrest.linkcut.service;

public interface LinkService {
    String createCutLink(String longLink);
    String getOriginalLink(String shortLink);
}
