package com.springrest.linkcut.models.repository;

import com.springrest.linkcut.models.UserLink;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLinkRepository extends JpaRepository<UserLink,Long> {
    @Transactional
    @Query("select u from UserLink u where u.short_link like :shortLink")
    List<UserLink>UserWithExistLink(String shortLink);
}
