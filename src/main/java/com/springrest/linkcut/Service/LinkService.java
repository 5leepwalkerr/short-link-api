package com.springrest.linkcut.Service;

public interface LinkService {
    String createCutLink(Long longLink);
    String getOriginalLink(String shortLink);
    boolean isContains(char[]fullLink,char[]allowedChars);
}
