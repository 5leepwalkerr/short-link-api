package com.springrest.linkcut.models.repository;

import com.springrest.linkcut.models.Link;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link,Long>{
    @Transactional
    @Query("select u from Link u where u.shortLink=:shortLink")
    Link UserWithExistLink(String shortLink);

    @Modifying
    @Transactional
    @Query("delete from Link p where p.link_id=:id")
    Long DeleteUserById(Long id);

    @Transactional
    @Query("select l.longLink from Link l where l.link_id=:id")
    String getLongLinkById(Long id);
}
