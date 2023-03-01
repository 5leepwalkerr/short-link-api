package com.springrest.linkcut.Service;

public interface LinkService {
    String createCutLink(String longLink);
    String getOriginalLink(String shortLink);
}
