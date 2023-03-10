package com.springrest.linkcut.Service;

import com.springrest.linkcut.models.UserLink;

import java.util.List;
import java.util.Optional;

public interface LinkService {
    String createCutLink(String longLink);
    String getOriginalLink(String shortLink);
}
