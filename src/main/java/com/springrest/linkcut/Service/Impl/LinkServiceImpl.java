package com.springrest.linkcut.Service.Impl;

import com.springrest.linkcut.Service.LinkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.*;


@Service
public class LinkServiceImpl implements LinkService {
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final char[] allowedChars = allowedString.toCharArray();
    private final int allowedCharsLen = allowedChars.length;
    @Override
    public String createCutLink(Long longLink){
        var resultBuild = new StringBuilder();
        while(longLink>0) {
            String line = String.valueOf(longLink % allowedCharsLen);
            if (Integer.valueOf(line) <= allowedCharsLen) {
                resultBuild.append(allowedChars[Integer.valueOf(line)]);
                longLink /= allowedCharsLen;
            }
            if(resultBuild.length()>9) break;
        }
        return resultBuild.toString();
    }
    @Override
    public String getOriginalLink(String shortLink) {
        /* написать функцию для возвращения короткого линка в оригинальный, улучшить код(красивее)
        написать проверки для нахождения в бд, написать ексепшены
         */
        return "";
    }
    @Override
    public boolean isContains(char[] fullLink, char[] allowedChars) {
        int count = 0;
        for(char b:fullLink) for(char a:allowedChars) if(b==a){
            count++;
        }
        return count==fullLink.length;
    }
}
