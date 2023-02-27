package com.springrest.linkcut.Service.Impl;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.UserLink;
import com.springrest.linkcut.models.repository.UserLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Character.*;


@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private UserLinkRepository userLinkRepository;
    private UserLink userLink;
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final char[] base62 = allowedString.toCharArray();
    private final int alphabetLen = base62.length;
    @Override
    public String createCutLink(Long longLink){
        var resultBuild = new StringBuilder();
        while(longLink>0) {
            String line = String.valueOf(longLink % alphabetLen);
            if (Integer.valueOf(line) <= alphabetLen) {
                resultBuild.append(base62[Integer.valueOf(line)]);
                longLink /= alphabetLen;
            }
            if(resultBuild.length()>9) break;
        }
        return resultBuild.toString();
    }
    @Override
    public String getOriginalLink(String shortLink) {
        List<UserLink> users = userLinkRepository.UserWithExistLink(shortLink);
        for(UserLink u:users){
            if(!users.isEmpty()&& users.size()==1) return u.getLongLink().toString();
            else return "Nothing to return, no exist links";
        }
        return null;
    }
}
