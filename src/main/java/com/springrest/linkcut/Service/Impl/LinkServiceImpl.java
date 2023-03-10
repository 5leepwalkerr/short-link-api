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
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final char[] base62 = allowedString.toCharArray();
    private final int alphabetLen = base62.length;
    private final String mineDomain = "http://localhost:8081/link/";

    @Override
    public String createCutLink(String longLink) {
        var resultBuild = new StringBuilder();
        Pattern patternLink = Pattern.compile("(?:http(?:s)?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:(?:watch)?\\?(?:.*&)?v(?:i)?=|(?:embed|v|vi|user)\\/))([^\\?&\\\"'<> #].+)");
        Matcher matcherLink = patternLink.matcher(longLink);

        String videoCode = "";
        resultBuild.append(mineDomain);

        if(matcherLink.find()) videoCode = matcherLink.group(1);

        String regexOnlyNums = "//d+"; // only digits 0-9
        if(videoCode.matches(regexOnlyNums)) {
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
            char[] arrOfLongLink = videoCode.toCharArray();
            for(int i=0;i<arrOfLongLink.length-1;i++){
               int charPosition = Character.getNumericValue(arrOfLongLink[i]);
               if(charPosition<=alphabetLen && charPosition>0) {
                   char code = base62[charPosition];
                   resultBuild.append(code);
               }
               if(resultBuild.length()-mineDomain.length() >8)break;
            }
        }
        return resultBuild.toString();
    }
    @Override
    public String getOriginalLink(String shortLink) {
        UserLink user = userLinkRepository.UserWithExistLink(mineDomain+shortLink);
            if (!user.getLongLink().isEmpty()) return user.getLongLink().toString();
            else return "Nothing to return, no exist links";
    }
}
