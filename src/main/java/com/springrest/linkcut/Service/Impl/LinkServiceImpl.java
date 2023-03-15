package com.springrest.linkcut.Service.Impl;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.UserLink;
import com.springrest.linkcut.models.repository.UserLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private UserLinkRepository userLinkRepository;
    private static final String ALLOWED_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_+=";
    private static final char[] BASE_66 = ALLOWED_STRING.toCharArray();
    private final int ALPHABET_LENGTH = BASE_66.length;
    private final String SITE_DOMAIN = "http://localhost:8080/link/";

    @Override
    public String createCutLink(String longLink) {
        var resultBuild = new StringBuilder();
        Pattern patternLink = Pattern.compile("(?:http(?:s)?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:(?:watch)?\\?(?:.*&)?v(?:i)?=|(?:embed|v|vi|user)\\/))([^\\?&\\\"'<> #].+)"); // regex for link body
        Matcher matcherLink = patternLink.matcher(longLink);

        String linkBody = "";
        resultBuild.append(SITE_DOMAIN);

        if(matcherLink.find()) linkBody = matcherLink.group(1);

        String onlyDigits = "//d+"; // only digits 0-9
        if(linkBody.matches(onlyDigits)) {
            Long intLongLink = Long.parseLong(longLink);
            while (intLongLink > 0) {
                String line = String.valueOf(intLongLink % ALPHABET_LENGTH);
                if (Long.valueOf(line) <= ALPHABET_LENGTH) {
                    resultBuild.append(BASE_66[Integer.valueOf(line)]);
                    intLongLink /= ALPHABET_LENGTH;
                }
                if (resultBuild.length() > 9) break;
            }
        }
        else{
            char[] arrOfLongLink = linkBody.toCharArray();
            for(int i=0;i<arrOfLongLink.length-1;i++){
               int charPosition = Character.getNumericValue(arrOfLongLink[i]);
               if(charPosition<=ALPHABET_LENGTH && charPosition>0) {
                   char code = BASE_66[charPosition];
                   resultBuild.append(code);
               }
               if(resultBuild.length() - SITE_DOMAIN.length() >9)break;
            }
        }
        return resultBuild.toString();
    }
    @Override
    public String getOriginalLink(String shortLink) {
        UserLink user = userLinkRepository.UserWithExistLink(shortLink);
        if(!user.getLongLink().isEmpty()) return user.getLongLink().toString();
        else return "Nothing to return, no exist links";
    }
}
