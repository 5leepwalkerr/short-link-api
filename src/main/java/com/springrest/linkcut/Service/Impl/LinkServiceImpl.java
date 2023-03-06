package com.springrest.linkcut.Service.Impl;

import com.springrest.linkcut.Service.LinkService;
import com.springrest.linkcut.models.UserLink;
import com.springrest.linkcut.models.repository.UserLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private UserLinkRepository userLinkRepository;

    private static final String ALLOWED_SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final char[] BASE_62 = ALLOWED_SYMBOLS.toCharArray();
    private final int SYMBOLS_LENGTH = BASE_62.length;

    @Override
    public String createCutLink(String longLink) {
        var resultBuild = new StringBuilder();
        Pattern regexLongLink = Pattern.compile("(?:http(?:s)?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:(?:watch)?\\?(?:.*&)?v(?:i)?=|(?:embed|v|vi|user)\\/))([^\\?&\\\"'<> #].+)"); // regex for body
        Matcher matcherRegex = regexLongLink.matcher(longLink);
        
        String bodyLink = "";
        String domainLink = "";
        
        if(matcherRegex.find()) {
            bodyLink = matcherRegex.group(1);
        }
        regexLongLink = Pattern.compile("(?:http(?:s)?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:(?:watch)?\\?(?:.*&)?v(?:i)?=|(?:embed|v|vi|user)))"); // regex for domainLink 
        matcherRegex = regexLongLink.matcher(longLink);
        if(matcherRegex.find()) {
            domainLink = matcherRegex.group(0);
        }
        
        String onlyDigitsRegex = "//d+"; // only digits 0-9
        if(bodyLink.matches(onlyDigitsRegex)) { // if link contains only digits in body
            Long intLongLink = Long.parseLong(longLink);
            resultBuild.append(domainLink); // append domain link before adding body of link
            while (intLongLink > 0) { // transfer from base62 to decimal
                String line = String.valueOf(intLongLink % SYMBOLS_LENGTH);
                if (Long.valueOf(line) <= SYMBOLS_LENGTH) {
                    resultBuild.append(BASE_62[Integer.valueOf(line)]);
                    intLongLink /= SYMBOLS_LENGTH;
                }
                if (resultBuild.length() > 9) break;
            }
        }
        else{
            char[] arrOfLongLink = bodyLink.toCharArray();
            resultBuild.append(domainLink); // append domain link before adding body of link
            for(int i=0;i<arrOfLongLink.length-1;i++){
               int charPosition = Character.getNumericValue(arrOfLongLink[i]); // char position in ASCII
               if(charPosition<=SYMBOLS_LENGTH && charPosition>0) {
                   char code = BASE_62[charPosition]; //  encrypt chars by position in allowed symbols array
                   resultBuild.append(code);
               }
               if(resultBuild.length()-domainLink.length() >9) break; // length of 9 chars gives 72 million variations
            }
        }
        return resultBuild.toString();
    }
    @Override
    public String getOriginalLink(String shortLink) {
        UserLink user  = userLinkRepository.UserWithExistLink(shortLink); // takes user with input short link
        for(int i=0;i<10;i++) {
            if (user.getShortLink() != null) {
                return user.getLongLink(); // returns user if he had input short link
            } else {
                return "Nothing to return, no exists links";
            }
        }
        return null;

    }
}
