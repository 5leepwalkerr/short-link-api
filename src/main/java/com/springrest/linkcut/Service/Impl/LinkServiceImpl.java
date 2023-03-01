package com.springrest.linkcut.Service.Impl;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.UserLink;
import com.springrest.linkcut.models.repository.UserLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private UserLinkRepository userLinkRepository;
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final char[] base62 = allowedString.toCharArray();
    private final int alphabetLen = base62.length;

    @Override
    public String createCutLink(String longLink) {
        var resultBuild = new StringBuilder();
        String regex = "//d+"; // only digits 0-9
        if(longLink.matches(regex)) {
            Long intLongLink = Long.parseLong(longLink);
            while (intLongLink > 0) {
                String line = String.valueOf(intLongLink % alphabetLen);
                if (Long.valueOf(line) <= alphabetLen) {
                    resultBuild.append(base62[Integer.valueOf(line)]);
                    intLongLink /= alphabetLen;
                }
                if (resultBuild.length() > 9) break;
            }
        }
        else{
            char[] arrOfLongLink = longLink.toCharArray();
            for(int i=0;i<arrOfLongLink.length-1;i++){
               int charPosition = Character.getNumericValue(arrOfLongLink[i]);
               if(charPosition<=alphabetLen && charPosition>0) {
                   char code = base62[charPosition];
                   resultBuild.append(code);
               }
               if(resultBuild.length()>9)break;
            }
        }
        return resultBuild.toString();
    }
    @Override
    public String getOriginalLink(String shortLink) {
        List<UserLink> users = userLinkRepository.UserWithExistLink(shortLink);
        for (UserLink u : users) {
            if (!users.isEmpty() && users.size() == 1) return u.getLongLink().toString();
            else return "Nothing to return, no exist links";
        }
        return null;
    }
}
