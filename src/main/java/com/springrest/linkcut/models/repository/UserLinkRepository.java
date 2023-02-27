package com.springrest.linkcut.models.repository;

import com.springrest.linkcut.models.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLinkRepository extends JpaRepository<UserLink,Long> {
}
