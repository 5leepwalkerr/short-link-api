package com.springrest.linkcut.service.impl;

import com.springrest.linkcut.exception.NotFoundLinkException;
import com.springrest.linkcut.service.LinkService;
import com.springrest.linkcut.models.Link;
import com.springrest.linkcut.models.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@CacheConfig(cacheNames = "linkCache")
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkRepository linkRepository;

    private static final String ALLOWED_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_+=";
    private static final char[] BASE_66 = ALLOWED_STRING.toCharArray();
    private final int ALPHABET_LENGTH = BASE_66.length;
    private static final String SITE_DOMAIN = "http://localhost:8083/link/";

    @Override
    public String createCutLink(String longLink) {
        var resultBuild = new StringBuilder();
        resultBuild.append(SITE_DOMAIN);

        String onlyDigits = "^[0-9]+$"; // only digits 0-9
        if(longLink.matches(onlyDigits)) {
            Long intLongLink = Long.parseLong(longLink);
            while (intLongLink > 0) {
                String line = String.valueOf(intLongLink % ALPHABET_LENGTH);
                if (Long.valueOf(line) <= ALPHABET_LENGTH) {
                    resultBuild.append(BASE_66[Integer.valueOf(line)]);
                    intLongLink /= ALPHABET_LENGTH;
                }
                if (resultBuild.length() - SITE_DOMAIN.length() > 9) break;
            }
        }
        //https://habr.com/ru/companies/ruvds/articles/509700/
        else{
            byte[] arrOfLongLink =  longLink.getBytes(StandardCharsets.UTF_8);
            for(int i=5;i<arrOfLongLink.length-1;i++){
               int charPosition = Character.getNumericValue(arrOfLongLink[i]);
               if(charPosition<=ALPHABET_LENGTH && charPosition>0) {
                   char codedChar = BASE_66[charPosition];
                   resultBuild.append(codedChar);
               }
               if(resultBuild.length() - SITE_DOMAIN.length() >9) break;
            }
        }
        return resultBuild.toString();
    }
    @Override
    public String getOriginalLink(String shortLink){
        if(linkRepository.existLink(shortLink)==null){
            throw new NotFoundLinkException("There is no such link or it has been deleted, try create another one!");
        }
        Link user = linkRepository.existLink(shortLink);
        if (!user.getLongLink().isEmpty()) {
            return user.getLongLink().toString();
        }
        else return "Nothing to return, no exist links";
    }
}
