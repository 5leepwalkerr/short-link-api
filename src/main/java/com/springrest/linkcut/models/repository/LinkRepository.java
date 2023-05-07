package com.springrest.linkcut.models.repository;

import com.springrest.linkcut.models.Link;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link,Long>{
    @Transactional
    @Query("select l.shortLink from Link l where l.shortLink =:shortLink")
    Optional<String> existLink(String shortLink);

    @Modifying
    @Transactional
    @Query("delete from Link p where p.linkId=:id")
    Long DeleteUserById(Long id);

    @Query("select l.longLink from Link l where l.shortLink=:shortLink")
    Optional<String> getLongLinkByShortLink(String shortLink);

    @Transactional
    @Query("select l.linkId from Link l where l.shortLink =:shortLink")
    Long getLongLinkIdBundleShortLinkByLink(String shortLink);
}
