package com.springrest.linkcut.models.repository;

import com.springrest.linkcut.models.UserLink;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLinkRepository extends JpaRepository<UserLink,Long> {
    @Transactional
    @Query("select u from UserLink u where u.shortLink like :shortLink")
    List<UserLink>UserWithExistLink(String shortLink);

    @Modifying
    @Transactional
    @Query("delete from UserLink p where p.user_id=:id")
    Integer DeleteUserById(Long id);

}
